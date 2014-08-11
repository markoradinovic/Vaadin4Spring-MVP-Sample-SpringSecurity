package org.vaadin.spring.sample.security.ui.signin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.mvp.MvpHasPresenterHandlers;
import org.vaadin.spring.mvp.MvpView;
import org.vaadin.spring.mvp.presenter.AbstractMvpPresenterView;
import org.vaadin.spring.navigator.VaadinView;
import org.vaadin.spring.sample.security.ui.UserSignedInEvent;
import org.vaadin.spring.sample.security.ui.ViewToken;
import org.vaadin.spring.sample.security.ui.security.HttpRequestResponseService;
import org.vaadin.spring.security.Security;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@UIScope
@VaadinView(name=ViewToken.SIGNIN)
public class SignInPresenter extends AbstractMvpPresenterView<SignInPresenter.SignInView> implements SignInPresenterHandlers {
	
	public interface SignInView extends MvpView, MvpHasPresenterHandlers<SignInPresenterHandlers> {
		void init();
		void setErrorMessage(String errorMessage);
	}
	
	@Autowired
	Security security;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	RememberMeServices rememberMeServices;
	
	@Autowired
	HttpRequestResponseService httpRequestResponseService;
	
	@Autowired
	public SignInPresenter(SignInView view, EventBus eventBus) {
		super(view, eventBus);
		getView().setPresenterHandlers(this);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		getView().init();		
	}

	@Override
	public void doSignIn(String username, String password, boolean rememberMe) {
		try {			
			
			/*
			 * security.login(username, password);
			 * 
			 */			
			final SecurityContext securityContext = SecurityContextHolder.getContext();
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

			final Authentication authentication = authenticationManager.authenticate(token);	  
	        securityContext.setAuthentication(authentication);
	        	                	        
	        if (rememberMe) {
	        	HttpServletRequest request = httpRequestResponseService.getCurrentRequest();
		        HttpServletResponse response = httpRequestResponseService.getCurrentResponse();		        
		        request.setAttribute(AbstractRememberMeServices.DEFAULT_PARAMETER, rememberMe);
		        rememberMeServices.loginSuccess(request, response, authentication);	        	
	        } 
			
	        
	        
			getEventBus().publish(EventScope.UI, this, new UserSignedInEvent());
			
			//Redirect to UserHome or Admin Home
			if (security.hasAuthority("ROLE_USER")) {
				UI.getCurrent().getNavigator().navigateTo(ViewToken.USER);
			} else {
				UI.getCurrent().getNavigator().navigateTo(ViewToken.ADMIN);
			}		
								
		} catch (AuthenticationException e) {
			getView().setErrorMessage(e.getMessage());
		}
		
	}

	

}
