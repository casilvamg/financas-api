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
	private InvestimentoRepository InvestimentoRepository;

	public Investimento atualizar(Long codigo, Investimento Investimento) {
		Investimento invSalvo = buscarInvestimentoPeloCodigo(codigo);
		
		BeanUtils.copyProperties(Investimento, invSalvo, "codigo");
		return InvestimentoRepository.save(invSalvo);
	}

	//public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		//Investimento pessoaSalva = buscarInvestimentoPeloCodigo(codigo);
		//.setAtivo(ativo);
		//InvestimentoRepository.save(pessoaSalva);
	//}
	
	public Investimento buscarInvestimentoPeloCodigo(Long codigo) {
		Investimento pessoaSalva = InvestimentoRepository.findOne(codigo);
		if (pessoaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalva;
	}
	
}