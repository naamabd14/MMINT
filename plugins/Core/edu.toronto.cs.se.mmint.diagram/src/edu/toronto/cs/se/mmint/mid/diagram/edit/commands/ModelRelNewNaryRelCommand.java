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
import org.eclipse.core.runtime.IStatus;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

import edu.toronto.cs.se.mmint.MMINT;
import edu.toronto.cs.se.mmint.MMINTException;
import edu.toronto.cs.se.mmint.MMINTException.Type;
import edu.toronto.cs.se.mmint.mid.ModelOrigin;
import edu.toronto.cs.se.mmint.mid.MultiModel;
import edu.toronto.cs.se.mmint.mid.constraint.MultiModelConstraintChecker;
import edu.toronto.cs.se.mmint.mid.diagram.edit.commands.ModelRelCreateCommand;
import edu.toronto.cs.se.mmint.mid.relationship.ModelRel;
import edu.toronto.cs.se.mmint.mid.ui.MultiModelDiagramUtils;
import edu.toronto.cs.se.mmint.mid.ui.MultiModelDialogCancellation;

/**
 * The command to create a model relationship.
 * 
 * @author Alessio Di Sandro
 * 
 */
public class ModelRelNewNaryRelCommand extends ModelRelCreateCommand {

	/**
	 * Constructor: initialises the superclass.
	 * 
	 * @param req
	 *            The request.
	 */
	public ModelRelNewNaryRelCommand(CreateElementRequest req) {

		super(req);
	}
	
    /**
     * Overrides superclass to re-initialize diagram type hierarchy.
     */
	protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

		IStatus status = super.doUndo(monitor, info);
		MultiModel multiModel = (MultiModel) getElementToEdit();
		if (!MultiModelConstraintChecker.isInstancesLevel(multiModel)) {
			MMINT.createTypeHierarchy(multiModel);
		}

		return status;
    }

    /**
     * Overrides superclass to re-initialize diagram type hierarchy.
     */
	protected IStatus doRedo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

		IStatus status = super.doRedo(monitor, info);
		MultiModel multiModel = (MultiModel) getElementToEdit();
		if (!MultiModelConstraintChecker.isInstancesLevel(multiModel)) {
			MMINT.createTypeHierarchy(multiModel);
		}

		return status;
    }

	/**
	 * Checks if a model relationship can be created.
	 * 
	 * @return True if a model relationship can be created, false otherwise.
	 */
	@Override
	public boolean canExecute() {

		return super.canExecute();
	}

	protected ModelRel doExecuteInstancesLevel() throws MMINTException, MultiModelDialogCancellation {

		MultiModel multiModel = (MultiModel) getElementToEdit();
		ModelRel modelRelType = MultiModelDiagramUtils.selectModelRelTypeToCreate(null, null);
		ModelRel newModelRel = modelRelType.createInstance(null, false, ModelOrigin.CREATED, multiModel);

		return newModelRel;
	}

	protected ModelRel doExecuteTypesLevel() throws MMINTException, MultiModelDialogCancellation {

		MultiModel multiModel = (MultiModel) getElementToEdit();
		ModelRel modelRelType = MultiModelDiagramUtils.selectModelRelTypeToExtend(multiModel, null, null);
		String newModelRelTypeName = MultiModelDiagramUtils.getStringInput("Create new light model relationship type", "Insert new model relationship type name", null);
		String[] constraint = MultiModelDiagramUtils.getConstraintInput("Create new light model relationship type", null);
		ModelRel newModelRelType = modelRelType.createSubtype(newModelRelTypeName, false, constraint[0], constraint[1]);
		MMINT.createTypeHierarchy(multiModel);

		return newModelRelType;
	}

	/**
	 * Creates a new model relationship.
	 * 
	 * @param monitor
	 *            The progress monitor.
	 * @param info
	 *            Additional parameter, not used.
	 * @return The ok result.
	 * @throws ExecutionException
	 *             If configuration command execution goes wrong.
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

		try {
			ModelRel newElement = (MultiModelConstraintChecker.isInstancesLevel((MultiModel) getElementToEdit())) ?
				doExecuteInstancesLevel() :
				doExecuteTypesLevel();
			doConfigure(newElement, monitor, info);
			((CreateElementRequest) getRequest()).setNewElement(newElement);
	
			return CommandResult.newOKCommandResult(newElement);
		}
		catch (ExecutionException ee) {
			throw ee;
		}
		catch (MultiModelDialogCancellation e) {
			return CommandResult.newCancelledCommandResult();
		}
		catch (MMINTException e) {
			MMINTException.print(Type.ERROR, "No model relationship created", e);
			return CommandResult.newErrorCommandResult("No model relationship created");
		}
	}

}
