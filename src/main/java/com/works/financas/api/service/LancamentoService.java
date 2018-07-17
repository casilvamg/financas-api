package com.works.financas.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.works.financas.api.model.StatusLancamento;
import com.works.financas.api.model.Lancamento;
import com.works.financas.api.repository.LancamentoRepository;

@Service
public class LancamentoService {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;

	public Lancamento atualizar(Long codigo, Lancamento lancamento) {
		Lancamento lancamentoSalvo = buscarLancamentoPeloCodigo(codigo);
		
		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");
		return lancamentoRepository.save(lancamentoSalvo);
	}
	
	public void atualizarPropriedadeStatus(Long codigo, String status) {
		Lancamento lancamentoSalvo = buscarLancamentoPeloCodigo(codigo);
		lancamentoSalvo.setStatus(StatusLancamento.valueOf(status));
		lancamentoRepository.save(lancamentoSalvo);
	}
	
	private Lancamento buscarLancamentoPeloCodigo(Long codigo) {
		Lancamento lancamentoSalvo = lancamentoRepository.findOne(codigo);
		if (lancamentoSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return lancamentoSalvo;
	}
}
