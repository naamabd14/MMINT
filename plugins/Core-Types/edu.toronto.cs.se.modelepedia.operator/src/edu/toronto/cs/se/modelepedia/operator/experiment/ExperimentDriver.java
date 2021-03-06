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
 *    Vivien Suen - Implementation.
 */
package edu.toronto.cs.se.modelepedia.operator.experiment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import edu.toronto.cs.se.mmint.MMINT;
import edu.toronto.cs.se.mmint.MMINTException;
import edu.toronto.cs.se.mmint.MultiModelTypeRegistry;
import edu.toronto.cs.se.mmint.MMINTException.Type;
import edu.toronto.cs.se.mmint.mid.Model;
import edu.toronto.cs.se.mmint.mid.library.MultiModelOperatorUtils;
import edu.toronto.cs.se.mmint.mid.library.MultiModelUtils;
import edu.toronto.cs.se.mmint.mid.operator.Operator;
import edu.toronto.cs.se.mmint.mid.operator.impl.OperatorImpl;
import edu.toronto.cs.se.mmint.mid.operator.impl.RandomOperatorImpl;
import edu.toronto.cs.se.modelepedia.operator.experiment.ExperimentSamples.DistributionType;

public class ExperimentDriver extends OperatorImpl {

	protected class ExperimentWatchdog implements Runnable {

		private ExperimentDriver driver;
		private EList<Model> actualParameters;
		private int experimentIndex;
		private boolean[] outputConfidences;

		public ExperimentWatchdog(ExperimentDriver driver, EList<Model> actualParameters, int experimentIndex) {

			this.driver = driver;
			this.actualParameters = actualParameters;
			this.experimentIndex = experimentIndex;
			outputConfidences = new boolean[outputs.length];
			for (int out = 0; out < outputs.length; out++) {
				outputConfidences[out] = (outputDoConfidences[out]) ? false : true;
			}
		}

