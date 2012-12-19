package br.com.ealbornoz.constructor.api;

import java.util.List;
import java.util.Set;

/**
 * Define um modelo para um produto.
 * 
 * @author guilherme
 */
public interface Template extends ItemWithName {
	
	/**
	 * Retorna o Schema do Template.
	 * @return o Schema do Template.
	 */
	Schema getSchema();
	
	/**
	 * Retorna o conjunto de valores dos atributos do Template. 
	 * @return o conjunto de valores dos atributos do Template.
	 */
	List<AttributeValue> getAttributeValueList();
	
	/**
	 * Seta o valor para o atributo.
	 * @param attributeName Nome do atributo que recebe o valor.
	 * @param value Valor do atributo.
	 */
	void setValue(String attributeName, String value);
	
	/**
	 * Seta o valor para o atributo.
	 * @param attributeName Nome do atributo que recebe o valor.
	 * @param value Valor do atributo.
	 * @param formula Formula para calculo do campo.
	 */
	void setValue(String attributeName, String value, Formula formula);
	
	/**
	 * Retorna um <code>AttributeValue</code>.
	 * @param attributeName Nome do atributo que recebe o valor.
	 * @return um <code>AttributeValue</code> correspondente ao parametro <code>attributeName</code>.
	 */
	AttributeValue getAttributeValue(String attributeName);

	/**
	 * Retorna um objeto com conjunto de Template.
	 * @return um objeto com conjunto de Template.
	 */
	List<TemplateAmount> getTemplateAmountList();
	
	/**
	 * Adiciona um template ao conjunto com quantidade padrao 1. 
	 * @param template Template que será adicionado.
	 */
	void addTemplate(Template template);
	
	/**
	 * Adiciona um template ao conjunto com quandidade variavel. 
	 * @param template Template que será adicionado.
	 * @param amount Valor da quantidade de template que ESTE Template possui. 
	 */
	void addTemplate(Template template, Long amount);
	
	/**
	 * Remove um Template através do seu nome.
	 * @param name Nome do Template que deve ser removido.
	 */
	void removeTemplate(String name);
	
	/**
	 * Verifica através do nome se o Template existe.
	 * @param name Nome do Template.
	 * @return true, se o template existir; false, se não.
	 */
	boolean hasTemplate(String name);

	/**
	 * Retorna um objeto Template através do seu nome.
	 * @param name Nome do Template.
	 * @return Objeto <code>Template</code> correspondente ao parametro name.
	 */
	TemplateAmount getTemplateAmount(String name);
	
	/**
	 * Retorna um objeto Template através do nome do schema.
	 * @param name Nome do Schema.
	 * @return Objeto <code>Template</code> correspondente ao parametro name.
	 */
	TemplateAmount getTemplateAmountBySchema(String name);
	
	/**
	 * Retorna o tipo do Template.
	 * @return o tipo do Template.
	 */
	TemplateType getTemplateType();

	/**
	 * Define uma formula que será usada para selecionar um template.
	 * @param formula Formula que será usada para selecionar um template.
	 */
	void setFormula(Formula formula);
	
	/**
	 * Retorna a formula.
	 * @return a formula.
	 */
	Formula getFormula();
	
	/**
	 * Retorna o objeto template escolhido da lista.
	 * @param product Produto que é composto pelo template. 
	 * @return objeto template escolhido da lista.
	 */
	Template getSelectedTemplate();
	
	/**
	 * Define o template selecionado.
	 * @param template
	 */
	void setSelectedTemplate(Template template);
	
	/**
	 * Retorna true se o Template for do tipo TemplateType.DEFAULT.
	 * @return true se o Template for do tipo TemplateType.DEFAULT.
	 */
	boolean isTemplateDefault();

	/**
	 * Retorna lista recursiva de atributos do tipo VARIABLE.
	 * @return lista recursiva de atributos do tipo VARIABLE.
	 */
	Set<AttributeValue> findVariableAttributeSet();

}
