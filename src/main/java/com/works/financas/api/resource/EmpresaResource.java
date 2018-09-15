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
import com.works.financas.api.model.Empresa;
import com.works.financas.api.repository.EmpresaRepository;
import com.works.financas.api.repository.filter.EmpresaFilter;
import com.works.financas.api.service.EmpresaService;

@RestController
@RequestMapping("/empresas")
public class EmpresaResource {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	//@GetMapping
	//public List<Empresa> pesquisar() {
	//	return empresaRepository.findAll();
	//}
	
	@GetMapping
	public Page<Empresa> pesquisar(EmpresaFilter empresaFilter, Pageable pageable) {
		return empresaRepository.filtrar(empresaFilter, pageable);
	}

	@PostMapping
	public ResponseEntity<Empresa> criar(@Valid @RequestBody Empresa empresa, HttpServletResponse response) {
		Empresa empresaSalva = empresaRepository.save(empresa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, empresaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(empresaSalva);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Empresa> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Empresa> empresa = empresaRepository.findById(codigo);
		return empresa.isPresent() ? ResponseEntity.ok(empresa.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		empresaRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Empresa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Empresa empresa) {
		Empresa pessoaSalva = empresaService.atualizar(codigo, empresa);
		return ResponseEntity.ok(pessoaSalva);
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		empresaService.atualizarPropriedadeAtivo(codigo, ativo);
	}
}