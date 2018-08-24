package com.works.financas.api.model;

public enum Tipo {

	RECEITA("Receita"),
	DESPESA("Despesa");
	
	private String descricao;
	
	Tipo(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}