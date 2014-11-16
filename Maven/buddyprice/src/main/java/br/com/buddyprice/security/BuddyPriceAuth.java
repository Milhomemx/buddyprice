package br.com.buddyprice.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import br.com.buddyprice.control.LoginController;
import br.com.buddyprice.model.Usuario;
import br.com.vexillum.control.security.AuthenticatorProvider;
import br.com.vexillum.control.security.ManagerAuthenticator;
import br.com.vexillum.model.Category;
import br.com.vexillum.util.EncryptUtils;
import br.com.vexillum.util.HibernateUtils;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.SpringFactory;

/**
 * @author Natan
 * Esta classe é responsável por prover a autenticação do sistema. ELa verificará se o usuário existe, e se a senha informada é igual a senha criptografada no banco de dados.
 * Ela verificará também se a cont está inativa.
 */
public class BuddyPriceAuth extends AuthenticatorProvider<Usuario> {

	private LoginController control;

	public BuddyPriceAuth() {
		control = SpringFactory.getController("loginController",
				LoginController.class,
				ReflectionUtils.prepareDataForPersistence(this));
	}

	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) auth;
		String userName = token.getName();
		String password = token.getCredentials() != null ? token
				.getCredentials().toString() : null;
		if((userName == "" || userName == null) || (password == "" || password == null))
			throw new BadCredentialsException("Usuário ou senha incorretos");
		Usuario user = control.getUser(userName, EncryptUtils.encryptOnSHA512(password));
		if (user != null && !user.getActive())
			throw new DisabledException(
					"Conta inativa, contate o administrador!");
		else if (user == null) {
			throw new BadCredentialsException("Usuário ou senha incorretos");
		}
		List<Category> userCategories = new ArrayList<Category>();
		userCategories.add(control.getCategoryUser(user));
		ManagerAuthenticator<Usuario, Category> manager = new ManagerAuthenticator<Usuario, Category>(
				HibernateUtils.materializeProxy(user), userCategories);
		manager.setAuthenticated(user != null);
		return manager;
	}

}
