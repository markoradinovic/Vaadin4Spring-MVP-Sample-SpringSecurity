package org.vaadin.spring.sample.security.ui.signin;

import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.mvp.view.AbstractMvpView;
import org.vaadin.spring.sample.security.ui.signin.SignInPresenter.SignInView;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@UIScope
@VaadinComponent
@SuppressWarnings("serial")
public class SignInViewImpl extends AbstractMvpView implements SignInView, ClickListener {

	private SignInPresenterHandlers mvpPresenterHandlers;
	
	private VerticalLayout layout;
	private Label caption;	
	private TextField username;
	private PasswordField password;
	private CheckBox rememberMe;
	private Button btnLogin;	
	private VerticalLayout loginPanel;
	private Label errorMessage;
	
	@Override
	public void postConstruct() {	
		super.postConstruct();		
		setSizeFull();
		layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setSpacing(true);
		setCompositionRoot(layout);
		
		caption = new Label("Sign in to Vaadin4Spring Security Demo");
		caption.addStyleName(ValoTheme.LABEL_H2);
		caption.setSizeUndefined();		
		layout.addComponent(caption);
		layout.setComponentAlignment(caption, Alignment.MIDDLE_CENTER);
		
		loginPanel = new VerticalLayout();
		loginPanel.setSizeUndefined();
		loginPanel.setSpacing(true);
		loginPanel.setMargin(true);
		layout.addComponent(loginPanel);
		layout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
		layout.setExpandRatio(loginPanel, 1);
		
		errorMessage = new Label();
		errorMessage.setWidth("300px");
		errorMessage.addStyleName(ValoTheme.LABEL_FAILURE);		
		errorMessage.setVisible(false);
		loginPanel.addComponent(errorMessage);
		
		username = new TextField("Username");
		username.setImmediate(true);
		username.setWidth("300px");
		username.setNullRepresentation("");
		username.setInputPrompt("Enter your username");
		loginPanel.addComponent(username);
		
		password = new PasswordField("Password");
		password.setImmediate(true);
		password.setWidth("300px");
		password.setNullRepresentation("");
		loginPanel.addComponent(password);
		
		rememberMe = new CheckBox("Remember me");
		rememberMe.setValue(false);
		rememberMe.addStyleName(ValoTheme.CHECKBOX_LARGE);
		loginPanel.addComponent(rememberMe);
		
		btnLogin = new Button("Signin", FontAwesome.UNLOCK);
		btnLogin.addStyleName(ValoTheme.BUTTON_PRIMARY);
		btnLogin.addClickListener(this);
		btnLogin.setWidth("100%");
		loginPanel.addComponent(btnLogin);							
		
		final Label infoLabel = new Label(FontAwesome.INFO_CIRCLE.getHtml() + " You can sign in as: <br/>\"user\" with password \"user\" <br/>\"admin\" with password \"admin\".", ContentMode.HTML);
		infoLabel.setWidth("300px");
		loginPanel.addComponent(infoLabel);
	}
	
	
	@Override
	public void init() {
		errorMessage.setVisible(false);
		username.setValidationVisible(false);
		password.setValidationVisible(false);
		username.setValue(null);
		password.setValue(null);		
	}


	@Override
	public void buttonClick(ClickEvent event) {
		/*
		 * Signin using username and password
		 */
		if (event.getButton() == btnLogin) {
			
			mvpPresenterHandlers.doSignIn(username.getValue(), password.getValue(), rememberMe.getValue());						
		}				
		
	}


	@Override
	public void setPresenterHandlers(
			SignInPresenterHandlers mvpPresenterHandlers) {
		this.mvpPresenterHandlers = mvpPresenterHandlers;
		
	}


	@Override
	public void setErrorMessage(String error) {
		errorMessage.setValue(error);
		errorMessage.setVisible(true);
		
	}

}
