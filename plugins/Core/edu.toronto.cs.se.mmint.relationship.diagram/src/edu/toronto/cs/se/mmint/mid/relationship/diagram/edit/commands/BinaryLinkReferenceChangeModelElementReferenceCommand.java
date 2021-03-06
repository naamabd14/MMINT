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
import edu.toronto.cs.se.mmint.mid.relationship.BinaryLinkReference;
import edu.toronto.cs.se.mmint.mid.relationship.ModelElementEndpoint;
import edu.toronto.cs.se.mmint.mid.relationship.ModelElementEndpointReference;
import edu.toronto.cs.se.mmint.mid.relationship.ModelElementReference;
import edu.toronto.cs.se.mmint.mid.relationship.diagram.edit.commands.BinaryLinkReferenceReorientCommand;
import edu.toronto.cs.se.mmint.mid.ui.MultiModelDiagramUtils;
import edu.toronto.cs.se.mmint.mid.ui.MultiModelDialogCancellation;

/**
 * The command to change a model element reference of a binary mapping link.
 * 
 * @author Alessio Di Sandro
 * 
 */
public class BinaryLinkReferenceChangeModelElementReferenceCommand extends BinaryLinkReferenceReorientCommand {

	private List<String> modelElemTypeEndpointUris;

	/**
	 * Constructor: initialises the superclass.
	 * 
	 * @param request
	 *            The request.
	 */
	public BinaryLinkReferenceChangeModelElementReferenceCommand(ReorientRelationshipRequest request) {

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
	 * Checks if the source model element reference can be reoriented.
	 * 
	 * @return True if the source model element reference can be reoriented,
	 *         false otherwise.
	 */
	@Override
	protected boolean canReorientSource() {

		boolean instance = MultiModelConstraintChecker.isInstancesLevel(getLink());

		return
			super.canReorientSource() && ((
				instance &&
				(modelElemTypeEndpointUris = MultiModelConstraintChecker.getAllowedModelElementEndpointReferences(getLink(), getLink().getModelElemEndpointRefs().get(0), getNewSource())) != null
			) || (
				!instance &&
				MultiModelConstraintChecker.isAllowedModelElementTypeEndpointReference(getLink(), getNewSource(), null)
			));
	}

	/**
	 * Checks if the target model element reference can be reoriented.
	 * 
	 * @return True if the target model element reference can be reoriented,
	 *         false otherwise.
	 */
	@Override
	protected boolean canReorientTarget() {

		boolean instance = MultiModelConstraintChecker.isInstancesLevel(getLink());

		return
			super.canReorientTarget() && ((
				instance &&
				(modelElemTypeEndpointUris = MultiModelConstraintChecker.getAllowedModelElementEndpointReferences(getLink(), getLink().getModelElemEndpointRefs().get(1), getNewTarget())) != null
			) || (
				!instance &&
				MultiModelConstraintChecker.isAllowedModelElementTypeEndpointReference(getLink(), null, getNewTarget())
			));
	}

	protected void doExecuteInstancesLevel(BinaryLinkReference containerLinkRef, ModelElementReference targetModelElemRef, boolean isBinarySrc) throws MMINTException, MultiModelDialogCancellation {

		ModelElementEndpointReference oldModelElemEndpointRef = (isBinarySrc) ?
			containerLinkRef.getModelElemEndpointRefs().get(0) :
			containerLinkRef.getModelElemEndpointRefs().get(1);
		ModelElementEndpointReference modelElemTypeEndpointRef = MultiModelDiagramUtils.selectModelElementTypeEndpointToCreate(containerLinkRef, modelElemTypeEndpointUris);
		modelElemTypeEndpointRef.getObject().replaceInstanceAndReference(oldModelElemEndpointRef, targetModelElemRef);
	}

	protected void doExecuteTypesLevel(BinaryLinkReference containerLinkTypeRef, ModelElementReference targetModelElemTypeRef, boolean isBinarySrc) throws MMINTException, MultiModelDialogCancellation {

		boolean wasOverriding = false;
		ModelElementEndpointReference oldModelElemTypeEndpointRef = null;
		if (containerLinkTypeRef.getModelElemEndpointRefs().size() == 2) {
			oldModelElemTypeEndpointRef = (isBinarySrc) ?
				containerLinkTypeRef.getModelElemEndpointRefs().get(0) :
				containerLinkTypeRef.getModelElemEndpointRefs().get(1);
			wasOverriding = true;
		}
		else if (containerLinkTypeRef.getModelElemEndpointRefs().size() == 1) {
			ModelElementEndpointReference singleModelElemTypeEndpointRef = containerLinkTypeRef.getModelElemEndpointRefs().get(0);
			wasOverriding = (isBinarySrc) ?
				(containerLinkTypeRef.getSourceModelElemRef() == singleModelElemTypeEndpointRef.getModelElemRef()) :
				(containerLinkTypeRef.getTargetModelElemRef() == singleModelElemTypeEndpointRef.getModelElemRef());
			if (wasOverriding) {
				oldModelElemTypeEndpointRef = singleModelElemTypeEndpointRef;
			}
		}

		ModelElementEndpoint modelElemTypeEndpoint = MultiModelTypeHierarchy.getOverriddenModelElementTypeEndpoint(containerLinkTypeRef, targetModelElemTypeRef);
		if (modelElemTypeEndpoint == null) {
			if (wasOverriding) { // was overriding, becomes non-overriding
				oldModelElemTypeEndpointRef.deleteTypeAndReference(true);
			}
			// was overriding, becomes non-overriding
			// was non-overriding, remains non-overriding
			containerLinkTypeRef.addModelElementTypeReference(targetModelElemTypeRef, isBinarySrc);
		}
		else {
			if (wasOverriding) { // was overriding, remains overriding
				modelElemTypeEndpoint.replaceSubtypeAndReference(oldModelElemTypeEndpointRef, oldModelElemTypeEndpointRef.getObject().getName(), targetModelElemTypeRef);
			}
			else { // was non-overriding, becomes overriding
				String detail = (isBinarySrc) ? "source" : "target";
				String newModelElemTypeEndpointName = MultiModelDiagramUtils.getStringInput("Create new " + detail + " model element type endpoint", "Insert new " + detail + " model element type endpoint role", targetModelElemTypeRef.getObject().getName());
				if (isBinarySrc && containerLinkTypeRef.getModelElemEndpointRefs().size() == 1) { // guarantee that src endpoint comes before tgt endpoint
					ModelElementEndpointReference tgtModelElemTypeEndpointRef = containerLinkTypeRef.getModelElemEndpointRefs().get(0);
					tgtModelElemTypeEndpointRef.deleteTypeAndReference(true);
					modelElemTypeEndpoint.createSubtypeAndReference(newModelElemTypeEndpointName, targetModelElemTypeRef, true, containerLinkTypeRef);
					tgtModelElemTypeEndpointRef.getObject().getSupertype().createSubtypeAndReference(tgtModelElemTypeEndpointRef.getObject().getName(), tgtModelElemTypeEndpointRef.getModelElemRef(), false, containerLinkTypeRef);
				}
				else {
					modelElemTypeEndpoint.createSubtypeAndReference(newModelElemTypeEndpointName, targetModelElemTypeRef, isBinarySrc, containerLinkTypeRef);
				}
			}
		}
		// no need to init type hierarchy, no need for undo/redo
	}

	/**
	 * Changes the source model element reference of a binary link.
	 * 
	 * @return The ok result.
	 * @throws ExecutionException
	 *             Never thrown.
	 */
	@Override
	protected CommandResult reorientSource() throws ExecutionException {

		try {
			if (MultiModelConstraintChecker.isInstancesLevel(getLink())) {
				doExecuteInstancesLevel(getLink(), getNewSource(), true);
			}
			else {
				doExecuteTypesLevel(getLink(), getNewSource(), true);
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

	/**
	 * Changes the target model element reference of a binary link.
	 * 
	 * @return The ok result.
	 * @throws ExecutionException
	 *             Never thrown.
	 */
	@Override
	protected CommandResult reorientTarget() throws ExecutionException {

		try {
			if (MultiModelConstraintChecker.isInstancesLevel(getLink())) {
				doExecuteInstancesLevel(getLink(), getNewTarget(), false);
			}
			else {
				doExecuteTypesLevel(getLink(), getNewTarget(), false);
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
