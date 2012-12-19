package br.com.ealbornoz.constructor.impl;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import br.com.ealbornoz.constructor.api.AttributeValue;
import br.com.ealbornoz.constructor.api.Template;
import br.com.ealbornoz.constructor.api.TemplateCost;

@Entity(name="template_cost")
public class TemplateCostImpl extends AbstractItem implements TemplateCost {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne(targetEntity=TemplateDefault.class)
	private Template template;
	
	@OneToOne(targetEntity=TemplateAttributeValue.class)
	private AttributeValue attributeValue;

	private Double cost;

	public TemplateCostImpl(Template template) {
		this.template = template;
		this.cost = new Double(0);
	}

	@Override
	public Template getTemplate() {
		return template;
	}

	@Override
	public AttributeValue getAttributeValue() {
		return attributeValue;
	}
	
	@Override
	public void setAttributeValue(AttributeValue attributeValue) {
		this.attributeValue = attributeValue;
	}

	@Override
	public Double getCost() {
		return cost;
	}
	
	@Override
	public void setCost(Double cost) {
		this.cost = cost;
	}
	
}

