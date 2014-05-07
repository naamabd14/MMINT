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
package edu.toronto.cs.se.mmint.mavo;

import edu.toronto.cs.se.mmint.mavo.library.MAVOUtils;
import edu.toronto.cs.se.mmint.mid.Model;
import edu.toronto.cs.se.mmint.mid.constraint.MultiModelConstraintChecker.MAVOTruthValue;
import edu.toronto.cs.se.modelepedia.java.reasoning.IJavaModelConstraint;

public class MAVOModelConstraint implements IJavaModelConstraint {

	@Override
	public MAVOTruthValue validate(Model model) {

		if (MAVOUtils.isMAVOModel(model)) {
			return MAVOTruthValue.TRUE;
		}
		return MAVOTruthValue.FALSE;
	}

}