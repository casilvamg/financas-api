package com.works.financas.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.works.financas.api.repository.RendimentoRepository;
import com.works.financas.api.model.Rendimento;

@Service
public class RendimentoService {
	
	@Autowired
	private RendimentoRepository rendimentoRepository;
	
	public void excluir(Long codigo) {
		rendimentoRepository.deleteById(codigo);
	}
	
	public Rendimento save(Rendimento rendimento) {
		return rendimentoRepository.save(rendimento);
	}
	
	public void delete(Long id) {
			rendimentoRepository.deleteById(id);
	}
	
	public void salvar(Rendimento rendimento) {		
		
		try {
			rendimentoRepository.save(rendimento);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido");
		}
	}
	
	public Rendimento atualizar(Long id, Rendimento rendimento) {
		Rendimento rendSalvo = buscarRendimentoPeloId(id);
		
		BeanUtils.copyProperties(rendimento, rendSalvo, "id");
		return rendimentoRepository.save(rendSalvo);
	}
	
	public Rendimento buscarRendimentoPeloId(Long id) {
		Optional<Rendimento> rendimentoSalvo = rendimentoRepository.findById(id);
		if (!rendimentoSalvo.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return rendimentoSalvo.get();
	}
}
