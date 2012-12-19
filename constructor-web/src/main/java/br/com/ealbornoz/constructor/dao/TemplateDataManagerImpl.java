package br.com.ealbornoz.constructor.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.ealbornoz.constructor.api.Schema;
import br.com.ealbornoz.constructor.api.Template;
import br.com.ealbornoz.constructor.api.TemplateAmount;
import br.com.ealbornoz.constructor.impl.AbstractTemplate;
import br.com.ealbornoz.constructor.impl.TemplateDefault;
import br.com.ealbornoz.constructor.impl.TemplateGroup;

@Repository("templateDataManager")
public class TemplateDataManagerImpl implements TemplateDataManager, Serializable {
	
	/**
	 * Serial version UID 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Template> listAll() {
		return getSession().createCriteria(AbstractTemplate.class).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Template> listTemplateDefault() {
		return getSession().createCriteria(TemplateDefault.class).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Template> listTemplateGroup() {
		return getSession().createCriteria(TemplateGroup.class).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Template> listTemplateBySchema(Schema schema) {
		
		Criteria criteria = getSession().createCriteria(AbstractTemplate.class);
		
		criteria.add(Restrictions.eq("schema", schema));
		
		return criteria.list();
	}
	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void save(Template template) {
		getSession().save(template);
	}
	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void update(Template template) {
		getSession().update(template);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Template> listTemplateDefaultBySchema(Schema schema) {
		
		Criteria criteria = getSession().createCriteria(TemplateDefault.class);
		
		criteria.add(Restrictions.eq("schema", schema));
		
		return criteria.list();
	}
	
	@Override
	public Template get(Long templateId) {
		return (Template) getSession().get(AbstractTemplate.class, templateId);
	}
	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void updateTemplateAmount(TemplateAmount templateAmount) {
		getSession().update(templateAmount);
	}
	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void remove(Template template) {
		getSession().delete(template);
	}
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
