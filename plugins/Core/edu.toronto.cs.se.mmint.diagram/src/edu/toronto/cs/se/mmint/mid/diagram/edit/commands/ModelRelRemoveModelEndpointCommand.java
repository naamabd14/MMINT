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
package edu.toronto.cs.se.mmint.mid.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;

import edu.toronto.cs.se.mmint.MMINTException;
import edu.toronto.cs.se.mmint.MMINTException.Type;
import edu.toronto.cs.se.mmint.MultiModelTypeHierarchy;
import edu.toronto.cs.se.mmint.mid.ModelEndpoint;
import edu.toronto.cs.se.mmint.mid.constraint.MultiModelConstraintChecker;
import edu.toronto.cs.se.mmint.mid.relationship.ModelRel;

/**
 * The command to remove a model from a model relationship.
 * 
 * @author Alessio Di Sandro
 * 
 */
public class ModelRelRemoveModelEndpointCommand extends DestroyElementCommand {

	/**
	 * Constructor: initialises the superclass.
	 * 
	 * @param request
	 *            The request.
	 */
	public ModelRelRemoveModelEndpointCommand(DestroyElementRequest request) {

		super(request);
	}

	/**
	 * Checks if a model can be removed.
	 * 
	 * @return True if a model can be removed, false otherwise.
	 */
	@Override
	public boolean canExecute() {

		return
			super.canExecute() && (
				MultiModelConstraintChecker.isInstancesLevel((ModelRel) getElementToEdit()) ||
				!MultiModelTypeHierarchy.isRootType((ModelEndpoint) getElementToDestroy())
			);
	}

	protected void doExecuteInstancesLevel() throws MMINTException {

		((ModelEndpoint) getElementToDestroy()).deleteInstanceAndReference(true);
	}

	protected void doExecuteTypesLevel() throws MMINTException {

		((ModelEndpoint) getElementToDestroy()).deleteTypeAndReference(true);
		// no need to init type hierarchy, no need for undo/redo
	}

	/**
	 * Removes a model from a model relationship.
	 * 
	 * @param monitor
	 *            The progress monitor.
	 * @param info
	 *            Additional parameter, not used.
	 * @return The ok result.
	 * @throws ExecutionException
	 *             Never thrown.
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

		try {
			if (MultiModelConstraintChecker.isInstancesLevel((ModelRel) getElementToEdit())) {
				doExecuteInstancesLevel();
			}
			else {
				doExecuteTypesLevel();
			}
	
			return super.doExecuteWithResult(monitor, info);
		}
		catch (MMINTException e) {
			MMINTException.print(Type.ERROR, "No model endpoint deleted", e);
			return CommandResult.newErrorCommandResult("No model endpoint deleted");
		}
	}

}
