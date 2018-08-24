package com.works.financas.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.works.financas.api.model.Empresa;
import com.works.financas.api.repository.empresa.EmpresaRepositoryQuery;

public interface EmpresaRepository extends JpaRepository<Empresa, Long>, EmpresaRepositoryQuery {

}