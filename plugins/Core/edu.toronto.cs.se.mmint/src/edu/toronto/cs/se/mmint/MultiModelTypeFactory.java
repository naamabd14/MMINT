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
package edu.toronto.cs.se.mmint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.eclipse.emf.common.util.EList;

import edu.toronto.cs.se.mmint.mid.EMFInfo;
import edu.toronto.cs.se.mmint.mid.ExtendibleElement;
import edu.toronto.cs.se.mmint.mid.ExtendibleElementConstraint;
import edu.toronto.cs.se.mmint.mid.ExtendibleElementEndpoint;
import edu.toronto.cs.se.mmint.mid.MIDFactory;
import edu.toronto.cs.se.mmint.mid.MIDLevel;
import edu.toronto.cs.se.mmint.mid.Model;
import edu.toronto.cs.se.mmint.mid.ModelElement;
import edu.toronto.cs.se.mmint.mid.ModelEndpoint;
import edu.toronto.cs.se.mmint.mid.MultiModel;
import edu.toronto.cs.se.mmint.mid.editor.Editor;
import edu.toronto.cs.se.mmint.mid.operator.ConversionOperator;
import edu.toronto.cs.se.mmint.mid.operator.Operator;
import edu.toronto.cs.se.mmint.mid.operator.Parameter;
import edu.toronto.cs.se.mmint.mid.operator.RandomOperator;
import edu.toronto.cs.se.mmint.mid.relationship.BinaryModelRel;
import edu.toronto.cs.se.mmint.mid.relationship.ExtendibleElementReference;
import edu.toronto.cs.se.mmint.mid.relationship.Link;
import edu.toronto.cs.se.mmint.mid.relationship.LinkReference;
import edu.toronto.cs.se.mmint.mid.relationship.ModelElementEndpoint;
import edu.toronto.cs.se.mmint.mid.relationship.ModelElementEndpointReference;
import edu.toronto.cs.se.mmint.mid.relationship.ModelElementReference;
import edu.toronto.cs.se.mmint.mid.relationship.ModelEndpointReference;
import edu.toronto.cs.se.mmint.mid.relationship.ModelRel;

/**
 * The factory to create/modify/remove all types.
 * 
 * @author Alessio Di Sandro
 * 
 */
public class MultiModelTypeFactory {

	public final static String ECORE_PIVOT_URI = "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot";
	public final static String ECORE_INVOCATION_DELEGATE = "invocationDelegates";
	public final static String ECORE_SETTING_DELEGATE = "settingDelegates";
	public final static String ECORE_VALIDATION_DELEGATE = "validationDelegates";
	public final static String ECORE_VALIDATION_CONSTRAINTS = "constraints";
	public final static String ECORE_REFLECTIVE_FILE_EXTENSION = "xmi";

	/**
	 * Adds a type to a multimodel.
	 * 
	 * @param newType
	 *            The new type to be added.
	 * @param type
	 *            The supertype of the new type, null only for root types.
	 * @param newTypeUri
	 *            The uri of the new type.
	 * @param newTypeName
	 *            The name of the new type.
	 * @param multiModel
	 *            The multimodel that will contain the new type.
	 * @throws MMINTException
	 *             If the uri of the new type is already registered in the
	 *             multimodel.
	 */
	public static void addType(ExtendibleElement newType, ExtendibleElement type, String newTypeUri, String newTypeName, MultiModel multiModel) throws MMINTException {

		if (multiModel.getExtendibleTable().containsKey(newTypeUri)) {
			throw new MMINTException("Type with uri " + newTypeUri + " is already registered");
		}

		newType.setUri(newTypeUri);
		newType.setName(newTypeName);
		newType.setLevel(MIDLevel.TYPES);
		newType.setSupertype(type);
		multiModel.getExtendibleTable().put(newTypeUri, newType);
	}

	/**
	 * Adds the cardinality to a type endpoint.
	 * 
	 * @param typeEndpoint
	 *            The type endpoint.
	 * @param lowerBound
	 *            The lower cardinality.
	 * @param upperBound
	 *            The upper cardinality, -1 for infinite.
	 */
	public static void addTypeEndpointCardinality(ExtendibleElementEndpoint typeEndpoint, int lowerBound, int upperBound) {

		typeEndpoint.setLowerBound(lowerBound);
		typeEndpoint.setUpperBound(upperBound);
	}

	/**
	 * Adds the target to a new type endpoint.
	 * 
	 * @param newTypeEndpoint
	 *            The new type endpoint.
	 * @param newType
	 *            The new type that is the target of the new type endpoint.
	 */
	protected static void addTypeEndpoint(ExtendibleElementEndpoint newTypeEndpoint, ExtendibleElement newType) {

		newTypeEndpoint.setTarget(newType);
		addTypeEndpointCardinality(newTypeEndpoint, 1, 1);
	}

