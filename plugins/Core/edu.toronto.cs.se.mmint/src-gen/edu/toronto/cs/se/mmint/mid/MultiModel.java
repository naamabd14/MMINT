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
package edu.toronto.cs.se.mmint.mid;

import edu.toronto.cs.se.mavo.MAVOModel;
import edu.toronto.cs.se.mmint.mid.editor.Editor;
import edu.toronto.cs.se.mmint.mid.operator.Operator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Multi Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The model management root.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.toronto.cs.se.mmint.mid.MultiModel#getModels <em>Models</em>}</li>
 *   <li>{@link edu.toronto.cs.se.mmint.mid.MultiModel#getEditors <em>Editors</em>}</li>
 *   <li>{@link edu.toronto.cs.se.mmint.mid.MultiModel#getOperators <em>Operators</em>}</li>
 *   <li>{@link edu.toronto.cs.se.mmint.mid.MultiModel#getExtendibleTable <em>Extendible Table</em>}</li>
 *   <li>{@link edu.toronto.cs.se.mmint.mid.MultiModel#getLevel <em>Level</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.toronto.cs.se.mmint.mid.MIDPackage#getMultiModel()
 * @model
 * @generated
 */
public interface MultiModel extends MAVOModel {
	/**
	 * Returns the value of the '<em><b>Models</b></em>' containment reference list.
	 * The list contents are of type {@link edu.toronto.cs.se.mmint.mid.Model}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The list of managed model types or model instances.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Models</em>' containment reference list.
	 * @see edu.toronto.cs.se.mmint.mid.MIDPackage#getMultiModel_Models()
	 * @model containment="true"
	 * @generated
	 */
	EList<Model> getModels();

	/**
	 * Returns the value of the '<em><b>Editors</b></em>' containment reference list.
	 * The list contents are of type {@link edu.toronto.cs.se.mmint.mid.editor.Editor}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The list of managed editor types or editor instances.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Editors</em>' containment reference list.
	 * @see edu.toronto.cs.se.mmint.mid.MIDPackage#getMultiModel_Editors()
	 * @model containment="true"
	 * @generated
	 */
	EList<Editor> getEditors();

	/**
	 * Returns the value of the '<em><b>Operators</b></em>' containment reference list.
	 * The list contents are of type {@link edu.toronto.cs.se.mmint.mid.operator.Operator}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The list of managed operator types or operator instances.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Operators</em>' containment reference list.
	 * @see edu.toronto.cs.se.mmint.mid.MIDPackage#getMultiModel_Operators()
	 * @model containment="true"
	 * @generated
	 */
	EList<Operator> getOperators();

	/**
	 * Returns the value of the '<em><b>Extendible Table</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link edu.toronto.cs.se.mmint.mid.ExtendibleElement},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The hash map for all element types or element instances in the MID, using the uri as key.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Extendible Table</em>' map.
	 * @see edu.toronto.cs.se.mmint.mid.MIDPackage#getMultiModel_ExtendibleTable()
	 * @model mapType="edu.toronto.cs.se.mmint.mid.EStringToExtendibleElementMap<org.eclipse.emf.ecore.EString, edu.toronto.cs.se.mmint.mid.ExtendibleElement>"
	 * @generated
	 */
	EMap<String, ExtendibleElement> getExtendibleTable();

	/**
	 * Returns the value of the '<em><b>Level</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.toronto.cs.se.mmint.mid.MIDLevel}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The metalevel (types: TYPES; instances: INSTANCES).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Level</em>' attribute.
	 * @see edu.toronto.cs.se.mmint.mid.MIDLevel
	 * @see #setLevel(MIDLevel)
	 * @see edu.toronto.cs.se.mmint.mid.MIDPackage#getMultiModel_Level()
	 * @model required="true"
	 * @generated
	 */
	MIDLevel getLevel();

	/**
	 * Sets the value of the '{@link edu.toronto.cs.se.mmint.mid.MultiModel#getLevel <em>Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Level</em>' attribute.
	 * @see edu.toronto.cs.se.mmint.mid.MIDLevel
	 * @see #getLevel()
	 * @generated
	 */
	void setLevel(MIDLevel value);

} // MultiModel
