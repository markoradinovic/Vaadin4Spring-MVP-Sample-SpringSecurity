package org.vaadin.spring.sample.security.ui.navigation;

import java.io.Serializable;

@SuppressWarnings("serial")
public class VaadinRedirectObject implements Serializable {
	
	public static final String REDIRECT_OBJECT_SESSION_ATTRIBUTE = "vaadin-redirect-object";

	private String redirectViewToken;
	
	private String errorMessage;
	
	public VaadinRedirectObject() {	
	}

	public VaadinRedirectObject(String redirectViewToken, String errorMessage) {
		super();
		this.redirectViewToken = redirectViewToken;
		this.errorMessage = errorMessage;
	}

	public String getRedirectViewToken() {
		return redirectViewToken;
	}

	public void setRedirectViewToken(String redirectViewToken) {
		this.redirectViewToken = redirectViewToken;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	
}