		@Override
		public void run() {

			try {
				// create experiment folder
				Model initialModel = actualParameters.get(0);
				IFolder folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(new Path(MultiModelUtils.replaceLastSegmentInUri(initialModel.getUri(), EXPERIMENT_SUBDIR + experimentIndex)));
				if (!folder.exists(null)) {
					folder.create(true, true, null);
				}
				List<Operator> operatorChain = new ArrayList<Operator>();
				EList<Model> outerParameters = actualParameters;
				for (int op = 0; op < experimentOperators.length; op++) {
					try {
						outerParameters = executeOperator(experimentIndex, -1, op, experimentOperators[op], outerParameters, operatorChain, outputConfidences);
					}
					catch (Exception e) {
						MMINTException.print(Type.WARNING, "Experiment " + experimentIndex + " out of " + (numExperiments-1) + " failed", e);
						MultiModelOperatorUtils.writePropertiesFile(
							writeProperties(null, experimentIndex),
							driver,
							initialModel,
							EXPERIMENT_SUBDIR + experimentIndex,
							MultiModelOperatorUtils.OUTPUT_PROPERTIES_SUFFIX
						);
						return;
					}
				}
	
				ExperimentSamples[] experiment = new ExperimentSamples[outputs.length];
				for (int out = 0; out < outputs.length; out++) {
					experiment[out] = new ExperimentSamples(minSamples, maxSamples - skipWarmupSamples, distribution, outputMins[out], outputMaxs[out], requestedConfidence, outputDoConfidences[out]);
				}
	
				// inner cycle: experiment setup is fixed, vary randomness and statistics
				int j;
				for (j = 0; j < maxSamples; j++) {
					// create sample folder
					folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(new Path(MultiModelUtils.replaceLastSegmentInUri(initialModel.getUri(), EXPERIMENT_SUBDIR + experimentIndex + MMINT.URI_SEPARATOR + SAMPLE_SUBDIR + j)));
					if (!folder.exists(null)) {
						folder.create(true, true, null);
					}
					EList<Model> innerParameters = outerParameters;
					boolean timedOut = false;
					// run time-bounded chain of operators
					ExecutorService executor = Executors.newSingleThreadExecutor();
					try {
						executor.submit(
							new SampleWatchdog(experimentIndex, j, innerParameters, operatorChain, outputConfidences)
						).get(maxProcessingTime, TimeUnit.SECONDS);
						executor.shutdown();
					}
					catch (Exception e) {
						executor.shutdownNow();
						timedOut = true;
						MMINTException.print(Type.WARNING, "Experiment " + experimentIndex + " out of " + (numExperiments-1) + ", sample " + j + " ran over time limit", e);
					}
					// skip warmup phase
					if (j < skipWarmupSamples) {
						continue;
					}
					// get results
					for (int out = 0; out < outputs.length; out++) {
						try {
							double sample = (timedOut) ?
								outputDefaults[out] :
								getOutput(initialModel, out, experimentIndex, j);
							if (sample == Double.MAX_VALUE) {
								MMINTException.print(Type.WARNING, "Experiment " + experimentIndex + " out of " + (numExperiments-1) + ", sample " + j + ", output " + outputs[out] + " skipped", null);
								continue;
							}
							outputConfidences[out] = experiment[out].addSample(sample);
						}
						catch (Exception e) {
							MMINTException.print(Type.WARNING, "Experiment " + experimentIndex + " out of " + (numExperiments-1) + ", sample " + j + ", output " + outputs[out] + " not available", e);
						}
					}
					// evaluate confidence intervals
					boolean allConfidence = true;
					for (int out = 0; out < outputs.length; out++) {
						allConfidence = outputConfidences[out] && allConfidence;
					}
					if (allConfidence) {
						break;
					}
				}
	
				// save output
				MultiModelOperatorUtils.writePropertiesFile(
					writeProperties(experiment, experimentIndex),
					driver,
					initialModel,
					EXPERIMENT_SUBDIR + experimentIndex,
					MultiModelOperatorUtils.OUTPUT_PROPERTIES_SUFFIX
				);
				writeGnuplotFile(driver, initialModel, experiment, experimentIndex, varX);
			}
			catch (Exception e) {
				MMINTException.print(Type.WARNING, "Experiment " + experimentIndex + " out of " + (numExperiments-1) + " failed", e);
			}
		}
	}

	protected class SampleWatchdog implements Runnable {

		private int experimentIndex;
		private int statisticsIndex;
		private EList<Model> parameters;
		private List<Operator> operatorChain;
		private boolean[] outputConfidences;

		public SampleWatchdog(int experimentIndex, int statisticsIndex, EList<Model> parameters, List<Operator> operatorChain, boolean[] outputConfidences) {

			this.experimentIndex = experimentIndex;
			this.statisticsIndex = statisticsIndex;
			this.parameters = parameters;
			this.operatorChain = operatorChain;
			this.outputConfidences = outputConfidences;
		}

		@Override
		public void run() {

			System.err.println("Running experiment " + experimentIndex + " out of " + (numExperiments-1) + ", sample " + statisticsIndex);
			for (int op = 0; op < statisticsOperators.length; op++) {
				try {
					parameters = executeOperator(experimentIndex, statisticsIndex, op, statisticsOperators[op], parameters, operatorChain, outputConfidences);
				}
				catch (Exception e) {
					MMINTException.print(Type.WARNING, "Experiment " + experimentIndex + " out of " + (numExperiments-1) + ", sample " + statisticsIndex + " failed", e);
					return;
				}
			}
		}

	}

