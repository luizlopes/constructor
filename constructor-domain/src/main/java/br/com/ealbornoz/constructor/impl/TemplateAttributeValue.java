package br.com.ealbornoz.constructor.impl;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import br.com.ealbornoz.constructor.api.Attribute;
import br.com.ealbornoz.constructor.api.Formula;

@Entity(name="template_attribute_value")
public class TemplateAttributeValue extends AbstractAttributeValue implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@OneToOne(targetEntity=FormulaImpl.class, cascade=CascadeType.ALL)
	private Formula formula;
	
	public TemplateAttributeValue() {}
	
	public TemplateAttributeValue(Attribute attribute, String value) {
		super(attribute, value);
	}
	
	@Override
	public Formula getFormula() {
		return formula;
	}

	@Override
	public void setFormula(Formula formula) {
		this.formula = formula;
	}

}
