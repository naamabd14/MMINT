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
package edu.toronto.cs.se.modelepedia.operator.patch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import edu.toronto.cs.se.mmint.MMINT;
import edu.toronto.cs.se.mmint.MMINTException;
import edu.toronto.cs.se.mmint.MultiModelTypeHierarchy;
import edu.toronto.cs.se.mmint.MultiModelTypeRegistry;
import edu.toronto.cs.se.mmint.MMINTException.Type;
import edu.toronto.cs.se.mmint.mid.EMFInfo;
import edu.toronto.cs.se.mmint.mid.MIDLevel;
import edu.toronto.cs.se.mmint.mid.Model;
import edu.toronto.cs.se.mmint.mid.ModelElement;
import edu.toronto.cs.se.mmint.mid.ModelOrigin;
import edu.toronto.cs.se.mmint.mid.MultiModel;
import edu.toronto.cs.se.mmint.mid.constraint.MultiModelConstraintChecker;
import edu.toronto.cs.se.mmint.mid.constraint.MultiModelConstraintChecker.MAVOTruthValue;
import edu.toronto.cs.se.mmint.mid.impl.ModelElementImpl;
import edu.toronto.cs.se.mmint.mid.library.MultiModelRegistry;
import edu.toronto.cs.se.mmint.mid.library.MultiModelUtils;
import edu.toronto.cs.se.mmint.mid.library.PrimitiveEObjectWrapper;
import edu.toronto.cs.se.mmint.mid.operator.ConversionOperator;
import edu.toronto.cs.se.mmint.mid.operator.Operator;
import edu.toronto.cs.se.mmint.mid.operator.impl.ConversionOperatorImpl;
import edu.toronto.cs.se.mmint.mid.relationship.BinaryModelRel;
import edu.toronto.cs.se.mmint.mid.relationship.Link;
import edu.toronto.cs.se.mmint.mid.relationship.LinkReference;
import edu.toronto.cs.se.mmint.mid.relationship.ModelElementReference;
import edu.toronto.cs.se.mmint.mid.relationship.ModelEndpointReference;
import edu.toronto.cs.se.mmint.mid.relationship.ModelRel;

public class ModelRelTypeTransformation extends ConversionOperatorImpl {

	protected static final String TRANSFORMATION_SUFFIX = "_transformed";

	protected EObject tgtRootModelObj;
	protected String tgtModelUri;

	@Override
	public void init() throws MMINTException {

		// state
		tgtRootModelObj = null;
	}

	@SuppressWarnings("unchecked")
	protected EObject transformModelObj(ModelEndpointReference srcModelTypeEndpointRef, EObject srcModelObj, Map<EObject, ModelElementReference> srcModelObjs, ModelEndpointReference tgtModelTypeEndpointRef, Map<EObject, EObject> tgtModelObjs) throws MMINTException {

		ModelElementReference tgtModelElemTypeRef = srcModelObjs.get(srcModelObj);
		EClass tgtModelTypeObj = (EClass) tgtModelElemTypeRef.getObject().getEMFTypeObject();
		EObject tgtModelObj = tgtModelTypeObj.getEPackage().getEFactoryInstance().create(tgtModelTypeObj);
		EObject srcContainerModelObj = srcModelObj.eContainer();
		if (srcContainerModelObj == null) { // root found
			tgtRootModelObj = tgtModelObj;
		}
		else {
			EObject tgtContainerModelObj = tgtModelObjs.get(srcContainerModelObj);
			if (tgtContainerModelObj == null) {
				// recursion
				tgtContainerModelObj = transformModelObj(srcModelTypeEndpointRef, srcContainerModelObj, srcModelObjs, tgtModelTypeEndpointRef, tgtModelObjs);
			}
			// find containment based on model element types first, then fallback to first one available
			String srcModelElemTypeContainmentUri = MultiModelRegistry.getModelAndModelElementUris(srcModelObj.eContainingFeature(), MIDLevel.TYPES)[1];
			ModelElementReference srcModelElemTypeContainment = MultiModelTypeHierarchy.getReference(srcModelElemTypeContainmentUri, srcModelTypeEndpointRef.getModelElemRefs());
			EReference containmentReference = null, fallbackContainmentReference = null;
			for (EReference containment : tgtContainerModelObj.eClass().getEAllContainments()) {
				if (MultiModelConstraintChecker.instanceofEMFClass(tgtModelObj, containment.getEType().getName())) {
					String tgtModelElemTypeContainmentUri = MultiModelRegistry.getModelAndModelElementUris(containment, MIDLevel.TYPES)[1];
					ModelElementReference tgtModelElemTypeContainment = MultiModelTypeHierarchy.getReference(tgtModelElemTypeContainmentUri, tgtModelTypeEndpointRef.getModelElemRefs());
					if (
						tgtModelElemTypeContainment == null ||
						srcModelElemTypeContainment.getModelElemEndpointRefs().get(0).eContainer() != tgtModelElemTypeContainment.getModelElemEndpointRefs().get(0).eContainer()
					) {
						if (fallbackContainmentReference == null) {
							fallbackContainmentReference = containment;
						}
						continue;
					}
					containmentReference = containment;
					break;
				}
			}
			if (containmentReference == null) {
				containmentReference = fallbackContainmentReference;
			}
			Object containment = tgtContainerModelObj.eGet(containmentReference);
			if (containment instanceof EList) {
				((EList<EObject>) containment).add(tgtModelObj);
			}
			else {
				tgtContainerModelObj.eSet(containmentReference, tgtModelObj);
			}
		}
		tgtModelObjs.put(srcModelObj, tgtModelObj);

		return tgtModelObj;
	}

