package com.works.financas.api.repository.empresa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.works.financas.api.model.Empresa;
import com.works.financas.api.repository.filter.EmpresaFilter;

public interface EmpresaRepositoryQuery {
	public Page<Empresa> filtrar(EmpresaFilter empresaFilter, Pageable pageable);
}
