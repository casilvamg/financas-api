package com.works.financas.api.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.works.financas.api.model.Pessoa;
import com.works.financas.api.repository.pessoa.PessoaRepositoryQuery;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery {
	List<Pessoa> findAllByAtivo(boolean status, Sort sort);
}