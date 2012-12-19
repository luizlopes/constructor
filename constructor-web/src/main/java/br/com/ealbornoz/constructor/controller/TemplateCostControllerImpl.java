package br.com.ealbornoz.constructor.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import br.com.ealbornoz.constructor.api.TemplateCost;
import br.com.ealbornoz.constructor.common.BaseEntityDataModel;
import br.com.ealbornoz.constructor.service.TemplateCostService;

@ManagedBean(name="templateCostController")
@ViewScoped
public class TemplateCostControllerImpl implements Serializable {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(TemplateCostControllerImpl.class);
	
	private TemplateCost selectedTemplateCost;
	
	@ManagedProperty("#{templateCostService}")
	private TemplateCostService templateCostService;
	
	public BaseEntityDataModel<TemplateCost> getTemplateCostList() {
		logger.info("Gerando BaseEntityDataModel para TemplateCost");
	
		BaseEntityDataModel<TemplateCost> model = new BaseEntityDataModel<TemplateCost>();
		model.setWrappedData(templateCostService.list());
		return model;
	}
	
	public TemplateCost getSelectedTemplateCost() {
		return selectedTemplateCost;
	}
	
	public void setSelectedTemplateCost(TemplateCost selectedTemplateCost) {
		this.selectedTemplateCost = selectedTemplateCost;
	}
	
	public void setTemplateCostService(TemplateCostService templateCostService) {
		this.templateCostService = templateCostService;
	}

}
