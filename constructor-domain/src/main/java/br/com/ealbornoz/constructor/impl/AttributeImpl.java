package br.com.ealbornoz.constructor.impl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.ealbornoz.constructor.api.Attribute;
import br.com.ealbornoz.constructor.api.Category;

@Entity(name="attribute")
public class AttributeImpl extends AbstractItemWithName implements Attribute, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(nullable=false, length=20)
	@Enumerated(value=EnumType.STRING)
	private Category category;
	
	public AttributeImpl() {}

	public AttributeImpl(String name, Category category) {
		this.name = name;
		this.category = category;
	}

	@Override
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}

}
