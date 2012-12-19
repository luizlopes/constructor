package br.com.ealbornoz.constructor.api;

/**
 * Executa uma formula para um produto.
 * 
 * @author guilherme
 *
 */
public interface FormulaProcessor {
	
	/**
	 * Adiciona objetos para serem utilizados na formula.
	 * 
	 * @param object que será utilizado pelo processador.
	 * @param name do objeto que será utilizado pelo processador. 
	 */
	void addObject(Object object, String name);
	
	/**
	 * Retorna a formula que deve ser executada.
	 * 
	 * @return a formula que deve ser executada.
	 */
	Formula getFormula();
	
	/**
	 * Seta a formula que deve ser executada.
	 * 
	 * @param formula que deve ser executada.
	 */
	void setFormula(Formula formula);
	
	/**
	 * Executa a formula.
	 */
	void execute();

}
