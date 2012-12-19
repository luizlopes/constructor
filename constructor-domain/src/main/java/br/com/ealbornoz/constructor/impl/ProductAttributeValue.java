package br.com.ealbornoz.constructor.impl;

import javax.persistence.Entity;

import br.com.ealbornoz.constructor.api.Attribute;

@SuppressWarnings("serial")
@Entity(name="product_attribute_value")
public class ProductAttributeValue extends AbstractAttributeValue {
	
	public ProductAttributeValue() {}
	
	public ProductAttributeValue(Attribute attribute, String value) {
		super(attribute, value);
	}

}
