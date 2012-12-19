package br.com.ealbornoz.constructor.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.ealbornoz.constructor.api.Product;
import br.com.ealbornoz.constructor.dao.ProductDataManager;

@Service("productService")
public class ProductServiceImpl implements ProductService, Serializable {
	
	/**
	 * Serial UID 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private ProductDataManager productDataManager;
	
	@Override
	public List<Product> listAll() {
		return productDataManager.listAll();
	}
	
	@Override
	public List<Product> listRoots() {
		return productDataManager.listRoots();
	}
	
	@Override
	public void save(Product product) {
		productDataManager.save(product);
	}
	
	@Override
	public Product get(Long id) {
		return productDataManager.get(id);
	}

	public void setProductDataManager(ProductDataManager productDataManager) {
		this.productDataManager = productDataManager;
	}
	
}