	@SuppressWarnings("unchecked")
	protected void transformModelObjFeature(EObject srcModelObj, String srcFeatureName, EObject tgtModelObj, String tgtFeatureName, List<PrimitiveEObjectWrapper> primitiveSrcModelObjs, List<PrimitiveEObjectWrapper> primitiveTgtModelObjs, Map<EObject, EObject> tgtModelObjs) {

		EStructuralFeature srcFeature = srcModelObj.eClass().getEStructuralFeature(srcFeatureName), tgtFeature = tgtModelObj.eClass().getEStructuralFeature(tgtFeatureName);
		if (srcFeature instanceof EReference && ((EReference) srcFeature).isContainment()) {
			return;
		}
		try {
			Object value = srcModelObj.eGet(srcFeature);
			if (srcFeature instanceof EReference) {
				if (value instanceof EList<?>) {
					for (EObject srcRefModelObj : (EList<EObject>) value) {
						EObject tgtRefModelObj = tgtModelObjs.get(srcRefModelObj);
						if (tgtRefModelObj == null) {
							continue;
						}
						MultiModelUtils.setModelObjFeature(tgtModelObj, tgtFeatureName, tgtRefModelObj);
					}
				}
				else {
					EObject srcRefModelObj = (EObject) value;
					EObject tgtRefModelObj = tgtModelObjs.get(srcRefModelObj);
					if (tgtRefModelObj != null) {
						MultiModelUtils.setModelObjFeature(tgtModelObj, tgtFeatureName, tgtRefModelObj);
					}
				}
			}
			else { // srcFeature instanceof EAttribute
				MultiModelUtils.setModelObjFeature(tgtModelObj, tgtFeatureName, value);
				primitiveSrcModelObjs.add(new PrimitiveEObjectWrapper(srcModelObj, srcFeature, value));
				primitiveTgtModelObjs.add(new PrimitiveEObjectWrapper(tgtModelObj, tgtFeature, value));
			}
		}
		catch (MMINTException e) {
			MMINTException.print(Type.WARNING, "Error transforming model object feature, skipping it", e);
		}
	}

