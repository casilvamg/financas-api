package com.works.financas.api.repository.titulo;

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

import com.works.financas.api.model.Titulo;
import com.works.financas.api.model.Titulo_;
import com.works.financas.api.repository.filter.TituloFilter;

public class TituloRepositoryImpl implements TituloRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Titulo> filtrar(TituloFilter tituloFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Titulo> criteria = builder.createQuery(Titulo.class);
		Root<Titulo> root = criteria.from(Titulo.class);
		
		Predicate[] predicates = criarRestricoes(tituloFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Titulo> query = manager.createQuery(criteria);
		
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(tituloFilter));
	}
	
	private Predicate[] criarRestricoes(TituloFilter tituloFilter, CriteriaBuilder builder,
			Root<Titulo> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(tituloFilter.getDescricao())) {
			predicates.add(builder.like(
					builder.lower(root.get(Titulo_.descricao)), "%" + tituloFilter.getDescricao().toLowerCase() + "%"));
		}
		
		if (tituloFilter.getDataVencimentoDe() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(root.get(Titulo_.dataVencimento), tituloFilter.getDataVencimentoDe()));
		}
		
		if (tituloFilter.getDataVencimentoAte() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(root.get(Titulo_.dataVencimento), tituloFilter.getDataVencimentoAte()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<Titulo> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total(TituloFilter tituloFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Titulo> root = criteria.from(Titulo.class);
		
		Predicate[] predicates = criarRestricoes(tituloFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}
}
