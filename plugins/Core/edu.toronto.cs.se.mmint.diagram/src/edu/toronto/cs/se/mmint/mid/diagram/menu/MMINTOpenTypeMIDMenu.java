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
package edu.toronto.cs.se.mmint.mid.diagram.menu;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IPath;

import edu.toronto.cs.se.mmint.MMINT;
import edu.toronto.cs.se.mmint.MMINTActivator;
import edu.toronto.cs.se.mmint.MMINTException;
import edu.toronto.cs.se.mmint.MMINTException.Type;
import edu.toronto.cs.se.mmint.mid.diagram.edit.parts.MultiModelEditPart;
import edu.toronto.cs.se.mmint.mid.diagram.part.MIDDiagramEditor;
import edu.toronto.cs.se.mmint.mid.diagram.part.MIDDiagramEditorPlugin;
import edu.toronto.cs.se.mmint.mid.ui.GMFDiagramUtils;

public class MMINTOpenTypeMIDMenu extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		String pluginPath = MMINTActivator.getDefault().getStateLocation().toOSString();
		String midModelUri = pluginPath + IPath.SEPARATOR + MMINT.TYPEMID_FILENAME;
		String midDiagramUri = midModelUri + GMFDiagramUtils.DIAGRAM_SUFFIX;
		File middiag = new File(midDiagramUri);
		if (!middiag.exists()) {
			createTypeMIDDiagram();
		}
		try {
			GMFDiagramUtils.openGMFDiagram(midDiagramUri, MIDDiagramEditor.ID, false);
		}
		catch (Exception e) {
			MMINTException.print(Type.ERROR, "Error opening Type MID diagram", e);
		}

		return null;
	}

	public static void createTypeMIDDiagram() {

		String pluginPath = MMINTActivator.getDefault().getStateLocation().toOSString();
		String midModelUri = pluginPath + IPath.SEPARATOR + MMINT.TYPEMID_FILENAME;
		String midDiagramUri = midModelUri + GMFDiagramUtils.DIAGRAM_SUFFIX;
		try {
			GMFDiagramUtils.createGMFDiagram(midModelUri, midDiagramUri, MultiModelEditPart.MODEL_ID, MIDDiagramEditorPlugin.ID, false);
		}
		catch (Exception e) {
			MMINTException.print(Type.ERROR, "Error creating Type MID diagram", e);
		}
	}

}
