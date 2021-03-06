package edu.toronto.cs.se.mmint;

import java.util.Random;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.toronto.cs.se.mmint.mid.operator.Operator;
import edu.toronto.cs.se.mmint.mid.operator.RandomOperator;

public class MMINTEcoreUtil extends EcoreUtil {

	public static <T extends EObject> T copy(T eObject) {

		Copier copier = new MMINTCopier();
		EObject result = copier.copy(eObject);
		copier.copyReferences();

		@SuppressWarnings("unchecked")T t = (T)result;
		return t;
	}

	public static class MMINTCopier extends Copier {

		private static final long serialVersionUID = 1L;

		@Override
		protected EObject createCopy(EObject eObject) {

			if (eObject instanceof Operator) {
				try {
					Operator operatorType = MultiModelTypeRegistry.getType(((Operator) eObject).getUri());
					if (operatorType != null) {
						Operator operatorTypeCopy = operatorType.getClass().newInstance();
						if (operatorTypeCopy instanceof RandomOperator) {
							((RandomOperator) operatorTypeCopy).setState(new Random());
						}
						return operatorTypeCopy;
					}
				}
				catch (Exception e) {
					// goto else
				}
			}

			return super.createCopy(eObject);
		}
	}

}
