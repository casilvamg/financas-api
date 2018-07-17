package com.works.financas.api.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.works.financas.api.model.Titulo;
import com.works.financas.api.repository.filter.TituloFilter;
import com.works.financas.api.repository.TituloRepository;
import com.works.financas.api.service.TituloService;

@RestController
@RequestMapping("/titulos")
public class TituloResource {
	
	@Autowired
	private TituloRepository tituloRepository;
	
	@Autowired
	private TituloService tituloService;
	
	@GetMapping
	public Page<Titulo> pesquisar(TituloFilter tituloFilter, Pageable pageable) {
		return tituloRepository.filtrar(tituloFilter, pageable);
	}
	
	@PostMapping
	public ResponseEntity<Titulo> criar(@Valid @RequestBody Titulo titulo, HttpServletResponse response) {
		Titulo tituloSalvo = tituloRepository.save(titulo);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
			.buildAndExpand(tituloSalvo.getCodigo()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(tituloSalvo);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Titulo> buscarPeloCodigo(@PathVariable Long codigo) {
		 Titulo titulo = tituloRepository.findOne(codigo);
		 return titulo != null ? ResponseEntity.ok(titulo) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		tituloRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Titulo> atualizar(@PathVariable Long codigo, @Valid @RequestBody Titulo titulo) {
		Titulo tituloSalvo = tituloService.atualizar(codigo, titulo);
		return ResponseEntity.ok(tituloSalvo);
	}
	
	@PutMapping("/{codigo}/status")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeStatus(@PathVariable Long codigo, @RequestBody String status) {
		tituloService.atualizarPropriedadeStatus(codigo, status); //verificar exception quando status for errado
	}
}