	/** The variables to variate the experiment setup. */
	private static final String PROPERTY_IN_VARIABLES = "variables";
	/** The variable values property suffix. */
	private static final String PROPERTY_IN_VARIABLEVALUES_SUFFIX = ".values";
	private static final String PROPERTY_IN_VARIABLEX_SUFFIX = ".varX";
	/** The initial seed for the pseudorandom generator. */
	private static final String PROPERTY_IN_SEED = "seed";
	/** Number of samples to discard at the beginning of each experiment (warmup phase). */
	private static final String PROPERTY_IN_SKIPWARMUPSAMPLES = "skipWarmupSamples";
	/** Min number of iterations (i.e. samples to generate). */
	private static final String PROPERTY_IN_MINSAMPLES = "minSamples";
	/** Max number of iterations (i.e. samples to generate). */
	private static final String PROPERTY_IN_MAXSAMPLES = "maxSamples";
	/** The distribution type to be used when evaluating the confidence. */
	private static final String PROPERTY_IN_DISTRIBUTIONTYPE = "distributionType";
	/** The requested range of confidence interval (% with respect to average value), after which the experiment can be stopped. */
	private static final String PROPERTY_IN_REQUESTEDCONFIDENCE = "requestedConfidence";
	private static final String PROPERTY_IN_NUMTHREADS = "numThreads";
	private static final int PROPERTY_IN_NUMTHREADS_DEFAULT = 1;
	/** The operators to be launched in the outer experiment setup cycle. */
	private static final String PROPERTY_IN_EXPERIMENTOPERATORS = "experimentOperators";
	private static final String[] PROPERTY_IN_EXPERIMENTOPERATORS_DEFAULT = new String[] {};
	/** The operators to be launched in the inner statistics cycle. */
	private static final String PROPERTY_IN_STATISTICSOPERATORS = "statisticsOperators";
	/** The variable operators property suffix. */
	private static final String PROPERTY_IN_ALLOPERATORS_SUFFIX = ".operator";
	/** The outputs of the experiment. */
	private static final String PROPERTY_IN_OUTPUTS = "outputs";
	/** The output default result property suffix. */
	private static final String PROPERTY_IN_OUTPUTDEFAULT_SUFFIX = ".defaultResult";
	/** Min value a sample can take. */
	private static final String PROPERTY_IN_OUTPUTMINSAMPLEVALUE_SUFFIX = ".minSampleValue";
	/** Max value a sample can take, -1 for unlimited. */
	private static final String PROPERTY_IN_OUTPUTMAXSAMPLEVALUE_SUFFIX = ".maxSampleValue";
	private static final String PROPERTY_IN_OUTPUTDOCONFIDENCE_SUFFIX = ".doConfidence";
	/** Max processing time to generate the outputs. */
	private static final String PROPERTY_IN_MAXPROCESSINGTIME = "maxProcessingTime";
	public static final String PROPERTY_OUT_RESULTLOW_SUFFIX = ".resultLow";
	public static final String PROPERTY_OUT_RESULTAVG_SUFFIX = ".resultAvg";
	public static final String PROPERTY_OUT_RESULTUP_SUFFIX = ".resultUp";
	private static final String PROPERTY_OUT_NUMSAMPLES_SUFFIX = ".numSamples";
	public static final String PROPERTY_OUT_VARIABLEINSTANCE_SUFFIX = ".instance";
	private static final String EXPERIMENT_SUBDIR = "experiment";
	private static final String SAMPLE_SUBDIR = "sample";
	private static final String GNUPLOT_SUFFIX = MultiModelOperatorUtils.OUTPUT_PROPERTIES_SUFFIX + ".dat";

	// experiment setup parameters
	private String[] vars;
	private String[][] varValues;
	private int varX;
	private int numExperiments;
	private String[][] experimentSetups;
	// experiment randomness parameters
	private long seed;
	private Random[] state;
	private int skipWarmupSamples;
	private int minSamples;
	private int maxSamples;
	private DistributionType distribution;
	private double requestedConfidence;
	// experiment operators
	private String[] experimentOperators;
	private String[] statisticsOperators;
	private String[][] varOperators;
	// experiment outputs
	private String[] outputs;
	private String[] outputOperators;
	private double[] outputDefaults;
	private double[] outputMins;
	private double[] outputMaxs;
	private boolean[] outputDoConfidences;
	// experiment efficiency
	private int maxProcessingTime;
	private int numThreads;

