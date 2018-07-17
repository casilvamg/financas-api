package com.works.financas.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.works.financas.api.model.Titulo;
import com.works.financas.api.repository.titulo.TituloRepositoryQuery;

public interface TituloRepository extends JpaRepository<Titulo, Long>, TituloRepositoryQuery { 

}
