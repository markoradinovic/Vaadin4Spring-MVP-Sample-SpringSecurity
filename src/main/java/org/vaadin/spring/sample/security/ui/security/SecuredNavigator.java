package org.vaadin.spring.sample.security.ui.security;

import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.navigator.SpringViewProvider;
import org.vaadin.spring.sample.security.ui.ViewToken;
import org.vaadin.spring.security.Security;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.Page;
import com.vaadin.ui.UI;

public class SecuredNavigator extends Navigator {

	private static final long serialVersionUID = -1550472603474329940L;
	
	final Security security;
	final SpringViewProvider viewProvider;
	final EventBus eventBus;
			
	public SecuredNavigator(UI ui, ViewDisplay display, SpringViewProvider viewProvider, Security security, EventBus eventBus) {
		super(ui, display);		
		this.security = security;
		this.viewProvider = viewProvider;
		this.eventBus = eventBus;
		addProvider(this.viewProvider);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.vaadin.navigator.Navigator#navigateTo(java.lang.String)
	 */
	@Override
	public void navigateTo(String navigationState) {
						
		if (ViewToken.VALID_TOKENS.contains(navigationState)) {
			
			/* This method in class should be public
			 * private boolean isViewBeanNameValidForCurrentUI(String beanName)
			 * 
			 *  workaround
			 */													
			if ( viewProvider.getView(navigationState) == null ) {
            	
				/*
				 * Handle entering UI from bookmark
				 */
				String uriFragment = extractViewToken();
				if (uriFragment.equals(navigationState)) {
					
					super.navigateTo(ViewToken.HOME);
					
				} else {
					/*
	            	 * View is probably @Secured
	            	 */
					eventBus.publish(EventScope.UI, UI.getCurrent(), new AccessDeniedEvent(navigationState));
				}
            	            	
            } else {
            	super.navigateTo(navigationState);
            }
			
		} else {
			//invalid ViewToken
			super.navigateTo(ViewToken.HOME);
		}		
		
	}
	
	
	private String extractViewToken() {
		String uriFragment = Page.getCurrent().getUriFragment();
		if (uriFragment != null && !uriFragment.isEmpty() && uriFragment.length() > 1) {
			return uriFragment.substring(1);
		} else {
			return "";
		}
		
	}


}
