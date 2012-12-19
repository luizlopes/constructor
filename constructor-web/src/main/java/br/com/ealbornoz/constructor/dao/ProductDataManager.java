package br.com.ealbornoz.constructor.dao;

import java.util.List;

import br.com.ealbornoz.constructor.api.Product;

/**
 * Interface para data manager para entidade Product.
 * 
 * @author luizlopes
 * @since 28/08/2012
 */
public interface ProductDataManager {

	/**
	 * Retorna todos registros de Product.
	 * @return todos registros de Product.
	 */
	List<Product> listAll();

	/**
	 * Retorna registros de Product que são raízes.
	 * @return registros de Product que são raízes.
	 */
	List<Product> listRoots();

	/**
	 * Persiste objeto Product.
	 * @param product objeto a ser persistido.
	 */
	void save(Product product);

	/**
	 * Retorna objeto Product através do seu id.
	 * @param id id do objeto a ser retornado.
	 * @return objeto Product através do seu id.
	 */
	Product get(Long id);

}
