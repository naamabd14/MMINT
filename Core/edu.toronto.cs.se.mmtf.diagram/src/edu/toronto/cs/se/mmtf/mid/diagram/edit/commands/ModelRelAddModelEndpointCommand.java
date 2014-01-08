/**
 * Copyright (c) 2013 Marsha Chechik, Alessio Di Sandro, Michalis Famelis,
 * Rick Salay.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Alessio Di Sandro - Implementation.
 */
package edu.toronto.cs.se.mmtf.mid.diagram.edit.commands;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;

import edu.toronto.cs.se.mmtf.MMTFException;
import edu.toronto.cs.se.mmtf.MultiModelTypeHierarchy;
import edu.toronto.cs.se.mmtf.mid.Model;
import edu.toronto.cs.se.mmtf.mid.ModelEndpoint;
import edu.toronto.cs.se.mmtf.mid.constraint.MultiModelConstraintChecker;
import edu.toronto.cs.se.mmtf.mid.diagram.library.MidDiagramUtils;
import edu.toronto.cs.se.mmtf.mid.relationship.ModelEndpointReference;

/**
 * The command to add a model to a model relationship.
 * 
 * @author Alessio Di Sandro
 * 
 */
public class ModelRelAddModelEndpointCommand extends ModelEndpointCreateCommand {

	private List<String> modelTypeEndpointUris;

	/**
	 * Constructor: initialises the superclass.
	 * 
	 * @param request
	 *            The request.
	 * @param source
	 *            The source model relationship.
	 * @param target
	 *            The target model.
	 */
	public ModelRelAddModelEndpointCommand(CreateRelationshipRequest request, EObject source, EObject target) {

		super(request, source, target);
		modelTypeEndpointUris = null;
	}

	/**
	 * Checks if a model can be added.
	 * 
	 * @return True if a model can be added, false otherwise.
	 */
	@Override
	public boolean canExecute() {

		boolean instance = MultiModelConstraintChecker.isInstancesLevel(getSource());

		return
			super.canExecute() && ((
				instance &&
				(modelTypeEndpointUris = MultiModelConstraintChecker.getAllowedModelEndpoints(getSource(), (Model) getTarget())) != null
			) || (
				!instance &&
				!MultiModelTypeHierarchy.isRootType(getSource()) &&
				(getTarget() == null || !MultiModelTypeHierarchy.isRootType(getTarget()))
			));
	}

	protected ModelEndpoint doExecuteInstancesLevel() throws MMTFException {

		ModelEndpointReference modelTypeEndpointRef = MidDiagramUtils.selectModelTypeEndpointToCreate(getSource(), modelTypeEndpointUris, "");
		ModelEndpointReference newModelEndpointRef = modelTypeEndpointRef.getObject().createInstanceAndReference((Model) getTarget(), false, getSource());

		return newModelEndpointRef.getObject();
	}

	protected ModelEndpoint doExecuteTypesLevel() throws MMTFException {

		String newModelTypeEndpointName = MidDiagramUtils.getStringInput("Create new light model type endpoint", "Insert new model type endpoint role", null);
		ModelEndpoint modelTypeEndpoint = MultiModelTypeHierarchy.getOverriddenModelTypeEndpoint(getSource(), (Model) getTarget());
		ModelEndpointReference modelTypeEndpointRef = MultiModelTypeHierarchy.getReference(modelTypeEndpoint.getUri(), getSource().getModelEndpointRefs());
		ModelEndpointReference newModelTypeEndpointRef = modelTypeEndpoint.createSubtypeAndReference(modelTypeEndpointRef, newModelTypeEndpointName, (Model) getTarget(), false, getSource());
		// no need to init type hierarchy, no need for undo/redo

		return newModelTypeEndpointRef.getObject();
	}

	/**
	 * Adds a model to a model relationship.
	 * 
	 * @param monitor
	 *            The progress monitor.
	 * @param info
	 *            Additional parameter, not used.
	 * @return The ok result.
	 * @throws ExecutionException
	 *             If this command can't be executed.
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

		try {
			if (!canExecute()) {
				throw new ExecutionException("Invalid arguments in create link command");
			}
			ModelEndpoint newElement = (MultiModelConstraintChecker.isInstancesLevel(getSource())) ?
				doExecuteInstancesLevel() :
				doExecuteTypesLevel();
			doConfigure(newElement, monitor, info);
			((CreateElementRequest) getRequest()).setNewElement(newElement);

			return CommandResult.newOKCommandResult(newElement);
		}
		catch (ExecutionException ee) {
			throw ee;
		}
		catch (MMTFException e) {
			MMTFException.print(MMTFException.Type.WARNING, "No model endpoint created", e);
			return CommandResult.newErrorCommandResult("No model endpoint created");
		}
	}

}