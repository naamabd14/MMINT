/*
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
package edu.toronto.cs.se.mmint.mid.diagram.navigator;

import org.eclipse.jface.viewers.ViewerSorter;

import edu.toronto.cs.se.mmint.mid.diagram.part.MidVisualIDRegistry;

/**
 * @generated
 */
public class MidNavigatorSorter extends ViewerSorter {

	/**
	 * @generated
	 */
	private static final int GROUP_CATEGORY = 4019;

	/**
	 * @generated
	 */
	private static final int SHORTCUTS_CATEGORY = 4018;

	/**
	 * @generated
	 */
	public int category(Object element) {
		if (element instanceof MidNavigatorItem) {
			MidNavigatorItem item = (MidNavigatorItem) element;
			if (item.getView().getEAnnotation("Shortcut") != null) { //$NON-NLS-1$
				return SHORTCUTS_CATEGORY;
			}
			return MidVisualIDRegistry.getVisualID(item.getView());
		}
		return GROUP_CATEGORY;
	}

}