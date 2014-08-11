package org.vaadin.spring.sample.security.ui.signup;

import org.vaadin.spring.mvp.MvpPresenterHandlers;
import org.vaadin.spring.sample.security.account.Account;

public interface SignUpPresenterHandlers extends MvpPresenterHandlers {
	
	void tryCreateAccount(Account account);

}
