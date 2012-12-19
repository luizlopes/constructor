package br.com.ealbornoz.constructor.impl;

import java.io.Serializable;

import br.com.ealbornoz.constructor.api.FormulaProcessor;
import br.com.ealbornoz.constructor.api.FormulaProcessorFactory;

public class RhinoFormulaProcessorFactory implements FormulaProcessorFactory, Serializable {

	@Override
	public FormulaProcessor createFormulaProcessor() {
		return new RhinoFormulaProcessor();
	}

}
