package com.works.financas.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.works.financas.api.model.Permissao;
import com.works.financas.api.repository.PermissaoRepository;

@Service
public class PermissaoService {
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	public Permissao save(Permissao permissao) {
		return permissaoRepository.save(permissao);
	}
	
	public Permissao atualizar(Long codigo, Permissao permissao) {
		Permissao invSalvo = buscarPermissaoPeloCodigo(codigo);
		
		BeanUtils.copyProperties(permissao, invSalvo, "codigo");
		return permissaoRepository.save(invSalvo);
	}
	
	public Permissao buscarPermissaoPeloCodigo(Long codigo) {
		Optional<Permissao> permissaoSalvo = permissaoRepository.findById(codigo);
		if (!permissaoSalvo.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return permissaoSalvo.get();
	}

}
