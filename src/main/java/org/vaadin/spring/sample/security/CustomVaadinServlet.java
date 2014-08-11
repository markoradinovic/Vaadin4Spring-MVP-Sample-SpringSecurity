package org.vaadin.spring.sample.security;

import javax.servlet.ServletException;

import org.vaadin.spring.servlet.SpringAwareVaadinServlet;

import com.vaadin.server.BootstrapFragmentResponse;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;

@SuppressWarnings("serial")
public class CustomVaadinServlet extends SpringAwareVaadinServlet {
	
	@Override
	protected void servletInitialized() throws ServletException {	
		super.servletInitialized();
		getService().addSessionInitListener(new SessionInitListener() {
			
			private static final long serialVersionUID = -6951911585827609232L;

			@Override
			public void sessionInit(SessionInitEvent event) throws ServiceException {
				
				event.getSession().addBootstrapListener(new BootstrapListener() {
					
					private static final long serialVersionUID = -7924761905948932337L;

					@Override
					public void modifyBootstrapPage(BootstrapPageResponse response) {
						response.getDocument().body().attr("lang", "sr");
						response.getDocument().head().prependElement("meta").attr("name", "viewport").attr("content", "width=device-width, initial-scale=1");
					}
					

					@Override
					public void modifyBootstrapFragment(
							BootstrapFragmentResponse response) {
						
					}
				});
				
			}
		});
	}

}
