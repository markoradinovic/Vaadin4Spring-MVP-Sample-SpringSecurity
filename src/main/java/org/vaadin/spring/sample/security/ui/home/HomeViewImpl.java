package org.vaadin.spring.sample.security.ui.home;

import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.mvp.view.AbstractMvpView;
import org.vaadin.spring.sample.security.ui.home.HomePresenter.HomeView;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@UIScope
@VaadinComponent
public class HomeViewImpl extends AbstractMvpView implements HomeView  {

	private HomePresenterHandlers mvpPresenterHandlers;		
	
	private VerticalLayout content;
	
	private Label caption;
	private Label loginInfo;
	
	@Override
	public void postConstruct() {	
		super.postConstruct();
		
		content = new VerticalLayout();
		content.setSpacing(true);
		content.setMargin(true);
		setCompositionRoot(content);
								
		caption = new Label("This is Home View", ContentMode.HTML);
		caption.addStyleName(ValoTheme.LABEL_H2);
		content.addComponent(caption);
		
		loginInfo = new Label("This is Home View", ContentMode.HTML);
		loginInfo.addStyleName(ValoTheme.LABEL_H2);
		content.addComponent(loginInfo);
				
	}
	
	@Override
	public void setPresenterHandlers(HomePresenterHandlers mvpPresenterHandlers) {
		this.mvpPresenterHandlers = mvpPresenterHandlers;
		
	}

	@Override
	public void initView(String userName, String loginType) {
		caption.setValue("Welcome back " + userName + "!");
		loginInfo.setValue("You are signed in using " + loginType + "!");
	}
	

}
