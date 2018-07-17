package com.works.financas.api.model;

public enum SituacaoTitulo {

	RECEBER("Receber"),
	PAGAR("Pagar");
	
	private String descricao;
	
	SituacaoTitulo(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}