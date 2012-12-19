package br.com.ealbornoz.constructor.impl;

import org.hibernate.FlushMode;
import org.hibernate.Session;

import br.com.ealbornoz.constructor.api.AttributeValue;
import br.com.ealbornoz.constructor.api.Category;
import br.com.ealbornoz.constructor.api.Formula;
import br.com.ealbornoz.constructor.api.Product;
import br.com.ealbornoz.constructor.api.Schema;
import br.com.ealbornoz.constructor.api.Template;
import br.com.ealbornoz.constructor.persistence.Dao;
import br.com.ealbornoz.constructor.persistence.impl.GenericDao;
import br.com.ealbornoz.constructor.persistence.util.HibernateUtil;

/**
 * @author guilherme
 */
public class SchemaFlowTest {
	
	Session session = HibernateUtil.getSession();
	
	public SchemaFlowTest() {
		System.out.println(session.getFlushMode());
	}
	
	public static void main(String[] args) {
		
		new SchemaFlowTest().ateste();

//		Schema s = new SchemaImpl("TESTE");
//		s.addAttribute(new AttributeImpl("G", Category.FIXED));
//		
//		System.out.println(s.hasAttribute("G"));
	}

	public void ateste() {

		session.beginTransaction();
		Schema schema = createEsteiraSchema();
		session.getTransaction().commit();
		
//		for (Schema s : schema.getSchemaList()) {
//			System.out.println(s.getName());
//		}
		
		session.beginTransaction();
		Template esteiraTemplate = createEsteiraTemplate(schema);
		session.getTransaction().commit();
		
		
		Dao<ProductImpl> dao = new GenericDao<ProductImpl>(session, ProductImpl.class);
		
		Product p = new ProductImpl(esteiraTemplate, new RhinoFormulaProcessorFactory() );
		System.out.println(p.getName());
		
		p.setValue("LARGURA", "100");
		
		p.setValue("ALTURA", "50");
		
		p.process();
		
		session.beginTransaction();
		dao.save((ProductImpl) p);
		session.getTransaction().commit();
		
		for (AttributeValue attrValue : p.getAttributeValueList()) {
			System.out.println(attrValue.getName() + " : " + attrValue.getString());
		}
		
		System.out.println(p.getAttributeValue("KILOGRAMA").getString());

		for (Product pp : p.getProductList()) {
			
			System.out.println("Produto : " + pp.getName());
			for (AttributeValue attrValue : pp.getAttributeValueList()) {
				System.out.println("   " + attrValue.getName() + ": " + attrValue.getString());
			}
		}
		
		session.close();
		
	}
	
