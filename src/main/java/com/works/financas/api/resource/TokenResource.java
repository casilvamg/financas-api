package com.works.financas.api.resource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.works.financas.api.config.property.FinancasApiProperty;

@RestController
@RequestMapping("/tokens")
public class TokenResource {
	
	@Autowired
	private FinancasApiProperty financasApiProperty;

	//Logout consiste em tirar valor do refreshToken Aula 6.14, ou seja, invalidar token
	@DeleteMapping("/revoke") 
	public void revoke(HttpServletRequest req, HttpServletResponse resp) {
		Cookie cookie = new Cookie("refreshToken", null);
		cookie.setHttpOnly(true);
	    cookie.setSecure(financasApiProperty.getSeguranca().isEnableHttps());
		// cookie.setSecure(true); // TODO: Em producao sera true 
		cookie.setPath(req.getContextPath() + "/oauth/token");
		cookie.setMaxAge(0);
		
		resp.addCookie(cookie);
		resp.setStatus(HttpStatus.NO_CONTENT.value());
	}
}