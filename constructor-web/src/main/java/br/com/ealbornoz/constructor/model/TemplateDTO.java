package br.com.ealbornoz.constructor.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.ealbornoz.constructor.api.TemplateType;

/**
 * Classe com finalidade de armazenar dados referentes a um objeto Template.
 * 
 * @author guilherme
 *
 */
public class TemplateDTO implements Serializable {
	
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private TemplateType templateType;
	
	private Long schemaId;

	private Long templateId;
	
	private Long templateChildId = new Long(0);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TemplateType getTemplateType() {
		return templateType;
	}

	public void setTemplateType(TemplateType templateType) {
		this.templateType = templateType;
	}

	public Long getSchemaId() {
		return schemaId;
	}
	
	public void setSchemaId(Long schemaId) {
		this.schemaId = schemaId;
	}

	public Long getTemplateId() {
		return templateId;
	}
	
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Long getTemplateChildId() {
		return templateChildId;
	}

	public void setTemplateChildId(Long templateChildId) {
		this.templateChildId = templateChildId;
	}
	
}
