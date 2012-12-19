package br.com.ealbornoz.constructor.common;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import br.com.ealbornoz.constructor.api.AttributeValue;
import br.com.ealbornoz.constructor.api.FormulaProcessorFactory;
import br.com.ealbornoz.constructor.api.Product;
import br.com.ealbornoz.constructor.api.Template;

/**
 * Classe que representa um Product vazio.
 * 
 * @author luizlopes
 * @since 29/08/2012
 */
public class ProductEmpty implements Product, Serializable {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void setName(String name) {
	}

	@Override
	public Long getId() {
		return null;
	}

	@Override
	public void setId(Long id) {
	}

	@Override
	public Template getTemplate() {
		return null;
	}

	@Override
	public Product getProductOwner() {
		return null;
	}

	@Override
	public void setProductOwner(Product productOwner) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AttributeValue> getAttributeValueList() {
		return Collections.EMPTY_LIST;
	}

	@Override
	public void setValue(String attributeName, String value) {
	}

	@Override
	public AttributeValue getAttributeValue(String attributeName) {
		return null;
	}

	@Override
	public List<Product> getProductList() {
		return null;
	}

	@Override
	public void addProduct(Product product) {
	}

	@Override
	public void removeProduct(String unitName) {
	}

	@Override
	public boolean hasProduct(String unitName) {
		return false;
	}

	@Override
	public Product getProduct(String name) {
		return null;
	}

	@Override
	public void process() {
	}

	@Override
	public boolean isRoot() {
		return false;
	}

	@Override
	public void setFormulaProcessorFactory(
			FormulaProcessorFactory formulaProcessorFactory) {
	}

	@Override
	public Long getAmount() {
		return null;
	}

	@Override
	public void setAmount(Long amount) {
	}

}
