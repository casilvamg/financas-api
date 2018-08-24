package com.works.financas.api.repository.empresa;

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

import com.works.financas.api.model.Empresa;
import com.works.financas.api.model.Empresa_;
import com.works.financas.api.repository.filter.EmpresaFilter;

public class EmpresaRepositoryImpl implements EmpresaRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Empresa> filtrar(EmpresaFilter empresaFilter, Pageable pageable) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Empresa> criteria = builder.createQuery(Empresa.class);
		Root<Empresa> root = criteria.from(Empresa.class);
		
		Predicate[] predicates = criarRestricoes(empresaFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Empresa> query = manager.createQuery(criteria);
		
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(empresaFilter));
	}
	
	private Predicate[] criarRestricoes(EmpresaFilter empresaFilter, CriteriaBuilder builder,
			Root<Empresa> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(empresaFilter.getNome())) {
			predicates.add(builder.like(
					builder.lower(root.get(Empresa_.nome)), "%" + empresaFilter.getNome().toLowerCase() + "%"));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<Empresa> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total(EmpresaFilter empresaFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Empresa> root = criteria.from(Empresa.class);
		
		Predicate[] predicates = criarRestricoes(empresaFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}
}
