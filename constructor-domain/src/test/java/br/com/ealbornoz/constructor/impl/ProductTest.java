package br.com.ealbornoz.constructor.impl;

import junit.framework.TestCase;
import br.com.ealbornoz.constructor.api.AttributeValue;
import br.com.ealbornoz.constructor.api.Category;
import br.com.ealbornoz.constructor.api.Formula;
import br.com.ealbornoz.constructor.api.Product;
import br.com.ealbornoz.constructor.api.Schema;
import br.com.ealbornoz.constructor.api.Template;

/**
 * Realiza testes na interface Product.
 * 
 * @author guilherme
 * 
 */
public class ProductTest extends TestCase {

	private static final double PESO_BASE_CALCULO = 0.20;
	private static final String ALTURA_VALUE = "10";
	private static final String LARGURA_VALUE = "5";
	private static final String PORTA = "PORTA";
	private static final String LARGURA = "LARGURA";
	private static final String ALTURA = "ALTURA";
	private static final String MOTOR = "MOTOR";
	private static final String CARGA = "CARGA";
	private static final String ESTEIRA = "ESTEIRA";
	private static final String PESO = "PESO";
	private static final String GRUPO_MOTOR = "GRUPO DE MOTORES";

	private Product portaProduct;

	public void setUp() {

		Schema motorSchema = createMotorSchema();

		Schema esteiraSchema = createEsteiraSchema();

		Schema portaSchema = createPortaSchema();
		portaSchema.addSchema(motorSchema);
		portaSchema.addSchema(esteiraSchema);

		Template motor10 = new TemplateDefault(motorSchema, MOTOR);
		motor10.setValue(CARGA, "10");

		Template motor20 = new TemplateDefault(motorSchema, MOTOR);
		motor20.setValue(CARGA, "20");

		Template motorGroup = createMotorGroup(motorSchema, motor10, motor20);

		Template esteiraTemplate = createEsteiraTemplate(esteiraSchema);

		Template portaTemplate = new TemplateDefault(portaSchema, PORTA);
		portaTemplate.addTemplate(motorGroup);
		portaTemplate.addTemplate(esteiraTemplate);
		portaTemplate.setValue(LARGURA, "1");
		portaTemplate.setValue(ALTURA, "1");

		portaProduct = new ProductImpl(portaTemplate,
				new RhinoFormulaProcessorFactory());
		portaProduct.setValue(LARGURA, LARGURA_VALUE);
		portaProduct.setValue(ALTURA, ALTURA_VALUE);
	}

	private Template createEsteiraTemplate(Schema esteiraSchema) {
		Template esteiraTemplate = new TemplateDefault(esteiraSchema, ESTEIRA);
		esteiraTemplate.setValue(LARGURA, "1");
		esteiraTemplate.setValue(ALTURA, "1");

		StringBuilder formula = new StringBuilder();
		formula.append("var peso = ").append(PESO_BASE_CALCULO);
		formula.append(" * product.getAttributeValue(\"ALTURA\").getDouble() * product.getAttributeValue(\"LARGURA\").getDouble();");
		formula.append("product.setValue(\"PESO\", peso);");

		Formula formulaPeso = new FormulaImpl();
		formulaPeso.setLanguage("Java Script");
		formulaPeso.setName("CALCULO PESO ESTEIRA");
		formulaPeso.setScript(formula.toString());

		esteiraTemplate.setValue(PESO, "1", formulaPeso);
		return esteiraTemplate;
	}

	private Template createMotorGroup(Schema motorSchema, Template motor10,
			Template motor20) {
		Template motorGroup = new TemplateGroup(motorSchema, GRUPO_MOTOR);
		motorGroup.addTemplate(motor10);
		motorGroup.addTemplate(motor20);
		Formula formulaMotor = new FormulaImpl();
		formulaMotor.setLanguage("Java Script");
		formulaMotor.setName("GRUPO MOTOR");
		StringBuilder script = new StringBuilder();
		script.append("var templateList = group.getTemplateAmountList(); ");
		script.append("var peso = product.getProduct(\"ESTEIRA\").getAttributeValue(\"PESO\").getDouble(); ");
		script.append("var selectedTemplate = null; ");
		script.append("var cargaSelecionada = null; ");
		script.append("for (var i = 0; i < group.getTemplateAmountList().size(); i++) { ");
		script.append("  var temp = templateList.get(i).getTemplate(); ");
		script.append("  var carga = temp.getAttributeValue(\"CARGA\").getDouble(); ");
		script.append("  if (carga > peso) { ");
		script.append("    if (selectedTemplate == null) { ");
		script.append("      selectedTemplate = temp; ");
		script.append("      cargaSelecionada = selectedTemplate.getAttributeValue(\"CARGA\").getDouble(); ");
		script.append("    } else if (carga < cargaSelecionada) { ");
		script.append("      selectedTemplate = temp; ");
		script.append("      cargaSelecionada = selectedTemplate.getAttributeValue(\"CARGA\").getDouble(); ");
		script.append("    } ");
		script.append("  } ");
		script.append("} ");
		script.append("group.setSelectedTemplate(selectedTemplate); ");
		formulaMotor.setScript(script.toString());
		motorGroup.setFormula(formulaMotor);
		return motorGroup;
	}

