/*
 * Copyright (c) 2012 Marsha Chechik, Alessio Di Sandro, Michalis Famelis,
 * Rick Salay, Vivien Suen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Alessio Di Sandro, Vivien Suen - Implementation.
 */
package edu.toronto.cs.se.modelepedia.istar_mavo.diagram.providers;

/**
 * @generated
 */
public class ElementInitializers {

	protected ElementInitializers() {
		// use #getInstance to access cached instance
	}

	/**
	 * @generated
	 */
	public static ElementInitializers getInstance() {
		ElementInitializers cached = edu.toronto.cs.se.modelepedia.istar_mavo.diagram.part.IStar_MAVODiagramEditorPlugin
				.getInstance().getElementInitializers();
		if (cached == null) {
			edu.toronto.cs.se.modelepedia.istar_mavo.diagram.part.IStar_MAVODiagramEditorPlugin
					.getInstance().setElementInitializers(
							cached = new ElementInitializers());
		}
		return cached;
	}
}