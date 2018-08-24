package com.works.financas.api.dto;

import java.math.BigDecimal;

import com.works.financas.api.model.Empresa;
import com.works.financas.api.model.Tipo;


public class LancamentoEstatisticaEmpresa {
	
	private Tipo tipo;
	
	private Empresa empresa;
	
	private BigDecimal total;

	public LancamentoEstatisticaEmpresa(Tipo tipo, Empresa empresa, BigDecimal total) {
		this.tipo = tipo;
		this.setEmpresa(empresa);
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

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}