	/**
	 * Adds additional info for a reference to a type.
	 * 
	 * @param newTypeRef
	 *            The new reference being added.
	 * @param newType
	 *            The new type for which the reference was created.
	 * @param typeRef
	 *            The reference to the supertype of the new type, null if such
	 *            reference doesn't exist.
	 * @param isModifiable
	 *            True if the new reference will allow modifications of the
	 *            referenced type, false otherwise.
	 * @param isContainer
	 *            True if the new reference is also the actual container of the
	 *            new type and not just a pointer to it, false otherwise.
	 */
	public static void addTypeReference(ExtendibleElementReference newTypeRef, ExtendibleElement newType, ExtendibleElementReference typeRef, boolean isModifiable, boolean isContainer) {

		if (isContainer) {
			newTypeRef.setContainedObject(newType);
		}
		else {
			newTypeRef.setReferencedObject(newType);
		}
		newTypeRef.setModifiable(isModifiable);
		newTypeRef.setSupertypeRef(typeRef);
	}

	/**
	 * Adds a model type to a multimodel.
	 * 
	 * @param newModelType
	 *            The new model type to be added.
	 * @param newModelTypeAbstract
	 *            True if the new model type is abstract, false otherwise.
	 * @param constraintLanguage
	 *            The constraint language of the constraint associated with the
	 *            new model type, null if no constraint is associated.
	 * @param constraintImplementation
	 *            The constraint implementation of the constraint associated
	 *            with the new model type, null if no constraint is associated.
	 * @param multiModel
	 *            The multimodel that will contain the new model type.
	 */
	public static void addModelType(Model newModelType, boolean newModelTypeAbstract, String constraintLanguage, String constraintImplementation, MultiModel multiModel) {

		newModelType.setAbstract(newModelTypeAbstract);
		ExtendibleElementConstraint modelConstraint = null;
		if (constraintLanguage != null) {
			modelConstraint = MIDFactory.eINSTANCE.createExtendibleElementConstraint();
			modelConstraint.setLanguage(constraintLanguage);
			modelConstraint.setImplementation(constraintImplementation);
		}
		newModelType.setConstraint(modelConstraint);

		multiModel.getModels().add(newModelType);
	}

	/**
	 * Adds a model element type to a model type.
	 * 
	 * @param newModelElemType
	 *            The new model element type being added.
	 * @param eInfo
	 *            The EMF info of the new model element type.
	 * @param modelType
	 *            The model type that will contain the new model element type.
	 */
	public static void addModelElementType(ModelElement newModelElemType, EMFInfo eInfo, Model modelType) {

		newModelElemType.setEInfo(eInfo);

		modelType.getModelElems().add(newModelElemType);
	}

