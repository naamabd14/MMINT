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
package edu.toronto.cs.se.mmint.mid.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.FontStyle;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ui.PlatformUI;

import edu.toronto.cs.se.mmint.MMINTException;
import edu.toronto.cs.se.mmint.mid.ExtendibleElement;
import edu.toronto.cs.se.mmint.mid.ExtendibleElementEndpoint;
import edu.toronto.cs.se.mmint.mid.constraint.MultiModelConstraintChecker;
import edu.toronto.cs.se.mmint.mid.library.MultiModelUtils;

public class GMFDiagramUtils {

	private final static String EXTELEM_NULLTYPE = "NOTYPE";
	/** The suffix for GMF diagrams. */
	public static final String DIAGRAM_SUFFIX = "diag";

	public static String getElementLabel(ExtendibleElement element) {
	
		String label = (element.getName() == null) ? "" : element.getName();
		if (MultiModelConstraintChecker.isInstancesLevel(element)) {
			ExtendibleElement type = element.getMetatype();
			String typeLabel = (type == null) ? EXTELEM_NULLTYPE : type.getName();
			label += " : " + typeLabel;
		}
	
		return label;
	}

	public static String getEndpointLabel(ExtendibleElementEndpoint endpoint) {
	
		String label = getElementLabel(endpoint);
	
		int low = endpoint.getLowerBound();
		int up = endpoint.getUpperBound();
		if (low == 0 && up == 1) {
			label += " [?]";
		}
		else if (low == 1 && up == 1) {
			// default
		}
		else if (low == 0 && up == -1) {
			label += " [*]";
		}
		else if (low == 1 && up == -1) {
			label += " [+]";
		}
		else {
			String up2 = (up == -1) ? "*" : Integer.toString(up);
			label += " [" + low + "," + up2 + "]";
		}
	
		return label;
	}

	public static String editElementLabel(ExtendibleElement element) {
	
		String name = element.getName();
		if (name == null) {
			name = "";
		}
	
		return name;
	}

	public static void createGMFDiagram(String modelUri, String diagramUri, String diagramKind, String diagramPluginId, boolean isWorkspaceRelative) throws Exception {

		ResourceSet domainResourceSet = new ResourceSetImpl();
		Resource modelResource = domainResourceSet.getResource(MultiModelUtils.getEMFUri(modelUri, isWorkspaceRelative), true);
		ResourceSet diagramResourceSet = new ResourceSetImpl();
		Resource diagramResource =	diagramResourceSet.createResource(MultiModelUtils.getEMFUri(diagramUri, isWorkspaceRelative));
		EObject rootModelObj = (EObject) modelResource.getContents().get(0);
		Diagram diagram = ViewService.createDiagram(
			rootModelObj,
			diagramKind,
			new PreferencesHint(diagramPluginId)
		);
		diagramResource.getContents().add(diagram);
		Map<String, Object> saveOptions = new HashMap<String, Object>();
		saveOptions.put(XMLResource.OPTION_ENCODING, "UTF-8");
		saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED, Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
		diagramResource.save(saveOptions);
	}

	public static void openGMFDiagram(String diagramUri, String diagramId, boolean isWorkspaceRelative) throws Exception {

		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
			new URIEditorInput(MultiModelUtils.getEMFUri(diagramUri, isWorkspaceRelative)),
			diagramId
		);
	}

	public static ComposedAdapterFactory getAdapterFactory() {

		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());

		return adapterFactory;
	}

	public static void colorDiagramElement(View diagramView, int color, int fontColor) throws MMINTException {

		if (!(diagramView instanceof Shape) && !(diagramView instanceof Connector)) {
			throw new MMINTException("Diagram element not supported: " + diagramView.getClass().getName());
		}

		if (diagramView instanceof Shape) {
			Shape node = (Shape) diagramView;
			node.setFillColor(color);
			node.setLineColor(color);
			if (fontColor >= 0) {
				node.setFontColor(fontColor);
			}
		}
		else if (diagramView instanceof Connector){
			Connector line = (Connector) diagramView;
			line.setLineColor(color);
			FontStyle labelFont = (FontStyle) line.getStyles().get(0);
			if (fontColor >= 0) {
				labelFont.setFontColor(fontColor);
			}
		}
	}

	public static @NonNull IFile getModelIFile(@NonNull IFile diagramFile) {

		IFile modelFile = diagramFile.getParent().getFile(new Path(diagramFile.getName().substring(0, diagramFile.getName().length() - DIAGRAM_SUFFIX.length())));

		return modelFile;
	}

	public static List<IFile> getTransactionalCommandAffectedFiles() {

		List<IFile> files = new ArrayList<IFile>();
		IFile diagramFile = (IFile) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
		if (diagramFile != null) {
			files.add(diagramFile);
			files.add(getModelIFile(diagramFile));
		}

		return files;
	}

}
