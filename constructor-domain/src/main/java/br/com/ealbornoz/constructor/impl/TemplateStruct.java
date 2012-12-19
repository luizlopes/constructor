package br.com.ealbornoz.constructor.impl;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import br.com.ealbornoz.constructor.api.Template;
import br.com.ealbornoz.constructor.api.TemplateAmount;

@Entity(name = "template_struct")
public class TemplateStruct extends AbstractItemWithName implements Serializable, TemplateAmount {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(targetEntity = AbstractTemplate.class)
	private Template template;
	
	private Long amount;
	
	public TemplateStruct() {
	}

	public TemplateStruct(Template template, Long amount) {
		this.template = template;
		this.amount = amount;
		this.name = template.getName(); 
	}

	/* (non-Javadoc)
	 * @see br.com.ealbornoz.constructor.impl.TemplateAmount#getTemplate()
	 */
	@Override
	public Template getTemplate() {
		return template;
	}

	/* (non-Javadoc)
	 * @see br.com.ealbornoz.constructor.impl.TemplateAmount#setTemplate(br.com.ealbornoz.constructor.api.Template)
	 */
	@Override
	public void setTemplate(Template template) {
		this.template = template;
	}

	/* (non-Javadoc)
	 * @see br.com.ealbornoz.constructor.impl.TemplateAmount#getAmount()
	 */
	@Override
	public Long getAmount() {
		return amount;
	}

	/* (non-Javadoc)
	 * @see br.com.ealbornoz.constructor.impl.TemplateAmount#setAmount(java.lang.Long)
	 */
	@Override
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
}
