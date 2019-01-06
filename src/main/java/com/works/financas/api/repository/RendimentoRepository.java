package com.works.financas.api.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.works.financas.api.model.Investimento;
import com.works.financas.api.model.Rendimento;

public interface RendimentoRepository extends JpaRepository<Rendimento, Long> { 
	List<Rendimento> findByInvestimento(Investimento investimento);
	Optional<Rendimento> findById(Long id);
	
	@Query("select r.valorComJuros from Rendimento r where r.investimento.codigo = :codigo and r.mes_corrente = 1")
    public BigDecimal getResgateBruto(@Param("codigo") Long codigo);
	
	@Query("select sum(r.IR) from Rendimento r where r.investimento.codigo = :codigo")
    public BigDecimal getComeCotas(@Param("codigo") Long codigo);
}
