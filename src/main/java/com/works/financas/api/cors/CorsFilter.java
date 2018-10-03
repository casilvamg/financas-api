package com.works.financas.api.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.works.financas.api.config.property.FinancasApiProperty;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE) //Aula 6.10
public class CorsFilter implements Filter {
	
	@Autowired
	private FinancasApiProperty financasApiProperty;

	//private String originPermitida = "http://localhost:4200"; // TODO: Configurar para diferentes ambientes
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		response.setHeader("Access-Control-Allow-Origin", financasApiProperty.getOriginPermitida());
        response.setHeader("Access-Control-Allow-Credentials", "true");
        
        System.out.println("TESTE1x...." + request.getMethod());
        System.out.println("TESTE1xx...." + financasApiProperty.getOriginPermitida());
		
		if ("OPTIONS".equals(request.getMethod()) && financasApiProperty.getOriginPermitida().equals(request.getHeader("Origin"))) {
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
        	response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
        	response.setHeader("Access-Control-Max-Age", "3600");
        	System.out.println("TESTE2....");
			
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			System.out.println("TESTE3....");
			chain.doFilter(req, resp);
		}
		
	}
	
	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}