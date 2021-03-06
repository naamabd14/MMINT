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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.microsoft.z3.FuncDecl;
import com.microsoft.z3.FuncInterp;
import com.microsoft.z3.Z3Exception;
import com.microsoft.z3.FuncInterp.Entry;

import edu.toronto.cs.se.mmint.MMINTException;
import edu.toronto.cs.se.mmint.MultiModelTypeRegistry;
import edu.toronto.cs.se.mmint.MMINTException.Type;
import edu.toronto.cs.se.mmint.mid.Model;
import edu.toronto.cs.se.mmint.mid.library.MultiModelOperatorUtils;
import edu.toronto.cs.se.mmint.mid.operator.Operator;
import edu.toronto.cs.se.mmint.mid.operator.impl.OperatorImpl;
import edu.toronto.cs.se.modelepedia.istar_mavo.Actor;
import edu.toronto.cs.se.modelepedia.istar_mavo.IStar;
import edu.toronto.cs.se.modelepedia.istar_mavo.IStar_MAVOPackage;
import edu.toronto.cs.se.modelepedia.istar_mavo.Intention;
import edu.toronto.cs.se.modelepedia.z3.Z3Model;
import edu.toronto.cs.se.modelepedia.z3.Z3IncrementalSolver;
import edu.toronto.cs.se.modelepedia.z3.Z3Utils;
import edu.toronto.cs.se.modelepedia.z3.Z3IncrementalSolver.Z3IncrementalBehavior;
import edu.toronto.cs.se.modelepedia.z3.Z3Model.Z3Bool;
import edu.toronto.cs.se.modelepedia.z3.mavo.Z3MAVOModelParser;

public class RE13 extends OperatorImpl {

	protected enum SMTLIBLabel {

		fs(IStar_MAVOPackage.eINSTANCE.getIntention_FullySatisfied()),
		ps(IStar_MAVOPackage.eINSTANCE.getIntention_PartiallySatisfied()),
		un(IStar_MAVOPackage.eINSTANCE.getIntention_Unknown()),
		co(IStar_MAVOPackage.eINSTANCE.getIntention_Conflict()),
		pd(IStar_MAVOPackage.eINSTANCE.getIntention_PartiallyDenied()),
		fd(IStar_MAVOPackage.eINSTANCE.getIntention_FullyDenied()),
		no(IStar_MAVOPackage.eINSTANCE.getIntention_NoLabel());

		private final EStructuralFeature modelFeature;

		SMTLIBLabel(EStructuralFeature modelFeature) {
			this.modelFeature = modelFeature;
		}

		EStructuralFeature getModelFeature() {
			return modelFeature;
		}
	}

	protected static final String PREVIOUS_OPERATOR_URI = "http://se.cs.toronto.edu/modelepedia/Operator_IStarMAVOToSMTLIB";
	private static final String PROPERTY_IN_TARGETSPROPERTY = "targetsProperty";
	private static final String PROPERTY_IN_TARGETSPROPERTY_DEFAULT = "";
	private static final String PROPERTY_OUT_TIMEMODEL = "timeModel";
	protected static final String PROPERTY_OUT_TIMEANALYSIS = "timeAnalysis";
	private static final String PROPERTY_OUT_TIMETARGETS = "timeTargets";
	private static final String PROPERTY_OUT_LABELS_SUFFIX = ".labels";
	private static final String PROPERTY_OUT_TARGETS = "targets";

	protected static final String SMTLIB_CONCRETIZATION = " c ";
	private static final Set<String> Z3_MODEL_NODETYPES = new HashSet<String>();
	static {
		Z3_MODEL_NODETYPES.add("Task");
		Z3_MODEL_NODETYPES.add("Goal");
		Z3_MODEL_NODETYPES.add("SoftGoal");
		Z3_MODEL_NODETYPES.add("Resource");
	}

	// input
	private boolean timeModelEnabled;
	protected boolean timeTargetsEnabled;
	private String targetsProperty;
	// state
	protected IStar istar;
	protected Map<String, Intention> intentions;
	protected Set<String> initialIntentions;
	protected Z3MAVOModelParser z3ModelParser;
	protected String smtEncoding;
	// output
	private long timeModel;
	private long timeAnalysis;
	protected long timeTargets;
	protected Z3Bool targets;

	@Override
	public void readInputProperties(Properties inputProperties) throws MMINTException {

		super.readInputProperties(inputProperties);
		timeModelEnabled = MultiModelOperatorUtils.getBoolProperty(inputProperties, PROPERTY_OUT_TIMEMODEL+MultiModelOperatorUtils.PROPERTY_IN_OUTPUTENABLED_SUFFIX);
		timeTargetsEnabled = MultiModelOperatorUtils.getBoolProperty(inputProperties, PROPERTY_OUT_TIMETARGETS+MultiModelOperatorUtils.PROPERTY_IN_OUTPUTENABLED_SUFFIX);
		targetsProperty = MultiModelOperatorUtils.getOptionalStringProperty(inputProperties, PROPERTY_IN_TARGETSPROPERTY, PROPERTY_IN_TARGETSPROPERTY_DEFAULT);
	}

