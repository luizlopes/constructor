package br.com.ealbornoz.constructor.impl;

import java.util.ArrayList;

import junit.framework.Assert;
import junit.framework.TestCase;
import br.com.ealbornoz.constructor.api.AttributeValue;
import br.com.ealbornoz.constructor.api.Category;
import br.com.ealbornoz.constructor.api.Product;
import br.com.ealbornoz.constructor.api.ProductCost;
import br.com.ealbornoz.constructor.api.Schema;
import br.com.ealbornoz.constructor.api.Template;

public class ProductCostTest extends TestCase {
	
	private static final String ESTEIRA = "ESTEIRA";
	private static final String AREA = "AREA";
	private static final String AREA_DEFAULT_VALUE = "1";
	private static final String MOTOR = "MOTOR";
	private static final String PORTA = "PORTA";
	
	private Template motorTemplate;
	private TemplateCostImpl motorTemplateCost;
	
	private Template esteiraTemplate;
	private TemplateCostImpl esteiraTemplateCost;
	private ArrayList<TemplateCostImpl> templateCostList;
	private Template portaTemplate;
	
	public void setUp() {
		
		motorTemplate = createMotorTemplate();
		
		motorTemplateCost = createMotorTemplateCost(motorTemplate);
		
		esteiraTemplate = createEsteiraTemplate();
		
		esteiraTemplateCost = createEsteiraTemplateCost(esteiraTemplate);
		
		portaTemplate = createPortaTemplate();
		
		templateCostList = new ArrayList<TemplateCostImpl>();
		templateCostList.add(motorTemplateCost);
		templateCostList.add(esteiraTemplateCost);
	}
	
	public void testCalculateProductSimpleCost() {
		
		ProductImpl motorProduct = new ProductImpl(motorTemplate, null);
		
		Assert.assertEquals( new Double(99) , new ProductCostImpl( motorProduct , templateCostList ).getTotalCost() );
		
	}

	public void testCalculateSimpleProductCost() {
		
		Product esteiraProduct = new ProductImpl(esteiraTemplate, null);
		
		Assert.assertEquals( new Double(10) , new ProductCostImpl( esteiraProduct , templateCostList ).getTotalCost() );
		
		esteiraProduct.setValue(AREA, "2");
		
		Assert.assertEquals( new Double(20), new ProductCostImpl( esteiraProduct , templateCostList ).getTotalCost() );
		
	}
	
	public void testCalculateComplexProductCost() {
		
		ProductImpl esteiraProduct = new ProductImpl(esteiraTemplate, null);
		
		ProductImpl motorProduct = new ProductImpl(motorTemplate, null);
		
		// Produto Complexo - Produto formado por outros produtos
		Product portaProduct = new ProductImpl(portaTemplate, null);
		portaProduct.addProduct(esteiraProduct);
		portaProduct.addProduct(motorProduct);
		//

		ProductCostImpl portaProductCost = new ProductCostImpl( portaProduct , templateCostList );
		
		Double esteiraCost = new ProductCostImpl( esteiraProduct, templateCostList ).getTotalCost(); 
		Double motorCost = new ProductCostImpl( motorProduct , templateCostList ).getTotalCost();
		
		Assert.assertEquals( motorCost + esteiraCost , portaProductCost.getTotalCost() );
		Assert.assertEquals( new Double(0) , portaProductCost.getCost() );
	}

	public void testOwnerProductCost() {
		
		Product esteiraProduct = new ProductImpl(esteiraTemplate, null);
		
		Product motorProduct = new ProductImpl(motorTemplate, null);
		
		// Produto Complexo - Produto formado por outros produtos
		Product portaProduct = new ProductImpl(portaTemplate, null);
		portaProduct.addProduct(esteiraProduct);
		portaProduct.addProduct(motorProduct);
		//
		
		ProductCost portaProductCost = new ProductCostImpl( portaProduct , templateCostList );
		
		ProductCost esteiraCost = portaProductCost.findChildByProduct(esteiraProduct); 
		
		Assert.assertEquals( portaProductCost , esteiraCost.getOwner() );
	}
	
	
	public void testCalculateCostForProductWithAmountGreaterThanOne() {
		
		Product esteiraProduct = new ProductImpl(esteiraTemplate, null);
		
		Double esteiraCostValue = new Double(10);
		Assert.assertEquals( esteiraCostValue , new ProductCostImpl( esteiraProduct , templateCostList ).getTotalCost() );
		
		esteiraProduct.setAmount(new Long(2));
		
		Assert.assertEquals( esteiraCostValue * 2 , new ProductCostImpl( esteiraProduct , templateCostList ).getTotalCost() );
	}
	
	public Template createPortaTemplate() {
		Schema porta = new SchemaImpl(PORTA);
		
		Template portaTemplate = new TemplateDefault(porta, PORTA);
		return portaTemplate;
	}
	
	public TemplateCostImpl createEsteiraTemplateCost(Template esteiraTemplate) {
		TemplateCostImpl esteiraCost = new TemplateCostImpl(esteiraTemplate);
		AttributeValue attributeValue = esteiraTemplate.getAttributeValue(AREA);
		esteiraCost.setAttributeValue( attributeValue );
		esteiraCost.setCost( new Double(10) );
		return esteiraCost;
	}
	
	public Template createEsteiraTemplate() {
		Schema esteira = new SchemaImpl(ESTEIRA);
		esteira.addAttribute( new AttributeImpl(AREA , Category.CALCULATED) );
		
		Template esteiraTemplate = new TemplateDefault(esteira, ESTEIRA);
		esteiraTemplate.setValue(AREA, AREA_DEFAULT_VALUE, null);
		return esteiraTemplate;
	}

	public Template createMotorTemplate() {
		Schema motor = new SchemaImpl(MOTOR);
		Template template = new TemplateDefault(motor, MOTOR);
		return template;
	}
	
	public TemplateCostImpl createMotorTemplateCost(Template template) {
		TemplateCostImpl cost = new TemplateCostImpl(template);
		cost.setAttributeValue(null);
		cost.setCost(new Double(99));
		return cost;
	}
}

