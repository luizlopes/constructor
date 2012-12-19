package br.com.ealbornoz.constructor.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import br.com.ealbornoz.constructor.api.AttributeValue;
import br.com.ealbornoz.constructor.api.Product;
import br.com.ealbornoz.constructor.api.ProductCost;
import br.com.ealbornoz.constructor.api.Template;
import br.com.ealbornoz.constructor.api.TemplateCost;

@Entity(name="product_cost")
public class ProductCostImpl extends AbstractItem implements ProductCost {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne(targetEntity=ProductImpl.class)
	private Product product;
	
	@Transient
	private List<TemplateCostImpl> templateCostList;
	
	@ManyToOne(targetEntity=TemplateCostImpl.class)
	private TemplateCost templateCost;
	
	private Double totalCost;
	
	private Double cost;
	
	private Double totalUnitCost;

	@ManyToOne(targetEntity=ProductCostImpl.class)
	private ProductCost productCostOwner;

	@OneToMany(targetEntity=ProductCostImpl.class)
	@JoinTable(name="product_cost_struct",
			joinColumns=@JoinColumn(name="product_cost_id"),
			inverseJoinColumns=@JoinColumn(name="product_cost_child"))
	private List<ProductCost> productCostList = new ArrayList<ProductCost>();

	
	public ProductCostImpl(Product product, List<TemplateCostImpl> templateCostList, ProductCost productCostOwner) {
		
		this.product          = product;
		this.templateCostList = templateCostList;
		this.templateCost     = findTemplateCost();
		this.cost             = calculateUnitCost();
		this.totalUnitCost    = calculateTotalUnitCost();
		this.totalCost        = calculateTotalCost();
		this.productCostOwner = productCostOwner;
	}


	private double calculateTotalCost() {
		return totalUnitCost * product.getAmount();
	}

	
	public ProductCostImpl(Product product, List<TemplateCostImpl> templateCostList) {
		this(product, templateCostList, null);
	}


	private TemplateCost findTemplateCost() {
		
		Template template = this.product.getTemplate();
		
		for (TemplateCostImpl templateCost : templateCostList) {
			if ( templateCost.getTemplate().equals(template) ) {
				return templateCost;
			}
		}
		
		return new TemplateCostImpl(template);
	}

	@Override
	public Double getTotalCost() {
		return totalCost;
	}
	
	@Override
	public Double getCost() {
		return cost;
	}

	private Double calculateTotalUnitCost() {
		
		Double totalChildCost = cost;
		
		for (Product child : product.getProductList() ) {
			
			ProductCostImpl childProductCost = new ProductCostImpl(child , templateCostList, this);
			productCostList.add(childProductCost);
			totalChildCost += childProductCost.getTotalCost();
		}
		
		return totalChildCost;
	}
	
	private Double calculateUnitCost() {
		
		AttributeValue templateAttributeValue = templateCost.getAttributeValue();
		
		if (templateAttributeValue != null) {
			AttributeValue productAttributeValue = product.getAttributeValue(templateAttributeValue.getName());
			Double productValue = productAttributeValue.getDouble();
			Double templateValue = templateAttributeValue.getDouble();
			double unitCost = (productValue * templateCost.getCost()) / templateValue;
			return unitCost;
		} else {
			return templateCost.getCost();
		}
		
	}

	@Override
	public ProductCost getOwner() {
		return productCostOwner;
	}


	@Override
	public ProductCost findChildByProduct(Product esteiraProduct) {
		
		for (ProductCost child : productCostList) {
			if ( child.getProduct().equals(esteiraProduct) )
				return child;
		}
		
		return null;
	}
	
	@Override
	public Product getProduct() {
		return product;
	}

	@Override
	public Double getTotalUnitCost() {
		return totalUnitCost;
	}
	
}
