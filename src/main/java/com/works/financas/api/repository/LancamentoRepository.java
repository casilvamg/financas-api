package com.works.financas.api.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.works.financas.api.model.Lancamento;
import com.works.financas.api.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery { 
	
	@Query("select sum(valor) from Lancamento where tipo = 'RECEITA' and MONTH(dataVencimento) = :mes")
    public BigDecimal sumValorPorReceita(@Param("mes") Integer mes);
	
	@Query("select sum(valor) from Lancamento where tipo = 'DESPESA' and MONTH(dataVencimento) = :mes")
    public BigDecimal sumValorPorDespesa(@Param("mes") Integer mes);

	@Query("select sum(valor) from Lancamento where tipo = 'RECEITA' and MONTH(dataVencimento) = :mes and dataPagamento != null")
    public BigDecimal sumValorPorReceitaAndFinalizado(@Param("mes") Integer mes);
	
	@Query("select sum(valor) from Lancamento where tipo = 'DESPESA' and MONTH(dataVencimento) = :mes and dataPagamento != null")
    public BigDecimal sumValorPorDespesaAndFinalizado(@Param("mes") Integer mes);
	
	@Query("select l from Lancamento l WHERE (MONTH(l.dataVencimento)=(MONTH(NOW())-1) and l.parcela != 0 and MONTH(NOW())-1 != 0)"
			+ "or (MONTH(l.dataVencimento)= 12 and l.parcela != 0)")
    public List<Lancamento> findByLancamentosMesAnteriorAndParcelaNotEqualZero();
	
	List<Lancamento> findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate data);
}
