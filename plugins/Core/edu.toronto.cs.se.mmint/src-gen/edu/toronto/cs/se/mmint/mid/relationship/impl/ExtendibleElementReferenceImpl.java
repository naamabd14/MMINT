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
package edu.toronto.cs.se.mmint.mid.relationship.impl;

import edu.toronto.cs.se.mmint.mid.ExtendibleElement;
import edu.toronto.cs.se.mmint.mid.relationship.ExtendibleElementReference;
import edu.toronto.cs.se.mmint.mid.relationship.RelationshipPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Extendible Element Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.toronto.cs.se.mmint.mid.relationship.impl.ExtendibleElementReferenceImpl#getReferencedObject <em>Referenced Object</em>}</li>
 *   <li>{@link edu.toronto.cs.se.mmint.mid.relationship.impl.ExtendibleElementReferenceImpl#getContainedObject <em>Contained Object</em>}</li>
 *   <li>{@link edu.toronto.cs.se.mmint.mid.relationship.impl.ExtendibleElementReferenceImpl#getObject <em>Object</em>}</li>
 *   <li>{@link edu.toronto.cs.se.mmint.mid.relationship.impl.ExtendibleElementReferenceImpl#getSupertypeRef <em>Supertype Ref</em>}</li>
 *   <li>{@link edu.toronto.cs.se.mmint.mid.relationship.impl.ExtendibleElementReferenceImpl#isModifiable <em>Modifiable</em>}</li>
 *   <li>{@link edu.toronto.cs.se.mmint.mid.relationship.impl.ExtendibleElementReferenceImpl#getUri <em>Uri</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ExtendibleElementReferenceImpl extends MinimalEObjectImpl.Container implements ExtendibleElementReference {
	/**
	 * The cached value of the '{@link #getReferencedObject() <em>Referenced Object</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferencedObject()
	 * @generated
	 * @ordered
	 */
	protected ExtendibleElement referencedObject;

	/**
	 * The cached value of the '{@link #getContainedObject() <em>Contained Object</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainedObject()
	 * @generated
	 * @ordered
	 */
	protected ExtendibleElement containedObject;

	/**
	 * The cached setting delegate for the '{@link #getObject() <em>Object</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObject()
	 * @generated
	 * @ordered
	 */
	protected EStructuralFeature.Internal.SettingDelegate OBJECT__ESETTING_DELEGATE = ((EStructuralFeature.Internal)RelationshipPackage.Literals.EXTENDIBLE_ELEMENT_REFERENCE__OBJECT).getSettingDelegate();

	/**
	 * The cached value of the '{@link #getSupertypeRef() <em>Supertype Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSupertypeRef()
	 * @generated
	 * @ordered
	 */
	protected ExtendibleElementReference supertypeRef;

	/**
	 * The default value of the '{@link #isModifiable() <em>Modifiable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isModifiable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MODIFIABLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isModifiable() <em>Modifiable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isModifiable()
	 * @generated
	 * @ordered
	 */
	protected boolean modifiable = MODIFIABLE_EDEFAULT;

	/**
	 * The cached setting delegate for the '{@link #getUri() <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected EStructuralFeature.Internal.SettingDelegate URI__ESETTING_DELEGATE = ((EStructuralFeature.Internal)RelationshipPackage.Literals.EXTENDIBLE_ELEMENT_REFERENCE__URI).getSettingDelegate();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExtendibleElementReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RelationshipPackage.Literals.EXTENDIBLE_ELEMENT_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtendibleElement getReferencedObject() {
		if (referencedObject != null && referencedObject.eIsProxy()) {
			InternalEObject oldReferencedObject = (InternalEObject)referencedObject;
			referencedObject = (ExtendibleElement)eResolveProxy(oldReferencedObject);
			if (referencedObject != oldReferencedObject) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__REFERENCED_OBJECT, oldReferencedObject, referencedObject));
			}
		}
		return referencedObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtendibleElement basicGetReferencedObject() {
		return referencedObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReferencedObject(ExtendibleElement newReferencedObject) {
		ExtendibleElement oldReferencedObject = referencedObject;
		referencedObject = newReferencedObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__REFERENCED_OBJECT, oldReferencedObject, referencedObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtendibleElement getContainedObject() {
		return containedObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContainedObject(ExtendibleElement newContainedObject, NotificationChain msgs) {
		ExtendibleElement oldContainedObject = containedObject;
		containedObject = newContainedObject;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__CONTAINED_OBJECT, oldContainedObject, newContainedObject);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContainedObject(ExtendibleElement newContainedObject) {
		if (newContainedObject != containedObject) {
			NotificationChain msgs = null;
			if (containedObject != null)
				msgs = ((InternalEObject)containedObject).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__CONTAINED_OBJECT, null, msgs);
			if (newContainedObject != null)
				msgs = ((InternalEObject)newContainedObject).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__CONTAINED_OBJECT, null, msgs);
			msgs = basicSetContainedObject(newContainedObject, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__CONTAINED_OBJECT, newContainedObject, newContainedObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtendibleElement getObject() {
		return (ExtendibleElement)OBJECT__ESETTING_DELEGATE.dynamicGet(this, null, 0, true, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtendibleElement basicGetObject() {
		return (ExtendibleElement)OBJECT__ESETTING_DELEGATE.dynamicGet(this, null, 0, false, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtendibleElementReference getSupertypeRef() {
		if (supertypeRef != null && supertypeRef.eIsProxy()) {
			InternalEObject oldSupertypeRef = (InternalEObject)supertypeRef;
			supertypeRef = (ExtendibleElementReference)eResolveProxy(oldSupertypeRef);
			if (supertypeRef != oldSupertypeRef) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__SUPERTYPE_REF, oldSupertypeRef, supertypeRef));
			}
		}
		return supertypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtendibleElementReference basicGetSupertypeRef() {
		return supertypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSupertypeRef(ExtendibleElementReference newSupertypeRef) {
		ExtendibleElementReference oldSupertypeRef = supertypeRef;
		supertypeRef = newSupertypeRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__SUPERTYPE_REF, oldSupertypeRef, supertypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isModifiable() {
		return modifiable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModifiable(boolean newModifiable) {
		boolean oldModifiable = modifiable;
		modifiable = newModifiable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__MODIFIABLE, oldModifiable, modifiable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUri() {
		return (String)URI__ESETTING_DELEGATE.dynamicGet(this, null, 0, true, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__CONTAINED_OBJECT:
				return basicSetContainedObject(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__REFERENCED_OBJECT:
				if (resolve) return getReferencedObject();
				return basicGetReferencedObject();
			case RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__CONTAINED_OBJECT:
				return getContainedObject();
			case RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__OBJECT:
				if (resolve) return getObject();
				return basicGetObject();
			case RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__SUPERTYPE_REF:
				if (resolve) return getSupertypeRef();
				return basicGetSupertypeRef();
			case RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__MODIFIABLE:
				return isModifiable();
			case RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__URI:
				return getUri();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__REFERENCED_OBJECT:
				setReferencedObject((ExtendibleElement)newValue);
				return;
			case RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__CONTAINED_OBJECT:
				setContainedObject((ExtendibleElement)newValue);
				return;
			case RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__SUPERTYPE_REF:
				setSupertypeRef((ExtendibleElementReference)newValue);
				return;
			case RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__MODIFIABLE:
				setModifiable((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__REFERENCED_OBJECT:
				setReferencedObject((ExtendibleElement)null);
				return;
			case RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__CONTAINED_OBJECT:
				setContainedObject((ExtendibleElement)null);
				return;
			case RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__SUPERTYPE_REF:
				setSupertypeRef((ExtendibleElementReference)null);
				return;
			case RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__MODIFIABLE:
				setModifiable(MODIFIABLE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__REFERENCED_OBJECT:
				return referencedObject != null;
			case RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__CONTAINED_OBJECT:
				return containedObject != null;
			case RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__OBJECT:
				return OBJECT__ESETTING_DELEGATE.dynamicIsSet(this, null, 0);
			case RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__SUPERTYPE_REF:
				return supertypeRef != null;
			case RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__MODIFIABLE:
				return modifiable != MODIFIABLE_EDEFAULT;
			case RelationshipPackage.EXTENDIBLE_ELEMENT_REFERENCE__URI:
				return URI__ESETTING_DELEGATE.dynamicIsSet(this, null, 0);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (modifiable: ");
		result.append(modifiable);
		result.append(')');
		return result.toString();
	}

} //ExtendibleElementReferenceImpl
