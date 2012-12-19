package br.com.ealbornoz.constructor.service;

import java.util.List;

import br.com.ealbornoz.constructor.api.Schema;

public interface SchemaService {
	
	public List<Schema> listAll();
	
	public void addSchema(Schema schema);
	
	public void updateSchema(Schema schema);

	public Schema get(Long id);

	public void removeSchema(Schema selectedSchema);

}
