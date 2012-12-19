package br.com.ealbornoz.constructor.api;

import java.util.List;

/**
 * Define um produto.
 * 
 * @author guilherme
 */
public interface Product extends ItemWithName {
	
	/**
	 * Retorna o Template do produto.
	 * @return o Template do produto.
	 */
	Template getTemplate();

	/**
	 * Retorna o ProductOwner do produto.
	 * @return o ProductOwner do produto.
	 */
	Product getProductOwner();

	/**
	 * Seta o valor do productOwner.
	 * @param productOwner
	 */
	void setProductOwner(Product productOwner);
	
	/**
	 * Retorna o conjunto de valores dos atributos do Produto. 
	 * @return o conjunto de valores dos atributos do Produto.
	 */
	List<AttributeValue> getAttributeValueList();
	
	/**
	 * Seta o valor para o atributo.
	 * @param attributeName Nome do atributo que recebe o valor.
	 * @param value Valor do atributo.
	 */
	void setValue(String attributeName, String value);
	
	/**
	 * Retorna um <code>AttributeValue</code>.
	 * @param attributeName Nome do atributo que recebe o valor.
	 * @return um <code>AttributeValue</code> correspondente ao parametro <code>attributeName</code>.
	 */
	AttributeValue getAttributeValue(String attributeName);
	
	/**
	 * Retorna um conjunto de <code>Product</code>.
	 * @return um conjunto de <code>Product</code>.
	 */
	List<Product> getProductList();
	
	/**
	 * Adiciona um Product ao conjunto. 
	 * @param product Product que será adicionado.
	 */
	void addProduct(Product product);
	
	/**
	 * Remove uma unidade através do seu nome.
	 * @param name Nome do Product que deve ser removido.
	 */
	void removeProduct(String unitName);
	
	/**
	 * Verifica através do nome se o Product existe.
	 * @param name Nome do Product.
	 * @return true, se o Product existir; false, se não.
	 */
	boolean hasProduct(String unitName);

	/**
	 * Retorna um objeto Product através do seu nome.
	 * @param name Nome do Product.
	 * @return Objeto <code>Product</code> correspondente ao parametro name.
	 */
	Product getProduct(String name);
	
	/**
	 * Processa o produto. 
	 */
	void process();
	
	/**
	 * Indica se o produto é raiz ou se ele é parte de outro produto.
	 * @return true, se o produto for raiz; false, se for parte de outro produto.
	 */
	boolean isRoot();
	
	/**
	 * Define a Factory para o FormulaProcessor.
	 * @param formulaProcessorFactory factory para o FormulaProcessor.
	 */
	void setFormulaProcessorFactory(FormulaProcessorFactory formulaProcessorFactory);

	/**
	 * Retorna a quantidade deste produto.
	 * @return a quantidade deste produto.
	 */
	Long getAmount();

	/**
	 * Define a quantidade deste produto.
	 * @param quantidade 
	 */
	void setAmount(Long amount);

}
