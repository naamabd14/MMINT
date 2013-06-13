/**
 * Copyright (c) 2013 Marsha Chechik, Alessio Di Sandro, Michalis Famelis,
 * Rick Salay, Vivien Suen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Alessio Di Sandro - Implementation.
 */
package edu.toronto.cs.se.modelepedia.uml.constraint;

import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.PackageableElement;

import edu.toronto.cs.se.mmtf.MMTF;
import edu.toronto.cs.se.mmtf.mid.Model;
import edu.toronto.cs.se.mmtf.mid.constraint.JavaModelConstraint;
import edu.toronto.cs.se.mmtf.mid.library.MultiModelRegistry;
import edu.toronto.cs.se.mmtf.mid.library.MultiModelTypeIntrospection;
import edu.toronto.cs.se.mmtf.mid.relationship.BinaryModelRel;
import edu.toronto.cs.se.mmtf.mid.relationship.ModelElementReference;

public class ComponentsDeployedConstraint extends JavaModelConstraint {

	public ComponentsDeployedConstraint(Model model) {

		super(model);
	}

	@Override
	public boolean validate() {

		BinaryModelRel deplRel = (BinaryModelRel) model;
		org.eclipse.uml2.uml.Model srcUmlModel = (org.eclipse.uml2.uml.Model) MultiModelTypeIntrospection.getRoot(deplRel.getSourceModel());
		for (PackageableElement umlModelObj : srcUmlModel.getPackagedElements()) {
			if (!(umlModelObj instanceof Component)) {
				continue;
			}
			String modelElemUri = MultiModelRegistry.getModelAndModelElementUris(umlModelObj, true)[1];
			boolean deployed = false;
			for (ModelElementReference modelElemRef : deplRel.getModelEndpointRefs().get(0).getModelElemRefs()) {
				if (
					modelElemUri.equals(modelElemRef.getUri().substring(0, modelElemRef.getUri().indexOf(MMTF.ROLE_SEPARATOR))) &&
					!modelElemRef.getModelElemEndpointRefs().isEmpty()
				) {
					deployed = true;
					break;
				}
			}
			if (!deployed) {
				return false;
			}
		}
		return true;
	}

}