package br.com.ealbornoz.constructor.api;

import java.util.Date;

/**
 * Representa um atributo e o seu respectivo valor.
 * 
 * @author guilherme
 */
public interface AttributeValue extends ItemWithName {

	/**
	 * Retorna o atributo.
	 * @return o atributo.
	 */
	Attribute getAttribute();
	
	/**
	 * Retorna o valor do atributo.
	 * @return Valor do atributo no formato de String.
	 */
	String getString();
	
	/**
	 * Retorna o valor do atributo.
	 * @return Valor do atributo no formato de Long.
	 */
	Long getLong();

	/**
	 * Retorna o valor do atributo.
	 * @return Valor do atributo no formato de Double.
	 */
	Double getDouble();
	
	/**
	 * Retorna o valor do atributo.
	 * @return Valor do atributo no formato de Date.
	 */
	Date getDate(String pattern);

	/**
	 * Retorna a formula do atributo.
	 * @return Formula do atributo.
	 */
	Formula getFormula();
	
	void setAttribute(Attribute attribute);
	
	void setValue(String value);
	
	void setFormula(Formula formula);

}
