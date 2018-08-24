package com.works.financas.api.repository.investimento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.works.financas.api.model.Investimento;
import com.works.financas.api.model.Investimento_;
import com.works.financas.api.repository.filter.InvestimentoFilter;

public class InvestimentoRepositoryImpl implements InvestimentoRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Investimento> filtrar(InvestimentoFilter InvestimentoFilter, Pageable pageable) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Investimento> criteria = builder.createQuery(Investimento.class);
		Root<Investimento> root = criteria.from(Investimento.class);
		
		Predicate[] predicates = criarRestricoes(InvestimentoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Investimento> query = manager.createQuery(criteria);
		
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(InvestimentoFilter));
	}
	
	private Predicate[] criarRestricoes(InvestimentoFilter InvestimentoFilter, CriteriaBuilder builder,
			Root<Investimento> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(InvestimentoFilter.getNome())) {
			predicates.add(builder.like(
					builder.lower(root.get(Investimento_.nome)), "%" + InvestimentoFilter.getNome().toLowerCase() + "%"));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<Investimento> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total(InvestimentoFilter InvestimentoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Investimento> root = criteria.from(Investimento.class);
		
		Predicate[] predicates = criarRestricoes(InvestimentoFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}
}

