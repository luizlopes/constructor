package br.com.ealbornoz.constructor.controller;

import java.util.List;

import br.com.ealbornoz.constructor.api.Schema;

public interface SchemaController {

	public abstract List<Schema> getSchemaList();
	
	public abstract void addSchema();
	
	public abstract void addAttribute();

}