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
package edu.toronto.cs.se.modelepedia.istar_mavo.operator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.toronto.cs.se.mavo.MAVOElement;
import edu.toronto.cs.se.mmint.MMINTException;
import edu.toronto.cs.se.mmint.mavo.library.MAVOUtils;
import edu.toronto.cs.se.mmint.mid.Model;
import edu.toronto.cs.se.mmint.mid.library.MultiModelOperatorUtils;
import edu.toronto.cs.se.mmint.mid.ui.MultiModelDiagramUtils;
import edu.toronto.cs.se.modelepedia.istar_mavo.IStar;
import edu.toronto.cs.se.modelepedia.istar_mavo.Intention;
import edu.toronto.cs.se.modelepedia.z3.Z3IncrementalSolver;
import edu.toronto.cs.se.modelepedia.z3.Z3Utils;
import edu.toronto.cs.se.modelepedia.z3.Z3IncrementalSolver.Z3IncrementalBehavior;
import edu.toronto.cs.se.modelepedia.z3.Z3Model;
import edu.toronto.cs.se.modelepedia.z3.Z3Model.Z3Bool;

public class REJ14 extends FASE14 {

	private final static String PROPERTY_IN_MODELCONSTRAINT = "modelConstraint";
	private final static String PROPERTY_IN_MODELCONSTRAINT_DEFAULT = null;
	private final static String PROPERTY_IN_GENERATETARGETSCONCRETIZATION = "generateTargetsConcretization";
	private final static Boolean PROPERTY_IN_GENERATETARGETSCONCRETIZATION_DEFAULT = false;

	// input
	private boolean timeAnalysisEnabled;
	private boolean timeRNFEnabled;
	private String modelConstraint;
	private boolean generateTargetsConcretization;

	@Override
	public void readInputProperties(Properties inputProperties) throws MMINTException {

		super.readInputProperties(inputProperties);
		timeAnalysisEnabled = MultiModelOperatorUtils.getBoolProperty(inputProperties, PROPERTY_OUT_TIMEANALYSIS+MultiModelOperatorUtils.PROPERTY_IN_OUTPUTENABLED_SUFFIX);
		timeRNFEnabled = MultiModelOperatorUtils.getBoolProperty(inputProperties, PROPERTY_OUT_TIMERNF+MultiModelOperatorUtils.PROPERTY_IN_OUTPUTENABLED_SUFFIX);
		modelConstraint = MultiModelOperatorUtils.getOptionalStringProperty(inputProperties, PROPERTY_IN_MODELCONSTRAINT, PROPERTY_IN_MODELCONSTRAINT_DEFAULT);
		generateTargetsConcretization = MultiModelOperatorUtils.getOptionalBoolProperty(inputProperties, PROPERTY_IN_GENERATETARGETSCONCRETIZATION, PROPERTY_IN_GENERATETARGETSCONCRETIZATION_DEFAULT);
	}

	@Override
	public void init() throws MMINTException {

		super.init();

		// state
		if (modelConstraint != null) {
			smtEncoding += Z3Utils.assertion(modelConstraint);
		}
	}

	@Override
	protected Z3Model doTargets(Z3IncrementalSolver z3IncSolver) {

		long extraTime = 0;
		if (!timeAnalysisEnabled) {
			long startTime = System.nanoTime();
			z3IncSolver.firstCheckSatAndGetModel(smtEncoding);
			extraTime = System.nanoTime() - startTime;
		}
		Z3Model z3Model = super.doTargets(z3IncSolver);
		timeTargets += extraTime;

		return z3Model;
	}

