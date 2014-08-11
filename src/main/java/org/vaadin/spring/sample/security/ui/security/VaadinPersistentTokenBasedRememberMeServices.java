package org.vaadin.spring.sample.security.ui.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

public class VaadinPersistentTokenBasedRememberMeServices extends
		PersistentTokenBasedRememberMeServices {

	public VaadinPersistentTokenBasedRememberMeServices(String key,
			UserDetailsService userDetailsService,
			PersistentTokenRepository tokenRepository) {		
		super(key, userDetailsService, tokenRepository);
	}

	@Override
	protected boolean rememberMeRequested(HttpServletRequest request,
			String parameter) {

		if (request.getAttribute(DEFAULT_PARAMETER) != null && (boolean)request.getAttribute(DEFAULT_PARAMETER)) {
			request.removeAttribute(DEFAULT_PARAMETER);
			return true;
		}
		return super.rememberMeRequested(request, parameter);		
	}

}
