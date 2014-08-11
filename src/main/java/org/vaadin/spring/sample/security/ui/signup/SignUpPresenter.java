package org.vaadin.spring.sample.security.ui.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.mvp.MvpHasPresenterHandlers;
import org.vaadin.spring.mvp.MvpView;
import org.vaadin.spring.mvp.presenter.AbstractMvpPresenterView;
import org.vaadin.spring.navigator.VaadinView;
import org.vaadin.spring.sample.security.account.Account;
import org.vaadin.spring.sample.security.account.AccountRepository;
import org.vaadin.spring.sample.security.account.UsernameAlreadyInUseException;
import org.vaadin.spring.sample.security.ui.UserSignedInEvent;
import org.vaadin.spring.sample.security.ui.ViewToken;
import org.vaadin.spring.security.Security;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@UIScope
@VaadinView(name=ViewToken.SIGNUP)
public class SignUpPresenter extends AbstractMvpPresenterView<SignUpPresenter.SignUpView> implements SignUpPresenterHandlers {

	public interface SignUpView extends MvpView, MvpHasPresenterHandlers<SignUpPresenterHandlers> {
		void initView();
		void setErrorMessage(String message);
	}
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	Security security;
	
	@Autowired
	public SignUpPresenter(SignUpView view, EventBus eventBus) {
		super(view, eventBus);
		getView().setPresenterHandlers(this);
	
	}	

	@Override
	public void enter(ViewChangeEvent event) {
		getView().initView();
		
	}

	@Override
	public void tryCreateAccount(Account account) {
		
		try {
			accountRepository.createAccount(account);
								
		} catch (UsernameAlreadyInUseException e) {
			getView().setErrorMessage(e.getMessage());
			return;
		}
		
		try {			
			security.login(account.getUsername(), account.getPassword());
			
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
