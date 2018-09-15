package com.works.financas.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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

import com.works.financas.api.event.RecursoCriadoEvent;
import com.works.financas.api.model.Investimento;
import com.works.financas.api.repository.InvestimentoRepository;
import com.works.financas.api.repository.filter.InvestimentoFilter;
import com.works.financas.api.service.InvestimentoService;

@RestController
@RequestMapping("/investimentos")
public class InvestimentoResource {

	@Autowired
	private InvestimentoRepository InvestimentoRepository;
	
	@Autowired
	private InvestimentoService InvestimentoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	//@GetMapping
	//public List<Investimento> pesquisar() {
	//	return InvestimentoRepository.findAll();
	//}
	
	@GetMapping
	public Page<Investimento> pesquisar(InvestimentoFilter InvestimentoFilter, Pageable pageable) {
		return InvestimentoRepository.filtrar(InvestimentoFilter, pageable);
	}

	@PostMapping
	public ResponseEntity<Investimento> criar(@Valid @RequestBody Investimento Investimento, HttpServletResponse response) {
		Investimento InvestimentoSalva = InvestimentoRepository.save(Investimento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, InvestimentoSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(InvestimentoSalva);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Investimento> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Investimento> investimento = InvestimentoRepository.findById(codigo);
		return investimento.isPresent() ? ResponseEntity.ok(investimento.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		InvestimentoRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Investimento> atualizar(@PathVariable Long codigo, @Valid @RequestBody Investimento Investimento) {
		Investimento pessoaSalva = InvestimentoService.atualizar(codigo, Investimento);
		return ResponseEntity.ok(pessoaSalva);
	}
	
	//@PutMapping("/{codigo}/ativo")
	//@ResponseStatus(HttpStatus.NO_CONTENT)
	//public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
	//	InvestimentoService.atualizarPropriedadeAtivo(codigo, ativo);
	//}
}