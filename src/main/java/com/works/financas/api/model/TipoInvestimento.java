package com.works.financas.api.model;

public enum TipoInvestimento {

	POUPANCA("Poupança"),
	FUNDOS("Fundo de Investimento"),
	PREVIDENCIA("Previdência Privada"),
	CDB("CDB"), 
	ACAO("Ação"),
	DEBENTURE("Debênture"),
	TESOURO("Tesouro Direto"),
	TITULOS("Títulos Públicos"),
	LETRAS("LCI e LCA"),
	CAPITALIZACAO("Título de Capitalização"); 
	
	private String descricao;
	
	TipoInvestimento(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}