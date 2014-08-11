package org.vaadin.spring.sample.security.ui;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.VaadinUI;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.navigator.SpringViewProvider;
import org.vaadin.spring.sample.security.ui.security.SecuredNavigator;
import org.vaadin.spring.sample.security.ui.security.SpringSecurityErrorHandler;
import org.vaadin.spring.security.Security;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@VaadinUI
@Title("Vaadin4Spring Security Demo")
@Theme("valo")
@SuppressWarnings("serial")
public class MainUI extends UI {
	
	
	@Autowired
	SpringViewProvider springViewProvider;		
	
	@Autowired
	EventBus eventBus;
	
	@Autowired
	Security security;
	
	@Autowired
	MainLayout mainLayout;

    @Override
    protected void init(VaadinRequest request) {
    	setLocale(new Locale.Builder().setLanguage("sr").setScript("Latn").setRegion("RS").build());
    	
    	SecuredNavigator securedNavigator = new SecuredNavigator(MainUI.this, mainLayout, springViewProvider, security, eventBus);
        securedNavigator.addViewChangeListener(mainLayout);
        
        setContent(mainLayout);
        
        setErrorHandler(new SpringSecurityErrorHandler());
        
        /*
         * Handling redirections
         */        
//        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();        
//        if (sessionStrategy.getAttribute(attrs, VaadinRedirectObject.REDIRECT_OBJECT_SESSION_ATTRIBUTE) != null) {        	
//        	VaadinRedirectObject redirectObject = (VaadinRedirectObject) sessionStrategy.getAttribute(attrs, VaadinRedirectObject.REDIRECT_OBJECT_SESSION_ATTRIBUTE);
//        	sessionStrategy.removeAttribute(attrs, VaadinRedirectObject.REDIRECT_OBJECT_SESSION_ATTRIBUTE);
//        	 
//        	navigator.navigateTo(redirectObject.getRedirectViewToken());
//        	
//        	if (redirectObject.getErrorMessage() != null) {
//        		Notification.show("Error", redirectObject.getErrorMessage(), Type.ERROR_MESSAGE);
//        	}
//        	
//        }
               
    }

}
