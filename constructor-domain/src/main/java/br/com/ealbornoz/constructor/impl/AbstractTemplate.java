package br.com.ealbornoz.constructor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import br.com.ealbornoz.constructor.api.AttributeValue;
import br.com.ealbornoz.constructor.api.Formula;
import br.com.ealbornoz.constructor.api.Schema;
import br.com.ealbornoz.constructor.api.Template;
import br.com.ealbornoz.constructor.api.TemplateAmount;
import br.com.ealbornoz.constructor.api.TemplateType;

@SuppressWarnings("serial")
@Entity(name="template")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="template_type", discriminatorType=DiscriminatorType.STRING)
public abstract class AbstractTemplate extends AbstractItemWithUniqueName implements Template {

	@ManyToOne(targetEntity = SchemaImpl.class)
	private Schema schema;
	
	@OneToMany(targetEntity = TemplateStruct.class,	cascade = CascadeType.ALL)
	@JoinColumn(name = "template_master_id")
	private List<TemplateAmount> templateAmountList = new ArrayList<TemplateAmount>();
	
	@Column(name="template_type" , insertable=false , updatable = false)
	@Enumerated(value=EnumType.STRING)
	protected TemplateType templateType;

	@Override
	public Schema getSchema() {
		return schema;
	}
	
	public void setSchema(Schema schema) {
		this.schema = schema;
	}
	
	@Override
	public List<TemplateAmount> getTemplateAmountList() {
		return templateAmountList;
	}

	@Override
	public void removeTemplate(String name) {
		int index = FindOnItemList.getIndex(getTemplateAmountList(), name);
		if (index >= 0) {
			getTemplateAmountList().remove(index);
		}
	}

	@Override
	public boolean hasTemplate(String name) {
		int index = FindOnItemList.getIndex(templateAmountList, name);
		
		return index >= 0;
	}

	@Override
	public TemplateAmount getTemplateAmount(String name) {
		int index = FindOnItemList.getIndex(templateAmountList, name);
		
		return templateAmountList.get(index);
	}
	
	@Override
	public TemplateAmount getTemplateAmountBySchema(String name) {
		
		for(TemplateAmount templateAmount : templateAmountList) {
			
			Schema templateAmountSchema = templateAmount.getTemplate().getSchema();
			
			if (templateAmountSchema.getName().equals(name)) {
				return templateAmount;
			}
		}
		
		return null;
	}
	
	public boolean isTemplateDefault() {
		return TemplateType.DEFAULT.equals( this.getTemplateType() );
	}

	@Override
	public List<AttributeValue> getAttributeValueList() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setValue(String attributeName, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setValue(String attributeName, String value, Formula formula) {
		throw new UnsupportedOperationException();
	}

	@Override
	public AttributeValue getAttributeValue(String attributeName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFormula(Formula formula) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Formula getFormula() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Template getSelectedTemplate() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void setSelectedTemplate(Template template) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void addTemplate(Template template) {
		addTemplate(template, new Long(1));
	}
	
	@Override
	public Set<AttributeValue> findVariableAttributeSet() {
		throw new UnsupportedOperationException();
	}
	
}
