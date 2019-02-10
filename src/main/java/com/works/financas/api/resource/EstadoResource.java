package com.works.financas.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.works.financas.api.model.Estado;
import com.works.financas.api.repository.EstadoRepository;

@RestController
@RequestMapping("/estados") //criar métodos que representem endpoints no Spring, precisamos criar um RestController
public class EstadoResource {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public List<Estado> listar() {
		return estadoRepository.findAll();
	}
}