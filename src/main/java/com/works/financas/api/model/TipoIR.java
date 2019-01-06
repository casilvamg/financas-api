package com.works.financas.api.model;

public enum TipoIR {
	REGRESSIVO("Regressivo"),
	PROGRESSIVO("Progressivo"),
	ISENTO("Isento"),
	FIXA("Fixa");
	
	private String descricao;
	
	TipoIR(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
