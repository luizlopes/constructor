package br.com.ealbornoz.constructor.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import br.com.ealbornoz.constructor.api.Formula;
import br.com.ealbornoz.constructor.api.FormulaProcessor;

public class RhinoFormulaProcessor implements FormulaProcessor, Serializable {

	private Formula formula;
	private Map<String, Object> objectMap = new HashMap<String, Object>();
	
	public RhinoFormulaProcessor() {
	}

	@Override
	public Formula getFormula() {
		return formula;
	}

	@Override
	public void setFormula(Formula formula) {
		this.formula = formula;
	}

	@Override
	public void execute() {
		Context cx = Context.enter();
		Scriptable scope = cx.initStandardObjects();
		
		for ( String name : objectMap.keySet() ) {
			Object wrappedOut = Context.javaToJS( objectMap.get(name) , scope );
			ScriptableObject.putProperty( scope , name , wrappedOut );
		}

		cx.evaluateString(scope, formula.getScript(), formula.getName(), 1,	null);
		
	}
	
	@Override
	public void addObject(Object object, String name) {
		objectMap.put(name, object);
	}

}