	protected void transform(BinaryModelRel traceModelRel, Model srcModel, int srcIndex, int tgtIndex) throws Exception {

		ModelRel traceModelRelType = traceModelRel.getMetatype();
		ModelEndpointReference srcModelTypeEndpointRef = traceModelRelType.getModelEndpointRefs().get(srcIndex);
		ModelEndpointReference tgtModelTypeEndpointRef = traceModelRelType.getModelEndpointRefs().get(tgtIndex);
		Map<EObject, ModelElementReference> srcModelObjs = new LinkedHashMap<EObject, ModelElementReference>();
		TreeIterator<EObject> srcModelObjsIter = srcModel.getEMFInstanceRoot().eResource().getAllContents();
		// first pass: get model objects to be transformed
		while (srcModelObjsIter.hasNext()) {
			EObject srcModelObj = srcModelObjsIter.next();
			ModelElement srcModelElemType = MultiModelConstraintChecker.getAllowedModelElementType(traceModelRel.getModelEndpointRefs().get(0), srcModelObj);
			if (srcModelElemType == null) {
				continue;
			}
			ModelElementReference srcModelElemTypeRef = MultiModelTypeHierarchy.getReference(srcModelElemType.getUri(), srcModelTypeEndpointRef.getModelElemRefs());
			ModelElementReference tgtModelElemTypeRef = ((LinkReference) srcModelElemTypeRef.getModelElemEndpointRefs().get(0).eContainer()).getModelElemEndpointRefs().get(tgtIndex).getModelElemRef();
			srcModelObjs.put(srcModelObj, tgtModelElemTypeRef);
		}
		// second pass: transform
		Map<EObject, EObject> tgtModelObjs = new LinkedHashMap<EObject, EObject>();
		for (EObject srcModelObj : srcModelObjs.keySet()) {
			if (tgtModelObjs.get(srcModelObj) != null) { // already transformed
				continue;
			}
			transformModelObj(srcModelTypeEndpointRef, srcModelObj, srcModelObjs, tgtModelTypeEndpointRef, tgtModelObjs);
		}
		// third pass: EAttributes and non-containment EReferences
		List<PrimitiveEObjectWrapper> primitiveSrcModelObjs = new ArrayList<PrimitiveEObjectWrapper>(), primitiveTgtModelObjs = new ArrayList<PrimitiveEObjectWrapper>();
		for (Map.Entry<EObject, EObject> tgtModelObjsEntry : tgtModelObjs.entrySet()) {
			EObject srcModelObj = tgtModelObjsEntry.getKey(), tgtModelObj = tgtModelObjsEntry.getValue();
			for (ModelElementReference srcModelElemTypeRef : srcModelTypeEndpointRef.getModelElemRefs()) {
				EMFInfo srcModelElemTypeEInfo = srcModelElemTypeRef.getObject().getEInfo();
				if (
					srcModelElemTypeEInfo.getFeatureName() == null ||
					!MultiModelConstraintChecker.instanceofEMFClass(srcModelObj, srcModelElemTypeEInfo.getClassName())
				) {
					continue;
				}
				ModelElementReference tgtModelElemTypeRef = ((LinkReference) srcModelElemTypeRef.getModelElemEndpointRefs().get(0).eContainer()).getModelElemEndpointRefs().get(tgtIndex).getModelElemRef();
				EMFInfo tgtModelElemTypeEInfo = tgtModelElemTypeRef.getObject().getEInfo();
				transformModelObjFeature(srcModelObj, srcModelElemTypeEInfo.getFeatureName(), tgtModelObj, tgtModelElemTypeEInfo.getFeatureName(), primitiveSrcModelObjs, primitiveTgtModelObjs, tgtModelObjs);
			}
		}
		for (int i = 0; i < primitiveSrcModelObjs.size(); i++) { // needed to avoid concurrent modification of tgtModelObjs
			tgtModelObjs.put(primitiveSrcModelObjs.get(i), primitiveTgtModelObjs.get(i));
		}
		// fourth pass: create model elements and links
		MultiModelUtils.createModelFile(tgtRootModelObj, tgtModelUri, true);
		for (Map.Entry<EObject, EObject> tgtModelObjEntry : tgtModelObjs.entrySet()) {
			EList<ModelElementReference> targetModelElemRefs = new BasicEList<ModelElementReference>();
			ModelElementReference srcModelElemRef = ModelElementImpl.createInstanceAndReference(tgtModelObjEntry.getKey(), null, traceModelRel.getModelEndpointRefs().get(0));
			targetModelElemRefs.add(srcModelElemRef);
			ModelElementReference tgtModelElemRef = ModelElementImpl.createInstanceAndReference(tgtModelObjEntry.getValue(), null, traceModelRel.getModelEndpointRefs().get(1));
			targetModelElemRefs.add(tgtModelElemRef);
			Link linkType = MultiModelTypeRegistry.getType(MultiModelConstraintChecker.getAllowedLinkTypeReferences(traceModelRelType, srcModelElemRef, tgtModelElemRef).get(0));
			LinkReference newLinkRef = linkType.createInstanceAndReferenceAndEndpointsAndReferences(true, targetModelElemRefs);
			newLinkRef.getObject().setName(srcModelElemRef.getObject().getName() + MMINT.BINARY_MODELREL_LINK_SEPARATOR + tgtModelElemRef.getObject().getName());
		}
	}

