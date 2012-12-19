package br.com.ealbornoz.constructor.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.ealbornoz.constructor.api.TemplateCost;
import br.com.ealbornoz.constructor.dao.TemplateCostDataManager;

@Service("templateCostService")
public class TemplateCostServiceImpl implements TemplateCostService, Serializable {
	
	/**
	 * Serial version UID. 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private TemplateCostDataManager templateCostDataManager;

	@Override
	public List<TemplateCost> list() {
		return templateCostDataManager.list();
	}
	
	public void setTemplateCostDataManager(
			TemplateCostDataManager templateCostDataManager) {
		this.templateCostDataManager = templateCostDataManager;
	}

}
