/**
 * Copyright (c) 2012-2014 Marsha Chechik, Alessio Di Sandro, Michalis Famelis,
 * Rick Salay.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Alessio Di Sandro - Implementation.
 */
package edu.toronto.cs.se.mmint.mid.relationship.diagram.edit.commands;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;

import edu.toronto.cs.se.mmint.MMINTException;
import edu.toronto.cs.se.mmint.MMINTException.Type;
import edu.toronto.cs.se.mmint.MultiModelTypeHierarchy;
import edu.toronto.cs.se.mmint.mid.constraint.MultiModelConstraintChecker;
import edu.toronto.cs.se.mmint.mid.relationship.LinkReference;
import edu.toronto.cs.se.mmint.mid.relationship.ModelElementEndpoint;
import edu.toronto.cs.se.mmint.mid.relationship.ModelElementEndpointReference;
import edu.toronto.cs.se.mmint.mid.relationship.ModelElementReference;
import edu.toronto.cs.se.mmint.mid.relationship.diagram.edit.commands.ModelElementEndpointReferenceReorientCommand;
import edu.toronto.cs.se.mmint.mid.ui.MultiModelDiagramUtils;
import edu.toronto.cs.se.mmint.mid.ui.MultiModelDialogCancellation;

/**
 * The command to change a model element reference of a link.
 * 
 * @author Alessio Di Sandro
 * 
 */
public class LinkReferenceChangeModelElementEndpointReferenceCommand extends ModelElementEndpointReferenceReorientCommand {

	private List<String> modelElemTypeEndpointUris;

	/**
	 * Constructor: initialises the superclass.
	 * 
	 * @param request
	 *            The request.
	 */
	public LinkReferenceChangeModelElementEndpointReferenceCommand(ReorientRelationshipRequest request) {

		super(request);
		modelElemTypeEndpointUris = null;
	}

	/**
	 * Checks if a model element reference can be changed.
	 * 
	 * @return True if a model element reference can be changed, false
	 *         otherwise.
	 */
	@Override
	public boolean canExecute() {

		return
			super.canExecute() && (
				MultiModelConstraintChecker.isInstancesLevel(getLink()) ||
				!MultiModelTypeHierarchy.isRootType(getLink().getObject())
			);
	}

	/**
	 * Checks if the source link can be changed.
	 * 
	 * @return True if the source link can be changed, false otherwise.
	 */
	@Override
	protected boolean canReorientSource() {

		return
			super.canReorientSource() && (
				!MultiModelConstraintChecker.isInstancesLevel(getLink()) ||
				(modelElemTypeEndpointUris = MultiModelConstraintChecker.getAllowedModelElementEndpointReferences(getNewSource(), null, getLink().getModelElemRef())) != null
			);
	}

	/**
	 * Checks if the target model element reference can be changed.
	 * 
	 * @return True if the target model element reference can be changed, false
	 *         otherwise.
	 */
	@Override
	protected boolean canReorientTarget() {

		return
			super.canReorientTarget() && (
				!MultiModelConstraintChecker.isInstancesLevel(getLink()) ||
				(modelElemTypeEndpointUris = MultiModelConstraintChecker.getAllowedModelElementEndpointReferences((LinkReference) getLink().eContainer(), getLink(), getNewTarget())) != null
			);
	}

	protected void doExecuteInstancesLevel(LinkReference linkRef, ModelElementReference modelElemRef, boolean isFullDelete) throws MMINTException, MultiModelDialogCancellation {

		ModelElementEndpointReference modelElemTypeEndpointRef = MultiModelDiagramUtils.selectModelElementTypeEndpointToCreate(linkRef, modelElemTypeEndpointUris);
		if (isFullDelete) {
			getLink().deleteInstanceAndReference(isFullDelete);
			modelElemTypeEndpointRef.getObject().createInstanceAndReference(modelElemRef, linkRef);
		}
		else {
			modelElemTypeEndpointRef.getObject().replaceInstanceAndReference(getLink(), modelElemRef);
		}
	}

	protected void doExecuteTypesLevel(LinkReference linkTypeRef, ModelElementReference modelElemTypeRef, boolean isFullDelete) throws MMINTException {

		ModelElementEndpoint modelElemTypeEndpoint = MultiModelTypeHierarchy.getOverriddenModelElementTypeEndpoint(linkTypeRef, modelElemTypeRef);
		if (isFullDelete) {
			getLink().deleteTypeAndReference(isFullDelete);
			modelElemTypeEndpoint.createSubtypeAndReference(getLink().getObject().getName(), modelElemTypeRef, false, linkTypeRef);
		}
		else {
			modelElemTypeEndpoint.replaceSubtypeAndReference(getLink(), getLink().getObject().getName(), modelElemTypeRef);
		}
		// no need to init type hierarchy, no need for undo/redo
	}

	@Override
	protected CommandResult reorientSource() throws ExecutionException {

		try {
			if (MultiModelConstraintChecker.isInstancesLevel(getLink())) {
				doExecuteInstancesLevel(getNewSource(), getLink().getModelElemRef(), true);
			}
			else {
				doExecuteTypesLevel(getNewSource(), getLink().getModelElemRef(), true);
			}

			return CommandResult.newOKCommandResult(getLink());
		}
		catch (MultiModelDialogCancellation e) {
			return CommandResult.newCancelledCommandResult();
		}
		catch (MMINTException e) {
			MMINTException.print(Type.ERROR, "No model element endpoint changed", e);
			return CommandResult.newErrorCommandResult("No model element endpoint changed");
		}
	}

	@Override
	protected CommandResult reorientTarget() throws ExecutionException {

		try {
			if (MultiModelConstraintChecker.isInstancesLevel(getLink())) {
				doExecuteInstancesLevel((LinkReference) getLink().eContainer(), getNewTarget(), false);
			}
			else {
				doExecuteTypesLevel((LinkReference) getLink().eContainer(), getNewTarget(), false);
			}

			return CommandResult.newOKCommandResult(getLink());
		}
		catch (MultiModelDialogCancellation e) {
			return CommandResult.newCancelledCommandResult();
		}
		catch (MMINTException e) {
			MMINTException.print(Type.ERROR, "No model element endpoint changed", e);
			return CommandResult.newErrorCommandResult("No model element endpoint changed");
		}
	}

}
