package org.vaadin.spring.sample.security.ui.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class HttpResponseFilter implements Filter {

	private ThreadLocal<HttpServletResponse> responses = new ThreadLocal<HttpServletResponse>();
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletResponse r = (HttpServletResponse) response;
		responses.set(r);
		chain.doFilter(request, response);
		responses.remove();		
	}
	
	public HttpServletResponse getHttpServletReponse() {
		return responses.get();
	}

	@Override
	public void destroy() {		
		
	}

}
