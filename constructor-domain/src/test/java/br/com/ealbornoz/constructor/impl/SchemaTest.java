package br.com.ealbornoz.constructor.impl;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import junit.framework.TestCase;
import br.com.ealbornoz.constructor.api.Attribute;
import br.com.ealbornoz.constructor.api.Category;
import br.com.ealbornoz.constructor.api.Schema;

/**
 * Realiza testes para a interface Schema.
 * 
 * @author guilherme
 *
 */
public class SchemaTest extends TestCase {
	
	private static final String PESO = "PESO";
	private static final String TIPO = "TIPO";
	private static final String LARGURA = "LARGURA";
	private static final String SCHEMA_CHILD_NAME = "SCHEMA_CHILD";
	
	private Attribute largura = new AttributeImpl(LARGURA, Category.VARIABLE);
	private Attribute tipo = new AttributeImpl(TIPO, Category.FIXED);
	private Attribute peso = new AttributeImpl(PESO, Category.CALCULATED);
	
	private Schema schema;
	private Validator validator;
	
	@Override
	protected void setUp() throws Exception {
		schema = new SchemaImpl();
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();

	}
	
	public void testHasNoAttribute() {
		assertFalse( schema.hasAttribute("FALSO") );
	}
	
	public void testHasAttribute() {
		addAttributes();
		
		assertTrue( schema.hasAttribute(LARGURA) );
		assertTrue( schema.hasAttribute(PESO) );
		assertTrue( schema.hasAttribute(TIPO) );
		
		assertFalse( schema.hasAttribute("FALSO") );
	}
	
	public void testGetAttributeOnEmptyList() {
		try {
			schema.getAttribute(LARGURA);
			assertFalse(true);
		} catch (Exception e) {
			assertEquals(ArrayIndexOutOfBoundsException.class, e.getClass());
		}
	}

	public void testGetAttribute() {
		addAttributes();
		
		Attribute largura = schema.getAttribute( LARGURA );
		assertEquals( new AttributeImpl(LARGURA , Category.VARIABLE) , largura);
		
		Attribute peso = schema.getAttribute( PESO );
		assertEquals( new AttributeImpl(PESO , Category.CALCULATED), peso);
		
		Attribute tipo = schema.getAttribute( TIPO );
		assertEquals( new AttributeImpl(TIPO , Category.FIXED), tipo);
	}
	
	public void testAttributeList() {
		assertEquals( 0 , schema.getAttributeList().size() );
		addAttributes();
		assertEquals( 3 , schema.getAttributeList().size() );
	}
	
	public void testRemoveAttributeOnEmptyList() {
		try {
			schema.removeAttribute(LARGURA);
			assertFalse(true);
		} catch (Exception e) {
			assertEquals(ArrayIndexOutOfBoundsException.class, e.getClass());
		}
	}
	
	public void testRemoveAttribute() {
		addAttributes();
		assertTrue( schema.hasAttribute(LARGURA) );
		schema.removeAttribute(LARGURA);
		assertFalse( schema.hasAttribute(LARGURA) );
	}
	
	public void testHasSchema() {
		assertFalse( schema.hasSchema(SCHEMA_CHILD_NAME) );
		addSchemas();
		assertTrue( schema.hasSchema(SCHEMA_CHILD_NAME) );
	}
	
	public void testGetNullSchema() {
		try {
			assertNull( schema.getSchema(SCHEMA_CHILD_NAME) );
		} catch (Exception e) {
			assertEquals(ArrayIndexOutOfBoundsException.class, e.getClass());
		}
	}
	
	public void testGetSchema() {
		addSchemas();
		assertEquals( new SchemaImpl(SCHEMA_CHILD_NAME), schema.getSchema(SCHEMA_CHILD_NAME) );
	}
	
	public void testSchemaList() {
		assertEquals( 0 , schema.getSchemaList().size() );
		addSchemas();
		assertEquals( 1 , schema.getSchemaList().size() );
	}
	
	public void testRemoveSchema() {
		addSchemas();
		assertEquals( SCHEMA_CHILD_NAME, schema.getSchema(SCHEMA_CHILD_NAME).getName() );
		schema.removeSchema(SCHEMA_CHILD_NAME);
		assertFalse( schema.hasSchema(SCHEMA_CHILD_NAME) );
	}
	
	private void addSchemas() {
		schema.addSchema( new SchemaImpl(SCHEMA_CHILD_NAME) );
	}
	
	private void addAttributes() {
		schema.addAttribute(largura);
		schema.addAttribute(tipo);
		schema.addAttribute(peso);
	}
	
	public void testSchemaSemNome() {
		Set<ConstraintViolation<Schema>> violations = validator.validate(schema, Default.class);
		assertEquals(1, violations.size());
	}
	
	public void testSchemaComNomeCurto() {
		schema.setName("A");
		Set<ConstraintViolation<Schema>> violations = validator.validate(schema, Default.class);
		assertEquals(1, violations.size());
	}
	
}
