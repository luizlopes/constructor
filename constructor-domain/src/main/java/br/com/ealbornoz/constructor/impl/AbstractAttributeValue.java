package br.com.ealbornoz.constructor.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Target;

import br.com.ealbornoz.constructor.api.Attribute;
import br.com.ealbornoz.constructor.api.AttributeValue;
import br.com.ealbornoz.constructor.api.Formula;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbstractAttributeValue extends AbstractItemWithName implements AttributeValue {

	@ManyToOne
	@Target(value=AttributeImpl.class)
	protected Attribute attribute;
	
	@Column(length=20)
	protected String value;

	
	public AbstractAttributeValue() {}
	
	public AbstractAttributeValue(Attribute attribute, String value) {
		setAttribute(attribute);
		this.value = value;
	}

	@Override
	public Attribute getAttribute() {
		return attribute;
	}

	@Override
	public String getString() {
		return value;
	}

	@Override
	public Long getLong() {
		return Long.parseLong(value);
	}

	@Override
	public Double getDouble() {
		return Double.parseDouble(value);
	}

	@Override
	public Date getDate(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(value);
		} catch (ParseException e) {
			new RuntimeException(e.getCause());
		}
		return null;
	}

	@Override
	public void setAttribute(Attribute attribute) {
		this.name = attribute.getName();
		this.attribute = attribute;
	}
	
	@Override
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	@Override
	public Formula getFormula() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFormula(Formula formula) {
		throw new UnsupportedOperationException();
	}
	
}
