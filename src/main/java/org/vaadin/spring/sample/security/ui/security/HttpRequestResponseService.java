package org.vaadin.spring.sample.security.ui.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpRequestResponseService {
	
	public HttpServletRequest getCurrentRequest();
	
	public HttpServletResponse getCurrentResponse();
	
}
