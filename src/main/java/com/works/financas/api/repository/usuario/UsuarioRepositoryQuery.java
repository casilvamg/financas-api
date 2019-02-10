package com.works.financas.api.repository.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.works.financas.api.model.Usuario;
import com.works.financas.api.repository.filter.UsuarioFilter;

public interface UsuarioRepositoryQuery {
	public Page<Usuario> filtrar(UsuarioFilter usuarioFilter, Pageable pageable);
}