	@Override
	public EList<Model> execute(EList<Model> actualParameters) throws Exception {

		ModelRel traceModelRelType = (ModelRel) actualParameters.get(0);
		Model srcModel = actualParameters.get(1);
		MultiModel multiModel = MultiModelRegistry.getMultiModel(srcModel);

		int srcIndex = (
			traceModelRelType instanceof BinaryModelRel ||
			MultiModelConstraintChecker.isAllowedModelEndpoint(traceModelRelType.getModelEndpointRefs().get(0), srcModel, new HashMap<String, Integer>())
		) ?
			0 : 1;
		int tgtIndex = 1 - srcIndex;
		Model tgtModelType = traceModelRelType.getModelEndpointRefs().get(tgtIndex).getObject().getTarget();
		tgtModelUri = MultiModelUtils.replaceFileExtensionInUri(
			MultiModelUtils.addFileNameSuffixInUri(srcModel.getUri(), TRANSFORMATION_SUFFIX),
			tgtModelType.getFileExtension()
		);
		Model tgtModel = tgtModelType.createInstance(tgtModelUri, ModelOrigin.CREATED, multiModel);
		BinaryModelRel traceModelRel = (BinaryModelRel) traceModelRelType.createInstance(null, true, ModelOrigin.CREATED, multiModel);
		traceModelRel.setName(srcModel.getName() + MMINT.BINARY_MODELREL_LINK_SEPARATOR + tgtModel.getName());
		traceModelRelType.getModelEndpointRefs().get(srcIndex).getObject().createInstanceAndReference(srcModel, traceModelRel);
		traceModelRelType.getModelEndpointRefs().get(tgtIndex).getObject().createInstanceAndReference(tgtModel, traceModelRel);
		transform(traceModelRel, srcModel, srcIndex, tgtIndex);
		tgtModel.createInstanceEditor();

		EList<Model> result = new BasicEList<Model>();
		result.add(tgtModel);
		result.add(traceModelRel);
		return result;
	}

	@Override
	public EList<Operator> getExecutables(EList<Model> actualModels, EList<EList<Model>> actualModelTypes, EList<Map<Integer, EList<ConversionOperator>>> conversions, EList<EList<Model>> generics) throws MMINTException {

		//TODO MMINT[TRANSFORMATION] this operator shouldn't appear when only a kleisli model rel type is available
		if (MultiModelConstraintChecker.isInstancesLevel(this)) {
			throw new MMINTException("Can't execute TYPES level operation on INSTANCES level element");
		}

		EList<Operator> executableOperatorTypes = new BasicEList<Operator>();
		// check 1: only one actual parameter
		if (actualModels.size() != 1) {
			return executableOperatorTypes;
		}
		for (ModelRel modelRelType : MultiModelTypeRegistry.getModelRelTypes()) {
			// check 2: satisfies transformation constraint
			if (new ModelRelTypeTransformationConstraint().validate(modelRelType) != MAVOTruthValue.TRUE) {
				continue;
			}
			Model srcModel = actualModels.get(0);
			// check 3: allowed source model
			if (
				!MultiModelConstraintChecker.isAllowedModelEndpoint(modelRelType.getModelEndpointRefs().get(0), srcModel, new HashMap<String, Integer>()) && (
					modelRelType instanceof BinaryModelRel ||
					!MultiModelConstraintChecker.isAllowedModelEndpoint(modelRelType.getModelEndpointRefs().get(1), srcModel, new HashMap<String, Integer>())
				)
			) {
				continue;
			}
			// create return structures with a new instance of this operator type
			Operator operatorType = new ModelRelTypeTransformation();
			operatorType.setName(getName());
			executableOperatorTypes.add(operatorType);
			conversions.add(new HashMap<Integer, EList<ConversionOperator>>());
			EList<Model> generic = new BasicEList<Model>();
			generic.add(modelRelType);
			generics.add(generic);
		}

		return executableOperatorTypes;
	}

}
