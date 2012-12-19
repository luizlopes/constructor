package br.com.ealbornoz.constructor.impl;

import br.com.ealbornoz.constructor.api.Category;
import br.com.ealbornoz.constructor.api.Schema;
import br.com.ealbornoz.constructor.api.Template;
import br.com.ealbornoz.constructor.api.TemplateType;

public class TemplateGroupTest extends AbstractTemplateTest {

	private static final String PESO = "PESO";
	private static final String REGRA_MOTOR = "REGRA DE MOTOR";
	private static final String MOTOR1 = "MOTOR 1";
	private static final String PESO_MOTOR_1 = "1";
	private static final String MOTOR2 = "MOTOR 2";
	private static final String PESO_MOTOR_2 = "3";
	private static final String MOTOR3 = "MOTOR 3";
	private static final String PESO_MOTOR_3 = "10";
	private Template templateGroup;

	@Override
	public Template getMasterTemplate() {
		return templateGroup;
	}

	@Override
	public void init() {
		Schema motorSchema = new SchemaImpl(MOTOR);
		motorSchema.addAttribute(new AttributeImpl(PESO, Category.FIXED));

		Template motor1 = new TemplateDefault(motorSchema, MOTOR1);
		motor1.setValue(PESO, PESO_MOTOR_1);

		Template motor2 = new TemplateDefault(motorSchema, MOTOR2);
		motor2.setValue(PESO, PESO_MOTOR_2);

		Template motor3 = new TemplateDefault(motorSchema, MOTOR3);
		motor3.setValue(PESO, PESO_MOTOR_3);

		templateGroup = new TemplateGroup(motorSchema, REGRA_MOTOR);
		templateGroup.addTemplate(motor1);
		templateGroup.addTemplate(motor2);
		templateGroup.addTemplate(motor3);
	}

	@Override
	public void testTemplateSelected() {
		templateGroup.setSelectedTemplate(null);
	}

	@Override
	public void testSelectTemplate() {
		assertNull(templateGroup.getSelectedTemplate());
		Template motorTemplate = templateGroup.getTemplateAmount(MOTOR2)
				.getTemplate();
		templateGroup.setSelectedTemplate(motorTemplate);
		assertEquals(motorTemplate, templateGroup.getSelectedTemplate());
	}

	@Override
	public void testSetFormula() {
		templateGroup.setFormula(null);
	}

	@Override
	public void testGetFormula() {
		templateGroup.getFormula();
	}

	@Override
	public void testTemplateType() {
		assertEquals(TemplateType.GROUP, templateGroup.getTemplateType());
	}

	@Override
	public void testIsTemplateDefault() {
		assertFalse(templateGroup.isTemplateDefault());
	}

	@Override
	public void addTemplates() {
		Schema schema = templateGroup.getSchema();
		Template motorTemplate = new TemplateDefault(schema, MOTOR);
		templateGroup.addTemplate(motorTemplate);
	}

	@Override
	public void testAttributeValueList() {
		try {
			templateGroup.getAttributeValueList();
			assertFalse(true);
		} catch (Exception e) {
			assertEquals(UnsupportedOperationException.class, e.getClass());
		}

	}

	@Override
	public void testGetAttributeValue() {
		try {
			templateGroup.getAttributeValue(null);
			assertFalse(true);
		} catch (Exception e) {
			assertEquals(UnsupportedOperationException.class, e.getClass());
		}
	}

	@Override
	public void testSetAttributeValueWithFormula() {
		try {
			templateGroup.setValue(null, null, null);
			assertFalse(true);
		} catch (Exception e) {
			assertEquals(UnsupportedOperationException.class, e.getClass());
		}

	}

	@Override
	public void testSetAttributeValueWithFormulaForInvalidAttribute() {
		try {
			templateGroup.setValue(null, null, null);
			assertFalse(true);
		} catch (Exception e) {
			assertEquals(UnsupportedOperationException.class, e.getClass());
		}
	}

	public void testSetValue() {
		try {
			templateGroup.setValue(null, null);
			assertFalse(true);
		} catch (Exception e) {
			assertEquals(UnsupportedOperationException.class, e.getClass());
		}

	}

	public void testAddTemplateWithInvalidSchema() {
		try {
			Schema schema = new SchemaImpl(ESTEIRA);
			Template templateInvalid = new TemplateDefault(schema, ESTEIRA);
			templateGroup.addTemplate(templateInvalid);
			assertFalse(true);
		} catch (Exception e) {
			assertEquals(IllegalArgumentException.class, e.getClass());
		}
	}

}
