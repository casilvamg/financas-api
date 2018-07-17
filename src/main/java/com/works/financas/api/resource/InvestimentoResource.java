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
import com.works.financas.api.model.Lancamento;
import com.works.financas.api.repository.InvestimentoRepository;
import com.works.financas.api.service.InvestimentoService;

@RestController
@RequestMapping("/investimentos")
public class InvestimentoResource {
	
	@Autowired
	private InvestimentoRepository investimentoRepository;
	
	@Autowired
	private InvestimentoService investimentoService;
	
	@GetMapping
	public List<Investimento> listar(Lancamento lancamento) {
		return investimentoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Investimento> criar(@Valid @RequestBody Investimento investimento, HttpServletResponse response) {
		Investimento investimentoSalvo = investimentoRepository.save(investimento);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
			.buildAndExpand(investimentoSalvo.getCodigo()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(investimentoSalvo);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Investimento> buscarPeloCodigo(@PathVariable Long codigo) {
		Investimento investimento = investimentoRepository.findOne(codigo);
		 return investimento != null ? ResponseEntity.ok(investimento) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		investimentoRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Investimento> atualizar(@PathVariable Long codigo, @Valid @RequestBody Investimento inv) {
		Investimento investimentoSalvo = investimentoService.atualizar(codigo, inv);
		return ResponseEntity.ok(investimentoSalvo);
	}

}
