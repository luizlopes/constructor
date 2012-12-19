package br.com.ealbornoz.constructor.impl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import br.com.ealbornoz.constructor.api.Formula;

@Entity(name="formula")
public class FormulaImpl extends AbstractItemWithName implements Formula, Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(length=20)
	private String language;
	
	@Column(length=500)
	private String script;
	
	public FormulaImpl() {}

	@Override
	public String getLanguage() {
		return language;
	}

	@Override
	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String getScript() {
		return script;
	}

	@Override
	public void setScript(String script) {
		this.script = script;
	}

}
