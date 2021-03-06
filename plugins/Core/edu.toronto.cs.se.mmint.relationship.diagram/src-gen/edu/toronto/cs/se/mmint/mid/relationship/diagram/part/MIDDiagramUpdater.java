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
package edu.toronto.cs.se.mmint.mid.relationship.diagram.part;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.tooling.runtime.update.DiagramUpdater;

import edu.toronto.cs.se.mmint.mid.relationship.BinaryLinkReference;
import edu.toronto.cs.se.mmint.mid.relationship.ExtendibleElementReference;
import edu.toronto.cs.se.mmint.mid.relationship.LinkReference;
import edu.toronto.cs.se.mmint.mid.relationship.ModelElementEndpointReference;
import edu.toronto.cs.se.mmint.mid.relationship.ModelElementReference;
import edu.toronto.cs.se.mmint.mid.relationship.ModelEndpointReference;
import edu.toronto.cs.se.mmint.mid.relationship.ModelRel;
import edu.toronto.cs.se.mmint.mid.relationship.RelationshipPackage;
import edu.toronto.cs.se.mmint.mid.relationship.diagram.edit.parts.BinaryLinkReference2EditPart;
import edu.toronto.cs.se.mmint.mid.relationship.diagram.edit.parts.BinaryLinkReferenceEditPart;
import edu.toronto.cs.se.mmint.mid.relationship.diagram.edit.parts.ExtendibleElementReferenceSupertypeRefEditPart;
import edu.toronto.cs.se.mmint.mid.relationship.diagram.edit.parts.LinkReference2EditPart;
import edu.toronto.cs.se.mmint.mid.relationship.diagram.edit.parts.LinkReferenceEditPart;
import edu.toronto.cs.se.mmint.mid.relationship.diagram.edit.parts.ModelElementEndpointReference2EditPart;
import edu.toronto.cs.se.mmint.mid.relationship.diagram.edit.parts.ModelElementEndpointReferenceEditPart;
import edu.toronto.cs.se.mmint.mid.relationship.diagram.edit.parts.ModelElementReference2EditPart;
import edu.toronto.cs.se.mmint.mid.relationship.diagram.edit.parts.ModelElementReference3EditPart;
import edu.toronto.cs.se.mmint.mid.relationship.diagram.edit.parts.ModelElementReferenceEditPart;
import edu.toronto.cs.se.mmint.mid.relationship.diagram.edit.parts.ModelEndpointReference2EditPart;
import edu.toronto.cs.se.mmint.mid.relationship.diagram.edit.parts.ModelEndpointReferenceEditPart;
import edu.toronto.cs.se.mmint.mid.relationship.diagram.edit.parts.ModelEndpointReferenceModelEndpointReferenceCompartmentEditPart;
import edu.toronto.cs.se.mmint.mid.relationship.diagram.edit.parts.ModelEndpointReferenceModelEndpointReferenceSuperCompartmentEditPart;
import edu.toronto.cs.se.mmint.mid.relationship.diagram.edit.parts.ModelRelEditPart;
import edu.toronto.cs.se.mmint.mid.relationship.diagram.providers.MIDElementTypes;

/**
 * @generated
 */
public class MIDDiagramUpdater {

	/**
	 * @generated
	 */
	public static boolean isShortcutOrphaned(View view) {
		return !view.isSetElement() || view.getElement() == null
				|| view.getElement().eIsProxy();
	}

