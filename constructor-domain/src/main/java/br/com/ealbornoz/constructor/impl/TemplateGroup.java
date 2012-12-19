package br.com.ealbornoz.constructor.impl;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import br.com.ealbornoz.constructor.api.Formula;
import br.com.ealbornoz.constructor.api.Schema;
import br.com.ealbornoz.constructor.api.Template;
import br.com.ealbornoz.constructor.api.TemplateType;

@Entity
@DiscriminatorValue("GROUP")
public class TemplateGroup extends AbstractTemplate implements Template, Serializable {

	private static final long serialVersionUID = 1L;

	@OneToOne(targetEntity=FormulaImpl.class, cascade=CascadeType.ALL)
	private Formula formula;
	
	@Transient
	private Template templateSelected;
	
	public TemplateGroup() {
	}
	
	public TemplateGroup(Schema schema, String name) {
		super();
		super.setSchema(schema);
		this.name = name;
	}
	
	@Override
	public TemplateType getTemplateType() {
		return TemplateType.GROUP;
	}
	
	@Override
	public void setFormula(Formula formula) {
		this.formula = formula;
	}

	@Override
	public Formula getFormula() {
		return formula;
	}

	@Override
	public Template getSelectedTemplate() {
		return templateSelected;
	}
	
	@Override
	public void setSelectedTemplate(Template template) {
		templateSelected = template;
	}

	public void addTemplate(Template template, Long amount) {
		if (!getSchema().equals(template.getSchema())) {
			throw new IllegalArgumentException();
		}
		getTemplateAmountList().add( new TemplateStruct( template , amount ) );
	}
	
	

}
