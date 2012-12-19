package br.com.ealbornoz.constructor.api;

/**
 * Representa uma Formula para ser executada.
 * 
 * @author guilherme
 *
 */
public interface Formula extends ItemWithName {
	
	/**
	 * Retorna a linguagem do script.
	 * 
	 * @return a linguagem do script.
	 */
	String getLanguage();
	
	/**
	 * Seta a linguagem do script.
	 * @param language a linguagem do script.
	 */
	void setLanguage(String language);
	
	/**
	 * Retorna o script que será executado.
	 * @return o script que será executado.
	 */
	String getScript();
	
	/**
	 * Seta o script que será executado.
	 * @param script que será executado.
	 */
	void setScript(String script);

}
