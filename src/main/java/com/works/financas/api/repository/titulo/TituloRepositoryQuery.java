package com.works.financas.api.repository.titulo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.works.financas.api.model.Titulo;
import com.works.financas.api.repository.filter.TituloFilter;

public interface TituloRepositoryQuery {
	public Page<Titulo> filtrar(TituloFilter tituloFilter, Pageable pageable);
}
