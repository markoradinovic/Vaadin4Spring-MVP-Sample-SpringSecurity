package org.vaadin.spring.sample.security.ui.signup;

import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.mvp.view.AbstractMvpView;
import org.vaadin.spring.sample.security.account.Account;
import org.vaadin.spring.sample.security.ui.signup.SignUpPresenter.SignUpView;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@UIScope
@VaadinComponent
public class SignUpViewImpl extends AbstractMvpView implements SignUpView, ClickListener {

	private SignUpPresenterHandlers mvpPresenterHandlers;
	
	private VerticalLayout layout;
	
	private VerticalLayout container;
	private FormLayout form;

	private TextField username;
	private PasswordField password;
	private TextField firstName;	
	private TextField lastName;
	
	private Label infoLabel;
	
	private Button btnSignUp;
	
	private BeanFieldGroup<Account> binder = new BeanFieldGroup<Account>(Account.class);
	
	@Override
	public void postConstruct() {
		super.postConstruct();		
		setSizeFull();
		
		layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setSpacing(true);
		setCompositionRoot(layout);
		
		infoLabel = new Label("Vaadin4Spring Security Demo - SignUp");
		infoLabel.addStyleName(ValoTheme.LABEL_H2);
		infoLabel.setSizeUndefined();
		layout.addComponent(infoLabel);
		layout.setComponentAlignment(infoLabel, Alignment.MIDDLE_CENTER);
		
		container = new VerticalLayout();
		container.setSizeUndefined();
		container.setSpacing(true);
		layout.addComponent(container);
		layout.setComponentAlignment(container, Alignment.MIDDLE_CENTER);
		layout.setExpandRatio(container, 1);
						
		form = new FormLayout();
		form.setWidth("400px");
		form.setSpacing(true);
		container.addComponent(form);
		buildForm();
					
		btnSignUp = new Button("Signup", FontAwesome.FLOPPY_O);
		btnSignUp.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnSignUp.addClickListener(this);
		container.addComponent(btnSignUp);
		container.setComponentAlignment(btnSignUp, Alignment.MIDDLE_CENTER);
	
	}
	
	@Override
	public void initView() {
		Account account = new Account(null, null, null, null, "ROLE_USER"); 
		binder.setItemDataSource(account);
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		try {
			binder.commit();
			
			Account account = binder.getItemDataSource().getBean();
			mvpPresenterHandlers.tryCreateAccount(account);
			
		} catch (CommitException e) {
			username.setValidationVisible(true);
			password.setValidationVisible(true);
			firstName.setValidationVisible(true);
			lastName.setValidationVisible(true);			
		} 		
	}
	
	private void buildForm() {
		username = new TextField("Username");
		username.setWidth("100%");
		username.setImmediate(true);
		username.setValidationVisible(false);
		username.setNullRepresentation("");
		username.setRequired(true);		
		form.addComponent(username);
		
		password = new PasswordField("Password");
		password.setWidth("100%");
		password.setImmediate(true);
		password.setValidationVisible(false);
		password.setNullRepresentation("");
		password.setRequired(true);
		form.addComponent(password);
		
		firstName = new TextField("First name");
		firstName.setWidth("100%");
		firstName.setValidationVisible(false);
		firstName.setNullRepresentation("");
		firstName.setImmediate(true);
		firstName.setRequired(true);
		form.addComponent(firstName);
		
		lastName = new TextField("Last name");
		lastName.setWidth("100%");
		lastName.setImmediate(true);
		lastName.setNullRepresentation("");
		lastName.setValidationVisible(false);
		lastName.setRequired(true);
		form.addComponent(lastName);
		
		binder.bind(username, "username");
		binder.bind(password, "password");
		binder.bind(firstName, "firstName");
		binder.bind(lastName, "lastName");	
	}

	

	@Override
	public void setErrorMessage(String message) {
		username.setComponentError(new UserError(message));
		username.setValidationVisible(true);
		password.setValidationVisible(true);
		firstName.setValidationVisible(true);
		lastName.setValidationVisible(true);
		
	}

	@Override
	public void setPresenterHandlers(
			SignUpPresenterHandlers mvpPresenterHandlers) {
		this.mvpPresenterHandlers = mvpPresenterHandlers;
		
	}
	

	
	
}
