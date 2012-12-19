package br.com.ealbornoz.constructor.impl;

import java.util.Set;

import javax.persistence.Temporal;

import br.com.ealbornoz.constructor.api.AttributeValue;
import br.com.ealbornoz.constructor.api.Category;
import br.com.ealbornoz.constructor.api.Schema;
import br.com.ealbornoz.constructor.api.Template;
import br.com.ealbornoz.constructor.api.TemplateType;

/**
 * Realiza teste para a classe TemplateDefault.
 * 
 * @author guilherme
 * 
 */
public class TemplateDefaultTest extends AbstractTemplateTest {

	private static final String LARGURA = "LARGURA";
	private static final String LARGURA_VALUE = "10";
	private static final String PESO = "PESO";
	private static final String PESO_VALUE = "1";

	private Template templateDefault;

	@Override
	public Template getMasterTemplate() {
		return templateDefault;
	}

	@Override
	public void addTemplates() {
		Schema schema = templateDefault.getSchema().getSchema(MOTOR);
		Template template = new TemplateDefault(schema, MOTOR);
		templateDefault.addTemplate(template);
	}

	@Override
	protected void setUp() throws Exception {
		init();
	}

	public void init() {
		Schema schema = new SchemaImpl(PORTA);

		schema.addSchema(new SchemaImpl(MOTOR));
		schema.addAttribute(new AttributeImpl(LARGURA, Category.VARIABLE));
		schema.addAttribute(new AttributeImpl(PESO, Category.CALCULATED));

		templateDefault = new TemplateDefault(schema, PORTA);
	}

	public void testTemplateSelected() {
		try {
			templateDefault.setSelectedTemplate(null);
			assertFalse(true);
		} catch (Exception e) {
			assertEquals(UnsupportedOperationException.class, e.getClass());
		}
	}

	public void testSelectTemplate() {
		try {
			templateDefault.getSelectedTemplate();
			assertFalse(true);
		} catch (Exception e) {
			assertEquals(UnsupportedOperationException.class, e.getClass());
		}

	}

	public void testSetFormula() {
		try {
			templateDefault.setFormula(null);
			assertFalse(true);
		} catch (Exception e) {
			assertEquals(UnsupportedOperationException.class, e.getClass());
		}

	}

	public void testGetFormula() {
		try {
			templateDefault.getFormula();
			assertFalse(true);
		} catch (Exception e) {
			assertEquals(UnsupportedOperationException.class, e.getClass());
		}
	}

	public void testTemplateType() {
		assertEquals(TemplateType.DEFAULT, templateDefault.getTemplateType());
	}

	public void testIsTemplateDefault() {
		assertTrue(templateDefault.isTemplateDefault());
	}

	@Override
	public void testGetAttributeValue() {
		assertNull(templateDefault.getAttributeValue(LARGURA));
		templateDefault.setValue(LARGURA, LARGURA_VALUE);
		assertEquals(LARGURA_VALUE, templateDefault.getAttributeValue(LARGURA)
				.getString());
	}

	@Override
	public void testAttributeValueList() {
		assertEquals(0, templateDefault.getAttributeValueList().size());
		templateDefault.setValue(LARGURA, LARGURA_VALUE);
		assertEquals(1, templateDefault.getAttributeValueList().size());
	}

	@Override
	public void testSetAttributeValueWithFormulaForInvalidAttribute() {
		try {
			templateDefault.setValue(LARGURA, LARGURA_VALUE, null);
			assertFalse(true);
		} catch (Exception e) {
			assertEquals(IllegalArgumentException.class, e.getClass());
		}

	}

	@Override
	public void testSetAttributeValueWithFormula() {
		templateDefault.setValue(PESO, PESO_VALUE, null);
	}
	
	public void testFindVariableAttributeSet() {
		
		templateDefault.setValue(LARGURA, "1");
		
		Set<AttributeValue> set = templateDefault.findVariableAttributeSet();
		
		assertFalse(set.isEmpty());
		
	}

}
