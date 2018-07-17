package com.works.financas.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.works.financas.api.model.Investimento;
import com.works.financas.api.repository.InvestimentoRepository;

@Service
public class InvestimentoService {
	
	@Autowired
	private InvestimentoRepository investimentoRepository;

	public Investimento atualizar(Long codigo, Investimento investimento) {
		Investimento investimentoSalvo = buscarInvestimentoPeloCodigo(codigo);
		
		BeanUtils.copyProperties(investimento, investimentoSalvo, "codigo");
		return investimentoRepository.save(investimentoSalvo);
	}

	private Investimento buscarInvestimentoPeloCodigo(Long codigo) {
		Investimento investimentoSalvo = investimentoRepository.findOne(codigo);
		if (investimentoSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return investimentoSalvo;
	}

}
