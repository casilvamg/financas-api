package com.works.financas.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.works.financas.api.model.Usuario;
import com.works.financas.api.repository.usuario.UsuarioRepositoryQuery;


public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQuery {
	
	public Optional<Usuario> findByEmail(String email);
	
}