package br.com.ealbornoz.constructor.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.ealbornoz.constructor.api.TemplateCost;

@Repository("templateCostDataManager")
public class TemplateCostDataManagerImpl implements TemplateCostDataManager, Serializable {
	
	/**
	 * Serial version UID. 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<TemplateCost> list() {
		return sessionFactory.getCurrentSession().createCriteria(TemplateCost.class).list();
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