	@Override
	public void init() throws MMINTException {

		// state
		istar = null;
		intentions = new HashMap<String, Intention>();
		initialIntentions = new HashSet<String>();
		IStarMAVOToSMTLIB previousOperator = (getPreviousOperator() == null) ?
			(IStarMAVOToSMTLIB) MultiModelTypeRegistry.<Operator>getType(PREVIOUS_OPERATOR_URI) :
			(IStarMAVOToSMTLIB) getPreviousOperator();
		z3ModelParser = previousOperator.getZ3MAVOModelParser();
		smtEncoding = z3ModelParser.getSMTLIBEncoding();
		// output
		timeModel = -1;
		timeAnalysis = -1;
		timeTargets = -1;
		targets = Z3Bool.UNKNOWN;
	}

	protected String writeIntentionLabels(Intention intention) {

		String labels = "";
		for (SMTLIBLabel label : SMTLIBLabel.values()) {
			if ((boolean) intention.eGet(label.getModelFeature())) {
				labels += label.name() + ",";
			}
		}
		if (!labels.equals("")) {
			labels = labels.substring(0, labels.length()-1);
		}

		return labels;
	}

	protected void writeProperties(Properties properties) {

		properties.setProperty(PROPERTY_OUT_TIMEMODEL, String.valueOf(timeModel));
		properties.setProperty(PROPERTY_OUT_TIMEANALYSIS, String.valueOf(timeAnalysis));
		properties.setProperty(PROPERTY_OUT_TIMETARGETS, String.valueOf(timeTargets));
		properties.setProperty(PROPERTY_OUT_TARGETS, targets.toString());
		for (Map.Entry<String, Intention> entry : intentions.entrySet()) {
			properties.setProperty(entry.getKey()+PROPERTY_OUT_LABELS_SUFFIX, writeIntentionLabels(entry.getValue()));
		}
	}

	private void getConcretizationAnalysisLabel(Map<String, Intention> intentions, Map<String, String> z3ModelNodes, Set<String> z3LabelNodeUniverses, SMTLIBLabel label, String nodeType, boolean elseValue) {

		EStructuralFeature labelFeature = label.getModelFeature();
		if (elseValue) {
			for (Map.Entry<String, String> z3ModelNode : z3ModelNodes.entrySet()) {
				String z3ModelNodeUniverse = z3ModelNode.getKey();
				if (!z3ModelNodeUniverse.startsWith(nodeType)) {
					continue;
				}
				if (z3LabelNodeUniverses.contains(z3ModelNodeUniverse)) {
					continue;
				}
				String z3ModelNodeFormulaVar = z3ModelNode.getValue();
				intentions.get(z3ModelNodeFormulaVar).eSet(labelFeature, true);
			}
		}
		else {
			for (String z3LabelNodeUniverse : z3LabelNodeUniverses) {
				String z3LabelNodeFormulaVar = z3ModelNodes.get(z3LabelNodeUniverse);
				if (z3LabelNodeFormulaVar == null) { // result of a node all true function
					continue;
				}
				intentions.get(z3LabelNodeFormulaVar).eSet(labelFeature, true);
			}
		}
	}

	protected void getConcretizationAnalysisLabels(Map<String, Intention> intentions, Z3Model z3Model) {

		try {
			Map<String, String> z3ModelNodes = z3ModelParser.getZ3MAVOModelNodes(z3Model);
			com.microsoft.z3.Model z3InternalModel = z3Model.getZ3InternalModel();
			for (SMTLIBLabel label : SMTLIBLabel.values()) {
				for (FuncDecl decl : z3InternalModel.getFuncDecls()) {
					if (!(decl.getName().toString().equals(label.name()) || decl.getName().toString().contains(label.name()+Z3Utils.Z3_MODEL_SEPARATOR))) {
						continue;
					}
					String nodeType = decl.getDomain()[0].getName().toString();
					if (!Z3_MODEL_NODETYPES.contains(nodeType)) { // edge function
						continue;
					}
					FuncInterp interp = z3InternalModel.getFuncInterp(decl);
					if (interp.getEntries().length == 0) {// function that calls another function
						continue;
					}
					Set<String> z3LabelNodeUniverses = new HashSet<String>();
					for (Entry entry : interp.getEntries()) {
						z3LabelNodeUniverses.add(entry.getArgs()[0].toString());
					}
					getConcretizationAnalysisLabel(intentions, z3ModelNodes, z3LabelNodeUniverses, label, nodeType, Boolean.parseBoolean(interp.getElse().toString()));
				}
			}
		}
		catch (Z3Exception e) {
			MMINTException.print(Type.WARNING, "Can't optimize analysis, skipping it", e);
		}
	}

