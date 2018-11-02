package com.works.financas.api.resource;

import com.works.financas.api.dto.LancamentoEstatisticaCategoria;
import com.works.financas.api.dto.LancamentoEstatisticaDia;
import com.works.financas.api.dto.LancamentoEstatisticaFluxoDeCaixa;
import com.works.financas.api.dto.LancamentoEstatisticaTipo;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.works.financas.api.model.Lancamento;
import com.works.financas.api.repository.filter.LancamentoFilter;
import com.works.financas.api.repository.projection.ResumoLancamento;
import com.works.financas.api.repository.LancamentoRepository;
import com.works.financas.api.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	/*@GetMapping("/relatorios/por-pessoa")
	public ResponseEntity<byte[]> relatorioPorPessoa(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate inicio, 
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fim) throws Exception {
		
		byte[] relatorio = lancamentoService.relatorioPorPessoa(inicio, fim);
		
		  HttpHeaders headers = new HttpHeaders();
		  headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
		  headers.add("Content-Disposition", "attachment; filename=relatorio_lancamentos.pdf");

		  return ResponseEntity.ok()
					.headers(headers)
					.body(relatorio);
	}*/
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.filtrar(lancamentoFilter, pageable);
	}
	
	@GetMapping("/estatisticas/por-tipo")
	public List<LancamentoEstatisticaTipo> porTipo() {
		return this.lancamentoRepository.porTipo(LocalDate.now());
	}
	
	@GetMapping("/estatisticas/por-categoria")
	public List<LancamentoEstatisticaCategoria> porCategoria() {

		return this.lancamentoRepository.porCategoria(LocalDate.now());
	}
	
	@GetMapping("/estatisticas/por-dia")
	public List<LancamentoEstatisticaDia> porDia() {
		return this.lancamentoRepository.porDia(LocalDate.now());
	}
	
	@GetMapping("/estatisticas/caixa")
	public List<LancamentoEstatisticaFluxoDeCaixa> buscarFluxoDeCaixa() {
		return this.lancamentoRepository.porMesFluxoCaixa(LocalDate.now());
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
			.buildAndExpand(lancamentoSalvo.getCodigo()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(lancamentoSalvo);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo) {
		 Optional <Lancamento> lancamento = lancamentoRepository.findById(codigo);
		 return lancamento.isPresent() ? ResponseEntity.ok(lancamento.get()) : ResponseEntity.notFound().build();
	}
	
	@GetMapping(params = "resumo")
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.resumir(lancamentoFilter, pageable);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long codigo) {
		lancamentoRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Lancamento> atualizar(@PathVariable Long codigo, @Valid @RequestBody Lancamento lancamento) {
		Lancamento tituloSalvo = lancamentoService.atualizar(codigo, lancamento);
		return ResponseEntity.ok(tituloSalvo);
	}
	
	//@PutMapping("/{codigo}/status")
	//@ResponseStatus(HttpStatus.NO_CONTENT)
	//public void atualizarPropriedadeStatus(@PathVariable Long codigo, @RequestBody String status) {
	//	lancamentoService.atualizarPropriedadeStatus(codigo, status); //verificar exception quando status for errado
	//}
}
