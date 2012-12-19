package br.com.ealbornoz.constructor.api;

/**
 * Define as caracteríticas de um atributo.
 * 
 * @author guilherme
 */
public interface Attribute extends ItemWithName {
	
	/**
	 * Retorna o nome do atributo.
	 * @return
	 */
	String getName();
	
	/**
	 * Retorna a categoria do atributo.
	 * @return
	 */
	Category getCategory();
	
}
