package com.works.financas.api.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.works.financas.api.model.Investimento;
import com.works.financas.api.model.Rendimento;
import com.works.financas.api.repository.RendimentoRepository;
import com.works.financas.api.service.RendimentoService;

@RestController
@RequestMapping("/rendimentos")
public class RendimentoResource {
	
	@Autowired
	private RendimentoRepository rendimentoRepository;
	
	@Autowired
	private RendimentoService rendimentoService;
	
	@GetMapping
	public List<Rendimento> listar(Rendimento rendimento) {
		return rendimentoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Rendimento> criar(@Valid @RequestBody Rendimento rendimento, HttpServletResponse response) {
		Rendimento rendimentoSalvo = rendimentoRepository.save(rendimento);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
			.buildAndExpand(rendimentoSalvo.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(rendimentoSalvo);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Rendimento> buscarPeloCodigo(@PathVariable Long codigo) {
		Rendimento rendimento = rendimentoRepository.findOne(codigo);
		 return rendimento != null ? ResponseEntity.ok(rendimento) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		rendimentoRepository.delete(codigo);
	}
		
	@PutMapping("/{codigo}")
	public ResponseEntity<Rendimento> atualizar(@PathVariable Long codigo, @Valid @RequestBody Rendimento rendimento) {
		Rendimento rendimentoSalvo = rendimentoService.atualizar(codigo, rendimento);
		return ResponseEntity.ok(rendimentoSalvo);
	}
}
