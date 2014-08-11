package org.vaadin.spring.sample.security.ui.user;

import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.mvp.view.AbstractMvpView;
import org.vaadin.spring.sample.security.ui.user.UserPresenter.UserView;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@UIScope
@VaadinComponent
@SuppressWarnings("serial")
public class UserViewImpl extends AbstractMvpView implements UserView{

	private UserPresenterHandlers mvpPresenterHandlers;
	
	private VerticalLayout content;
	private Label caption;
	private Label info;
	private Label dummyInfo;
	
	private Button btnTest;
	
	@Override
	public void postConstruct() {	
		super.postConstruct();
		
		content = new VerticalLayout();
		content.setSpacing(true);
		content.setMargin(true);
		setCompositionRoot(content);
								
		caption = new Label("This is User view", ContentMode.HTML);
		caption.addStyleName(ValoTheme.LABEL_H2);
		content.addComponent(caption);
		
		info = new Label("Invoke admin service", ContentMode.HTML);
		info.addStyleName(ValoTheme.LABEL_H2);
		content.addComponent(info);
		
		btnTest = new Button("Click me", FontAwesome.STAR);
		content.addComponent(btnTest);
		btnTest.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				mvpPresenterHandlers.callDummyService();
				
			}
		});
		
		dummyInfo = new Label("", ContentMode.HTML);
		dummyInfo.addStyleName(ValoTheme.LABEL_H2);
		content.addComponent(dummyInfo);
		
		
				
	}
		
	@Override
	public void initView() {		
	}

	@Override
	public void setMessage(String message) {
		dummyInfo.setValue(message);
		
	}

	@Override
	public void setPresenterHandlers(UserPresenterHandlers mvpPresenterHandlers) {
		this.mvpPresenterHandlers = mvpPresenterHandlers;
		
	}
}