	private Schema createPortaSchema() {
		Schema portaSchema = new SchemaImpl(PORTA);
		portaSchema.addAttribute(new AttributeImpl(LARGURA, Category.VARIABLE));
		portaSchema.addAttribute(new AttributeImpl(ALTURA, Category.VARIABLE));
		return portaSchema;
	}

	private Schema createEsteiraSchema() {
		Schema esteiraSchema = new SchemaImpl(ESTEIRA);
		esteiraSchema
				.addAttribute(new AttributeImpl(LARGURA, Category.VARIABLE));
		esteiraSchema
				.addAttribute(new AttributeImpl(ALTURA, Category.VARIABLE));
		esteiraSchema
				.addAttribute(new AttributeImpl(PESO, Category.CALCULATED));
		return esteiraSchema;
	}

	private Schema createMotorSchema() {
		Schema motorSchema = new SchemaImpl(MOTOR);
		motorSchema.addAttribute(new AttributeImpl(CARGA, Category.FIXED));
		return motorSchema;
	}

	public void testIsRoot() {
		assertTrue(portaProduct.isRoot());
	}

	public void testGetProduct() {
		assertEquals(ESTEIRA, portaProduct.getProduct(ESTEIRA).getName());
	}

	public void testGetNullProduct() {
		assertNull(portaProduct.getProduct(null));
	}

	public void testHasProduct() {
		assertTrue(portaProduct.hasProduct(ESTEIRA));
	}

	public void testHasNullProduct() {
		assertFalse(portaProduct.hasProduct(null));
	}

	public void testRemoveProduct() {
		assertTrue(portaProduct.hasProduct(ESTEIRA));
		portaProduct.removeProduct(ESTEIRA);
		assertFalse(portaProduct.hasProduct(ESTEIRA));
	}

	public void testRemoveNullProduct() {
		try {
			assertFalse(portaProduct.hasProduct(null));
			portaProduct.removeProduct(null);
			assertFalse(true);
		} catch (Exception e) {
			assertEquals(ArrayIndexOutOfBoundsException.class, e.getClass());
		}
	}

	public void testAddProduct() {
		String guia = "GUIA";
		Schema guiaSchema = new SchemaImpl(guia);
		guiaSchema.addAttribute(new AttributeImpl(LARGURA, Category.VARIABLE));

		Template guiaTemplate = new TemplateDefault(guiaSchema, guia);
		guiaTemplate.setValue(LARGURA, "1");

		ProductImpl guiaProduct = new ProductImpl(guiaTemplate, null);
		portaProduct.addProduct(guiaProduct);

		assertTrue(portaProduct.hasProduct(guia));
		assertEquals(portaProduct, guiaProduct.getProductOwner());
	}

	public void testProductProcess() {

		AttributeValue alturaValue = portaProduct.getProduct(ESTEIRA)
				.getAttributeValue(ALTURA);
		AttributeValue larguraValue = portaProduct.getProduct(ESTEIRA)
				.getAttributeValue(LARGURA);
		AttributeValue pesoValue = portaProduct.getProduct(ESTEIRA)
				.getAttributeValue(PESO);

		assertEquals(new Long(ALTURA_VALUE), alturaValue.getLong());
		assertEquals(new Long(LARGURA_VALUE), larguraValue.getLong());
		assertEquals(new Long(1), pesoValue.getLong());

		portaProduct.process();

		pesoValue = portaProduct.getProduct(ESTEIRA).getAttributeValue(PESO);

		Double expectedPesoValue = new Double(new Long(ALTURA_VALUE)
				* new Long(LARGURA_VALUE) * PESO_BASE_CALCULO);
		assertEquals(expectedPesoValue, pesoValue.getDouble());

		assertEquals(20,
				portaProduct.getProduct("MOTOR").getAttributeValue(CARGA)
						.getDouble().longValue());
	}

	public void testAddTemplate() {
		assertTrue(true);
	}

	public void testDefaultAmount() {
		assertEquals(new Long(1), portaProduct.getAmount());
	}

	public void testSetQuantidade() {

		assertEquals(new Long(1), portaProduct.getAmount());
		assertEquals(new Long(1), portaProduct.getProduct(ESTEIRA).getAmount());

		portaProduct.setAmount(2L);

		assertEquals(new Long(2), portaProduct.getAmount());
		assertEquals(new Long(2), portaProduct.getProduct(ESTEIRA).getAmount());

	}

	public void testTemplateSetQuantidade() {

		Schema esteiraSchema = createEsteiraSchema();

		Template esteiraTemplate = createEsteiraTemplate(esteiraSchema);

		Schema portaSchema = createPortaSchema();
		portaSchema.addSchema(esteiraSchema);

		Template porta2Template = new TemplateDefault(portaSchema, PORTA);
		porta2Template.addTemplate(esteiraTemplate, 3L);

		Product produto = new ProductImpl(porta2Template,
				new RhinoFormulaProcessorFactory());

		assertEquals(new Long(3), produto.getProduct(ESTEIRA).getAmount());

	}

}
