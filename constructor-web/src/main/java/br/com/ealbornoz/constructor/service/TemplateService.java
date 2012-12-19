package br.com.ealbornoz.constructor.service;

import java.util.List;

import br.com.ealbornoz.constructor.api.Schema;
import br.com.ealbornoz.constructor.api.Template;
import br.com.ealbornoz.constructor.api.TemplateAmount;
import br.com.ealbornoz.constructor.model.TemplateDTO;

public interface TemplateService {

	List<Template> listAll();

	Template addTemplate(TemplateDTO template);

	List<Template> listTemplateDefault();

	Template addTemplateWizard(TemplateDTO template);

	List<Template> listTemplateBySchema(Schema schema);

	List<Template> listTemplateGroup();

	List<Template> listTemplateDefaultBySchema(Schema schema);

	Template get(Long templateId);

	void save(Template template);
	
	void update(Template template);
	
	void updateTemplateAmount(TemplateAmount templateAmount);

	void removeTemplate(Template template);

}