	@Override
	public void readInputProperties(Properties inputProperties) throws MMINTException {

		// outer cycle parameters: vary experiment setup
		vars = MultiModelOperatorUtils.getStringProperties(inputProperties, PROPERTY_IN_VARIABLES);
		varValues = new String[vars.length][];
		numExperiments = 1;
		for (int i = 0; i < vars.length; i++) {
			varValues[i] = MultiModelOperatorUtils.getStringProperties(inputProperties, vars[i]+PROPERTY_IN_VARIABLEVALUES_SUFFIX);
			numExperiments *= varValues[i].length;
			if (MultiModelOperatorUtils.getOptionalBoolProperty(inputProperties, vars[i]+PROPERTY_IN_VARIABLEX_SUFFIX, false)) {
				varX = i;
			}
		}

		// inner cycle parameters: experiment setup is fixed, vary randomness and statistics
		seed = MultiModelOperatorUtils.getIntProperty(inputProperties, PROPERTY_IN_SEED);
		skipWarmupSamples = MultiModelOperatorUtils.getIntProperty(inputProperties, PROPERTY_IN_SKIPWARMUPSAMPLES);
		minSamples = MultiModelOperatorUtils.getIntProperty(inputProperties, PROPERTY_IN_MINSAMPLES);
		maxSamples = MultiModelOperatorUtils.getIntProperty(inputProperties, PROPERTY_IN_MAXSAMPLES);
		distribution = DistributionType.valueOf(MultiModelOperatorUtils.getStringProperty(inputProperties, PROPERTY_IN_DISTRIBUTIONTYPE));
		requestedConfidence = MultiModelOperatorUtils.getDoubleProperty(inputProperties, PROPERTY_IN_REQUESTEDCONFIDENCE);

		// operators
		experimentOperators = MultiModelOperatorUtils.getOptionalStringProperties(inputProperties, PROPERTY_IN_EXPERIMENTOPERATORS, PROPERTY_IN_EXPERIMENTOPERATORS_DEFAULT);
		statisticsOperators = MultiModelOperatorUtils.getStringProperties(inputProperties, PROPERTY_IN_STATISTICSOPERATORS);
		varOperators = new String[vars.length][];
		for (int i = 0; i < vars.length; i++) {
			varOperators[i] = MultiModelOperatorUtils.getStringProperties(inputProperties, vars[i]+PROPERTY_IN_ALLOPERATORS_SUFFIX);
		}

		// outputs
		outputs = MultiModelOperatorUtils.getStringProperties(inputProperties, PROPERTY_IN_OUTPUTS);
		outputOperators = new String[outputs.length];
		outputDefaults = new double[outputs.length];
		outputMins = new double[outputs.length];
		outputMaxs = new double[outputs.length];
		outputDoConfidences = new boolean[outputs.length];
		for (int i = 0; i < outputs.length; i++) {
			outputOperators[i] = MultiModelOperatorUtils.getStringProperty(inputProperties, outputs[i]+PROPERTY_IN_ALLOPERATORS_SUFFIX);
			outputDefaults[i] = MultiModelOperatorUtils.getDoubleProperty(inputProperties, outputs[i]+PROPERTY_IN_OUTPUTDEFAULT_SUFFIX);
			outputMins[i] = MultiModelOperatorUtils.getDoubleProperty(inputProperties, outputs[i]+PROPERTY_IN_OUTPUTMINSAMPLEVALUE_SUFFIX);
			outputMaxs[i] = MultiModelOperatorUtils.getDoubleProperty(inputProperties, outputs[i]+PROPERTY_IN_OUTPUTMAXSAMPLEVALUE_SUFFIX);
			outputDoConfidences[i] = MultiModelOperatorUtils.getBoolProperty(inputProperties, outputs[i]+PROPERTY_IN_OUTPUTDOCONFIDENCE_SUFFIX);
		}

		// efficiency
		maxProcessingTime = MultiModelOperatorUtils.getIntProperty(inputProperties, PROPERTY_IN_MAXPROCESSINGTIME);
		numThreads = MultiModelOperatorUtils.getOptionalIntProperty(inputProperties, PROPERTY_IN_NUMTHREADS, PROPERTY_IN_NUMTHREADS_DEFAULT);
	}

