package br.com.ealbornoz.constructor.api;

public interface TemplateCost extends Item {
	
	public abstract Template getTemplate();

	public abstract AttributeValue getAttributeValue();

	public abstract void setAttributeValue(AttributeValue attributeValue);

	public abstract Double getCost();

	public abstract void setCost(Double cost);

}