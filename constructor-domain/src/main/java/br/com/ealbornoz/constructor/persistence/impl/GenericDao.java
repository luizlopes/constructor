package br.com.ealbornoz.constructor.persistence.impl;

import java.util.List;

import org.hibernate.Session;

import br.com.ealbornoz.constructor.persistence.Dao;
import br.com.ealbornoz.constructor.persistence.Persistence;

public class GenericDao<T extends Persistence> implements Dao<T> {

	private Session session;
	private Class<T> persistentClass;

	public GenericDao(Session session, Class<T> persistentClass) {
		this.session = session;
		this.persistentClass = persistentClass;
	}

	@SuppressWarnings("unchecked")
	public List<T> list() {
		return session.createCriteria(persistentClass).list();
	}

	@SuppressWarnings("unchecked")
	public T find(Long id) {
		return (T) session.load(persistentClass, id);
	}

	public void save(T t) {
		session.saveOrUpdate(t);
	}

	public void delete(T t) {
		session.delete(t);
	}

}
