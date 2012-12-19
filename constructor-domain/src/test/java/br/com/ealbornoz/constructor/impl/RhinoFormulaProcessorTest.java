package br.com.ealbornoz.constructor.impl;

import junit.framework.Assert;
import junit.framework.TestCase;
import br.com.ealbornoz.constructor.api.Formula;
import br.com.ealbornoz.constructor.api.FormulaProcessor;

public class RhinoFormulaProcessorTest extends TestCase {

	public void testSomeFormula() {
		StringBuilder multiplicacao = new StringBuilder();

		Formula multiplicacaoFormula = new FormulaImpl();
		multiplicacaoFormula.setName("MULTIPLICACAO");
		multiplicacaoFormula.setScript(" m.append(3 * 5); ");

		FormulaProcessor formulaProcessor = new RhinoFormulaProcessor();
		formulaProcessor.setFormula(multiplicacaoFormula);
		formulaProcessor.addObject(multiplicacao, "m");
		formulaProcessor.execute();

		Assert.assertEquals(new Double(3 * 5).toString(),
				multiplicacao.toString());
	}

	public void testSomeFormulaWithError() {

		try {
			StringBuilder multiplicacao = new StringBuilder();

			Formula multiplicacaoFormula = new FormulaImpl();
			multiplicacaoFormula.setName("DIVISAO_POR_ZERO");
			multiplicacaoFormula.setScript(" m.append(3 / 0); ");
			multiplicacaoFormula
					.setScript(" throw new java.lang.Exception(\"divisao por zero \"); ");

			FormulaProcessor formulaProcessor = new RhinoFormulaProcessor();
			formulaProcessor.setFormula(multiplicacaoFormula);
			formulaProcessor.addObject(multiplicacao, "m");
			formulaProcessor.execute();

			System.out.println(multiplicacao.toString());
		} catch (Exception e) {
			assertTrue(true);
		}

	}

}
