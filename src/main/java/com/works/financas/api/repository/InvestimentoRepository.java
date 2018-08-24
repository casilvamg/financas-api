package com.works.financas.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.works.financas.api.model.Investimento;
import com.works.financas.api.repository.investimento.InvestimentoRepositoryQuery;

public interface InvestimentoRepository extends JpaRepository<Investimento, Long>, InvestimentoRepositoryQuery {

}