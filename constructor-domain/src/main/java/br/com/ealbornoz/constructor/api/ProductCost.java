package br.com.ealbornoz.constructor.api;


/**
 * Representa o custo de um produto e de seus filhos.
 * 
 * @author guilherme
 *
 */
public interface ProductCost {

	/**
	 * Retorna o ProductCost do produto pai.
	 * @return o ProductCost do produto pai.
	 */
	ProductCost getOwner();

	/**
	 * Retorna o calculo do valor total do custo, somando todos os custos dos produtos filhos 
	 * e multiplicando pela quantidade (amount) de produtos.
	 * @return o calculo do valor total do custo, somando todos os custos dos produtos filhos.
	 */
	Double getTotalCost();
	
	/**
	 * Retorna o calculo do valor total do custo, somando todos os custos dos produtos filhos,
	 * sem multiplicar pela quantidade de produtos.
	 * @return o calculo do valor total do custo, somando todos os custos dos produtos filhos.
	 */
	Double getTotalUnitCost();

	/**
	 * Retorna o valor isolado do produto, sem considerar os valores de custos dos filhos. 
	 * @return
	 */
	Double getCost();
	
	/**
	 * Retorna um ProductCost para de produto filho.
	 * 
	 * @param product filho que deve ter seu custo retornado.
	 * @return um ProductCost para de produto filho.
	 */
	ProductCost findChildByProduct(Product product);

	/**
	 * Retorna o Produto do ProductCost.
	 * @return o Produto do ProductCost.
	 */
	Product getProduct();
	
	

}