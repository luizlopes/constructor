package br.com.ealbornoz.constructor.dao;

import java.util.List;

import br.com.ealbornoz.constructor.api.Schema;

public interface SchemaDataManager {
	
	public List<Schema> listAll();

	public void save(Schema schema);
	
	public void update(Schema schema);

	public Schema get(Long id);

	public void remove(Schema schema);

}
