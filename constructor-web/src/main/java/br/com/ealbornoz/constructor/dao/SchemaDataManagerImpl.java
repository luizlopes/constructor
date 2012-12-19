package br.com.ealbornoz.constructor.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.ealbornoz.constructor.api.Schema;
import br.com.ealbornoz.constructor.impl.SchemaImpl;

@Repository("schemaDataManager")
@Transactional
public class SchemaDataManagerImpl implements SchemaDataManager, Serializable {
	
	/**
	 * Serial version UID 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SessionFactory sessionFactory;

	public SchemaDataManagerImpl() {
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Schema> listAll() {
		return getSession().createCriteria(SchemaImpl.class).list();
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void save(Schema schema) {
		getSession().save(schema);
	}
	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void update(Schema schema) {
		getSession().update(schema);
	}
	
	@Override
	public Schema get(Long id) {
		return (Schema) getSession().get(SchemaImpl.class, id);
	}
	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void remove(Schema schema) {
		getSession().delete(schema);
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	private Session getSession() {
		return getSessionFactory().getCurrentSession();
	}
	
}
