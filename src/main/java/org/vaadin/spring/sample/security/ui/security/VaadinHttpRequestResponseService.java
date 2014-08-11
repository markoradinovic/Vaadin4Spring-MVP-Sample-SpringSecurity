package org.vaadin.spring.sample.security.ui.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(value = org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
public class VaadinHttpRequestResponseService implements HttpRequestResponseService {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpServletResponse response;
	
	@Override
	public HttpServletRequest getCurrentRequest() {		
		return request;
	}

	@Override
	public HttpServletResponse getCurrentResponse() {
		return response;
	}

}
