package br.com.ealbornoz.constructor.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.ealbornoz.constructor.api.Attribute;
import br.com.ealbornoz.constructor.api.Category;
import br.com.ealbornoz.constructor.api.Formula;
import br.com.ealbornoz.constructor.api.Schema;
import br.com.ealbornoz.constructor.api.Template;
import br.com.ealbornoz.constructor.api.TemplateAmount;
import br.com.ealbornoz.constructor.api.TemplateType;
import br.com.ealbornoz.constructor.dao.SchemaDataManager;
import br.com.ealbornoz.constructor.dao.TemplateDataManager;
import br.com.ealbornoz.constructor.impl.FormulaImpl;
import br.com.ealbornoz.constructor.impl.TemplateDefault;
import br.com.ealbornoz.constructor.impl.TemplateGroup;
import br.com.ealbornoz.constructor.model.TemplateDTO;

@Service("templateService")
public class TemplateServiceImpl implements TemplateService, Serializable {
	
	/**
	 * Serial version UID 
	 */
	private static final long serialVersionUID = 1L;

	private static final String JAVASCRIPT_LANGUAGE = "JavaScript";

	@Resource
	private TemplateDataManager templateDataManager;
	
	@Resource
	private SchemaDataManager schemaDataManager;
	
	@Override
	public List<Template> listAll() {
		return templateDataManager.listAll();
	}
	
	@Override
	public List<Template> listTemplateDefault() {
		return templateDataManager.listTemplateDefault();
	}
	
	@Override
	public List<Template> listTemplateGroup() {
		return templateDataManager.listTemplateGroup();
	}
	
	@Override
	public Template addTemplate(TemplateDTO templateDTO) {
		
		Template template = null;
		
		Schema schema = schemaDataManager.get(templateDTO.getSchemaId()); 
		
		if (TemplateType.DEFAULT.equals(templateDTO.getTemplateType())) {
			template = new TemplateDefault(schema, templateDTO.getName());
		} else if(TemplateType.GROUP.equals(templateDTO.getTemplateType())) {
			template = new TemplateGroup(schema, templateDTO.getName());
		}
		
		templateDataManager.save(template);
		
		return template;
	}
	
	@Override
	public Template addTemplateWizard(TemplateDTO templateDTO) {
		
		Template template = addTemplate(templateDTO);
		
		if (template.isTemplateDefault()) {
			for(Attribute attr : template.getSchema().getAttributeList()) {
				
				template.setValue(attr.getName(), "[vazio]");
				
				if (Category.CALCULATED.equals(attr.getCategory())) {
					Formula formula = new FormulaImpl();
					formula.setLanguage(JAVASCRIPT_LANGUAGE);
					formula.setName(attr.getName());
					template.getAttributeValue(attr.getName()).setFormula(formula);
				}
			}
		} else {
			Formula formula = new FormulaImpl();
			formula.setLanguage(JAVASCRIPT_LANGUAGE);
			formula.setName(template.getName());
			template.setFormula(formula);
		}
		
		templateDataManager.save(template);
		
		return template;
	}
	
	@Override
	public List<Template> listTemplateBySchema(Schema schema) {
		return templateDataManager.listTemplateBySchema(schema);
	}
	
	@Override
	public List<Template> listTemplateDefaultBySchema(Schema schema) {
		return templateDataManager.listTemplateDefaultBySchema(schema);
	}
	
	@Override
	public Template get(Long templateId) {
		return templateDataManager.get(templateId);
	}
	
	@Override
	public void save(Template template) {
		templateDataManager.save(template);
	}
	
	@Override
	public void update(Template template) {
		templateDataManager.update(template);
	}
	
	@Override
	public void updateTemplateAmount(TemplateAmount templateAmount) {
		templateDataManager.updateTemplateAmount(templateAmount);
	}
	
	@Override
	public void removeTemplate(Template template) {
		templateDataManager.remove(template);
	}
	
	public void setTemplateDataManager(TemplateDataManager templateDataManager) {
		this.templateDataManager = templateDataManager;
	}

}
