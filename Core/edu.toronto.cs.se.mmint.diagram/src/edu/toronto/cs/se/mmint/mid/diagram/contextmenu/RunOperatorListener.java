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
package edu.toronto.cs.se.mmint.mid.diagram.contextmenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.PlatformUI;

import edu.toronto.cs.se.mmint.MMINTException;
import edu.toronto.cs.se.mmint.MMINTException.Type;
import edu.toronto.cs.se.mmint.mid.Model;
import edu.toronto.cs.se.mmint.mid.MultiModel;
import edu.toronto.cs.se.mmint.mid.operator.ConversionOperator;
import edu.toronto.cs.se.mmint.mid.operator.Operator;
import edu.toronto.cs.se.mmint.mid.ui.GMFDiagramUtils;

public class RunOperatorListener extends SelectionAdapter {

	MultiModel instanceMid;
	Operator operator;
	EList<Model> actualParameters;
	Map<Integer, EList<ConversionOperator>> conversionMap;

	public RunOperatorListener(MultiModel instanceMid, Operator operator, EList<Model> actualParameters, Map<Integer, EList<ConversionOperator>> conversionMap) {

		this.instanceMid = instanceMid;
		this.operator = operator;
		this.actualParameters = actualParameters;
		this.conversionMap = conversionMap;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {

		List<IFile> files = new ArrayList<IFile>();
		IFile diagramFile = (IFile) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
		IFile modelFile = diagramFile.getParent().getFile(new Path(diagramFile.getName().substring(0, diagramFile.getName().length() - GMFDiagramUtils.DIAGRAM_SUFFIX.length())));
		files.add(diagramFile);
		files.add(modelFile);
		AbstractTransactionalCommand operatorCommand = new RunOperatorCommand(
			TransactionUtil.getEditingDomain(instanceMid),
			"Run operator",
			files
		);
		try {
			OperationHistoryFactory.getOperationHistory().execute(operatorCommand, null, null);
		}
		catch (ExecutionException ex) {
			MMINTException.print(Type.ERROR, "Operator " + operator.getName() + " history execution error", ex);
		}
	}

	protected class RunOperatorCommand extends AbstractTransactionalCommand {

		public RunOperatorCommand(TransactionalEditingDomain domain, String label, List<IFile> affectedFiles) {

			super(domain, label, affectedFiles);
		}

		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

			try {
				//TODO MMINT: is conversionMap ordered?? I don't think so
				// run all conversion operators
				if (!conversionMap.isEmpty()) {
					for (Entry<Integer, EList<ConversionOperator>> entry : conversionMap.entrySet()) {
						int i = entry.getKey();
						List<ConversionOperator> conversionList = entry.getValue();
						Model newActualParameter = actualParameters.get(i);
						for (ConversionOperator operator : conversionList) {
							EList<Model> operatorParameters = new BasicEList<Model>();
							operatorParameters.add(newActualParameter);
							Properties inputProperties = operator.getInputProperties();
							operator.readInputProperties(inputProperties);
							operator.init();
							newActualParameter = operator.execute(operatorParameters).get(0);
						}
						actualParameters.set(i, newActualParameter);
					}
				}
				// run operator
				Properties inputProperties = operator.getInputProperties();
				operator.readInputProperties(inputProperties);
				operator.init();
				operator.execute(actualParameters);
				// cleanup all conversion operators
				if (!conversionMap.isEmpty()) {
					for (Entry<Integer, EList<ConversionOperator>> entry : conversionMap.entrySet()) {
						for (ConversionOperator operator : entry.getValue()) {
							((ConversionOperator) operator).cleanup();
						}
					}
				}

				return CommandResult.newOKCommandResult();
			}
			catch (Exception e) {
				MMINTException.print(Type.ERROR, "Operator " + operator.getName() + " execution error", e);
				return CommandResult.newErrorCommandResult("Operator " + operator.getName() + " execution error");
			}
		}

	}

}
