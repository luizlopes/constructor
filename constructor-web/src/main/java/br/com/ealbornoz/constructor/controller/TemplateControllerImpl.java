package br.com.ealbornoz.constructor.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.com.ealbornoz.constructor.api.Schema;
import br.com.ealbornoz.constructor.api.Template;
import br.com.ealbornoz.constructor.api.TemplateAmount;
import br.com.ealbornoz.constructor.api.TemplateType;
import br.com.ealbornoz.constructor.common.BaseEntityDataModel;
import br.com.ealbornoz.constructor.common.BaseEntitySelectItem;
import br.com.ealbornoz.constructor.common.BaseEnumSelectItem;
import br.com.ealbornoz.constructor.impl.TemplateDefault;
import br.com.ealbornoz.constructor.model.TemplateAmountDTO;

@ManagedBean(name="templateController")
@ViewScoped
public class TemplateControllerImpl extends AbstractTemplateController implements Serializable {
	
	/**
	 * Serial version UID 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<TemplateAmountDTO> templateChildList = new ArrayList<TemplateAmountDTO>();

	public TemplateControllerImpl() {
		logger.info("Iniciando Controller " + this.getClass().getSimpleName());
	}
	
	@Override
	public BaseEntityDataModel<Template> getTemplateList() {
		logger.info("Gerando BaseEntityDataModel para Template");
	
		BaseEntityDataModel<Template> model = new BaseEntityDataModel<Template>();
		model.setWrappedData(templateService.listTemplateDefault());
		return model;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SelectItem[] getTemplateTypeSelectItems() {
		logger.info("Gerando array de SelectItem para TemplateType.");
		
		SelectItem[] itens = null;
		
		try {
			itens = new BaseEnumSelectItem(TemplateType.class).getItems();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return itens;
	}
	
	public void setTemplateChildList(List<TemplateAmountDTO> templateChildList) {
		this.templateChildList = templateChildList;
	}
	
	public List<TemplateAmountDTO> getTemplateChildList() {
		
		if (FacesContext.getCurrentInstance().getRenderResponse() || isSelectedTemplateChanged()) {
			setSelectedTemplateChanged(false);
			obterTemplateChildList();
		}
	
		return templateChildList ;	
	}

	private void obterTemplateChildList() {
		
		templateChildList  = new ArrayList<TemplateAmountDTO>();
		
		if (selectedTemplate != null && selectedTemplate.getSchema() != null) {
			
			List<Schema> schemaChildList = new ArrayList<Schema>();
			schemaChildList.addAll(selectedTemplate.getSchema().getSchemaList());

			Iterator<TemplateAmount> iterator = selectedTemplate.getTemplateAmountList().iterator();
			
			while (iterator.hasNext()) {
				
				TemplateAmount templateAmount = iterator.next();
				
				Schema schema = templateAmount.getTemplate().getSchema();
				List<Template> templateList = templateService.listTemplateBySchema(schema);
				BaseEntitySelectItem<List<Template>> baseEntityTemplateList = new BaseEntitySelectItem<List<Template>>(templateList);
				
				TemplateAmountDTO dto = new TemplateAmountDTO();
				dto.setId(templateAmount.getId());
				dto.setTemplateId(templateAmount.getTemplate().getId());
				dto.setAmount(templateAmount.getAmount());
				dto.setSchema(schema);
				dto.setTemplateSelectItems(baseEntityTemplateList.getItems());
				
				templateChildList.add(dto);
				
				// Remove da lista de schemas filhos
				schemaChildList.remove(schema);
			}
			
			// Utiliza a lista de schemas filhos para popular a lista de templates filhos
			Iterator<Schema> iterator2 = schemaChildList.iterator();
			
			while(iterator2.hasNext()) {
				
				Schema schema = iterator2.next();
				
				List<Template> templateList = templateService.listTemplateBySchema(schema);
				BaseEntitySelectItem<List<Template>> baseEntityTemplateList = new BaseEntitySelectItem<List<Template>>(templateList);
				
				TemplateAmountDTO dto = new TemplateAmountDTO();
				dto.setAmount(new Long(0));
				dto.setSchema(schema);
				dto.setTemplateSelectItems(baseEntityTemplateList.getItems());
				
				templateChildList.add(dto);
			}
		}
	}
	
	public void updateTemplateChildList() {
		
		if (selectedTemplate != null) {
			for(TemplateAmountDTO dto : templateChildList) {
				
				TemplateAmount templateAmount = selectedTemplate.getTemplateAmountBySchema(dto.getSchema().getName());
				
				Template newTemplate = templateService.get(dto.getTemplateId());
				templateAmount.setTemplate(newTemplate);
				templateAmount.setName(newTemplate.getName());
				templateAmount.setAmount(dto.getAmount());
				
				templateService.update(selectedTemplate);
			}
		}
	}
	
	public void updateAttributeValueList() {
		
		if (selectedTemplate != null) {
			templateService.update(selectedTemplate);
		}
	}

	@Override
	public Template getSelectedTemplate() {
		if (selectedTemplate == null) {
			return new TemplateDefault();
		}
		return selectedTemplate;
	}
	
	@Override
	public TemplateType getTemplateType() {
		return TemplateType.DEFAULT;
	}
	
}
