package br.com.ealbornoz.constructor.api;

public interface ItemWithName extends Item {
	
	/**
	 * Retorna o nome da unidade.
	 * @return o nome da unidade.
	 */
	String getName();
	
	void setName(String name);

}
