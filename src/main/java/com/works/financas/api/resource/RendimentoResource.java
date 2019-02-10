package com.works.financas.api.resource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.works.financas.api.model.Rendimento;
import com.works.financas.api.repository.RendimentoRepository;
import com.works.financas.api.service.InvestimentoService;
import com.works.financas.api.service.RendimentoService;

@RestController
@RequestMapping("/rendimentos")
public class RendimentoResource {
	
	@Autowired
	private RendimentoService rendimentoService;
	
	@Autowired
	private InvestimentoService investimentoService;
	
	@Autowired
	private RendimentoRepository rendimentoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@PostMapping("/novo")
	public ResponseEntity<Rendimento> criar(@Valid @RequestBody Rendimento rendimento, HttpServletResponse response) {
		
		rendimento.setRendimentoPct((rendimento.getJuros().multiply(new BigDecimal("100")).divide((rendimento.getValor()), 2, RoundingMode.HALF_EVEN)));
		rendimento.setValorComJuros((rendimento.getValor().add(rendimento.getJuros())));
		
		Rendimento rendimentoSalva = rendimentoService.save(rendimento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, rendimentoSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(rendimentoSalva);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_INVESTIMENTO') and #oauth2.hasScope('read')")
	//public List<Rendimento> buscarRendimentosPorInvestimento(@PathVariable Long codigo, Pageable pageable) {
	public List<Rendimento> buscarRendimentosPorInvestimento(@PathVariable Long codigo) {
		return rendimentoRepository.findByInvestimento(investimentoService.buscarInvestimentoPeloCodigo(codigo));
	}
	
	@GetMapping("/rendimento/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_INVESTIMENTO') and #oauth2.hasScope('read')")
	public Optional<Rendimento> buscarRendimento(@PathVariable Long id) {
		return rendimentoRepository.findById(id);
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_INVESTIMENTO') and #oauth2.hasScope('read')") //Aula 6.12
	public List<Rendimento> listar() {
		return rendimentoRepository.findAll();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		rendimentoService.delete(codigo);
	}
	
	@PutMapping("/novo/{id}")
	public ResponseEntity<Rendimento> atualizar(@PathVariable Long id, @Valid @RequestBody Rendimento rendimento) {
						
		rendimento.setRendimentoPct((rendimento.getJuros().multiply(new BigDecimal("100")).divide((rendimento.getValor()), 2, RoundingMode.HALF_EVEN)));
		rendimento.setValorComJuros((rendimento.getValor().add(rendimento.getJuros())));
		
		Rendimento rendSalva = rendimentoService.atualizar(id, rendimento);
		return ResponseEntity.ok(rendSalva);
	}
}