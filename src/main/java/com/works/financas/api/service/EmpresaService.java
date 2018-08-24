package com.works.financas.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.works.financas.api.model.Empresa;
import com.works.financas.api.repository.EmpresaRepository;


@Service
public class EmpresaService {
	
	@Autowired
	private EmpresaRepository empresaRepository;

	public Empresa atualizar(Long codigo, Empresa empresa) {
		Empresa pessoaSalva = buscarPessoaPeloCodigo(codigo);
		
		BeanUtils.copyProperties(empresa, pessoaSalva, "codigo");
		return empresaRepository.save(pessoaSalva);
	}

	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Empresa pessoaSalva = buscarPessoaPeloCodigo(codigo);
		pessoaSalva.setAtivo(ativo);
		empresaRepository.save(pessoaSalva);
	}
	
	public Empresa buscarPessoaPeloCodigo(Long codigo) {
		Empresa pessoaSalva = empresaRepository.findOne(codigo);
		if (pessoaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalva;
	}
	
}