	/**
	 * Adds additional info for a model relationship type.
	 * 
	 * @param newModelRelType
	 *            The new model relationship type being added.
	 * @param modelRelType
	 *            The supertype of the new model relationship type.
	 * @throws MMINTException 
	 */
	public static void addModelRelType(ModelRel newModelRelType, ModelRel modelRelType) throws MMINTException {

		List<LinkReference> skipLinkRefs = new ArrayList<LinkReference>();
		// copy model type references
		Iterator<ModelEndpointReference> modelTypeEndpointRefIter = MultiModelTypeHierarchy.getTypeRefHierarchyIterator(modelRelType.getModelEndpointRefs());
		while (modelTypeEndpointRefIter.hasNext()) {
			ModelEndpointReference modelTypeEndpointRefSuper = modelTypeEndpointRefIter.next();
			if (MultiModelTypeHierarchy.isRootType(modelTypeEndpointRefSuper.getObject().getTarget())) { // don't copy model type endpoints to the root model type
				for (ModelElementReference modelElemTypeRefSuper : modelTypeEndpointRefSuper.getModelElemRefs()) {
					for (ModelElementEndpointReference modelElemTypeEndpointRefSuper : modelElemTypeRefSuper.getModelElemEndpointRefs()) {
						skipLinkRefs.add((LinkReference) modelElemTypeEndpointRefSuper.eContainer());
					}
				}
				continue;
			}
			ModelEndpointReference newModelTypeEndpointRef = modelTypeEndpointRefSuper.getObject().createTypeReference(false, newModelRelType);
			// copy model element type references
			Iterator<ModelElementReference> modelElemTypeRefIter = MultiModelTypeHierarchy.getTypeRefHierarchyIterator(modelTypeEndpointRefSuper.getModelElemRefs());
			while (modelElemTypeRefIter.hasNext()) {
				ModelElementReference modelElemTypeRefSuper = modelElemTypeRefIter.next();
				ModelElementReference modelElemTypeRef = MultiModelTypeHierarchy.getReference(modelElemTypeRefSuper.getSupertypeRef(), newModelTypeEndpointRef.getModelElemRefs());
				modelElemTypeRefSuper.getObject().createTypeReference(modelElemTypeRef, false, newModelTypeEndpointRef);
			}
		}
		// copy link type references
		Iterator<LinkReference> linkTypeRefIter = MultiModelTypeHierarchy.getTypeRefHierarchyIterator(modelRelType.getLinkRefs());
		while (linkTypeRefIter.hasNext()) {
			LinkReference linkTypeRefSuper = linkTypeRefIter.next();
			if (skipLinkRefs.contains(linkTypeRefSuper)) { // don't copy link types using model element types from the root model type
				continue;
			}
			LinkReference linkTypeRef = MultiModelTypeHierarchy.getReference(linkTypeRefSuper.getSupertypeRef(), newModelRelType.getLinkRefs());
			LinkReference newLinkTypeRef = linkTypeRefSuper.getObject().createTypeReference(linkTypeRef, false, newModelRelType);
			// connect it to model element type references (takes care of binary too)
			Iterator<ModelElementEndpointReference> modelElemTypeEndpointRefIter = MultiModelTypeHierarchy.getTypeRefHierarchyIterator(linkTypeRefSuper.getModelElemEndpointRefs());
			while (modelElemTypeEndpointRefIter.hasNext()) {
				ModelElementEndpointReference modelElemTypeEndpointRefSuper = modelElemTypeEndpointRefIter.next();
				ModelElementEndpointReference modelElemTypeEndpointRef = null;
				ModelElementEndpointReference modelElemTypeEndpointRefSuper2 = modelElemTypeEndpointRefSuper.getSupertypeRef();
				if (modelElemTypeEndpointRefSuper2 != null) {
					LinkReference linkTypeRefSuper2 = (LinkReference) modelElemTypeEndpointRefSuper2.eContainer();
					LinkReference linkTypeRef2 = MultiModelTypeHierarchy.getReference(linkTypeRefSuper2, newModelRelType.getLinkRefs());
					modelElemTypeEndpointRef = MultiModelTypeHierarchy.getReference(modelElemTypeEndpointRefSuper2, linkTypeRef2.getModelElemEndpointRefs());
				}
				ModelElementReference modelElemTypeRefSuper = modelElemTypeEndpointRefSuper.getModelElemRef();
				ModelEndpointReference modelTypeEndpointRef = MultiModelTypeHierarchy.getReference((ModelEndpointReference) modelElemTypeRefSuper.eContainer(), newModelRelType.getModelEndpointRefs());
				ModelElementReference newModelElemTypeRef = MultiModelTypeHierarchy.getReference(modelElemTypeRefSuper, modelTypeEndpointRef.getModelElemRefs());
				modelElemTypeEndpointRefSuper.getObject().createTypeReference(modelElemTypeEndpointRef, newModelElemTypeRef, false, false, newLinkTypeRef);
			}
		}
	}

	/**
	 * Adds a model type endpoint to a model relationship type.
	 * 
	 * @param newModelTypeEndpoint
	 *            The new model type endpoint to be added.
	 * @param targetModelType
	 *            The new model type that is the target of the new model type
	 *            endpoint.
	 * @param isBinarySrc
	 *            (Only for a binary model relationship type container) True if
	 *            the target model type is the source in the binary model
	 *            relationship type container, false otherwise.
	 * @param containerModelRelType
	 *            The model relationship type that will contain the new model
	 *            type endpoint.
	 * @throws MMINTException 
	 */
	public static void addModelTypeEndpoint(ModelEndpoint newModelTypeEndpoint, Model targetModelType, boolean isBinarySrc, ModelRel containerModelRelType) throws MMINTException {

		addTypeEndpoint(newModelTypeEndpoint, targetModelType);
		containerModelRelType.getModelEndpoints().add(newModelTypeEndpoint);
		if (containerModelRelType instanceof BinaryModelRel) {
			((BinaryModelRel) containerModelRelType).addModelType(targetModelType, isBinarySrc);
		}
	}

	/**
	 * Adds a link type to a model relationship type.
	 * 
	 * @param newLinkType
	 *            The new link type to be added.
	 * @param linkType
	 *            The supertype of the new link type.
	 * @param modelRelType
	 *            The model relationship type that will contain the new link
	 *            type.
	 */
	public static void addLinkType(Link newLinkType, Link linkType, ModelRel modelRelType) {

		// keep track of inherited model elements, but not root ones
		if (linkType != null && !linkType.getUri().equals(MMINT.ROOT_LINK_URI)) {
			for (ModelElementEndpointReference modelElemTypeEndpointRef : linkType.getModelElemEndpointRefs()) {
				newLinkType.getModelElemEndpointRefs().add(modelElemTypeEndpointRef);
			}
		}
		modelRelType.getLinks().add(newLinkType);
	}

