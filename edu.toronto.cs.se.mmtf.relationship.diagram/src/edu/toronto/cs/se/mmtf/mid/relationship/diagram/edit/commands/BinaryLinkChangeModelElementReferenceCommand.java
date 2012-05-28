/*
 * Copyright (c) 2012 Marsha Chechik, Alessio Di Sandro, Michalis Famelis,
 * Rick Salay, Vivien Suen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Alessio Di Sandro - Implementation.
 */
package edu.toronto.cs.se.mmtf.mid.relationship.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;

import edu.toronto.cs.se.mmtf.mid.relationship.ModelElementReference;
import edu.toronto.cs.se.mmtf.mid.relationship.ModelRel;
import edu.toronto.cs.se.mmtf.mid.relationship.diagram.edit.policies.MidBaseItemSemanticEditPolicy;

/**
 * The command to change a model element reference of a binary mapping link.
 * 
 * @author Alessio Di Sandro
 * 
 */
public class BinaryLinkChangeModelElementReferenceCommand extends BinaryLinkReorientCommand {

	/**
	 * Constructor: initialises the superclass.
	 * 
	 * @param request
	 *            The request.
	 */
	public BinaryLinkChangeModelElementReferenceCommand(ReorientRelationshipRequest request) {

		super(request);
	}

	/**
	 * Checks if the source can be reoriented.
	 * 
	 * @return True if the source can be reoriented, false otherwise.
	 */
	protected boolean canReorientSource() {

		if (!(getOldSource() instanceof ModelElementReference && getNewSource() instanceof ModelElementReference)) {
			return false;
		}
		if (getLink().getElementRefs().size() != 2) {
			return false;
		}
		ModelElementReference target = (ModelElementReference) getLink()
				.getElementRefs().get(1);
		if (!(getLink().eContainer() instanceof ModelRel)) {
			return false;
		}
		ModelRel container = (ModelRel) getLink().eContainer();

		return MidBaseItemSemanticEditPolicy.getLinkConstraints()
				.canExistBinaryLink_4003(container, getLink(), getNewSource(),
						target);
	}

	/**
	 * Checks if the target can be reoriented.
	 * 
	 * @return True if the target can be reoriented, false otherwise.
	 */
	protected boolean canReorientTarget() {

		if (!(getOldSource() instanceof ModelElementReference && getNewSource() instanceof ModelElementReference)) {
			return false;
		}
		if (getLink().getElementRefs().size() != 2) {
			return false;
		}
		ModelElementReference source = (ModelElementReference) getLink()
				.getElementRefs().get(0);
		if (!(getLink().eContainer() instanceof ModelRel)) {
			return false;
		}
		ModelRel container = (ModelRel) getLink().eContainer();

		return MidBaseItemSemanticEditPolicy.getLinkConstraints()
				.canExistBinaryLink_4003(container, getLink(), source,
						getNewTarget());
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

		getLink().getElementRefs().set(0, getNewSource());

		return CommandResult.newOKCommandResult(getLink());
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

		getLink().getElementRefs().set(1, getNewTarget());

		return CommandResult.newOKCommandResult(getLink());
	}

}