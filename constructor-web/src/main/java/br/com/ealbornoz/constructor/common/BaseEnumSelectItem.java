package br.com.ealbornoz.constructor.common;

import javax.faces.model.SelectItem;

public class BaseEnumSelectItem<T extends Class<Enum<?>>> {
	
	private final T enumClass;

	public BaseEnumSelectItem(T enumClass) {
		this.enumClass = enumClass;
	}
	
	public SelectItem[] getItems() throws ClassNotFoundException {
		
		Class<?> clazz = Class.forName(enumClass.getName());
		
	    Enum<?>[] constArray = (Enum[]) clazz.getEnumConstants(); 

	    int length = constArray.length;
	    
		SelectItem[] itens = new SelectItem[length];
	    
	    for (int i = 0; i < length; i++) {
	    	itens[i] = new SelectItem(constArray[i], constArray[i].name());
	    }
		
		return itens;
	}

}
