package br.com.ealbornoz.constructor.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import br.com.ealbornoz.constructor.api.Attribute;
import br.com.ealbornoz.constructor.api.AttributeValue;
import br.com.ealbornoz.constructor.api.Category;
import br.com.ealbornoz.constructor.api.FormulaProcessor;
import br.com.ealbornoz.constructor.api.FormulaProcessorFactory;
import br.com.ealbornoz.constructor.api.Product;
import br.com.ealbornoz.constructor.api.Template;
import br.com.ealbornoz.constructor.api.TemplateAmount;

@SuppressWarnings("serial")
@Entity(name = "product")
public class ProductImpl extends AbstractItemWithName implements Product {

	@ManyToOne(targetEntity = TemplateDefault.class)
	protected Template template;

	@OneToMany(targetEntity = ProductAttributeValue.class, cascade = CascadeType.ALL)
	@JoinColumn(name="product_id", nullable = false)
	protected List<AttributeValue> attributeValueList = new ArrayList<AttributeValue>();

	@OneToMany(targetEntity = ProductImpl.class, mappedBy = "productOwner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private final List<Product> productList = new ArrayList<Product>();
	
	@ManyToOne(targetEntity = ProductImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_owner_id")
	private Product productOwner;
	
	@SuppressWarnings("unused")
	private ProductImpl(){};
	
	@Transient
	private FormulaProcessorFactory formulaProcessorFactory;

	private Long amount;

	public ProductImpl(Template template, Long amount, ProductImpl productOwner, FormulaProcessorFactory formulaProcessorFactory) {
		
		if ( ! template.isTemplateDefault() ) {
			throw new IllegalArgumentException();
		}
		
		this.template = template;
		this.productOwner = productOwner;
		this.name = template.getName();
		this.formulaProcessorFactory = formulaProcessorFactory;
		this.amount = amount;
		
		initAttributes();
		initProducts();
	}

	public ProductImpl(Template template, FormulaProcessorFactory formulaProcessorFactory) {
		this(template, new Long(1), null, formulaProcessorFactory);
	}

	private void initProducts() {
		for (TemplateAmount tpl : template.getTemplateAmountList()) {
			
			if ( tpl.getTemplate().isTemplateDefault() ) {
				ProductImpl p = new ProductImpl(tpl.getTemplate(), tpl.getAmount(), this, formulaProcessorFactory);
				productList.add(p);
			}
			
		}
	}

	private void initAttributes() {
		
		for (AttributeValue attr : this.template.getAttributeValueList()) {
			setAttributeValue(new ProductAttributeValue(attr.getAttribute(), attr.getString()));
		}
	}

	@Override
	public Template getTemplate() {
		return template;
	}
	
	@Override
	public Product getProductOwner() {
		return productOwner;
	}
	
	@Override
	public void setProductOwner(Product productOwner) {
		this.productOwner = productOwner;
	}

	@Override
	public List<AttributeValue> getAttributeValueList() {
		return attributeValueList;
	}

	@Override
	public void setValue(String attributeName, String value) {

		Attribute attr = template.getSchema().getAttribute(attributeName);

		if (attr.getCategory().equals(Category.VARIABLE)
				|| attr.getCategory().equals(Category.CALCULATED)) {

			AttributeValue attrValue = new ProductAttributeValue(attr, value);
			setAttributeValue(attrValue);
		}

		setChildValue(attributeName, value);
	}

	private void setChildValue(String attributeName, String value) {
		for (Product p : productList) {
			AttributeValue attrValue = p.getAttributeValue(attributeName);
			if (attrValue != null) {
				if (Category.VARIABLE.equals(attrValue.getAttribute().getCategory())) {
					p.setValue(attributeName, value);
				}
			}
		}
	}

	@Override
	public AttributeValue getAttributeValue(String attributeName) {
		for (AttributeValue attrValue : attributeValueList) {
			if (attrValue.getName().equals(attributeName)) {
				return attrValue;
			}
		}
		return null;
	}

	@Override
	public List<Product> getProductList() {
		return productList;
	}

	@Override
	public void addProduct(Product product) {
		product.setProductOwner(this);
		productList.add(product);
	}

	@Override
	public void removeProduct(String name) {
		int index = FindOnItemList.getIndex(productList, name);
		productList.remove(index);
	}

	@Override
	public boolean hasProduct(String unitName) {
		int index = FindOnItemList.getIndex(productList, unitName);
		return (index >= 0 ? true : false);
	}

	@Override
	public Product getProduct(String name) {
		for (Product product : productList) {
			if (product.getName().equals(name)) {
				return product;
			}
		}
		return null;
	}

	@Override
	public void process() {
		for (Product child : productList) {
			child.process();
		}
		calculate();
		select();
	}

	private int findAttributeValueIndex(String attributeName) {
		for (int i = 0; i < attributeValueList.size(); i++) {
			AttributeValue attrValue = attributeValueList.get(i);
			if (attrValue.getName().equals(attributeName)) {
				return i;
			}
		}
		return -1;
	}

	private void setAttributeValue(AttributeValue attr) {
		int attrValueIndex = findAttributeValueIndex(attr.getName());
		if (attrValueIndex >= 0) {
			attributeValueList.remove(attrValueIndex);
		}
		attributeValueList.add(attr);
	}

	private void select() {
		
		for (TemplateAmount templateAmount : template.getTemplateAmountList()) {

			if ( ! templateAmount.getTemplate().isTemplateDefault() ) {
				
				FormulaProcessor formulaProcessor = formulaProcessorFactory.createFormulaProcessor();
				formulaProcessor.addObject( this , "product" );
				formulaProcessor.addObject( templateAmount.getTemplate() , "group" );
				formulaProcessor.setFormula( templateAmount.getTemplate().getFormula() );
				formulaProcessor.execute();
				
				Template selectedTemplate = templateAmount.getTemplate().getSelectedTemplate();
				Long amount = templateAmount.getTemplate().getTemplateAmount(selectedTemplate.getName()).getAmount();
				Product p = new ProductImpl(selectedTemplate, amount, this, formulaProcessorFactory);
				productList.add(p);
				setValues();
			}
			
		}
	}

	/**
	 * Seta os valores variaveis. Usado principalmente para os produtos que são
	 * incluídos por último (depois do processamento).
	 */
	private void setValues() {
		// TODO Auto-generated method stub
	}

	private void calculate() {
		
		for (AttributeValue attrValue : template.getAttributeValueList()) {
			if (attrValue.getAttribute().getCategory().equals(Category.CALCULATED)) {
				FormulaProcessor formulaProcessor = formulaProcessorFactory.createFormulaProcessor();
				formulaProcessor.addObject( this , "product" );
				formulaProcessor.setFormula(attrValue.getFormula());
				formulaProcessor.execute();
			}
		}
		
	}

	@Override
	public boolean isRoot() {
		return (productOwner == null);
	}
	
	public void setFormulaProcessorFactory(FormulaProcessorFactory formulaProcessorFactory) {
		this.formulaProcessorFactory = formulaProcessorFactory;
	}
	
	public FormulaProcessorFactory getFormulaProcessorFactory() {
		return formulaProcessorFactory;
	}

	@Override
	public Long getAmount() {
		return amount;
	}
	
	@Override
	public void setAmount(Long amount) {
		this.amount = amount;
		
		for(Product child : productList) {
			child.setAmount( child.getAmount() * amount );
		}
	}
	
}
