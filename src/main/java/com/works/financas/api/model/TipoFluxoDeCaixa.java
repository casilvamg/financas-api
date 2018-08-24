package com.works.financas.api.model;

public enum TipoFluxoDeCaixa {

	Total("TOTAL"),
	Realizado("REALIZADO"),
	Faltante("FALTANTE");
	
	private String descricao;
	
	TipoFluxoDeCaixa(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}