package com.works.financas.api.resource;

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
import com.works.financas.api.model.Permissao;
import com.works.financas.api.repository.PermissaoRepository;
import com.works.financas.api.service.PermissaoService;

@RestController
@RequestMapping("/permissoes")
public class PermissaoResource {
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	@Autowired
	private PermissaoService permissaoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_USUARIO') and #oauth2.hasScope('read')") //Aula 6.12
	public List<Permissao> listar() {
		return permissaoRepository.findAll();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_USUARIO') and #oauth2.hasScope('write')")
	public ResponseEntity<Permissao> criar(@Valid @RequestBody Permissao permissao, HttpServletResponse response) {
		permissao.setDescricao(permissao.getDescricao().toUpperCase());
		Permissao permissaoSalva = permissaoService.save(permissao);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, permissaoSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(permissao);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_USUARIO') and #oauth2.hasScope('read')")
	public ResponseEntity<Permissao> buscarPeloCodigo(@PathVariable Long codigo) {
		 Optional<Permissao> permissao = permissaoRepository.findById(codigo);
		 return permissao.isPresent() ? ResponseEntity.ok(permissao.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		permissaoRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Permissao> atualizar(@PathVariable Long codigo, @Valid @RequestBody Permissao permissao) {
		Permissao permissaoSalva = permissaoService.atualizar(codigo, permissao);
		return ResponseEntity.ok(permissaoSalva);
	}
	

}
