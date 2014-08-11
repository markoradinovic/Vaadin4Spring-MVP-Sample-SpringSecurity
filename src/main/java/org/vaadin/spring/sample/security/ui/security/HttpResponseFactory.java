package org.vaadin.spring.sample.security.ui.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class HttpResponseFactory implements FactoryBean<HttpServletResponse>, ApplicationContextAware {

	private ApplicationContext applicationContext;
	
	@Override
	public HttpServletResponse getObject() throws Exception {
		HttpResponseFilter httpResponseFilter = applicationContext.getBean(HttpResponseFilter.class);	
		return httpResponseFilter.getHttpServletReponse();
	}

	@Override
	public Class<?> getObjectType() {	
		return HttpServletResponse.class;
	}

	@Override
	public boolean isSingleton() {		
		return false;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		
	}

}
