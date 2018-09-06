package com.works.financas.api.repository.lancamento;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.works.financas.api.dto.LancamentoEstatisticaCategoria;
import com.works.financas.api.dto.LancamentoEstatisticaDia;
import com.works.financas.api.dto.LancamentoEstatisticaEmpresa;
import com.works.financas.api.dto.LancamentoEstatisticaFluxoDeCaixa;
import com.works.financas.api.dto.LancamentoEstatisticaTipo;
import com.works.financas.api.model.Lancamento;
import com.works.financas.api.repository.filter.LancamentoFilter;
import com.works.financas.api.repository.projection.ResumoLancamento;

public interface LancamentoRepositoryQuery {
	public List<LancamentoEstatisticaTipo> porTipo(LocalDate mesReferencia);
	public List<LancamentoEstatisticaCategoria> porCategoria(LocalDate mesReferencia);
	public List<LancamentoEstatisticaDia> porDia(LocalDate mesReferencia);
	
	public List<LancamentoEstatisticaFluxoDeCaixa> porMesFluxoCaixa(LocalDate mesReferencia);
	
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	
	public List<LancamentoEstatisticaEmpresa> porEmpresa(LocalDate inicio, LocalDate fim);
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);

}