	/**
	 * Adds a model element type endpoint to a link type.
	 * 
	 * @param newModelElemTypeEndpoint
	 *            The new model element type endpoint to be added.
	 * @param targetModelElemType
	 *            The new model element type that is the target of the new model
	 *            element type endpoint.
	 * @param containerLinkType
	 *            The link type that will contain the new model element type
	 *            endpoint.
	 */
	public static void addModelElementTypeEndpoint(ModelElementEndpoint newModelElemTypeEndpoint, ModelElement targetModelElemType, Link containerLinkType) {

		addTypeEndpoint(newModelElemTypeEndpoint, targetModelElemType);
		containerLinkType.getModelElemEndpoints().add(newModelElemTypeEndpoint);
	}

	/**
	 * Adds additional info for a reference to a model element type endpoint.
	 * 
	 * @param newModelElemTypeEndpointRef
	 *            The new reference to the new model element type endpoint being
	 *            added.
	 * @param linkType
	 *            The link type that contains the referenced model element type
	 *            endpoint.
	 */
	public static void addModelElementTypeEndpointReference(ModelElementEndpointReference newModelElemTypeEndpointRef, Link linkType) {

		linkType.getModelElemEndpointRefs().add(newModelElemTypeEndpointRef);
	}

	/**
	 * Adds an editor type to a multimodel.
	 * 
	 * @param newEditorType
	 *            The new editor type to be added.
	 * @param modelTypeUri
	 *            The uri of the model type handled by the new editor type.
	 * @param editorId
	 *            The id of the corresponding Eclipse editor.
	 * @param wizardId
	 *            The wizard id of the corresponding Eclipse editor.
	 * @param wizardDialogClassName
	 *            The fully qualified name of a Java class that handles the
	 *            creation of the model type through the new editor type.
	 * @param multiModel
	 *            The multimodel that will contain the new editor type.
	 */
	public static void addEditorType(Editor newEditorType, String modelTypeUri, String editorId, String wizardId, String wizardDialogClassName, MultiModel multiModel) {

		newEditorType.setModelUri(modelTypeUri);
		newEditorType.setId(editorId);
		newEditorType.setWizardId(wizardId);
		newEditorType.setWizardDialogClass(wizardDialogClassName);

		multiModel.getEditors().add(newEditorType);
	}

	/**
	 * Adds a editor type to a model type.
	 * 
	 * @param editorType
	 *            The editor type.
	 * @param modelType
	 *            The model type handled by the editor type.
	 */
	public static void addModelTypeEditor(Editor editorType, Model modelType) {

		modelType.getEditors().add(editorType);
	}

	/**
	 * Adds an operator type to a multimodel.
	 * 
	 * @param newOperatorType
	 *            The new operator type to be added.
	 * @param multiModel
	 *            The multimodel that will contain the new operator type.
	 */
	protected static void addOperatorType(Operator newOperatorType, MultiModel multiModel) {

		multiModel.getOperators().add(newOperatorType);
	}

	/**
	 * Adds a parameter type (i.e. a formal parameter) to an operator type.
	 * 
	 * @param newParamType
	 *            The new parameter type to be added.
	 * @param newParamTypeName
	 *            The name of the new parameter type.
	 * @param modelType
	 *            The model type that is the target of the new parameter type.
	 * @param isVararg
	 *            True if the new parameter type represents a variable number of
	 *            parameter types of the same kind, false otherwise.
	 * @param paramTypes
	 *            The list of parameter types that will contain the new
	 *            parameter type.
	 * @param operatorType
	 *            The operator type that will contain the new parameter type.
	 */
	protected static void addOperatorTypeParameter(Parameter newParamType, String newParamTypeName, Model modelType, boolean isVararg, EList<Parameter> paramTypes, Operator operatorType) {

		newParamType.setModel(modelType);
		newParamType.setName(newParamTypeName);
		newParamType.setVararg(isVararg);
		paramTypes.add(newParamType);
	}

	/**
	 * Adds additional info for a random operator type.
	 * 
	 * @param operatorType
	 *            The random operator type being added.
	 */
	public static void addOperatorTypeRandom(RandomOperator operatorType) {

		operatorType.setState(new Random());
	}

	/**
	 * Adds additional info for a conversion operator type.
	 * 
	 * @param operatorType
	 *            The conversion operator type being added.
	 */
	public static void addOperatorTypeConversion(ConversionOperator operatorType) {

		operatorType.getInputs().get(0).getModel().getConversionOperators().add(operatorType);
	}

}