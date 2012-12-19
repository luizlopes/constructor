package br.com.ealbornoz.constructor.model;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import br.com.ealbornoz.constructor.api.ItemWithName;
import br.com.ealbornoz.constructor.api.Schema;

/**
 * Classe DTO para exibição lista de TemplateAmount.
 * 
 * @author guilherme
 *
 */
public class TemplateAmountDTO implements ItemWithName, Serializable {
	
	/**
	 * Serial version UID 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long templateId;
	private SelectItem[] templateSelectItems;
	private Schema schema;
	private Long amount;
	
	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public String getName() {
		return null;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public void setName(String name) {
	}
	
	public Long getTemplateId() {
		return templateId;
	}
	
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public SelectItem[] getTemplateSelectItems() {
		return templateSelectItems;
	}

	public void setTemplateSelectItems(SelectItem[] templateSelectItems) {
		this.templateSelectItems = templateSelectItems;
	}

	public Schema getSchema() {
		return schema;
	}
	
	public void setSchema(Schema schema) {
		this.schema = schema;
	}
	
	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
}
