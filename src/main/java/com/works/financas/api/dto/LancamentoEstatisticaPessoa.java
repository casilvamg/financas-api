package com.works.financas.api.dto;

import java.math.BigDecimal;

import com.works.financas.api.model.Pessoa;
import com.works.financas.api.model.Tipo;


public class LancamentoEstatisticaPessoa {
	
	private Tipo tipo;
	
	private Pessoa pessoa;
	
	private BigDecimal total;

	public LancamentoEstatisticaPessoa(Tipo tipo, Pessoa pessoa, BigDecimal total) {
		this.tipo = tipo;
		this.setPessoa(pessoa);
		this.total = total;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
}