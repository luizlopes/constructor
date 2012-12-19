package br.com.ealbornoz.constructor.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import br.com.ealbornoz.constructor.api.Attribute;
import br.com.ealbornoz.constructor.api.AttributeValue;
import br.com.ealbornoz.constructor.api.Category;
import br.com.ealbornoz.constructor.api.Formula;
import br.com.ealbornoz.constructor.api.Schema;
import br.com.ealbornoz.constructor.api.Template;
import br.com.ealbornoz.constructor.api.TemplateAmount;
import br.com.ealbornoz.constructor.api.TemplateType;

/**
 * Define um Template Padrão.
 * 
 * @author guilherme
 */

@Entity
@DiscriminatorValue("DEFAULT")
public class TemplateDefault extends AbstractTemplate implements Template, Serializable {

	private static final long serialVersionUID = 1L;
	
	@OneToMany(targetEntity = TemplateAttributeValue.class, cascade = CascadeType.ALL)
	@JoinColumn(name="template_id", nullable = false)
	private List<AttributeValue> attributeValueList = new ArrayList<AttributeValue>();

	public TemplateDefault() {
	}
	
	public TemplateDefault(Schema schema, String name) {
		super();
		super.setSchema(schema);
		this.name = name;
	}

	@Override
	public TemplateType getTemplateType() {
		return TemplateType.DEFAULT;
	}
	
	@Override
	public List<AttributeValue> getAttributeValueList() {
		return attributeValueList;
	}

	@Override
	public void setValue(String attributeName, String value) {
		AttributeValue attributeValue = new TemplateAttributeValue(getSchema().getAttribute(attributeName), value);
		attributeValueList.add(attributeValue);
	}
	
	@Override
	public void setValue(String attributeName, String value, Formula formula) {
		
		Attribute attribute = getSchema().getAttribute(attributeName);
		if ( !attribute.getCategory().equals( Category.CALCULATED ) ) {
			throw new IllegalArgumentException();
		}
		
		AttributeValue attributeValue = new TemplateAttributeValue(attribute, value);
		attributeValue.setFormula(formula);
		attributeValueList.add(attributeValue);
	}
	
	@Override
	public AttributeValue getAttributeValue(String attributeName) {
		int attrValueIndex = FindOnItemList.getIndex(attributeValueList, attributeName);
		if (attrValueIndex >= 0) {
			return attributeValueList.get(attrValueIndex);
		}
		return null;
	}
	
	@Override
	public void addTemplate(Template template, Long amount) {
		if ( ! getSchema().hasSchema( template.getSchema().getName() ) ) {
			throw new IllegalArgumentException();
		}
		getTemplateAmountList().add( new TemplateStruct( template , amount ) );
	}
	
	@Override
	public Set<AttributeValue> findVariableAttributeSet() {
		
		Set<AttributeValue> variableAttributeSet = null;
		
		for (TemplateAmount templateAmount : getTemplateAmountList()) {
			Template template = templateAmount.getTemplate();
			if (template.isTemplateDefault()) {
				variableAttributeSet = template.findVariableAttributeSet();
			}
		}

		if (variableAttributeSet == null) {
			variableAttributeSet = new HashSet<AttributeValue>(); 
		}
		
		for (AttributeValue attributeValue : attributeValueList) {
			if (Category.VARIABLE.equals(attributeValue.getAttribute().getCategory())) {
				variableAttributeSet.add(attributeValue);
			}
		}
		
		return variableAttributeSet;
	}
	
}
