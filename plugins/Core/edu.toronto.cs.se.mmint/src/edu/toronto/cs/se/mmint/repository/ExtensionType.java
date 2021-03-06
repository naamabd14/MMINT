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
package edu.toronto.cs.se.mmint.repository;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;

import edu.toronto.cs.se.mmint.MultiModelHeavyTypeFactory;
import edu.toronto.cs.se.mmint.mid.ExtendibleElement;

/**
 * An edu.toronto.cs.se.mmint.extendibles extension containing a "heavy" type.
 * 
 * @author Alessio Di Sandro
 * 
 */
public class ExtensionType {

	/** The uri of the extension type. */
	private String uri;
	/** The name of the extension type. */
	private String name;
	/** The uri of the supertype of the extension type. */
	private String supertypeUri;
	/** The factory of the extension type. */
	private MultiModelHeavyTypeFactory factory;
	/** The custom type for the extension type. */
	private ExtendibleElement newType;

	/**
	 * Constructor: reads the extension and gets the info for the extension
	 * type.
	 * 
	 * @param extensionConfig
	 *            The extension configuration.
	 */
	public ExtensionType(IConfigurationElement extensionConfig) {

		IConfigurationElement typeConfig = extensionConfig.getChildren(MMINTConstants.CHILD_TYPE)[0];
		uri = typeConfig.getAttribute(MMINTConstants.TYPE_ATTR_URI);
		name = typeConfig.getAttribute(MMINTConstants.TYPE_ATTR_NAME);
		IConfigurationElement[] supertypeConfigs = typeConfig.getChildren(MMINTConstants.TYPE_CHILD_SUPERTYPE);
		supertypeUri = (supertypeConfigs.length == 0) ?
			null :
			supertypeConfigs[0].getAttribute(MMINTConstants.TYPE_SUPERTYPE_ATTR_URI);
	}

	/**
	 * Constructor: reads the extension and gets the info for the extension
	 * type.
	 * 
	 * @param extensionConfig
	 *            The extension configuration.
	 * @param defaultFactory
	 *            The default "heavy" type factory.
	 */
	public ExtensionType(IConfigurationElement extensionConfig, MultiModelHeavyTypeFactory defaultFactory) {

		this(extensionConfig);
		IConfigurationElement typeConfig = extensionConfig.getChildren(MMINTConstants.CHILD_TYPE)[0];
		if (typeConfig.getAttribute(MMINTConstants.TYPE_ATTR_CLASS) == null) {
			factory = defaultFactory;
			newType = null;
		}
		else {
			try {
				Object object = typeConfig.createExecutableExtension(MMINTConstants.TYPE_ATTR_CLASS);
				if (object instanceof MultiModelHeavyTypeFactory) {
					factory = (MultiModelHeavyTypeFactory) object;
					newType = null;
				}
				else {
					factory = defaultFactory;
					newType = (ExtendibleElement) object;
				}
			}
			catch (Exception e) {
				factory = defaultFactory;
				newType = null;
			}
		}
	}

	/**
	 * Constructor: reads the extension and gets the info for the extension
	 * type, allowing for multiple inheritance.
	 * 
	 * @param extensionConfig
	 *            The extension configuration.
	 * @param multipleInheritanceTable
	 *            The table for multiple inheritance support.
	 * @param defaultFactory
	 *            The default "heavy" type factory.
	 */
	public ExtensionType(IConfigurationElement extensionConfig, Map<String, Set<String>> multipleInheritanceTable, MultiModelHeavyTypeFactory defaultFactory) {

		this(extensionConfig, defaultFactory);
		IConfigurationElement[] supertypeConfigs = extensionConfig.getChildren(MMINTConstants.CHILD_TYPE)[0].getChildren(MMINTConstants.TYPE_CHILD_SUPERTYPE);
		if (supertypeConfigs.length > 1) {
			for (int i = 1; i < supertypeConfigs.length; i++) {
				String multipleSupertypeUri = supertypeConfigs[i].getAttribute(MMINTConstants.TYPE_SUPERTYPE_ATTR_URI);
				Set<String> supertypes = multipleInheritanceTable.get(multipleSupertypeUri);
				if (supertypes == null) {
					supertypes = new HashSet<String>();
					multipleInheritanceTable.put(multipleSupertypeUri, supertypes);
				}
				supertypes.add(uri);
			}
		}
	}

	/**
	 * Gets the uri of the extension type.
	 * 
	 * @return The uri of the extension type.
	 */
	public String getUri() {

		return uri;
	}

	/**
	 * Gets the name of the extension type.
	 * 
	 * @return The name of the extension type.
	 */
	public String getName() {

		return name;
	}

	/**
	 * Gets the uri of the supertype of the extension type.
	 * 
	 * @return The uri of the supertype of the extension type.
	 */
	public String getSupertypeUri() {

		return supertypeUri;
	}

	/**
	 * Gets the factory of the extension type.
	 * 
	 * @return The factory of the extension type.
	 */
	public MultiModelHeavyTypeFactory getFactory() {

		return factory;
	}

	/**
	 * Gets the custom type for the extension type.
	 * 
	 * @return The custom type for the extension type, null if the standard type
	 *         will be created by the factory.
	 */
	public ExtendibleElement getNewType() {

		return newType;
	}

}