	/**
	 * @generated
	 */
	public static List<MIDNodeDescriptor> getSemanticChildren(View view) {
		switch (MIDVisualIDRegistry.getVisualID(view)) {
		case ModelRelEditPart.VISUAL_ID:
			return getModelRel_1000SemanticChildren(view);
		case ModelEndpointReferenceModelEndpointReferenceCompartmentEditPart.VISUAL_ID:
			return getModelEndpointReferenceModelEndpointReferenceCompartment_7004SemanticChildren(view);
		case ModelEndpointReferenceModelEndpointReferenceSuperCompartmentEditPart.VISUAL_ID:
			return getModelEndpointReferenceModelEndpointReferenceSuperCompartment_7005SemanticChildren(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<MIDNodeDescriptor> getModelRel_1000SemanticChildren(
			View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		ModelRel modelElement = (ModelRel) view.getElement();
		LinkedList<MIDNodeDescriptor> result = new LinkedList<MIDNodeDescriptor>();
		for (Iterator<?> it = modelElement.getModelEndpointRefs().iterator(); it
				.hasNext();) {
			ModelEndpointReference childElement = (ModelEndpointReference) it
					.next();
			int visualID = MIDVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == ModelEndpointReferenceEditPart.VISUAL_ID) {
				result.add(new MIDNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == ModelEndpointReference2EditPart.VISUAL_ID) {
				result.add(new MIDNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getLinkRefs().iterator(); it
				.hasNext();) {
			LinkReference childElement = (LinkReference) it.next();
			int visualID = MIDVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == LinkReferenceEditPart.VISUAL_ID) {
				result.add(new MIDNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == LinkReference2EditPart.VISUAL_ID) {
				result.add(new MIDNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDNodeDescriptor> getModelEndpointReferenceModelEndpointReferenceCompartment_7004SemanticChildren(
			View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		ModelEndpointReference modelElement = (ModelEndpointReference) containerView
				.getElement();
		LinkedList<MIDNodeDescriptor> result = new LinkedList<MIDNodeDescriptor>();
		for (Iterator<?> it = modelElement.getModelElemRefs().iterator(); it
				.hasNext();) {
			ModelElementReference childElement = (ModelElementReference) it
					.next();
			int visualID = MIDVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == ModelElementReferenceEditPart.VISUAL_ID) {
				result.add(new MIDNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDNodeDescriptor> getModelEndpointReferenceModelEndpointReferenceSuperCompartment_7005SemanticChildren(
			View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		ModelEndpointReference modelElement = (ModelEndpointReference) containerView
				.getElement();
		LinkedList<MIDNodeDescriptor> result = new LinkedList<MIDNodeDescriptor>();
		for (Iterator<?> it = modelElement.getModelElemRefs().iterator(); it
				.hasNext();) {
			ModelElementReference childElement = (ModelElementReference) it
					.next();
			int visualID = MIDVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == ModelElementReference2EditPart.VISUAL_ID) {
				result.add(new MIDNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == ModelElementReference3EditPart.VISUAL_ID) {
				result.add(new MIDNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getContainedLinks(View view) {
		switch (MIDVisualIDRegistry.getVisualID(view)) {
		case ModelRelEditPart.VISUAL_ID:
			return getModelRel_1000ContainedLinks(view);
		case ModelEndpointReferenceEditPart.VISUAL_ID:
			return getModelEndpointReference_2007ContainedLinks(view);
		case LinkReferenceEditPart.VISUAL_ID:
			return getLinkReference_2008ContainedLinks(view);
		case LinkReference2EditPart.VISUAL_ID:
			return getLinkReference_2009ContainedLinks(view);
		case ModelEndpointReference2EditPart.VISUAL_ID:
			return getModelEndpointReference_2010ContainedLinks(view);
		case ModelElementReferenceEditPart.VISUAL_ID:
			return getModelElementReference_3005ContainedLinks(view);
		case ModelElementReference2EditPart.VISUAL_ID:
			return getModelElementReference_3006ContainedLinks(view);
		case ModelElementReference3EditPart.VISUAL_ID:
			return getModelElementReference_3007ContainedLinks(view);
		case ModelElementEndpointReferenceEditPart.VISUAL_ID:
			return getModelElementEndpointReference_4011ContainedLinks(view);
		case BinaryLinkReferenceEditPart.VISUAL_ID:
			return getBinaryLinkReference_4012ContainedLinks(view);
		case BinaryLinkReference2EditPart.VISUAL_ID:
			return getBinaryLinkReference_4013ContainedLinks(view);
		case ModelElementEndpointReference2EditPart.VISUAL_ID:
			return getModelElementEndpointReference_4014ContainedLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getIncomingLinks(View view) {
		switch (MIDVisualIDRegistry.getVisualID(view)) {
		case ModelEndpointReferenceEditPart.VISUAL_ID:
			return getModelEndpointReference_2007IncomingLinks(view);
		case LinkReferenceEditPart.VISUAL_ID:
			return getLinkReference_2008IncomingLinks(view);
		case LinkReference2EditPart.VISUAL_ID:
			return getLinkReference_2009IncomingLinks(view);
		case ModelEndpointReference2EditPart.VISUAL_ID:
			return getModelEndpointReference_2010IncomingLinks(view);
		case ModelElementReferenceEditPart.VISUAL_ID:
			return getModelElementReference_3005IncomingLinks(view);
		case ModelElementReference2EditPart.VISUAL_ID:
			return getModelElementReference_3006IncomingLinks(view);
		case ModelElementReference3EditPart.VISUAL_ID:
			return getModelElementReference_3007IncomingLinks(view);
		case ModelElementEndpointReferenceEditPart.VISUAL_ID:
			return getModelElementEndpointReference_4011IncomingLinks(view);
		case BinaryLinkReferenceEditPart.VISUAL_ID:
			return getBinaryLinkReference_4012IncomingLinks(view);
		case BinaryLinkReference2EditPart.VISUAL_ID:
			return getBinaryLinkReference_4013IncomingLinks(view);
		case ModelElementEndpointReference2EditPart.VISUAL_ID:
			return getModelElementEndpointReference_4014IncomingLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getOutgoingLinks(View view) {
		switch (MIDVisualIDRegistry.getVisualID(view)) {
		case ModelEndpointReferenceEditPart.VISUAL_ID:
			return getModelEndpointReference_2007OutgoingLinks(view);
		case LinkReferenceEditPart.VISUAL_ID:
			return getLinkReference_2008OutgoingLinks(view);
		case LinkReference2EditPart.VISUAL_ID:
			return getLinkReference_2009OutgoingLinks(view);
		case ModelEndpointReference2EditPart.VISUAL_ID:
			return getModelEndpointReference_2010OutgoingLinks(view);
		case ModelElementReferenceEditPart.VISUAL_ID:
			return getModelElementReference_3005OutgoingLinks(view);
		case ModelElementReference2EditPart.VISUAL_ID:
			return getModelElementReference_3006OutgoingLinks(view);
		case ModelElementReference3EditPart.VISUAL_ID:
			return getModelElementReference_3007OutgoingLinks(view);
		case ModelElementEndpointReferenceEditPart.VISUAL_ID:
			return getModelElementEndpointReference_4011OutgoingLinks(view);
		case BinaryLinkReferenceEditPart.VISUAL_ID:
			return getBinaryLinkReference_4012OutgoingLinks(view);
		case BinaryLinkReference2EditPart.VISUAL_ID:
			return getBinaryLinkReference_4013OutgoingLinks(view);
		case ModelElementEndpointReference2EditPart.VISUAL_ID:
			return getModelElementEndpointReference_4014OutgoingLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getModelRel_1000ContainedLinks(
			View view) {
		ModelRel modelElement = (ModelRel) view.getElement();
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getContainedTypeModelFacetLinks_BinaryLinkReference_4012(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_BinaryLinkReference_4013(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getModelEndpointReference_2007ContainedLinks(
			View view) {
		ModelEndpointReference modelElement = (ModelEndpointReference) view
				.getElement();
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getLinkReference_2008ContainedLinks(
			View view) {
		LinkReference modelElement = (LinkReference) view.getElement();
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ModelElementEndpointReference_4011(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ModelElementEndpointReference_4014(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getLinkReference_2009ContainedLinks(
			View view) {
		LinkReference modelElement = (LinkReference) view.getElement();
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ModelElementEndpointReference_4011(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ModelElementEndpointReference_4014(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getModelEndpointReference_2010ContainedLinks(
			View view) {
		ModelEndpointReference modelElement = (ModelEndpointReference) view
				.getElement();
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getModelElementReference_3005ContainedLinks(
			View view) {
		ModelElementReference modelElement = (ModelElementReference) view
				.getElement();
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getModelElementReference_3006ContainedLinks(
			View view) {
		ModelElementReference modelElement = (ModelElementReference) view
				.getElement();
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getModelElementReference_3007ContainedLinks(
			View view) {
		ModelElementReference modelElement = (ModelElementReference) view
				.getElement();
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getModelElementEndpointReference_4011ContainedLinks(
			View view) {
		ModelElementEndpointReference modelElement = (ModelElementEndpointReference) view
				.getElement();
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getBinaryLinkReference_4012ContainedLinks(
			View view) {
		BinaryLinkReference modelElement = (BinaryLinkReference) view
				.getElement();
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ModelElementEndpointReference_4011(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ModelElementEndpointReference_4014(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getBinaryLinkReference_4013ContainedLinks(
			View view) {
		BinaryLinkReference modelElement = (BinaryLinkReference) view
				.getElement();
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ModelElementEndpointReference_4011(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ModelElementEndpointReference_4014(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getModelElementEndpointReference_4014ContainedLinks(
			View view) {
		ModelElementEndpointReference modelElement = (ModelElementEndpointReference) view
				.getElement();
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getModelEndpointReference_2007IncomingLinks(
			View view) {
		ModelEndpointReference modelElement = (ModelEndpointReference) view
				.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getLinkReference_2008IncomingLinks(
			View view) {
		LinkReference modelElement = (LinkReference) view.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getLinkReference_2009IncomingLinks(
			View view) {
		LinkReference modelElement = (LinkReference) view.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getModelEndpointReference_2010IncomingLinks(
			View view) {
		ModelEndpointReference modelElement = (ModelEndpointReference) view
				.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getModelElementReference_3005IncomingLinks(
			View view) {
		ModelElementReference modelElement = (ModelElementReference) view
				.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_ModelElementEndpointReference_4011(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_BinaryLinkReference_4012(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_BinaryLinkReference_4013(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_ModelElementEndpointReference_4014(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getModelElementReference_3006IncomingLinks(
			View view) {
		ModelElementReference modelElement = (ModelElementReference) view
				.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_ModelElementEndpointReference_4011(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_BinaryLinkReference_4012(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_BinaryLinkReference_4013(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_ModelElementEndpointReference_4014(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getModelElementReference_3007IncomingLinks(
			View view) {
		ModelElementReference modelElement = (ModelElementReference) view
				.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_ModelElementEndpointReference_4011(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_BinaryLinkReference_4012(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_BinaryLinkReference_4013(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_ModelElementEndpointReference_4014(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getModelElementEndpointReference_4011IncomingLinks(
			View view) {
		ModelElementEndpointReference modelElement = (ModelElementEndpointReference) view
				.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getBinaryLinkReference_4012IncomingLinks(
			View view) {
		BinaryLinkReference modelElement = (BinaryLinkReference) view
				.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getBinaryLinkReference_4013IncomingLinks(
			View view) {
		BinaryLinkReference modelElement = (BinaryLinkReference) view
				.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getModelElementEndpointReference_4014IncomingLinks(
			View view) {
		ModelElementEndpointReference modelElement = (ModelElementEndpointReference) view
				.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getIncomingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getModelEndpointReference_2007OutgoingLinks(
			View view) {
		ModelEndpointReference modelElement = (ModelEndpointReference) view
				.getElement();
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getLinkReference_2008OutgoingLinks(
			View view) {
		LinkReference modelElement = (LinkReference) view.getElement();
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ModelElementEndpointReference_4011(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ModelElementEndpointReference_4014(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getLinkReference_2009OutgoingLinks(
			View view) {
		LinkReference modelElement = (LinkReference) view.getElement();
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ModelElementEndpointReference_4011(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ModelElementEndpointReference_4014(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getModelEndpointReference_2010OutgoingLinks(
			View view) {
		ModelEndpointReference modelElement = (ModelEndpointReference) view
				.getElement();
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getModelElementReference_3005OutgoingLinks(
			View view) {
		ModelElementReference modelElement = (ModelElementReference) view
				.getElement();
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_BinaryLinkReference_4012(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_BinaryLinkReference_4013(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getModelElementReference_3006OutgoingLinks(
			View view) {
		ModelElementReference modelElement = (ModelElementReference) view
				.getElement();
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_BinaryLinkReference_4012(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_BinaryLinkReference_4013(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getModelElementReference_3007OutgoingLinks(
			View view) {
		ModelElementReference modelElement = (ModelElementReference) view
				.getElement();
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_BinaryLinkReference_4012(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_BinaryLinkReference_4013(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getModelElementEndpointReference_4011OutgoingLinks(
			View view) {
		ModelElementEndpointReference modelElement = (ModelElementEndpointReference) view
				.getElement();
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getBinaryLinkReference_4012OutgoingLinks(
			View view) {
		BinaryLinkReference modelElement = (BinaryLinkReference) view
				.getElement();
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ModelElementEndpointReference_4011(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ModelElementEndpointReference_4014(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getBinaryLinkReference_4013OutgoingLinks(
			View view) {
		BinaryLinkReference modelElement = (BinaryLinkReference) view
				.getElement();
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ModelElementEndpointReference_4011(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ModelElementEndpointReference_4014(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<MIDLinkDescriptor> getModelElementEndpointReference_4014OutgoingLinks(
			View view) {
		ModelElementEndpointReference modelElement = (ModelElementEndpointReference) view
				.getElement();
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		result.addAll(getOutgoingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<MIDLinkDescriptor> getContainedTypeModelFacetLinks_ModelElementEndpointReference_4011(
			LinkReference container) {
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		for (Iterator<?> links = container.getModelElemEndpointRefs()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof ModelElementEndpointReference) {
				continue;
			}
			ModelElementEndpointReference link = (ModelElementEndpointReference) linkObject;
			if (ModelElementEndpointReferenceEditPart.VISUAL_ID != MIDVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			ModelElementReference dst = link.getModelElemRef();
			result.add(new MIDLinkDescriptor(container, dst, link,
					MIDElementTypes.ModelElementEndpointReference_4011,
					ModelElementEndpointReferenceEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<MIDLinkDescriptor> getContainedTypeModelFacetLinks_BinaryLinkReference_4012(
			ModelRel container) {
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		for (Iterator<?> links = container.getLinkRefs().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof BinaryLinkReference) {
				continue;
			}
			BinaryLinkReference link = (BinaryLinkReference) linkObject;
			if (BinaryLinkReferenceEditPart.VISUAL_ID != MIDVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			ModelElementReference dst = link.getTargetModelElemRef();
			ModelElementReference src = link.getSourceModelElemRef();
			result.add(new MIDLinkDescriptor(src, dst, link,
					MIDElementTypes.BinaryLinkReference_4012,
					BinaryLinkReferenceEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<MIDLinkDescriptor> getContainedTypeModelFacetLinks_BinaryLinkReference_4013(
			ModelRel container) {
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		for (Iterator<?> links = container.getLinkRefs().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof BinaryLinkReference) {
				continue;
			}
			BinaryLinkReference link = (BinaryLinkReference) linkObject;
			if (BinaryLinkReference2EditPart.VISUAL_ID != MIDVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			ModelElementReference dst = link.getTargetModelElemRef();
			ModelElementReference src = link.getSourceModelElemRef();
			result.add(new MIDLinkDescriptor(src, dst, link,
					MIDElementTypes.BinaryLinkReference_4013,
					BinaryLinkReference2EditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<MIDLinkDescriptor> getContainedTypeModelFacetLinks_ModelElementEndpointReference_4014(
			LinkReference container) {
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		for (Iterator<?> links = container.getModelElemEndpointRefs()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof ModelElementEndpointReference) {
				continue;
			}
			ModelElementEndpointReference link = (ModelElementEndpointReference) linkObject;
			if (ModelElementEndpointReference2EditPart.VISUAL_ID != MIDVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			ModelElementReference dst = link.getModelElemRef();
			result.add(new MIDLinkDescriptor(container, dst, link,
					MIDElementTypes.ModelElementEndpointReference_4014,
					ModelElementEndpointReference2EditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<MIDLinkDescriptor> getIncomingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(
			ExtendibleElementReference target,
			Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		Collection<EStructuralFeature.Setting> settings = crossReferences
				.get(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == RelationshipPackage.eINSTANCE
					.getExtendibleElementReference_SupertypeRef()) {
				result.add(new MIDLinkDescriptor(
						setting.getEObject(),
						target,
						MIDElementTypes.ExtendibleElementReferenceSupertypeRef_4010,
						ExtendibleElementReferenceSupertypeRefEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<MIDLinkDescriptor> getIncomingTypeModelFacetLinks_ModelElementEndpointReference_4011(
			ModelElementReference target,
			Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		Collection<EStructuralFeature.Setting> settings = crossReferences
				.get(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != RelationshipPackage.eINSTANCE
					.getModelElementEndpointReference_ModelElemRef()
					|| false == setting.getEObject() instanceof ModelElementEndpointReference) {
				continue;
			}
			ModelElementEndpointReference link = (ModelElementEndpointReference) setting
					.getEObject();
			if (ModelElementEndpointReferenceEditPart.VISUAL_ID != MIDVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			if (false == link.eContainer() instanceof LinkReference) {
				continue;
			}
			LinkReference container = (LinkReference) link.eContainer();
			result.add(new MIDLinkDescriptor(container, target, link,
					MIDElementTypes.ModelElementEndpointReference_4011,
					ModelElementEndpointReferenceEditPart.VISUAL_ID));

		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<MIDLinkDescriptor> getIncomingTypeModelFacetLinks_BinaryLinkReference_4012(
			ModelElementReference target,
			Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		Collection<EStructuralFeature.Setting> settings = crossReferences
				.get(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != RelationshipPackage.eINSTANCE
					.getBinaryLinkReference_TargetModelElemRef()
					|| false == setting.getEObject() instanceof BinaryLinkReference) {
				continue;
			}
			BinaryLinkReference link = (BinaryLinkReference) setting
					.getEObject();
			if (BinaryLinkReferenceEditPart.VISUAL_ID != MIDVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			ModelElementReference src = link.getSourceModelElemRef();
			result.add(new MIDLinkDescriptor(src, target, link,
					MIDElementTypes.BinaryLinkReference_4012,
					BinaryLinkReferenceEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<MIDLinkDescriptor> getIncomingTypeModelFacetLinks_BinaryLinkReference_4013(
			ModelElementReference target,
			Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		Collection<EStructuralFeature.Setting> settings = crossReferences
				.get(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != RelationshipPackage.eINSTANCE
					.getBinaryLinkReference_TargetModelElemRef()
					|| false == setting.getEObject() instanceof BinaryLinkReference) {
				continue;
			}
			BinaryLinkReference link = (BinaryLinkReference) setting
					.getEObject();
			if (BinaryLinkReference2EditPart.VISUAL_ID != MIDVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			ModelElementReference src = link.getSourceModelElemRef();
			result.add(new MIDLinkDescriptor(src, target, link,
					MIDElementTypes.BinaryLinkReference_4013,
					BinaryLinkReference2EditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<MIDLinkDescriptor> getIncomingTypeModelFacetLinks_ModelElementEndpointReference_4014(
			ModelElementReference target,
			Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		Collection<EStructuralFeature.Setting> settings = crossReferences
				.get(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != RelationshipPackage.eINSTANCE
					.getModelElementEndpointReference_ModelElemRef()
					|| false == setting.getEObject() instanceof ModelElementEndpointReference) {
				continue;
			}
			ModelElementEndpointReference link = (ModelElementEndpointReference) setting
					.getEObject();
			if (ModelElementEndpointReference2EditPart.VISUAL_ID != MIDVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			if (false == link.eContainer() instanceof LinkReference) {
				continue;
			}
			LinkReference container = (LinkReference) link.eContainer();
			result.add(new MIDLinkDescriptor(container, target, link,
					MIDElementTypes.ModelElementEndpointReference_4014,
					ModelElementEndpointReference2EditPart.VISUAL_ID));

		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<MIDLinkDescriptor> getOutgoingFeatureModelFacetLinks_ExtendibleElementReference_SupertypeRef_4010(
			ExtendibleElementReference source) {
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		ExtendibleElementReference destination = source.getSupertypeRef();
		if (destination == null) {
			return result;
		}
		result.add(new MIDLinkDescriptor(source, destination,
				MIDElementTypes.ExtendibleElementReferenceSupertypeRef_4010,
				ExtendibleElementReferenceSupertypeRefEditPart.VISUAL_ID));
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<MIDLinkDescriptor> getOutgoingTypeModelFacetLinks_BinaryLinkReference_4012(
			ModelElementReference source) {
		ModelRel container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element
				.eContainer()) {
			if (element instanceof ModelRel) {
				container = (ModelRel) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		for (Iterator<?> links = container.getLinkRefs().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof BinaryLinkReference) {
				continue;
			}
			BinaryLinkReference link = (BinaryLinkReference) linkObject;
			if (BinaryLinkReferenceEditPart.VISUAL_ID != MIDVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			ModelElementReference dst = link.getTargetModelElemRef();
			ModelElementReference src = link.getSourceModelElemRef();
			if (src != source) {
				continue;
			}
			result.add(new MIDLinkDescriptor(src, dst, link,
					MIDElementTypes.BinaryLinkReference_4012,
					BinaryLinkReferenceEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<MIDLinkDescriptor> getOutgoingTypeModelFacetLinks_BinaryLinkReference_4013(
			ModelElementReference source) {
		ModelRel container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element
				.eContainer()) {
			if (element instanceof ModelRel) {
				container = (ModelRel) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<MIDLinkDescriptor> result = new LinkedList<MIDLinkDescriptor>();
		for (Iterator<?> links = container.getLinkRefs().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof BinaryLinkReference) {
				continue;
			}
			BinaryLinkReference link = (BinaryLinkReference) linkObject;
			if (BinaryLinkReference2EditPart.VISUAL_ID != MIDVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			ModelElementReference dst = link.getTargetModelElemRef();
			ModelElementReference src = link.getSourceModelElemRef();
			if (src != source) {
				continue;
			}
			result.add(new MIDLinkDescriptor(src, dst, link,
					MIDElementTypes.BinaryLinkReference_4013,
					BinaryLinkReference2EditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static final DiagramUpdater TYPED_INSTANCE = new DiagramUpdater() {
		/**
		 * @generated
		 */
		@Override
		public List<MIDNodeDescriptor> getSemanticChildren(View view) {
			return MIDDiagramUpdater.getSemanticChildren(view);
		}

		/**
		 * @generated
		 */
		@Override
		public List<MIDLinkDescriptor> getContainedLinks(View view) {
			return MIDDiagramUpdater.getContainedLinks(view);
		}

		/**
		 * @generated
		 */
		@Override
		public List<MIDLinkDescriptor> getIncomingLinks(View view) {
			return MIDDiagramUpdater.getIncomingLinks(view);
		}

		/**
		 * @generated
		 */
		@Override
		public List<MIDLinkDescriptor> getOutgoingLinks(View view) {
			return MIDDiagramUpdater.getOutgoingLinks(view);
		}
	};

}
