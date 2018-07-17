package com.works.financas.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.works.financas.api.model.Investimento;

public interface InvestimentoRepository extends JpaRepository<Investimento, Long> { 

}
