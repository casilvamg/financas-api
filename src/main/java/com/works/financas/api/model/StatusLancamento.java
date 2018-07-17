package com.works.financas.api.model;

public enum StatusLancamento {

	PENDENTE("Pendente"),
	CANCELADO("Cancelado"),
	RECEBIDO("Finalizado");
	
	private String descricao;
	
	StatusLancamento(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}