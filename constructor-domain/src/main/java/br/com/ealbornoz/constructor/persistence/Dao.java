package br.com.ealbornoz.constructor.persistence;

import java.util.List;

public interface Dao<T> {
	
	List<T> list();
	
	T find(Long id);
	
	void save(T t);
	
	void delete(T t);

}
