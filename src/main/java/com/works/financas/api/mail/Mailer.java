package com.works.financas.api.mail;

import java.util.HashMap;
//import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.works.financas.api.model.Lancamento;
import com.works.financas.api.repository.LancamentoRepository;

@Component
public class Mailer {
	
	/*
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TemplateEngine thymeleaf;
		
	@Autowired
	private LancamentoRepository repo;
	
	public void avisarSobreLancamentosVencidos(
			List<Lancamento> vencidos, String destinario) {
		Map<String, Object> variaveis = new HashMap<>();
		variaveis.put("lancamentos", vencidos);
		
		//List<String> emails = destinatarios.stream()
				//.map(u -> u.getEmail())
				//.collect(Collectors.toList());
		
		this.enviarEmail("casilvamgdev@gmail.com", 
				destinario, 
				"Lançamentos vencidos", 
				"mail/aviso-lancamentos-vencidos", 
				variaveis);
	}
	
	@EventListener
	private void teste(ApplicationReadyEvent event) {
		String template = "mail/aviso-lancamentos-vencidos";	
		List<Lancamento> lista = repo.findAll();
				
		Map<String, Object> variaveis = new HashMap<>();
		variaveis.put("lancamentos", lista);
		
		this.enviarEmail("casilvamgdev@gmail.com", 
				"casilvamg@gmail.com",     
				"Testando", template, variaveis); 
	
	}
	
	/*@EventListener
	private void teste(ApplicationReadyEvent event) {
		this.enviarEmail("casilvamgdev@gmail.com", 
				Arrays.asList("casilvamg@gmail.com"),  
				"Testando", "Olá!<br/>Teste ok.");
		System.out.println("Terminado o envio de e-mail...");  
	}*/
	/*
	public void enviarEmail(String remetente, 
			String destinatarios, String assunto, String template, 
			Map<String, Object> variaveis) {
		Context context = new Context(new Locale("pt", "BR"));
		
		variaveis.entrySet().forEach(
				e -> context.setVariable(e.getKey(), e.getValue()));
		
		String mensagem = thymeleaf.process(template, context);
		
		this.enviarEmail(remetente, destinatarios, assunto, mensagem);
	}
	
	public void enviarEmail(String remetente, 
			String destinatarios, String assunto, String mensagem) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setFrom(remetente);
			//helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
			helper.setTo(destinatarios);
			helper.setSubject(assunto);
			helper.setText(mensagem, true);
			
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new RuntimeException("Problemas com o envio de e-mail!", e); 
		}
	}*/
}