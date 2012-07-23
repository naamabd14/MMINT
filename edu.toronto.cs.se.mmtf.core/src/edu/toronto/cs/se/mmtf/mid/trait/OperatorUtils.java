/*
 * Copyright (c) 2012 Marsha Chechik, Alessio Di Sandro, Michalis Famelis,
 * Rick Salay, Vivien Suen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Alessio Di Sandro - Implementation.
 */
package edu.toronto.cs.se.mmtf.mid.trait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;

import edu.toronto.cs.se.mmtf.MMTFException;
import edu.toronto.cs.se.mmtf.mid.Model;
import edu.toronto.cs.se.mmtf.mid.operator.Operator;
import edu.toronto.cs.se.mmtf.mid.operator.OperatorExecutable;

public class OperatorUtils {

	public static final String INPUT_PROPERTIES_SUFFIX = "In";
	public static final String OUTPUT_PROPERTIES_SUFFIX = "Out";
	private static final String PROPERTIES_SUFFIX = ".properties";
	/** The separator for multiple properties with the same key. */
	private static final String PROPERTY_SEPARATOR = ",";
	private static final String PROPERTY_UPDATEMID = "updateMid";

	private static String getBaseUri(OperatorExecutable operator, Model anyOperatorParameter, String subdirName, boolean createSubdir) {

		String workspaceUri = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
		String projectUri = anyOperatorParameter.getUri().substring(0, anyOperatorParameter.getUri().lastIndexOf(IPath.SEPARATOR)+1);
		String operatorName = ((Operator) operator.eContainer()).getName();
		String baseUri = workspaceUri + projectUri;
		if (subdirName != null) {
			if (createSubdir) {
				File dir = new File(baseUri + subdirName);
				dir.mkdir();
				baseUri += dir.getName() + IPath.SEPARATOR;
			}
			else {
				baseUri += subdirName + IPath.SEPARATOR;
			}
		}
		baseUri += operatorName;

		return baseUri;
	}

	public static Properties getInputPropertiesFile(OperatorExecutable operator, Model anyOperatorParameter, String subdirName, boolean createSubdir) throws Exception {

		String inputPropertiesFile =
			getBaseUri(operator, anyOperatorParameter, subdirName, createSubdir) +
			INPUT_PROPERTIES_SUFFIX +
			PROPERTIES_SUFFIX;
		Properties inputProperties = new Properties();
		inputProperties.load(new FileInputStream(inputPropertiesFile));

		return inputProperties;
	}

	public static void writePropertiesFile(Properties outputProperties, OperatorExecutable operator, Model anyOperatorParameter, String subdirName, boolean createSubdir, String suffix) throws Exception {

		String outputPropertiesFile =
			getBaseUri(operator, anyOperatorParameter, subdirName, createSubdir) +
			suffix +
			PROPERTIES_SUFFIX;
		outputProperties.store(new FileOutputStream(outputPropertiesFile), "");
	}

	public static String getStringProperty(Properties properties, String propertyName) throws MMTFException {

		String property = properties.getProperty(propertyName);
		if (property == null) {
			throw new MMTFException("Missing property " + propertyName);
		}

		return property;
	}

	public static int getIntProperty(Properties properties, String propertyName) throws MMTFException {

		int property = Integer.parseInt(getStringProperty(properties, propertyName));
		if (property == -1) {
			property = Integer.MAX_VALUE;
		}

		return property;
	}

	public static double getDoubleProperty(Properties properties, String propertyName) throws MMTFException {

		double property = Double.parseDouble(getStringProperty(properties, propertyName));
		if (property == -1) {
			property = Double.MAX_VALUE;
		}

		return property;
	}

	public static String[] getStringProperties(Properties properties, String propertyName) throws MMTFException {

		return getStringProperty(properties, propertyName).split(PROPERTY_SEPARATOR);
	}

	public static boolean isUpdatingMid(Properties properties) {

		try {
			return Boolean.parseBoolean(getStringProperty(properties, PROPERTY_UPDATEMID));
		}
		catch (MMTFException e) {
			return true;
		}
	}

}