	private Properties writeProperties(ExperimentSamples[] experiment, int experimentIndex) {

		Properties properties = new Properties();

		if (experiment != null) { // only with outputs
			for (int out = 0; out < outputs.length; out++) {
				properties.setProperty(outputs[out]+PROPERTY_OUT_RESULTAVG_SUFFIX, String.valueOf(experiment[out].getAverage()));
				if (outputDoConfidences[out]) {
					properties.setProperty(outputs[out]+PROPERTY_OUT_RESULTUP_SUFFIX, String.valueOf(experiment[out].getUpperConfidence()));
					properties.setProperty(outputs[out]+PROPERTY_OUT_RESULTLOW_SUFFIX, String.valueOf(experiment[out].getLowerConfidence()));
				}
				properties.setProperty(outputs[out]+PROPERTY_OUT_NUMSAMPLES_SUFFIX, String.valueOf(experiment[out].getNumSamples()));
			}
		}
		for (int i = 0; i < vars.length; i++) {
			properties.setProperty(vars[i]+PROPERTY_OUT_VARIABLEINSTANCE_SUFFIX, experimentSetups[experimentIndex][i]);
		}

		return properties;
	}

	private void writeGnuplotFile(Operator driver, Model initialModel, ExperimentSamples[] experiment, int experimentIndex, int varX) {

		if (experiment == null) { // no outputs
			return;
		}

		// append outputs
		StringBuilder gnuplotBuilder = new StringBuilder(experimentSetups[experimentIndex][varX]);
		for (int out = 0; out < outputs.length; out++) {
			gnuplotBuilder.append(' ');
			gnuplotBuilder.append(experiment[out].getAverage());
			if (outputDoConfidences[out]) {
				gnuplotBuilder.append(' ');
				gnuplotBuilder.append(experiment[out].getUpperConfidence());
				gnuplotBuilder.append(' ');
				gnuplotBuilder.append(experiment[out].getLowerConfidence());
			}
		}		

		// write output
		try {
			MultiModelOperatorUtils.writeTextFile(driver, initialModel, EXPERIMENT_SUBDIR + experimentIndex, GNUPLOT_SUFFIX, gnuplotBuilder);
		}
		catch (IOException e) {
			MMINTException.print(Type.WARNING, "Experiment " + experimentIndex + " out of " + (numExperiments-1) + ", gnuplot output failed", e);
		}
	}

	private void cartesianProduct(String[][] experimentSetups) {

		for (int i = 0; i < numExperiments; i++) {
			int k = 1;
			for (int j = 0; j < varValues.length; j++) {
				String[] value = varValues[j];
				String choice = value[(i/k) % value.length];
				experimentSetups[i][j] = choice;
				k *= value.length;
			}
		}
	}

