package com.works.financas.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.works.financas.api.model.Categoria;


public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}