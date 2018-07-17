package com.works.financas.api.model;

public enum SituacaoLancamento {

	RECEBER("Receber"),
	PAGAR("Pagar");
	
	private String descricao;
	
	SituacaoLancamento(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}