	private EList<Model> executeOperator(int experimentIndex, int statisticsIndex, int operatorIndex, String operatorUri, EList<Model> actualParameters, List<Operator> operatorChain, boolean[] outputConfidences) throws Exception {

		// empty operator list
		if (operatorUri.equals("")) {
			return actualParameters;
		}

		// get operator
		Operator operator = MultiModelTypeRegistry.getType(operatorUri);
		if (operator == null) {
			throw new MMINTException("Operator uri " + operatorUri + " is not registered");
		}
		operatorChain.add(operator);
		int previousOperatorIndex = operatorChain.size() - 2;
		if (previousOperatorIndex >= 0) {
			operator.setPreviousOperator(operatorChain.get(previousOperatorIndex));
		}

		// write operator input properties
		Properties operatorProperties = new Properties();
		for (int i = 0; i < varOperators.length; i++) {
			for (int j = 0; j < varOperators[i].length; j++) {
				if (varOperators[i][j].equals(operatorUri)) {
					operatorProperties.setProperty(vars[i], experimentSetups[experimentIndex][i]);
					break;
				}
			}
		}
		for (int out = 0; out < outputs.length; out++) {
			if (outputDoConfidences[out] && operatorUri.equals(outputOperators[out])) {
				if (!outputConfidences[out]) {
					operatorProperties.setProperty(outputs[out]+MultiModelOperatorUtils.PROPERTY_IN_OUTPUTENABLED_SUFFIX, "true");
				}
				else {
					operatorProperties.setProperty(outputs[out]+MultiModelOperatorUtils.PROPERTY_IN_OUTPUTENABLED_SUFFIX, "false");
				}
			}
		}
		// figure out experiment subdirs
		String nextSubdir = null;
		if (operatorIndex == 0) {
			if (statisticsIndex < 0) {
				nextSubdir = EXPERIMENT_SUBDIR + experimentIndex;
			}
			else {
				nextSubdir = (experimentOperators.length == 0) ?
					EXPERIMENT_SUBDIR + experimentIndex + MMINT.URI_SEPARATOR + SAMPLE_SUBDIR + statisticsIndex:
					SAMPLE_SUBDIR + statisticsIndex;
			}
			operatorProperties.setProperty(MultiModelOperatorUtils.PROPERTY_IN_SUBDIR, nextSubdir);
			operator.setInputSubdir(nextSubdir);
		}
		// set state if operator needs it
		if (operator instanceof RandomOperatorImpl) {
			((RandomOperatorImpl) operator).setState(state[experimentIndex]);
		}
		// never update the mid, it will explode
		operatorProperties.setProperty(MultiModelOperatorUtils.PROPERTY_IN_UPDATEMID, "false");

		// execute and get state
		operator.readInputProperties(operatorProperties);
		operator.init();
		EList<Model> result = operator.execute(actualParameters);
		if (operator instanceof RandomOperatorImpl) {
			state[experimentIndex] = ((RandomOperatorImpl) operator).getState();
		}

		return result;
	}

	private double getOutput(Model initialModel, int outputIndex, int experimentIndex, int statisticsIndex) throws Exception {

		// get output operator
		Operator operator = MultiModelTypeRegistry.getType(outputOperators[outputIndex]);
		if (operator == null) {
			throw new MMINTException("Operator uri " + outputOperators[outputIndex] + " is not registered");
		}

		String experimentSubdir = EXPERIMENT_SUBDIR + experimentIndex + MMINT.URI_SEPARATOR + SAMPLE_SUBDIR + statisticsIndex;
		Properties resultProperties = MultiModelOperatorUtils.getPropertiesFile(
			operator,
			initialModel,
			experimentSubdir,
			MultiModelOperatorUtils.OUTPUT_PROPERTIES_SUFFIX
		);

		return MultiModelOperatorUtils.getDoubleProperty(resultProperties, outputs[outputIndex]);
	}

	@Override
	public EList<Model> execute(EList<Model> actualParameters) throws Exception {

		long startTime = System.nanoTime();

		// prepare experiment setup
		state = new Random[numExperiments];
		for (int i = 0; i < numExperiments; i++) {
			state[i] = new Random(seed + i);
		}
		experimentSetups = new String[numExperiments][vars.length];
		cartesianProduct(experimentSetups);

		ExecutorService executor = Executors.newFixedThreadPool(numThreads);
		// outer cycle: vary experiment setup
		for (int i = 0; i < numExperiments; i++) {

			// run time-bounded chain of experiments
			try {
				executor.submit(new ExperimentWatchdog(this, actualParameters, i));
			}
			catch (Exception e) {
				MMINTException.print(Type.WARNING, "Experiment " + i + " out of " + (numExperiments-1) + " failed", e);
			}
		}

		executor.shutdown();
		executor.awaitTermination(36, TimeUnit.HOURS);
		long endTime = System.nanoTime();
		System.err.println("The whole experiment driver took " + (endTime-startTime) + " nanoseconds");

		return null;
	}

}
