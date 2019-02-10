package com.works.financas.api.config;

import org.springframework.context.annotation.Profile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

/*
 * 
 * Classe de configuração que restrinja acesso aos recursos 
 * 
 */
@Profile("oauth-security")
@Configuration  
public class ResourceServerConfig extends ResourceServerConfigurerAdapter { //Servidor que contém os recursos
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/categorias").permitAll()                //perde o sentido mencionado aula 6.12 (04:41)
				.antMatchers("/usuarios/buscarPorEmail").permitAll()  //perde o sentido mencionado aula 6.12 (04:41)
				.anyRequest().authenticated() //para todas demais rotas a autenticação se faz necessária
				.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.csrf().disable();
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.stateless(true);
	}
	
	@Bean
	public MethodSecurityExpressionHandler createExpressionHandler() {  //Aula 6.12
		return new OAuth2MethodSecurityExpressionHandler();
	}
	
}