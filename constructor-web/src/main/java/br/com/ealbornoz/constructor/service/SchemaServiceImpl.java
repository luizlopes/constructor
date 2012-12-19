package br.com.ealbornoz.constructor.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.ealbornoz.constructor.api.Schema;
import br.com.ealbornoz.constructor.dao.SchemaDataManager;

@Service("schemaService")
public class SchemaServiceImpl implements SchemaService, Serializable {
	
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private SchemaDataManager schemaDataManager;

	@Override
	public List<Schema> listAll() {
		return schemaDataManager.listAll();
	}

	@Override
	public void addSchema(Schema schema) {
		schemaDataManager.save(schema);
	}
	
	@Override
	public void updateSchema(Schema schema) {
		schemaDataManager.update(schema);
	}
	
	@Override
	public Schema get(Long id) {
		return schemaDataManager.get(id);
	}
	
	@Override
	public void removeSchema(Schema schema) {
		schemaDataManager.remove(schema);
	}
	
	public void setSchemaDataManager(SchemaDataManager schemaDataManager) {
		this.schemaDataManager = schemaDataManager;
	}

}
