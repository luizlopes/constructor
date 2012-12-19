package br.com.ealbornoz.constructor.api;

import java.util.List;

/**
 * Define um esquema de um de modelo e produto.
 * 
 * @author guilherme
 */
public interface Schema extends ItemWithName {
	
	/**
	 * Retorna o nome do Schema.
	 * @return o nome do Schema.
	 */
	String getName();

	/**
	 * Retorna o conjunto de atributos do Schema.
	 * @return conjunto de atributos do Schema.
	 */
	List<Attribute> getAttributeList();
	
	/**
	 * Adiciona um atribute ao conjunto.
	 * @param attribute 
	 */
	void addAttribute(Attribute attribute);
	
	/**
	 * Remove um atributo através do seu nome.
	 * @param name Nome do atributo que deve ser removido.
	 */
	void removeAttribute(String name);
	
	/**
	 * Verifica através do nome se o atributo existe.
	 * @param name Nome do atributo.
	 * @return true, se o atributo existir; false, se não existir.
	 */
	boolean hasAttribute(String name);
	
	/**
	 * Retorna um objeto atributo.
	 * @param name Nome do atributo. 
	 * @return Objeto <code>Attribute</code> correspondente ao parametro <code>name</code>. 
	 */
	Attribute getAttribute(String name);
	
	/**
	 * Retorna um conjunto de schemas que compõe o Schema.
	 * @return conjunto de schemas do Schema
	 */
	List<Schema> getSchemaList();
	
	/**
	 * Adiciona um schema ao conjunto.
	 * @param schema Schema que será adicionado.
	 */
	void addSchema(Schema schema);
	
	/**
	 * Remove um Schema através do seu nome.
	 * @param name Nome do Schema que deve ser removido.
	 */
	void removeSchema(String name);
	
	/**
	 * Verifica através do nome se existe o schema no conjunto de esquemas.
	 * @param name Nome do Schema.
	 * @return true, se o schema existir; false, se não existir.
	 */
	boolean hasSchema(String name);
	
	/**
	 * Retorna um objeto Schema através do seu nome.
	 * @param name Nome do Schema
	 * @return Objeto <code>Schema</code> correspondente ao parametro name.
	 */
	Schema getSchema(String name);

}
