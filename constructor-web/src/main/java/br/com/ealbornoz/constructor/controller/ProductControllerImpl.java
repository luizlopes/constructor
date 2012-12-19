package br.com.ealbornoz.constructor.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.ealbornoz.constructor.api.AttributeValue;
import br.com.ealbornoz.constructor.api.Product;
import br.com.ealbornoz.constructor.api.Template;
import br.com.ealbornoz.constructor.common.BaseEntityDataModel;
import br.com.ealbornoz.constructor.common.BaseEntitySelectItem;
import br.com.ealbornoz.constructor.common.ProductEmpty;
import br.com.ealbornoz.constructor.impl.ProductImpl;
import br.com.ealbornoz.constructor.impl.RhinoFormulaProcessorFactory;
import br.com.ealbornoz.constructor.service.ProductService;
import br.com.ealbornoz.constructor.service.TemplateService;

@ManagedBean(name="productController")
@ViewScoped
public class ProductControllerImpl implements Serializable {
	
	/**
	 * Serial version UID 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(ProductControllerImpl.class);

	private static final String STEP_SET_VALUES = "setValues";

	private static final String STEP_PROCESS = "process";
	
	private static final ProductEmpty PRODUCT_EMPTY = new ProductEmpty();
	
	private TreeNode selectedNode;

	@ManagedProperty("#{productService}")
	private ProductService productService;
	
	@ManagedProperty("#{templateService}")
	private TemplateService templateService;

	private Set<AttributeValue> variableAttributeSet;

	private Long templateId;

	private Template template;

	private Product product;
	
	private Product selectedProduct;

	private Long amount;

	private TreeNode selectedProductNode;
	
	public ProductControllerImpl() {
		logger.info("Iniciando Controller " + this.getClass().getSimpleName());
	}
	
	public BaseEntityDataModel<Product> getProductList() {
		logger.info("Gerando BaseEntityDataModel para Template");
	
		BaseEntityDataModel<Product> model = new BaseEntityDataModel<Product>();
		model.setWrappedData(productService.listRoots());
		return model;
	}
	
	public BaseEntityDataModel<AttributeValue> getAttributeValueSet() {
		BaseEntityDataModel<AttributeValue> model = new BaseEntityDataModel<AttributeValue>();
		if (variableAttributeSet != null) {
			model.setWrappedData(new ArrayList<AttributeValue>(variableAttributeSet));
		}
		return model;
	}
	
	public TreeNode getRoot() {
		
		TreeNode root = new DefaultTreeNode("root", null);
		
		List<Product> productList = productService.listRoots();
		
		for (Product product : productList) {
			
			createNode(product, root);
			
		}
		
		return root;
	}

	private void createNode(Product product, TreeNode parent) {
	
		if (product.getProductList() == null || product.getProductList().isEmpty()) {
			
			new DefaultTreeNode(product, parent);

		} else {
		
			DefaultTreeNode newParent = new  DefaultTreeNode(product, parent);
			
			for (Product productElement : product.getProductList()) {
				createNode(productElement, newParent);
			}
		}
	}
	
	public SelectItem[] getTemplateSelectItems() {
		logger.info("Gerando array de SelectItem para Template");
		
		List<Template> lista = templateService.listTemplateDefault();
		SelectItem[] itens = new BaseEntitySelectItem<List<Template>>(lista).getItems();
		return itens;
	}
	
	public void addProduct() {
		if (product != null && !PRODUCT_EMPTY.equals(product)) {
			
			productService.save(product);
			
			selecionarTreeNode(product);
			
			product = null;
		}
	}

	private void selecionarTreeNode(Product selectProduct) {
		
		for (TreeNode node : getRoot().getChildren()) {
			
			Long elementId = ((Product) node.getData()).getId();
			
			if (product.getId().equals(elementId)) {
				selectedNode = node;
			}
		}
	}
	
	public String onFlowProcess(FlowEvent flowEvent) {
		
		String nextStep = flowEvent.getNewStep();
		
		if (templateId != null && STEP_SET_VALUES.equals(nextStep)) {

			template = templateService.get(templateId);
			
			variableAttributeSet = template.findVariableAttributeSet();
			
		} else if (STEP_PROCESS.equals(nextStep)) {
			
			template = templateService.get(templateId);
			
			product = new ProductImpl(template, amount, null, new RhinoFormulaProcessorFactory());
			
			for(AttributeValue attributeValue : variableAttributeSet) {
				product.setValue(attributeValue.getName(), attributeValue.getString());
			}
			
			product.process();
			
		}
		
		return nextStep;
	}
	
	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}
	
	public Product getSelectedProduct() {
		if (selectedNode != null) {
			Product data = (Product) selectedNode.getData();
			Product p = productService.get(data.getId());
			return p;
		} else {
			return PRODUCT_EMPTY;
		}
	}
	
	public Set<AttributeValue> getVariableAttributeSet() {
		return variableAttributeSet;
	}

	public void setVariableAttributeSet(Set<AttributeValue> variableAttributeSet) {
		this.variableAttributeSet = variableAttributeSet;
	}
	
	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	
	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public Product getProduct() {
		if (product == null) {
			product = new ProductEmpty();
		}
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
		selectedProductNode = new DefaultTreeNode("root", null);
		createNode(selectedProduct, selectedProductNode);
	}

	public TreeNode getSelectedProductNode() {
		return selectedProductNode;
	}

	public void setSelectedProductNode(TreeNode selectedProductNode) {
		this.selectedProductNode = selectedProductNode;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}
	
}