	private Template createEsteiraTemplate(Schema schema) {
		
		Dao<TemplateDefault> dao = new GenericDao<TemplateDefault>(session, TemplateDefault.class);
		
		
		Schema motorSchema = schema.getSchema("MOTOR");
		Template motor20Template = new TemplateDefault(motorSchema, "MOTOR 20");
		motor20Template.setValue("CARGA", "20");
		dao.save((TemplateDefault) motor20Template);
		
		
		Template motor50Template = new TemplateDefault(motorSchema, "MOTOR 50");
		motor50Template.setValue("CARGA", "50");
		dao.save((TemplateDefault) motor50Template);
		
		
		Template motor500Template = new TemplateDefault(motorSchema, "MOTOR 500");
		motor500Template.setValue("CARGA", "500");
		dao.save((TemplateDefault) motor500Template);
		
		Template motor5000Template = new TemplateDefault(motorSchema, "MOTOR 5000");
		motor5000Template.setValue("CARGA", "5000");
		dao.save((TemplateDefault) motor5000Template);
		
		
		Template soleiraTemplate = new TemplateDefault(schema.getSchema("SOLEIRA"), "SOLEIRA PADRAO");
		soleiraTemplate.setValue("TIPO", "BORRACHA");
		soleiraTemplate.setValue("LARGURA", "0");
		
		Formula soleiraFormula = new FormulaImpl();
		soleiraFormula.setName("CALCULA PESO SOLEIRA PADRAO");
		soleiraFormula.setLanguage("JAVASCRIPT");
		soleiraFormula.setScript("var peso = 0.05 * product.getAttributeValue(\"LARGURA\").getDouble(); product.setValue(\"KILOGRAMA\", peso);");
		
		soleiraTemplate.setValue("KILOGRAMA", "0", soleiraFormula);
		dao.save((TemplateDefault) soleiraTemplate);
		
		
		Template esteiraTemplate = new TemplateDefault(schema, "ESTEIRA 1");
		esteiraTemplate.setValue("MATERIAL", "ALUMINIO");
		esteiraTemplate.setValue("MODELO", "PTC 90");
		esteiraTemplate.setValue("LARGURA", "0");
		esteiraTemplate.setValue("ALTURA", "0");
		
		
		StringBuilder formula = new StringBuilder(); 
		formula.append("var peso = 0.35 * product.getAttributeValue(\"ALTURA\").getDouble() * product.getAttributeValue(\"LARGURA\").getDouble();");
		formula.append("peso = product.getProduct(\"SOLEIRA PADRAO\").getAttributeValue(\"KILOGRAMA\").getDouble() * 1 + peso;");
		formula.append("product.setValue(\"KILOGRAMA\", peso);");
		
		Formula formulaEsteira = new FormulaImpl();
		formulaEsteira.setName("CALCULA PESO ESTEIRA 1");
		formulaEsteira.setLanguage("JAVASCRIPT");
		formulaEsteira.setScript(formula.toString());
		
		esteiraTemplate.setValue("KILOGRAMA", "0", formulaEsteira);
		esteiraTemplate.addTemplate(soleiraTemplate);
		dao.save((TemplateDefault) soleiraTemplate);
		
		
		Dao<TemplateGroup> ruleDao = new GenericDao<TemplateGroup>(session, TemplateGroup.class);
		
		TemplateGroup templateGroup = new TemplateGroup(motorSchema, "REGRA DE MOTORES");
		templateGroup.addTemplate(motor50Template);
		templateGroup.addTemplate(motor20Template);
		templateGroup.addTemplate(motor500Template);
		templateGroup.addTemplate(motor5000Template);
		
		StringBuilder rule = new StringBuilder();
		rule.append("var templateList = group.getTemplateAmountList(); ");
		rule.append("var peso = product.getAttributeValue(\"KILOGRAMA\").getDouble(); ");
		rule.append("var selectedTemplate = null; ");
		rule.append("var cargaSelecionada = null; ");
		rule.append("for (var i = 0; i < templateList.size(); i++) { ");
		rule.append("  var template = templateList.get(i).getTemplate()	; ");
		rule.append("  var carga = template.getAttributeValue(\"CARGA\").getDouble(); ");
		rule.append("  if (carga > peso) { ");
		rule.append("    if (selectedTemplate == null) { ");
		rule.append("      selectedTemplate = template; ");
		rule.append("      cargaSelecionada = selectedTemplate.getAttributeValue(\"CARGA\").getDouble(); ");
		rule.append("    } else if (carga < cargaSelecionada) { ");
		rule.append("      selectedTemplate = template; ");
		rule.append("      cargaSelecionada = selectedTemplate.getAttributeValue(\"CARGA\").getDouble(); ");
		rule.append("    } ");
		rule.append("  } ");
		rule.append("} ");
		rule.append("group.setSelectedTemplate(selectedTemplate); ");
//		rule.append("");
		Formula f = new FormulaImpl();
		f.setScript(rule.toString());
		f.setLanguage("JAVASCRIPT");
		f.setName(templateGroup.getName());
		templateGroup.setFormula(f);
		
		ruleDao.save((TemplateGroup) templateGroup);
		
		esteiraTemplate.addTemplate(templateGroup);
		dao.save((TemplateDefault) esteiraTemplate);
		
		return esteiraTemplate;
	}

	private Schema createEsteiraSchema() {
		
		Dao<SchemaImpl> dao = new GenericDao<SchemaImpl>(this.session, SchemaImpl.class);
		
		
		AttributeImpl cargaAttr = new AttributeImpl("CARGA", Category.FIXED);
		
		SchemaImpl motor = new SchemaImpl("MOTOR");

		motor.addAttribute(cargaAttr);
		
		dao.save(motor);
		
		
		Schema soleira = new SchemaImpl("SOLEIRA");
		
		soleira.addAttribute(new AttributeImpl("TIPO", Category.FIXED));
		
		soleira.addAttribute(new AttributeImpl("LARGURA", Category.VARIABLE));
		
		soleira.addAttribute(new AttributeImpl("KILOGRAMA", Category.CALCULATED));
		
		dao.save((SchemaImpl) soleira);
		
		
		Schema esteira = new SchemaImpl("ESTEIRA");
		
		esteira.addAttribute(new AttributeImpl("MODELO", Category.FIXED));
		
		esteira.addAttribute(new AttributeImpl("MATERIAL", Category.FIXED));
		
		esteira.addAttribute(new AttributeImpl("LARGURA", Category.VARIABLE));
		
		esteira.addAttribute(new AttributeImpl("ALTURA", Category.VARIABLE));
		
		esteira.addAttribute(new AttributeImpl("KILOGRAMA", Category.CALCULATED));
		
		esteira.addSchema(soleira);
		
		esteira.addSchema(motor);
		
		dao.save((SchemaImpl) esteira);
		
		
		return esteira;
	}

}
