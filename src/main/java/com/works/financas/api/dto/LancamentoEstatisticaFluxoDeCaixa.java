package com.works.financas.api.dto;

import java.math.BigDecimal;

import com.works.financas.api.model.Tipo;
import com.works.financas.api.model.TipoFluxoDeCaixa;

public class LancamentoEstatisticaFluxoDeCaixa {
	
	private TipoFluxoDeCaixa tipo;

	private BigDecimal totalPagar;
	
	private BigDecimal totalReceber;
			
	public LancamentoEstatisticaFluxoDeCaixa(TipoFluxoDeCaixa tipo, BigDecimal totalPagar, BigDecimal totalReceber) {
		this.tipo = tipo;
		this.totalPagar = totalPagar;
		this.totalReceber = totalReceber;
	}

	public TipoFluxoDeCaixa getTipo() {
		return tipo;
	}

	public BigDecimal getTotalPagar() {
		return totalPagar;
	}

	public void setTotalPagar(BigDecimal totalPagar) {
		this.totalPagar = totalPagar;
	}

	public BigDecimal getTotalReceber() {
		return totalReceber;
	}

	public void setTotalReceber(BigDecimal totalReceber) {
		this.totalReceber = totalReceber;
	}

	public void setTipo(TipoFluxoDeCaixa tipo) {
		this.tipo = tipo;
	}
}
