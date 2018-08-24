package com.works.financas.api.dto;

import java.math.BigDecimal;

import com.works.financas.api.model.Tipo;


public class LancamentoEstatisticaTipo {
	
	private Tipo tipo;
	
	private BigDecimal total;
			
	public LancamentoEstatisticaTipo(Tipo tipo, BigDecimal total) {
		this.tipo = tipo;
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
}