package br.com.ealbornoz.constructor.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.validation.Valid;

import org.apache.log4j.Logger;

import br.com.ealbornoz.constructor.api.Schema;
import br.com.ealbornoz.constructor.api.Template;
import br.com.ealbornoz.constructor.api.TemplateType;
import br.com.ealbornoz.constructor.common.BaseEntityDataModel;
import br.com.ealbornoz.constructor.common.BaseEntitySelectItem;
import br.com.ealbornoz.constructor.model.TemplateDTO;
import br.com.ealbornoz.constructor.service.SchemaService;
import br.com.ealbornoz.constructor.service.TemplateService;

public abstract class AbstractTemplateController implements Serializable {
	
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;

	protected static final Logger logger = Logger.getLogger(TemplateControllerImpl.class);
	
	@ManagedProperty("#{templateService}")
	protected TemplateService templateService;
	
	@ManagedProperty("#{schemaService}")
	private SchemaService schemaService;

	
	protected Template selectedTemplate;
	
	@Valid
	protected TemplateDTO template;
	
	private boolean selectedTemplateChanged;
	

	public abstract Template getSelectedTemplate();
	
	public abstract TemplateType getTemplateType();
	
	public abstract BaseEntityDataModel<Template> getTemplateList();
	

	public AbstractTemplateController() {
		super();
		initTemplate();
	}

	protected void initTemplate() {
		template = new TemplateDTO();
	}
	
	public void addTemplate() {
		logger.info("Adicionando novo Template " + template.getName() + ", Tipo: " + template.getTemplateType());
		selectedTemplate = templateService.addTemplateWizard(template);
		initTemplate();
	}
	
	public void removeTemplate(ActionEvent actionEvent) {
		logger.info("Removendo Template " + actionEvent.getComponent().getAttributes().get("templateId").toString());
		
		Long id = Long.parseLong(actionEvent.getComponent().getAttributes().get("templateId").toString());
		
		if (id != null && id.longValue() > 0) {
			selectedTemplate = templateService.get(id);
			templateService.removeTemplate(selectedTemplate);
			selectedTemplate = null;
		}
		
	}
	
	public SelectItem[] getSchemaSelectItems() {
		logger.info("Gerando array de SelectItem para Schema");
		
		List<Schema> lista = schemaService.listAll();
		SelectItem[] itens = new BaseEntitySelectItem<List<Schema>>(lista, true).getItems();
		return itens;
	}

	public void setSelectedTemplate(Template selectedTemplate) {
		
		if (selectedTemplate != null && selectedTemplate.equals(this.selectedTemplate)) {
			selectedTemplateChanged = false;
		} else {
			selectedTemplateChanged = true;
		}
		
		this.selectedTemplate = selectedTemplate;
	}

	public TemplateDTO getTemplate() {
		return template;
	}

	public void setTemplate(TemplateDTO template) {
		this.template = template;
	}
	
	public boolean isSelectedTemplateChanged() {
		return selectedTemplateChanged;
	}
	
	public void setSelectedTemplateChanged(boolean selectedTemplateChanged) {
		this.selectedTemplateChanged = selectedTemplateChanged;
	}
	
	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}
	
	public void setSchemaService(SchemaService schemaService) {
		this.schemaService = schemaService;
	}


}