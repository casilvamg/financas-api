package com.works.financas.api.dto;

import java.math.BigDecimal;

public class InvestimentoRendimentoResgate {

	private String nome;
	private BigDecimal resgate;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public BigDecimal getResgate() {
		return resgate;
	}
	public void setResgate(BigDecimal resgate) {
		this.resgate = resgate;
	}
}
