package br.com.buddyprice.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * @author Natan
 * Classe respons�vel pelo gerenciamento de falha do sistema de autentica��o.
 * A falha pode ser decorrente do usu�rio/senha incorretos ou conta inativa.
 */
public class BuddyPriceAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		if(exception instanceof BadCredentialsException){
			response.sendRedirect("pages/user/login.zul?error=true");
		} else if(exception instanceof DisabledException){
			response.sendRedirect("pages/user/login.zul?inactiveaccount=true");
		}
	}

}
