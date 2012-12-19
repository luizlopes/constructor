package br.com.ealbornoz.constructor.dao;

import java.util.List;

import br.com.ealbornoz.constructor.api.Schema;
import br.com.ealbornoz.constructor.api.Template;
import br.com.ealbornoz.constructor.api.TemplateAmount;

public interface TemplateDataManager {

	List<Template> listAll();

	void save(Template template);
	
	void update(Template template);

	List<Template> listTemplateDefault();

	List<Template> listTemplateBySchema(Schema schema);

	List<Template> listTemplateGroup();

	List<Template> listTemplateDefaultBySchema(Schema schema);

	Template get(Long templateId);
	
	void updateTemplateAmount(TemplateAmount templateAmount);

	void remove(Template template);

}
