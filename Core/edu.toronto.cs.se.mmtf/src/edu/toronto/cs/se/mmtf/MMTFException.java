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
package edu.toronto.cs.se.mmtf;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.PlatformUI;

/**
 * Custom exception for MMTF.
 *
 * @author Alessio Di Sandro
 * 
 */
public class MMTFException extends Exception {

	/** Exception types. */
	public enum Type {WARNING, ERROR}

	private static final long serialVersionUID = 1L;

	private Type type;

	//TODO MMTF: rework ->
	// 1) Use a private Type type, pass it in constructor and create a print function that inspects it.
	// 2) Everywhere an exception is used, clearly differentiate between mmtf exceptions and other ones.
	// 3) Make gmf commands aware of "real" errors or simple user cancellations (e.g. returning a cancel command)
	/**
	 * Prints an exception.
	 * 
	 * @param type
	 *            The exception category.
	 * @param message
	 *            The exception message.
	 * @param e
	 *            The exception or error.
	 */
	public static void print(Type type, String message, Throwable e) {

		switch (type) {
			case WARNING:
				message = "WARNING: " + message;
				break;
			case ERROR:
				message = "ERROR: " + message;
				break;
			default:
				message = "UNKNOWN PROBLEM: " + message;
		}
		System.err.println(message);
		if (e != null) {
			System.err.println(" -> " + e.toString());
		}
		if (type == Type.ERROR) {
			IStatus status = (e == null) ?
				new Status(IStatus.ERROR, "edu.toronto.cs.se.mmtf", "") :
				new Status(IStatus.ERROR, "edu.toronto.cs.se.mmtf", e.toString());
			ErrorDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "MMTF Error", message, status);
		}
	}

	public static void print(String message, Exception e) {

		Type type = (e instanceof MMTFException) ? ((MMTFException) e).getType() : Type.ERROR;
		print(type, message, e);
	}

	/**
	 * Constructor: initialises the superclass.
	 * 
	 * @param message
	 *            The exception message.
	 */
	public MMTFException(String message) {

		this(Type.WARNING, message);
	}

	/**
	 * Constructor: initialises the superclass with a chained exception.
	 * 
	 * @param message
	 *            The exception message.
	 * @param cause
	 *            The exception that caused this exception.
	 */
	public MMTFException(String message, Throwable cause) {

		this(Type.WARNING, message, cause);
	}

	public MMTFException(Type type, String message) {

		super(message);
		this.type = type;
	}

	public MMTFException(Type type, String message, Throwable cause) {

		super(message, cause);
		this.type = type;
	}

	public Type getType() {

		return type;
	}

}
