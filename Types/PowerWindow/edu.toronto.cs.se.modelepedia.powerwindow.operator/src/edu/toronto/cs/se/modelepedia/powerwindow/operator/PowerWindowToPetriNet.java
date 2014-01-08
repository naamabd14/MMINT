/**
 * Copyright (c) 2013 Marsha Chechik, Alessio Di Sandro, Michalis Famelis,
 * Rick Salay.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Alessio Di Sandro - Implementation.
 */
package edu.toronto.cs.se.modelepedia.powerwindow.operator;

import java.util.Date;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import edu.toronto.cs.se.mmtf.mavo.library.MultiModelMAVOInstanceFactory;
import edu.toronto.cs.se.mmtf.mid.Model;
import edu.toronto.cs.se.mmtf.mid.ModelOrigin;
import edu.toronto.cs.se.mmtf.mid.MultiModel;
import edu.toronto.cs.se.mmtf.mid.library.MultiModelRegistry;
import edu.toronto.cs.se.mmtf.mid.library.MultiModelTypeIntrospection;
import edu.toronto.cs.se.mmtf.mid.library.MultiModelUtils;
import edu.toronto.cs.se.mmtf.mid.operator.impl.ConversionOperatorExecutableImpl;
import edu.toronto.cs.se.modelepedia.petrinet.PetriNet;
import edu.toronto.cs.se.modelepedia.petrinet.PetriNetFactory;
import edu.toronto.cs.se.modelepedia.petrinet.PetriNetPackage;
import edu.toronto.cs.se.modelepedia.petrinet.Place;
import edu.toronto.cs.se.modelepedia.powerwindow.Window;

public class PowerWindowToPetriNet extends ConversionOperatorExecutableImpl {

	private static final int DELAY_BOUND = 50;
	private static final String FILE_SUFFIX = "_pw2pn";
	private Model newPetrinetModel;

	@Override
	public EList<Model> execute(EList<Model> actualParameters) throws Exception {

		// convert
		Model windowModel = actualParameters.get(0);
		Window window = (Window) MultiModelTypeIntrospection.getRoot(windowModel);
		PetriNet newPetrinet = PetriNetFactory.eINSTANCE.createPetriNet();
		if (window.getSensor() != null && window.getSensor().getDelay() < DELAY_BOUND) {
			Place newPlace = PetriNetFactory.eINSTANCE.createPlace();
			newPetrinet.getNodes().add(newPlace);
		}

		// serialize
		String newPetrinetModelUri = MultiModelUtils.replaceFileExtensionInUri(windowModel.getUri(), PetriNetPackage.eNAME);
		newPetrinetModelUri = MultiModelUtils.addFileNameSuffixInUri(newPetrinetModelUri, FILE_SUFFIX + (new Date()).getTime());
		MultiModelUtils.createModelFile(newPetrinet, newPetrinetModelUri, true);

		// create model
		MultiModel multiModel = MultiModelRegistry.getMultiModel(windowModel);
		newPetrinetModel = MultiModelMAVOInstanceFactory.createModelAndEditor(null, newPetrinetModelUri, ModelOrigin.CREATED, multiModel);

		EList<Model> result = new BasicEList<Model>();
		result.add(newPetrinetModel);

		return result;
	}

	@Override
	public void cleanup() throws Exception {

		if (newPetrinetModel != null) {
			MultiModelUtils.deleteModelFile(newPetrinetModel);
			newPetrinetModel = null;
		}
	}

}