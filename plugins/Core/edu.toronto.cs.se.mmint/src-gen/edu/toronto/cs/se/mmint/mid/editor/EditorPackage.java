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
package edu.toronto.cs.se.mmint.mid.editor;

import edu.toronto.cs.se.mmint.mid.MIDPackage;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see edu.toronto.cs.se.mmint.mid.editor.EditorFactory
 * @model kind="package"
 * @generated
 */
public interface EditorPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "editor";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://se.cs.toronto.edu/mmint/MID/Editor";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "editor";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EditorPackage eINSTANCE = edu.toronto.cs.se.mmint.mid.editor.impl.EditorPackageImpl.init();

	/**
	 * The meta object id for the '{@link edu.toronto.cs.se.mmint.mid.editor.impl.EditorImpl <em>Editor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.toronto.cs.se.mmint.mid.editor.impl.EditorImpl
	 * @see edu.toronto.cs.se.mmint.mid.editor.impl.EditorPackageImpl#getEditor()
	 * @generated
	 */
	int EDITOR = 0;

	/**
	 * The feature id for the '<em><b>Formula Variable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR__FORMULA_VARIABLE = MIDPackage.EXTENDIBLE_ELEMENT__FORMULA_VARIABLE;

	/**
	 * The feature id for the '<em><b>May</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR__MAY = MIDPackage.EXTENDIBLE_ELEMENT__MAY;

	/**
	 * The feature id for the '<em><b>Set</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR__SET = MIDPackage.EXTENDIBLE_ELEMENT__SET;

	/**
	 * The feature id for the '<em><b>Var</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR__VAR = MIDPackage.EXTENDIBLE_ELEMENT__VAR;

	/**
	 * The feature id for the '<em><b>Alternatives</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR__ALTERNATIVES = MIDPackage.EXTENDIBLE_ELEMENT__ALTERNATIVES;

	/**
	 * The feature id for the '<em><b>Supertype</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR__SUPERTYPE = MIDPackage.EXTENDIBLE_ELEMENT__SUPERTYPE;

	/**
	 * The feature id for the '<em><b>Metatype</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR__METATYPE = MIDPackage.EXTENDIBLE_ELEMENT__METATYPE;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR__URI = MIDPackage.EXTENDIBLE_ELEMENT__URI;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR__NAME = MIDPackage.EXTENDIBLE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR__LEVEL = MIDPackage.EXTENDIBLE_ELEMENT__LEVEL;

	/**
	 * The feature id for the '<em><b>Metatype Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR__METATYPE_URI = MIDPackage.EXTENDIBLE_ELEMENT__METATYPE_URI;

	/**
	 * The feature id for the '<em><b>Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR__DYNAMIC = MIDPackage.EXTENDIBLE_ELEMENT__DYNAMIC;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR__CONSTRAINT = MIDPackage.EXTENDIBLE_ELEMENT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Model Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR__MODEL_URI = MIDPackage.EXTENDIBLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR__ID = MIDPackage.EXTENDIBLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Wizard Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR__WIZARD_ID = MIDPackage.EXTENDIBLE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>File Extensions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR__FILE_EXTENSIONS = MIDPackage.EXTENDIBLE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Wizard Dialog Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR__WIZARD_DIALOG_CLASS = MIDPackage.EXTENDIBLE_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_FEATURE_COUNT = MIDPackage.EXTENDIBLE_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The operation id for the '<em>Create Subtype Uri</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR___CREATE_SUBTYPE_URI__STRING_STRING = MIDPackage.EXTENDIBLE_ELEMENT___CREATE_SUBTYPE_URI__STRING_STRING;

	/**
	 * The operation id for the '<em>Get Metatype</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR___GET_METATYPE = MIDPackage.EXTENDIBLE_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Supertype</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR___GET_SUPERTYPE = MIDPackage.EXTENDIBLE_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Create Subtype</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR___CREATE_SUBTYPE__STRING_STRING_STRING_STRING_STRING_STRING = MIDPackage.EXTENDIBLE_ELEMENT_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Delete Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR___DELETE_TYPE = MIDPackage.EXTENDIBLE_ELEMENT_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Create Instance</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR___CREATE_INSTANCE__STRING_MULTIMODEL = MIDPackage.EXTENDIBLE_ELEMENT_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Invoke Instance Wizard</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR___INVOKE_INSTANCE_WIZARD__ISTRUCTUREDSELECTION = MIDPackage.EXTENDIBLE_ELEMENT_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Delete Instance</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR___DELETE_INSTANCE = MIDPackage.EXTENDIBLE_ELEMENT_OPERATION_COUNT + 6;

	/**
	 * The number of operations of the '<em>Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_OPERATION_COUNT = MIDPackage.EXTENDIBLE_ELEMENT_OPERATION_COUNT + 7;

	/**
	 * The meta object id for the '{@link edu.toronto.cs.se.mmint.mid.editor.impl.DiagramImpl <em>Diagram</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.toronto.cs.se.mmint.mid.editor.impl.DiagramImpl
	 * @see edu.toronto.cs.se.mmint.mid.editor.impl.EditorPackageImpl#getDiagram()
	 * @generated
	 */
	int DIAGRAM = 1;

	/**
	 * The feature id for the '<em><b>Formula Variable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__FORMULA_VARIABLE = EDITOR__FORMULA_VARIABLE;

	/**
	 * The feature id for the '<em><b>May</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__MAY = EDITOR__MAY;

	/**
	 * The feature id for the '<em><b>Set</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__SET = EDITOR__SET;

	/**
	 * The feature id for the '<em><b>Var</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__VAR = EDITOR__VAR;

	/**
	 * The feature id for the '<em><b>Alternatives</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__ALTERNATIVES = EDITOR__ALTERNATIVES;

	/**
	 * The feature id for the '<em><b>Supertype</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__SUPERTYPE = EDITOR__SUPERTYPE;

	/**
	 * The feature id for the '<em><b>Metatype</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__METATYPE = EDITOR__METATYPE;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__URI = EDITOR__URI;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__NAME = EDITOR__NAME;

	/**
	 * The feature id for the '<em><b>Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__LEVEL = EDITOR__LEVEL;

	/**
	 * The feature id for the '<em><b>Metatype Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__METATYPE_URI = EDITOR__METATYPE_URI;

	/**
	 * The feature id for the '<em><b>Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__DYNAMIC = EDITOR__DYNAMIC;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__CONSTRAINT = EDITOR__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Model Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__MODEL_URI = EDITOR__MODEL_URI;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__ID = EDITOR__ID;

	/**
	 * The feature id for the '<em><b>Wizard Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__WIZARD_ID = EDITOR__WIZARD_ID;

	/**
	 * The feature id for the '<em><b>File Extensions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__FILE_EXTENSIONS = EDITOR__FILE_EXTENSIONS;

	/**
	 * The feature id for the '<em><b>Wizard Dialog Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__WIZARD_DIALOG_CLASS = EDITOR__WIZARD_DIALOG_CLASS;

	/**
	 * The number of structural features of the '<em>Diagram</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_FEATURE_COUNT = EDITOR_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Create Subtype Uri</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM___CREATE_SUBTYPE_URI__STRING_STRING = EDITOR___CREATE_SUBTYPE_URI__STRING_STRING;

	/**
	 * The operation id for the '<em>Get Metatype</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM___GET_METATYPE = EDITOR___GET_METATYPE;

	/**
	 * The operation id for the '<em>Get Supertype</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM___GET_SUPERTYPE = EDITOR___GET_SUPERTYPE;

	/**
	 * The operation id for the '<em>Delete Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM___DELETE_TYPE = EDITOR___DELETE_TYPE;

	/**
	 * The operation id for the '<em>Delete Instance</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM___DELETE_INSTANCE = EDITOR___DELETE_INSTANCE;

	/**
	 * The operation id for the '<em>Create Subtype</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM___CREATE_SUBTYPE__STRING_STRING_STRING_STRING_STRING_STRING = EDITOR_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Create Instance</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM___CREATE_INSTANCE__STRING_MULTIMODEL = EDITOR_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Invoke Instance Wizard</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM___INVOKE_INSTANCE_WIZARD__ISTRUCTUREDSELECTION = EDITOR_OPERATION_COUNT + 2;

	/**
	 * The number of operations of the '<em>Diagram</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_OPERATION_COUNT = EDITOR_OPERATION_COUNT + 3;


	/**
	 * The meta object id for the '<em>IStructured Selection</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jface.viewers.IStructuredSelection
	 * @see edu.toronto.cs.se.mmint.mid.editor.impl.EditorPackageImpl#getIStructuredSelection()
	 * @generated
	 */
	int ISTRUCTURED_SELECTION = 2;

	/**
	 * The meta object id for the '<em>Creation Wizard Dialog</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.toronto.cs.se.mmint.mid.ui.EditorCreationWizardDialog
	 * @see edu.toronto.cs.se.mmint.mid.editor.impl.EditorPackageImpl#getEditorCreationWizardDialog()
	 * @generated
	 */
	int EDITOR_CREATION_WIZARD_DIALOG = 3;


	/**
	 * Returns the meta object for class '{@link edu.toronto.cs.se.mmint.mid.editor.Editor <em>Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Editor</em>'.
	 * @see edu.toronto.cs.se.mmint.mid.editor.Editor
	 * @generated
	 */
	EClass getEditor();

	/**
	 * Returns the meta object for the attribute '{@link edu.toronto.cs.se.mmint.mid.editor.Editor#getModelUri <em>Model Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Model Uri</em>'.
	 * @see edu.toronto.cs.se.mmint.mid.editor.Editor#getModelUri()
	 * @see #getEditor()
	 * @generated
	 */
	EAttribute getEditor_ModelUri();

	/**
	 * Returns the meta object for the attribute '{@link edu.toronto.cs.se.mmint.mid.editor.Editor#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see edu.toronto.cs.se.mmint.mid.editor.Editor#getId()
	 * @see #getEditor()
	 * @generated
	 */
	EAttribute getEditor_Id();

	/**
	 * Returns the meta object for the attribute '{@link edu.toronto.cs.se.mmint.mid.editor.Editor#getWizardId <em>Wizard Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Wizard Id</em>'.
	 * @see edu.toronto.cs.se.mmint.mid.editor.Editor#getWizardId()
	 * @see #getEditor()
	 * @generated
	 */
	EAttribute getEditor_WizardId();

	/**
	 * Returns the meta object for the attribute list '{@link edu.toronto.cs.se.mmint.mid.editor.Editor#getFileExtensions <em>File Extensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>File Extensions</em>'.
	 * @see edu.toronto.cs.se.mmint.mid.editor.Editor#getFileExtensions()
	 * @see #getEditor()
	 * @generated
	 */
	EAttribute getEditor_FileExtensions();

	/**
	 * Returns the meta object for the attribute '{@link edu.toronto.cs.se.mmint.mid.editor.Editor#getWizardDialogClass <em>Wizard Dialog Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Wizard Dialog Class</em>'.
	 * @see edu.toronto.cs.se.mmint.mid.editor.Editor#getWizardDialogClass()
	 * @see #getEditor()
	 * @generated
	 */
	EAttribute getEditor_WizardDialogClass();

	/**
	 * Returns the meta object for the '{@link edu.toronto.cs.se.mmint.mid.editor.Editor#getMetatype() <em>Get Metatype</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Metatype</em>' operation.
	 * @see edu.toronto.cs.se.mmint.mid.editor.Editor#getMetatype()
	 * @generated
	 */
	EOperation getEditor__GetMetatype();

	/**
	 * Returns the meta object for the '{@link edu.toronto.cs.se.mmint.mid.editor.Editor#getSupertype() <em>Get Supertype</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Supertype</em>' operation.
	 * @see edu.toronto.cs.se.mmint.mid.editor.Editor#getSupertype()
	 * @generated
	 */
	EOperation getEditor__GetSupertype();

	/**
	 * Returns the meta object for the '{@link edu.toronto.cs.se.mmint.mid.editor.Editor#createSubtype(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String) <em>Create Subtype</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Create Subtype</em>' operation.
	 * @see edu.toronto.cs.se.mmint.mid.editor.Editor#createSubtype(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 * @generated
	 */
	EOperation getEditor__CreateSubtype__String_String_String_String_String_String();

	/**
	 * Returns the meta object for the '{@link edu.toronto.cs.se.mmint.mid.editor.Editor#deleteType() <em>Delete Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Delete Type</em>' operation.
	 * @see edu.toronto.cs.se.mmint.mid.editor.Editor#deleteType()
	 * @generated
	 */
	EOperation getEditor__DeleteType();

	/**
	 * Returns the meta object for the '{@link edu.toronto.cs.se.mmint.mid.editor.Editor#createInstance(java.lang.String, edu.toronto.cs.se.mmint.mid.MultiModel) <em>Create Instance</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Create Instance</em>' operation.
	 * @see edu.toronto.cs.se.mmint.mid.editor.Editor#createInstance(java.lang.String, edu.toronto.cs.se.mmint.mid.MultiModel)
	 * @generated
	 */
	EOperation getEditor__CreateInstance__String_MultiModel();

	/**
	 * Returns the meta object for the '{@link edu.toronto.cs.se.mmint.mid.editor.Editor#invokeInstanceWizard(org.eclipse.jface.viewers.IStructuredSelection) <em>Invoke Instance Wizard</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Invoke Instance Wizard</em>' operation.
	 * @see edu.toronto.cs.se.mmint.mid.editor.Editor#invokeInstanceWizard(org.eclipse.jface.viewers.IStructuredSelection)
	 * @generated
	 */
	EOperation getEditor__InvokeInstanceWizard__IStructuredSelection();

	/**
	 * Returns the meta object for the '{@link edu.toronto.cs.se.mmint.mid.editor.Editor#deleteInstance() <em>Delete Instance</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Delete Instance</em>' operation.
	 * @see edu.toronto.cs.se.mmint.mid.editor.Editor#deleteInstance()
	 * @generated
	 */
	EOperation getEditor__DeleteInstance();

	/**
	 * Returns the meta object for class '{@link edu.toronto.cs.se.mmint.mid.editor.Diagram <em>Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Diagram</em>'.
	 * @see edu.toronto.cs.se.mmint.mid.editor.Diagram
	 * @generated
	 */
	EClass getDiagram();

	/**
	 * Returns the meta object for the '{@link edu.toronto.cs.se.mmint.mid.editor.Diagram#createSubtype(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String) <em>Create Subtype</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Create Subtype</em>' operation.
	 * @see edu.toronto.cs.se.mmint.mid.editor.Diagram#createSubtype(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 * @generated
	 */
	EOperation getDiagram__CreateSubtype__String_String_String_String_String_String();

	/**
	 * Returns the meta object for the '{@link edu.toronto.cs.se.mmint.mid.editor.Diagram#createInstance(java.lang.String, edu.toronto.cs.se.mmint.mid.MultiModel) <em>Create Instance</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Create Instance</em>' operation.
	 * @see edu.toronto.cs.se.mmint.mid.editor.Diagram#createInstance(java.lang.String, edu.toronto.cs.se.mmint.mid.MultiModel)
	 * @generated
	 */
	EOperation getDiagram__CreateInstance__String_MultiModel();

	/**
	 * Returns the meta object for the '{@link edu.toronto.cs.se.mmint.mid.editor.Diagram#invokeInstanceWizard(org.eclipse.jface.viewers.IStructuredSelection) <em>Invoke Instance Wizard</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Invoke Instance Wizard</em>' operation.
	 * @see edu.toronto.cs.se.mmint.mid.editor.Diagram#invokeInstanceWizard(org.eclipse.jface.viewers.IStructuredSelection)
	 * @generated
	 */
	EOperation getDiagram__InvokeInstanceWizard__IStructuredSelection();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.jface.viewers.IStructuredSelection <em>IStructured Selection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>IStructured Selection</em>'.
	 * @see org.eclipse.jface.viewers.IStructuredSelection
	 * @model instanceClass="org.eclipse.jface.viewers.IStructuredSelection"
	 * @generated
	 */
	EDataType getIStructuredSelection();

	/**
	 * Returns the meta object for data type '{@link edu.toronto.cs.se.mmint.mid.ui.EditorCreationWizardDialog <em>Creation Wizard Dialog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Creation Wizard Dialog</em>'.
	 * @see edu.toronto.cs.se.mmint.mid.ui.EditorCreationWizardDialog
	 * @model instanceClass="edu.toronto.cs.se.mmint.mid.ui.EditorCreationWizardDialog"
	 * @generated
	 */
	EDataType getEditorCreationWizardDialog();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EditorFactory getEditorFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link edu.toronto.cs.se.mmint.mid.editor.impl.EditorImpl <em>Editor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.toronto.cs.se.mmint.mid.editor.impl.EditorImpl
		 * @see edu.toronto.cs.se.mmint.mid.editor.impl.EditorPackageImpl#getEditor()
		 * @generated
		 */
		EClass EDITOR = eINSTANCE.getEditor();

		/**
		 * The meta object literal for the '<em><b>Model Uri</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EDITOR__MODEL_URI = eINSTANCE.getEditor_ModelUri();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EDITOR__ID = eINSTANCE.getEditor_Id();

		/**
		 * The meta object literal for the '<em><b>Wizard Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EDITOR__WIZARD_ID = eINSTANCE.getEditor_WizardId();

		/**
		 * The meta object literal for the '<em><b>File Extensions</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EDITOR__FILE_EXTENSIONS = eINSTANCE.getEditor_FileExtensions();

		/**
		 * The meta object literal for the '<em><b>Wizard Dialog Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EDITOR__WIZARD_DIALOG_CLASS = eINSTANCE.getEditor_WizardDialogClass();

		/**
		 * The meta object literal for the '<em><b>Get Metatype</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EDITOR___GET_METATYPE = eINSTANCE.getEditor__GetMetatype();

		/**
		 * The meta object literal for the '<em><b>Get Supertype</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EDITOR___GET_SUPERTYPE = eINSTANCE.getEditor__GetSupertype();

		/**
		 * The meta object literal for the '<em><b>Create Subtype</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EDITOR___CREATE_SUBTYPE__STRING_STRING_STRING_STRING_STRING_STRING = eINSTANCE.getEditor__CreateSubtype__String_String_String_String_String_String();

		/**
		 * The meta object literal for the '<em><b>Delete Type</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EDITOR___DELETE_TYPE = eINSTANCE.getEditor__DeleteType();

		/**
		 * The meta object literal for the '<em><b>Create Instance</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EDITOR___CREATE_INSTANCE__STRING_MULTIMODEL = eINSTANCE.getEditor__CreateInstance__String_MultiModel();

		/**
		 * The meta object literal for the '<em><b>Invoke Instance Wizard</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EDITOR___INVOKE_INSTANCE_WIZARD__ISTRUCTUREDSELECTION = eINSTANCE.getEditor__InvokeInstanceWizard__IStructuredSelection();

		/**
		 * The meta object literal for the '<em><b>Delete Instance</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EDITOR___DELETE_INSTANCE = eINSTANCE.getEditor__DeleteInstance();

		/**
		 * The meta object literal for the '{@link edu.toronto.cs.se.mmint.mid.editor.impl.DiagramImpl <em>Diagram</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.toronto.cs.se.mmint.mid.editor.impl.DiagramImpl
		 * @see edu.toronto.cs.se.mmint.mid.editor.impl.EditorPackageImpl#getDiagram()
		 * @generated
		 */
		EClass DIAGRAM = eINSTANCE.getDiagram();

		/**
		 * The meta object literal for the '<em><b>Create Subtype</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation DIAGRAM___CREATE_SUBTYPE__STRING_STRING_STRING_STRING_STRING_STRING = eINSTANCE.getDiagram__CreateSubtype__String_String_String_String_String_String();

		/**
		 * The meta object literal for the '<em><b>Create Instance</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation DIAGRAM___CREATE_INSTANCE__STRING_MULTIMODEL = eINSTANCE.getDiagram__CreateInstance__String_MultiModel();

		/**
		 * The meta object literal for the '<em><b>Invoke Instance Wizard</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation DIAGRAM___INVOKE_INSTANCE_WIZARD__ISTRUCTUREDSELECTION = eINSTANCE.getDiagram__InvokeInstanceWizard__IStructuredSelection();

		/**
		 * The meta object literal for the '<em>IStructured Selection</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jface.viewers.IStructuredSelection
		 * @see edu.toronto.cs.se.mmint.mid.editor.impl.EditorPackageImpl#getIStructuredSelection()
		 * @generated
		 */
		EDataType ISTRUCTURED_SELECTION = eINSTANCE.getIStructuredSelection();

		/**
		 * The meta object literal for the '<em>Creation Wizard Dialog</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.toronto.cs.se.mmint.mid.ui.EditorCreationWizardDialog
		 * @see edu.toronto.cs.se.mmint.mid.editor.impl.EditorPackageImpl#getEditorCreationWizardDialog()
		 * @generated
		 */
		EDataType EDITOR_CREATION_WIZARD_DIALOG = eINSTANCE.getEditorCreationWizardDialog();

	}

} //EditorPackage
