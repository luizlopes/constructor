package br.com.ealbornoz.constructor.common;

import java.util.Collection;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import br.com.ealbornoz.constructor.api.Item;

/**
 * 
 * @author guilherme
 *
 * @param <T>
 */
public class BaseEntityDataModel<T extends Item> extends ListDataModel<T> implements
		SelectableDataModel<T> {
	
	@Override
	public Object getRowKey(T object) {
		return object.getId();
	}

	@Override
	public T getRowData(String rowKey) {
		
		@SuppressWarnings("unchecked")
		Collection<T> lista = (Collection<T>) getWrappedData();
		
		for(T t : lista) {
			if (rowKey.equals(t.getId().toString())) {
				return t;
			}
		}
		
		return null;
	}

}
