package com.works.financas.api.repository.lancamento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.works.financas.api.dto.LancamentoEstatisticaCategoria;
import com.works.financas.api.dto.LancamentoEstatisticaDia;
import com.works.financas.api.dto.LancamentoEstatisticaEmpresa;
import com.works.financas.api.dto.LancamentoEstatisticaFluxoDeCaixa;
import com.works.financas.api.dto.LancamentoEstatisticaTipo;
import com.works.financas.api.model.Lancamento;
import com.works.financas.api.model.Lancamento_;
import com.works.financas.api.model.TipoFluxoDeCaixa;
import com.works.financas.api.repository.LancamentoRepository;
import com.works.financas.api.repository.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Override
	public List<LancamentoEstatisticaEmpresa> porEmpresa(LocalDate inicio, LocalDate fim) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		
		CriteriaQuery<LancamentoEstatisticaEmpresa> criteriaQuery = criteriaBuilder.
				createQuery(LancamentoEstatisticaEmpresa.class);
		
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		criteriaQuery.select(criteriaBuilder.construct(LancamentoEstatisticaEmpresa.class, 
				root.get(Lancamento_.tipo),
				root.get(Lancamento_.empresa),
				criteriaBuilder.sum(root.get(Lancamento_.valor))));
		
		criteriaQuery.where(
				criteriaBuilder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), 
						inicio),
				criteriaBuilder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), 
						fim));
		
		criteriaQuery.groupBy(root.get(Lancamento_.tipo), 
				root.get(Lancamento_.empresa));
		
		TypedQuery<LancamentoEstatisticaEmpresa> typedQuery = manager
				.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}
	
	@Override
	public List<LancamentoEstatisticaDia> porDia(LocalDate mesReferencia) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		
		CriteriaQuery<LancamentoEstatisticaDia> criteriaQuery = criteriaBuilder.
				createQuery(LancamentoEstatisticaDia.class);
		
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		criteriaQuery.select(criteriaBuilder.construct(LancamentoEstatisticaDia.class, 
				root.get(Lancamento_.tipo),
				root.get(Lancamento_.dataVencimento),
				criteriaBuilder.sum(root.get(Lancamento_.valor))));
		
		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());
		
		criteriaQuery.where(
				criteriaBuilder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), 
						primeiroDia),
				criteriaBuilder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), 
						ultimoDia));
		
		criteriaQuery.groupBy(root.get(Lancamento_.tipo), 
				root.get(Lancamento_.dataVencimento));
		
		TypedQuery<LancamentoEstatisticaDia> typedQuery = manager
				.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}
	
	
	@Override
	public List<LancamentoEstatisticaCategoria> porCategoria(LocalDate mesReferencia) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		
		CriteriaQuery<LancamentoEstatisticaCategoria> criteriaQuery = criteriaBuilder.
				createQuery(LancamentoEstatisticaCategoria.class);
		
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		criteriaQuery.select(criteriaBuilder.construct(LancamentoEstatisticaCategoria.class, 
				root.get(Lancamento_.categoria),
				criteriaBuilder.sum(root.get(Lancamento_.valor))));
		
		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());
		
		
		criteriaQuery.where(
				criteriaBuilder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), 
						primeiroDia),
				criteriaBuilder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), 
						ultimoDia));
		
		criteriaQuery.groupBy(root.get(Lancamento_.categoria));

		
		
		TypedQuery<LancamentoEstatisticaCategoria> typedQuery = manager
				.createQuery(criteriaQuery);
				
		return typedQuery.getResultList();
	}
	
	@Override
	public List<LancamentoEstatisticaTipo> porTipo(LocalDate mesReferencia) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		
		CriteriaQuery<LancamentoEstatisticaTipo> criteriaQuery = criteriaBuilder.
				createQuery(LancamentoEstatisticaTipo.class);
		
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		criteriaQuery.select(criteriaBuilder.construct(LancamentoEstatisticaTipo.class, 
				root.get(Lancamento_.tipo),
				criteriaBuilder.sum(root.get(Lancamento_.valor))));	
				
		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());
		
		
		criteriaQuery.where(
				criteriaBuilder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), 
						primeiroDia),
				criteriaBuilder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), 
						ultimoDia));
		
		criteriaQuery.groupBy(root.get(Lancamento_.tipo));

		
		
		TypedQuery<LancamentoEstatisticaTipo> typedQuery = manager
				.createQuery(criteriaQuery);
						
		return typedQuery.getResultList();
	}
	
	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}
	
	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
			Root<Lancamento> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			predicates.add(builder.like(
					builder.lower(root.get(Lancamento_.descricao)), "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
		}
		
		if (lancamentoFilter.getDataVencimentoDe() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoDe()));
		}
		
		if (lancamentoFilter.getDataVencimentoAte() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoAte()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<Lancamento> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	@Override
	public List<LancamentoEstatisticaFluxoDeCaixa> porMesFluxoCaixa(LocalDate mesReferencia) {
		List<LancamentoEstatisticaFluxoDeCaixa> lista = new ArrayList<LancamentoEstatisticaFluxoDeCaixa>();
		
		BigDecimal totalPagar = lancamentoRepository.sumValorPorDespesa(mesReferencia.getMonthValue());
		BigDecimal totalReceber = lancamentoRepository.sumValorPorReceita(mesReferencia.getMonthValue());
		BigDecimal realizadoPagar = lancamentoRepository.sumValorPorDespesaAndFinalizado(mesReferencia.getMonthValue());
		BigDecimal realizadoReceber = lancamentoRepository.sumValorPorReceitaAndFinalizado(mesReferencia.getMonthValue());
		
		totalPagar = (totalPagar == null) ? new BigDecimal("0.0") : totalPagar;	
		totalReceber = (totalReceber == null) ? new BigDecimal("0.0") : totalReceber;
		realizadoPagar = (realizadoPagar == null) ? new BigDecimal("0.0") : realizadoPagar;
		realizadoReceber = (realizadoReceber == null) ? new BigDecimal("0.0") : realizadoReceber;
							
		lista.add(new LancamentoEstatisticaFluxoDeCaixa(TipoFluxoDeCaixa.Total, totalPagar, totalReceber));
		lista.add(new LancamentoEstatisticaFluxoDeCaixa(TipoFluxoDeCaixa.Realizado, realizadoPagar, realizadoReceber));	
		
		lista.add(new LancamentoEstatisticaFluxoDeCaixa(TipoFluxoDeCaixa.Faltante, totalPagar.subtract(realizadoPagar), totalReceber.subtract(realizadoReceber))); //faltante pagar		
		return lista;
	}

	/*@Override
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		criteria.select(builder.construct(ResumoLancamento.class
				, root.get(Lancamento_.codigo), root.get(Lancamento_.descricao)
				, root.get(Lancamento_.dataVencimento), root.get(Lancamento_.dataPagamento)
				, root.get(Lancamento_.valor), root.get(Lancamento_.tipo)
				, root.get(Lancamento_.categoria).get(Categoria_.nome)
				, root.get(Lancamento_.pessoa).get(Pessoa_.nome)));
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<ResumoLancamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}*/
}
