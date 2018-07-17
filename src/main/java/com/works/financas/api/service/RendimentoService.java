package com.works.financas.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.works.financas.api.model.Investimento;
import com.works.financas.api.model.Lancamento;
import com.works.financas.api.model.Rendimento;
import com.works.financas.api.repository.RendimentoRepository;

@Service
public class RendimentoService {
	
	@Autowired
	private RendimentoRepository rendimentoRepository;
	
	public Rendimento atualizar(Long codigo, Rendimento rendimento) {
		Rendimento rendimentoSalvo = buscarRendimentoPeloCodigo(codigo);
		
		BeanUtils.copyProperties(rendimento, rendimentoSalvo, "codigo");
		return rendimentoRepository.save(rendimentoSalvo);
	}
	
	private Rendimento buscarRendimentoPeloCodigo(Long codigo) {
		Rendimento rendimentoSalvo = rendimentoRepository.findOne(codigo);
		if (rendimentoSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return rendimentoSalvo;
	}
}
