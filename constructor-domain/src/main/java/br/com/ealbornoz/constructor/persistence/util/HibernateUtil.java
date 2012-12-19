package br.com.ealbornoz.constructor.persistence.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	
	static {
		Configuration cfg = new AnnotationConfiguration();
		cfg.configure();
		sessionFactory = cfg.buildSessionFactory();  
	}
	
	public static Session getSession() {
		return sessionFactory.openSession();
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
}