	protected void doAnalysis(Z3IncrementalSolver z3IncSolver) {

		long startTime = System.nanoTime();

		z3IncSolver.firstCheckSatAndGetModel(smtEncoding);
		if (timeModelEnabled) {
			timeModel = System.nanoTime() - startTime;
		}
		String intentionProperty, labelProperty;
		for (Map.Entry<String, Intention> entry : intentions.entrySet()) {
			String intentionFormulaVar = entry.getKey();
			Intention intention = entry.getValue();
			if (initialIntentions.contains(intentionFormulaVar)) { // skip intentions with initial label
				continue;
			}
			intentionProperty = Z3Utils.SMTLIB_ASSERT;
			if (intention.isMay()) {
				intentionProperty +=
					Z3Utils.SMTLIB_AND +
					Z3Utils.exists(
						Z3Utils.emptyPredicate(SMTLIB_CONCRETIZATION + intention.eClass().getName()),
						Z3Utils.predicate(Z3Utils.SMTLIB_NODE_FUNCTION, intentionFormulaVar + SMTLIB_CONCRETIZATION)
					)
				;
			}
			intentionProperty +=
				Z3Utils.SMTLIB_FORALL +
				Z3Utils.SMTLIB_PREDICATE_START +
				Z3Utils.emptyPredicate(SMTLIB_CONCRETIZATION + intention.eClass().getName()) +
				Z3Utils.SMTLIB_PREDICATE_END +
				Z3Utils.SMTLIB_IMPLICATION +
				Z3Utils.predicate(Z3Utils.SMTLIB_NODE_FUNCTION, intentionFormulaVar + SMTLIB_CONCRETIZATION)
			;
			for (SMTLIBLabel label : SMTLIBLabel.values()) {
				if ((boolean) intention.eGet(label.getModelFeature())) { // skip already checked
					continue;
				}
				labelProperty = intentionProperty + Z3Utils.predicate(Z3Utils.SMTLIB_PREDICATE_START + label.name(), SMTLIB_CONCRETIZATION) + Z3Utils.SMTLIB_PREDICATE_END + Z3Utils.SMTLIB_PREDICATE_END;
				if (intention.isMay()) {
					labelProperty += Z3Utils.SMTLIB_PREDICATE_END;
				}
				labelProperty += Z3Utils.SMTLIB_PREDICATE_END;
				Z3Model z3Model = z3IncSolver.checkSatAndGetModel(labelProperty, Z3IncrementalBehavior.POP);
				if (z3Model.getZ3Bool() == Z3Bool.SAT) {
					intention.eSet(label.getModelFeature(), true);
					getConcretizationAnalysisLabels(intentions, z3Model);
				}
			}
		}

		timeAnalysis = System.nanoTime() - startTime;
	}

	protected Z3Model doTargets(Z3IncrementalSolver z3IncSolver) {

		long startTime = System.nanoTime();

		Z3Model z3Model = z3IncSolver.checkSatAndGetModel(Z3Utils.assertion(targetsProperty), Z3IncrementalBehavior.NORMAL);
		targets = z3Model.getZ3Bool();

		timeTargets = System.nanoTime() - startTime;

		return z3Model;
	}

	protected Map<String, Intention> collectIntentions(IStar istar) {

		Map<String, Intention> intentions = new HashMap<String, Intention>();
		for (Actor actor : istar.getActors()) {
			for (Intention intention : actor.getIntentions()) {
				intentions.put(intention.getFormulaVariable(), intention);
			}
		}
		for (Intention intention : istar.getDependums()) {
			intentions.put(intention.getFormulaVariable(), intention);
		}

		return intentions;
	}

	protected void collectAnalysisModelObjs(Model istarModel) throws MMINTException {

		istar = (IStar) istarModel.getEMFInstanceRoot();
		intentions = collectIntentions(istar);
		for (Map.Entry<String, Intention> entry : intentions.entrySet()) {
			Intention intention = entry.getValue();
			if (
				intention.isFullySatisfied() ||
				intention.isPartiallySatisfied() ||
				intention.isUnknown() ||
				intention.isConflict() ||
				intention.isPartiallyDenied() ||
				intention.isFullyDenied() ||
				intention.isNoLabel()
			) {
				initialIntentions.add(entry.getKey());
			}
		}
	}

	@Override
	public EList<Model> execute(EList<Model> actualParameters) throws Exception {

		Model istarModel = actualParameters.get(0);

		// run solver
		collectAnalysisModelObjs(istarModel);
		Z3IncrementalSolver z3IncSolver = new Z3IncrementalSolver();
		doAnalysis(z3IncSolver);
		if (timeTargetsEnabled) {
			doTargets(z3IncSolver);
		}

		// save output
		Properties outputProperties = new Properties();
		writeProperties(outputProperties);
		MultiModelOperatorUtils.writePropertiesFile(
			outputProperties,
			this,
			istarModel,
			null,
			MultiModelOperatorUtils.OUTPUT_PROPERTIES_SUFFIX
		);

		return actualParameters;
	}

}
