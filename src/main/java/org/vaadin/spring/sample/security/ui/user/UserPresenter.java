package org.vaadin.spring.sample.security.ui.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.mvp.MvpHasPresenterHandlers;
import org.vaadin.spring.mvp.MvpView;
import org.vaadin.spring.mvp.presenter.AbstractMvpPresenterView;
import org.vaadin.spring.navigator.VaadinView;
import org.vaadin.spring.sample.security.service.DummyService;
import org.vaadin.spring.sample.security.ui.ViewToken;
import org.vaadin.spring.security.Security;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

@SuppressWarnings("serial")
@UIScope
@VaadinView(name=ViewToken.USER)
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class UserPresenter extends AbstractMvpPresenterView<UserPresenter.UserView> implements UserPresenterHandlers {
	
	public interface UserView extends MvpView, MvpHasPresenterHandlers<UserPresenterHandlers> {
		public void initView();
		public void setMessage(String message);
	}
	
	@Autowired
	DummyService dummyService;
	
	@Autowired
	Security security;
	
	@Autowired
	public UserPresenter(UserView view, EventBus eventBus) {
		super(view, eventBus);
		getView().setPresenterHandlers(this);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		getView().initView();
		
	}

	@Override
	public void callDummyService() {
		getView().setMessage(dummyService.heyDummy(security.getAuthentication().getName()));
		
	}

	

}
