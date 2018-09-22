package com.works.financas.api.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.works.financas.api.config.property.FinancasApiProperty;

@Configuration
public class MailConfig {
	
	@Autowired
	private FinancasApiProperty financasApiProperty;

	@Bean
	public JavaMailSender javaMailSender() {
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp"); //protocolo utilizado SMTP
		props.put("mail.smtp.auth", true);           //autenticado
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.connectiontimeout", 10000);
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); //adicionado CAS
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setJavaMailProperties(props);
		mailSender.setHost(financasApiProperty.getMail().getHost());
		mailSender.setPort(financasApiProperty.getMail().getPort());
		mailSender.setUsername(financasApiProperty.getMail().getUsername());
		mailSender.setPassword(financasApiProperty.getMail().getPassword());
		
		return mailSender;
	}
}