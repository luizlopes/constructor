package br.com.ealbornoz.constructor.controller;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.ealbornoz.constructor.api.Formula;
import br.com.ealbornoz.constructor.api.Schema;
import br.com.ealbornoz.constructor.api.Template;
import br.com.ealbornoz.constructor.api.TemplateAmount;
import br.com.ealbornoz.constructor.api.TemplateType;
import br.com.ealbornoz.constructor.common.BaseEntityDataModel;
import br.com.ealbornoz.constructor.common.BaseEntitySelectItem;
import br.com.ealbornoz.constructor.impl.FormulaImpl;
import br.com.ealbornoz.constructor.impl.TemplateGroup;

@ManagedBean(name="groupController")
@ViewScoped
public class TemplateGroupControllerImpl extends AbstractTemplateController implements Serializable {
	
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	private static final String JAVASCRIPT_LANGUAGE = "JavaScript";
	private String templateScript;

	public TemplateGroupControllerImpl() {
		logger.info("Iniciando Controller " + this.getClass().getSimpleName());
		selectedTemplate = new TemplateGroup();
	}
	
	@Override
	public BaseEntityDataModel<Template> getTemplateList() {
		logger.info("Gerando BaseEntityDataModel para Group");
		BaseEntityDataModel<Template> model = new BaseEntityDataModel<Template>();
		model.setWrappedData(templateService.listTemplateGroup());
		return model;
	}
	
	public SelectItem[] getTemplateSelectItems() {
		
		SelectItem[] items = null;
		
		if (selectedTemplate.getId() != null) {
			
			if (template.getTemplateId() == null 
					|| template.getTemplateId().longValue() == 0) {
				
				template.setTemplateId(selectedTemplate.getId());
				template.setSchemaId(selectedTemplate.getSchema().getId());
				
			}
			
			List<Template> templateList = getObterTemplateChildList();
			
			BaseEntitySelectItem<List<Template>> baseEntitySelectItem = new BaseEntitySelectItem<List<Template>>(templateList);
			items = baseEntitySelectItem.getItems();
		}

		return items;
	}

	public List<Template> getObterTemplateChildList() {
		Schema schema = selectedTemplate.getSchema();
		
		List<Template> templateList = templateService.listTemplateDefaultBySchema(schema);

		Iterator<TemplateAmount> iterator = selectedTemplate.getTemplateAmountList().iterator();
		
		while (iterator.hasNext()) {
			
			TemplateAmount templateAmount = iterator.next();
			
			templateList.remove(templateAmount.getTemplate());
		}
		
		return templateList;
	}
	
	public void addTemplateAmount() {
		
		if (selectedTemplate.getId() != null) {
			
			Template templateParent = templateService.get(selectedTemplate.getId());
			
			Template templateChild = templateService.get(template.getTemplateChildId());
			
			templateParent.addTemplate(templateChild);
			
			templateService.save(templateParent);
		}
	}
	
	public void updateTemplate() {
		if (selectedTemplate.getId() != null) {
			templateService.save(selectedTemplate);
		}
	}
	
	public String getTemplateScript() {
		templateScript = "";
		if (selectedTemplate.getFormula() != null) {
			templateScript = selectedTemplate.getFormula().getScript();
		}
		return templateScript;
	}
	
	public void setTemplateScript(String templateScript) {
		this.templateScript = templateScript;
	}
	
	public void updateTemplateScript() {
		Formula formula = selectedTemplate.getFormula() ;
		if (formula == null) {
			formula = new FormulaImpl();
			formula.setLanguage(JAVASCRIPT_LANGUAGE);
			formula.setName(selectedTemplate.getName());
			formula.setScript(templateScript);
			selectedTemplate.setFormula(formula);
		} else {
			formula.setScript(templateScript);
		}
		templateService.save(selectedTemplate);
	}

	@Override
	public Template getSelectedTemplate() {
		return selectedTemplate;
	}

	@Override
	public TemplateType getTemplateType() {
		return TemplateType.GROUP;
	}
	
}
