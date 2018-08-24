package com.works.financas.api.repository.investimento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.works.financas.api.model.Investimento;
import com.works.financas.api.repository.filter.InvestimentoFilter;

public interface InvestimentoRepositoryQuery {
	public Page<Investimento> filtrar(InvestimentoFilter InvestimentoFilter, Pageable pageable);
}
