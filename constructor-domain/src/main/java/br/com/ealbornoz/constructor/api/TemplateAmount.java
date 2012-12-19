package br.com.ealbornoz.constructor.api;

import br.com.ealbornoz.constructor.api.Template;

public interface TemplateAmount extends ItemWithName {

	public abstract Template getTemplate();

	public abstract void setTemplate(Template template);

	public abstract Long getAmount();

	public abstract void setAmount(Long amount);

}