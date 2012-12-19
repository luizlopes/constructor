package br.com.ealbornoz.constructor.service;

import java.util.List;

import br.com.ealbornoz.constructor.api.Product;

/**
 * Interface para serviços da entidade Product.
 * 
 * @author luizlopes
 * @since 28/08/2012
 */
public interface ProductService {

	/**
	 * Retorna todos os registros de Product.
	 * @return todos os registros de Product.
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
