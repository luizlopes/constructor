package br.com.ealbornoz.constructor.common;

import java.util.List;

import javax.faces.model.SelectItem;

import br.com.ealbornoz.constructor.api.ItemWithName;

public class BaseEntitySelectItem<T extends List<? extends ItemWithName>> {
	
	private final T itemList;
	private boolean addSelecione;

	public BaseEntitySelectItem(T itemList) {
		this.itemList = itemList;
	}
	
	public BaseEntitySelectItem(T itemList, boolean addSelecione) {
		this.itemList = itemList;
		this.addSelecione = addSelecione;
	}
	
	public SelectItem[] getItems() {
		
		SelectItem[] itens;
		int inicio = 0;

		if (addSelecione) {
			itens = new SelectItem[itemList.size() + 1];
			itens[0] = new SelectItem("", "Selecione");
			inicio = 1;
		} else {
			itens = new SelectItem[itemList.size()];
		}
		
//		for (int i = inicio; i < itemList.size(); i++) {
//			ItemWithName item = itemList.get(i);
//			itens[i] = new SelectItem(item.getId(), item.getName());
//		}
		
		for(ItemWithName item : itemList) {
			itens[inicio] = new SelectItem(item.getId(), item.getName());
			inicio++;
		}
		
		return itens;
	}

}