	private String[] getConcretization(IStar istar, Z3Model z3Model) {

		//TODO MMINT[Z3] Change api names, make this process an api, make nodes+edges an api
		//TODO MMINT[MAVO] Rethink mavo apis to include all of this
		/*TODO
		 * mavoModelObjs = formulaVar->mavoModelObj
		 * smtNodes/smtEdges = int->formulaVar
		 * getZ3ModelNodes()/getZ3ModelEdges() = universe->int
		 * S: x concretizationVar to same formulaVar
		 * V: 1 concretizationVar to x formulaVar
		 */
		String concretization = "", smtConcretizationConstraint = "";
		Map<String, List<String>> z3ModelElems = new HashMap<String, List<String>>();
		Map<String, String> z3ModelElemsTemp = z3ModelParser.getZ3MAVOModelElements(z3Model);
		for (Entry<String, String> z3ModelElem : z3ModelElemsTemp.entrySet()) {
			String z3ModelElemUniverse = z3ModelElem.getKey();
			String z3ModelElemFormulaVar = z3ModelElem.getValue();
			List<String> z3ModelElemFormulaVars = z3ModelElems.get(z3ModelElemUniverse);
			if (z3ModelElemFormulaVars == null) {
				z3ModelElemFormulaVars = new ArrayList<String>();
				z3ModelElems.put(z3ModelElemUniverse, z3ModelElemFormulaVars);
			}
			z3ModelElemFormulaVars.add(z3ModelElemFormulaVar);
		}
		for (Entry<String, MAVOElement> mavoModelObjEntry : mavoModelObjs.entrySet()) {
			MAVOElement mavoModelObj = mavoModelObjEntry.getValue();
			String formulaVar = mavoModelObjEntry.getKey();
			String sort = mavoModelObj.eClass().getName();
			String function = encodeMAVConstraintFunction(mavoModelObj);
			int counterMS = 0;
			List<String> mergedV = null;
			for (List<String> z3ModelElemFormulaVars : z3ModelElems.values()) {
				if (z3ModelElemFormulaVars.contains(formulaVar)) {
					counterMS++;
					mergedV = z3ModelElemFormulaVars;
				}
			}
			boolean isNegation;
			String smtConstraint = "";
			if (mavoModelObj.isMay()) {
				isNegation = (counterMS == 0);
				if (isNegation) {
					concretization += formulaVar + " deleted (M)" + System.lineSeparator();
				}
				smtConstraint = encodeMConstraint(sort, function, formulaVar, isNegation);
			}
			if (mavoModelObj.isSet() && counterMS > 0) {
				isNegation = (counterMS > 1);
				if (isNegation) {
					concretization += formulaVar + " split into " + counterMS + " (S)" + System.lineSeparator();
				}
				smtConstraint = encodeSConstraint(sort, function, formulaVar, isNegation);
			}
			if (mavoModelObj.isVar() && counterMS > 0) {
				isNegation = (mergedV.size() > 1);
				if (isNegation) {
					concretization += formulaVar + " merged with " + mergedV + " (V)" + System.lineSeparator();
				}
				else {
					mergedV = MAVOUtils.getMergeableFormulaVars(istar, mavoModelObj);
					if (mergedV.size() == 0) {
						continue;
					}
				}
				smtConstraint = encodeVConstraint(sort, function, formulaVar, mergedV, isNegation);
			}
			smtConcretizationConstraint += smtConstraint;
		}
		concretization += System.lineSeparator();
		Map<String, Intention> intentions = super.collectIntentions(istar);
		getConcretizationAnalysisLabels(intentions, z3Model);
		for (Map.Entry<String, Intention> entry : intentions.entrySet()) {
			concretization += entry.getKey() + "(" + writeIntentionLabels(entry.getValue()) + ") ";
		}
		smtConcretizationConstraint = Z3Utils.assertion(Z3Utils.not(Z3Utils.and(smtConcretizationConstraint)));

		return new String[] {concretization, smtConcretizationConstraint};
	}

	private IStar copyIStarRootModelObj() {

		IStar istarCopy = EcoreUtil.copy(istar);
		Map<String, Intention> intentions = super.collectIntentions(istarCopy);
		for (Intention intention : intentions.values()) {
			for (SMTLIBLabel label : SMTLIBLabel.values()) {
				intention.eSet(label.getModelFeature(), false);
			}
		}

		return istarCopy;
	}

	@Override
	public EList<Model> execute(EList<Model> actualParameters) throws Exception {

		Model istarModel = actualParameters.get(0);

		// run
		collectAnalysisModelObjs(istarModel);
		Z3IncrementalSolver z3IncSolver = new Z3IncrementalSolver();
		if (timeAnalysisEnabled) {
			doAnalysis(z3IncSolver);
		}
		if (timeTargetsEnabled) {
			Z3Model z3Model = doTargets(z3IncSolver);
			if (targets == Z3Bool.SAT) {
				if (timeRNFEnabled) {
					doRNF(z3IncSolver, z3Model);
				}
				if (generateTargetsConcretization) {
					while (true) {
						String[] concretization = getConcretization(copyIStarRootModelObj(), z3Model);
						//TODO MMINT[MAVO] Integrate with mu-mmint code to show concretization model
						if (!MultiModelDiagramUtils.getBooleanInput("Concretization", concretization[0] + System.lineSeparator() + System.lineSeparator() + "Do you want another concretization?")) {
							break;
						}
						//TODO MMINT[TOSEM] Integrate with tosem allsat to negate current concretization
						z3Model = z3IncSolver.checkSatAndGetModel(concretization[1], Z3IncrementalBehavior.NORMAL);
						if (z3Model.getZ3Bool() != Z3Bool.SAT) {
							break;
						}
					}
				}
			}
		}

		// output
		Properties outputProperties = new Properties();
		writeProperties(outputProperties);
		MultiModelOperatorUtils.writePropertiesFile(
			outputProperties,
			this,
			istarModel,
			null,
			MultiModelOperatorUtils.OUTPUT_PROPERTIES_SUFFIX
		);
		if (timeRNF != -1) {
			writeRNF(istarModel);
		}

		return actualParameters;
	}

}
