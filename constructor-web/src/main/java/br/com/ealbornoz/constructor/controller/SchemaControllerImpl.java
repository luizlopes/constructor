package br.com.ealbornoz.constructor.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.validation.Valid;

import org.apache.log4j.Logger;

import br.com.ealbornoz.constructor.api.Attribute;
import br.com.ealbornoz.constructor.api.Category;
import br.com.ealbornoz.constructor.api.Schema;
import br.com.ealbornoz.constructor.common.BaseEntityDataModel;
import br.com.ealbornoz.constructor.common.BaseEntitySelectItem;
import br.com.ealbornoz.constructor.common.BaseEnumSelectItem;
import br.com.ealbornoz.constructor.impl.AttributeImpl;
import br.com.ealbornoz.constructor.impl.SchemaImpl;
import br.com.ealbornoz.constructor.service.SchemaService;

@ManagedBean(name="schemaController")
public class SchemaControllerImpl {
	
	@ManagedProperty("#{schemaService}")
	private SchemaService schemaService;

	@Valid
	private Schema schema;
	
	private Schema selectedSchema;
	
	private Long childSchemaId;
	
	private Attribute attribute;
	
	private Logger logger = Logger.getLogger(SchemaControllerImpl.class);
	
	public SchemaControllerImpl() {
		logger.info("Iniciando Controller");
		initSchema();
		initAttribute();
	}

	private void initAttribute() {
		attribute = new AttributeImpl();
	}

	private void initSchema() {
		schema = new SchemaImpl();
	}
	
	public BaseEntityDataModel<Schema> getSchemaList() {
		logger.info("Gerando BaseEntityDataModel para Schema.");
		BaseEntityDataModel<Schema> model = new BaseEntityDataModel<Schema>();
		model.setWrappedData(schemaService.listAll());
		return model;
	}
	
	public void addSchema() {
		logger.info("Adicionando novo Schema " + schema.getName());
		schemaService.addSchema(schema);
		selectedSchema = schema;
		initSchema();
	}
	
	public void removeSchema(ActionEvent actionEvent) {
		logger.info("Removendo Schema " + actionEvent.getComponent().getAttributes().get("schemaId").toString());

		Long id = Long.parseLong(actionEvent.getComponent().getAttributes().get("schemaId").toString());
		
		if (id != null && id.longValue() > 0) {
			selectedSchema = schemaService.get(id);
			schemaService.removeSchema(selectedSchema);
			selectedSchema = null;
		}
	}
	
	public void addAttribute() {
		
		if (selectedSchema == null) {
			return;
		}
		
		logger.info("Adicionando novo Attribute " + attribute.getName() + " para Schema " + selectedSchema.getName());
		selectedSchema.addAttribute(attribute);
		schemaService.updateSchema(selectedSchema);
		initAttribute();
	}
	
	public void addChildSchema() {
		
		logger.info("Adicionando novo Schema filho ");
		
		if (selectedSchema == null && getChildSchemaId().longValue() > 0) {
			return;
		}
		
		logger.info("Adicionando novo Schema filho " + childSchemaId + " para Schema " + selectedSchema.getName());
		
		Schema childSchema = schemaService.get(childSchemaId);
		
		selectedSchema.addSchema(childSchema );
		schemaService.updateSchema(selectedSchema);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SelectItem[] getCategorySelectItems() {
		
		logger.info("Gerando array de SelectItem para Category.");
		
		SelectItem[] itens = null;
		
		try {
			itens = new BaseEnumSelectItem(Category.class).getItems();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return itens;
	}
	
	public SelectItem[] getSchemaSelectItems() {
		
		logger.info("Gerando array de SelectItem para Schema");
		
		List<Schema> lista = schemaService.listAll();
		
		if (selectedSchema != null) {
			
			lista.remove(selectedSchema);
			
			List<Schema> schemaList = selectedSchema.getSchemaList();
			
			for (Schema s : schemaList) {
				lista.remove(s);
			}
		}
		
		SelectItem[] itens = new BaseEntitySelectItem<List<Schema>>(lista).getItems();
		
		return itens;
	}
	
	public Schema getSchema() {
		return this.schema;
	}
	
	public void setSchema(Schema schema) {
		this.schema = schema;
	}
	
	public Schema getSelectedSchema() {
		if (selectedSchema == null) {
			return new SchemaImpl();
		}
		return selectedSchema;
	}
	
	public void setSelectedSchema(Schema selectedSchema) {
		this.selectedSchema = selectedSchema;
	}
	
	public Long getChildSchemaId() {
		
		if (childSchemaId == null) {
			childSchemaId = new Long(0);
		}
		
		return childSchemaId;
	}
	
	public void setChildSchemaId(Long childSchemaId) {
		this.childSchemaId = childSchemaId;
	}
	
	
	public Attribute getAttribute() {
		return attribute;
	}
	
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public void setSchemaService(SchemaService schemaService) {
		this.schemaService = schemaService;
	}
	
}
