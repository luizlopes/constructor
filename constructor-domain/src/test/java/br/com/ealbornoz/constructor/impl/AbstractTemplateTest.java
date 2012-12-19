package br.com.ealbornoz.constructor.impl;

import junit.framework.TestCase;
import br.com.ealbornoz.constructor.api.Template;
import br.com.ealbornoz.constructor.api.TemplateAmount;


public abstract class AbstractTemplateTest extends TestCase {
	
	protected static final String MOTOR = "MOTOR";
	
	protected static final String ESTEIRA = "ESTEIRA";
	
	protected static final String PORTA = "PORTA";
	
	public abstract void addTemplates();
	
	public abstract Template getMasterTemplate();
	
	public abstract void init();
	
	public abstract void testTemplateSelected();
	
	public abstract void testSelectTemplate();
	
	public abstract void testSetFormula();
	
	public abstract void testGetFormula();
	
	public abstract void testTemplateType();

	public abstract void testIsTemplateDefault();
	
	public abstract void testAttributeValueList();
	
	public abstract void testGetAttributeValue();
	
	public abstract void testSetAttributeValueWithFormula();
	
	public abstract void testSetAttributeValueWithFormulaForInvalidAttribute();
	
	@Override
	protected void setUp() throws Exception {
		init();
	}

	public void testGetTemplateInEmptyList() {
		try {
			getMasterTemplate().getTemplateAmount(MOTOR);
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		}
	}

	public void testGetTemplate() {
		addTemplates();
		TemplateAmount template = getMasterTemplate().getTemplateAmount(MOTOR);
		assertEquals( MOTOR , template.getName() );
	}
	
	public void testHasTemplate() {
		assertFalse( getMasterTemplate().hasTemplate(MOTOR) );
		addTemplates();
		assertTrue( getMasterTemplate().hasTemplate(MOTOR) );
	}
	
	public void testRemoveTemplate() {
		getMasterTemplate().removeTemplate(ESTEIRA);
		addTemplates();
		assertTrue( getMasterTemplate().hasTemplate(MOTOR) );
		getMasterTemplate().removeTemplate(MOTOR);
		assertFalse( getMasterTemplate().hasTemplate(MOTOR) );
	}

}
