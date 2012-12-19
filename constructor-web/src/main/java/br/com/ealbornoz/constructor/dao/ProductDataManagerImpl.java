package br.com.ealbornoz.constructor.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.ealbornoz.constructor.api.Product;
import br.com.ealbornoz.constructor.impl.ProductImpl;

@Repository
public class ProductDataManagerImpl implements ProductDataManager, Serializable {

	/**
	 * Serial UID 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> listAll() {
		return getSession().createCriteria(ProductImpl.class).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> listRoots() {
		Criteria criteria = getSession().createCriteria(ProductImpl.class);
		criteria.add(Restrictions.isNull("productOwner"));
		return criteria.list();
	}
	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void save(Product product) {
		getSession().save(product);
	}
	
	@Override
	public Product get(Long id) {
		return (Product) getSession().get(ProductImpl.class, id);
	}
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
}
