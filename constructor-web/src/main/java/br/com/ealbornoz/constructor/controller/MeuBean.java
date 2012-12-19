package br.com.ealbornoz.constructor.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ManagedBean(name="meuBean")
@RequestScoped
public class MeuBean {

	private String nome;

	@NotNull(message="Erro porra!")
	@Size(min=10, message="Tamanho minimo invalido")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void testa() {
		System.out.println("Teste");
	}
	
	
}
