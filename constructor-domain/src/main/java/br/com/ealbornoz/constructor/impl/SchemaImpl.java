package br.com.ealbornoz.constructor.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import br.com.ealbornoz.constructor.api.Attribute;
import br.com.ealbornoz.constructor.api.Schema;

@Entity(name = "schema_t")
public class SchemaImpl extends AbstractItemWithUniqueName implements Schema {

	private static final long serialVersionUID = 1L;

	@OneToMany(targetEntity = AttributeImpl.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "schema_id", nullable = false)
	private List<Attribute> attributeList = new ArrayList<Attribute>();
	
	@ManyToMany(targetEntity=SchemaImpl.class)
	@JoinTable(name = "schema_struct", 
		joinColumns = @JoinColumn(name = "schema_id"), 
		inverseJoinColumns = @JoinColumn(name = "schema_owned_id"))
	private List<Schema> schemaList = new ArrayList<Schema>();
	
	public SchemaImpl(){}
	
	public SchemaImpl(String name) {
		super();
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<Attribute> getAttributeList() {
		return attributeList;
	}

	@Override
	public void addAttribute(Attribute attribute) {
		attributeList.add(attribute);
	}

	@Override
	public void removeAttribute(String name) {
		int index = FindOnItemList.getIndex(attributeList, name);
		attributeList.remove(index);
	}

	@Override
	public boolean hasAttribute(String name) {
		return findAttribute(name) >= 0;
	}

	private int findAttribute(String name) {
		int index = FindOnItemList.getIndex(attributeList, name);
		return index;
	}

	@Override
	public Attribute getAttribute(String name) {
		return attributeList.get(findAttribute(name));
	}
	
	@Override
	public List<Schema> getSchemaList() {
		return Collections.unmodifiableList(schemaList);
	}

	@Override
	public void addSchema(Schema schema) {
		schemaList.add(schema);
	}

	@Override
	public void removeSchema(String name) {
		int index = FindOnItemList.getIndex(schemaList, name);
		schemaList.remove(index);
	}

	@Override
	public boolean hasSchema(String name) {
		int index = FindOnItemList.getIndex(schemaList, name);
		return index >= 0;
	}

	@Override
	public Schema getSchema(String name) {
		int index = FindOnItemList.getIndex(schemaList, name);
		return schemaList.get(index);
	}
	
}
