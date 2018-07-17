package com.works.financas.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.works.financas.api.model.StatusTitulo;
import com.works.financas.api.model.Titulo;
import com.works.financas.api.repository.TituloRepository;

@Service
public class TituloService {
	
	@Autowired
	private TituloRepository tituloRepository;

	public Titulo atualizar(Long codigo, Titulo titulo) {
		Titulo tituloSalvo = buscarTituloPeloCodigo(codigo);
		
		BeanUtils.copyProperties(titulo, tituloSalvo, "codigo");
		return tituloRepository.save(tituloSalvo);
	}
	
	public void atualizarPropriedadeStatus(Long codigo, String status) {
		Titulo tituloSalvo = buscarTituloPeloCodigo(codigo);
		tituloSalvo.setStatus(StatusTitulo.valueOf(status));
		tituloRepository.save(tituloSalvo);
	}
	
	private Titulo buscarTituloPeloCodigo(Long codigo) {
		Titulo tituloSalvo = tituloRepository.findOne(codigo);
		if (tituloSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return tituloSalvo;
	}
}
