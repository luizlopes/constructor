package br.com.ealbornoz.constructor.impl;

import java.util.List;

import br.com.ealbornoz.constructor.api.ItemWithName;

/**
 * Classe de busca em lista de <code>Item</code>.
 * 
 * @author guilherme
 *
 */
public class FindOnItemList {
	
	/**
	 * Retorna o indice do item procurado na lista. Se o item não for encontrado, retorna -1.
	 * 
	 * @param t objeto <code>List</code> com elementos que extendem de <code>ItemWithName</code>.
	 * @param name string a ser pesquisada na lista.
	 * @return o indice do item procurado na lista.
	 */
	public static <T extends List<? extends ItemWithName>> int getIndex(T t, String name) {
				
		for (int i=0; i < t.size(); i++) {
			if (t.get(i).getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}

}
