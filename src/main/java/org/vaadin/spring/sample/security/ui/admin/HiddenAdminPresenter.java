package org.vaadin.spring.sample.security.ui.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.mvp.MvpView;
import org.vaadin.spring.mvp.presenter.AbstractMvpPresenterView;
import org.vaadin.spring.navigator.VaadinView;
import org.vaadin.spring.sample.security.ui.ViewToken;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

@SuppressWarnings("serial")
@UIScope
@VaadinView(name=ViewToken.ADMIN_HIDDEN)
@Secured({"ROLE_ADMIN"})
@PreAuthorize("isFullyAuthenticated()") //do not allow Remember-Me
public class HiddenAdminPresenter extends AbstractMvpPresenterView<HiddenAdminPresenter.HiddenAdminView> {
	
	public interface HiddenAdminView extends MvpView {
		void initView();
	}
	
	@Autowired
	public HiddenAdminPresenter(HiddenAdminView view, EventBus eventBus) {
		super(view, eventBus);
	}

	

	@Override
	public void enter(ViewChangeEvent event) {
		getView().initView();
		
	}

}
