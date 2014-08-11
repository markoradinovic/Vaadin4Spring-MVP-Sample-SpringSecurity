package org.vaadin.spring.sample.security.ui.security;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.ExpressionBasedAnnotationAttributeFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.util.MethodInvocationUtils;
import org.springframework.util.ClassUtils;
import org.vaadin.spring.navigator.SpringViewProvider.ViewProviderAccessDelegate;
import org.vaadin.spring.security.Security;

import com.vaadin.ui.UI;

public class PreAuthorizeSpringViewProviderAccessDelegate implements ViewProviderAccessDelegate {

	private final Security security;
    private final ApplicationContext applicationContext;

    @Autowired
    public PreAuthorizeSpringViewProviderAccessDelegate(Security security, ApplicationContext applicationContext) {
        this.security = security;
        this.applicationContext = applicationContext;
    }
	
	@Override
	public boolean isAccessGranted(String beanName, UI ui) {
		
		final PreAuthorize viewSecured = applicationContext.findAnnotationOnBean(beanName, PreAuthorize.class);
					
		if (viewSecured != null) {

			final Class<?> targetClass = AopUtils.getTargetClass(applicationContext.getBean(beanName));
			final Method method = ClassUtils.getMethod(AopUtils.getTargetClass(applicationContext.getBean(beanName)), "enter", com.vaadin.navigator.ViewChangeListener.ViewChangeEvent.class);								
			final MethodInvocation methodInvocation = MethodInvocationUtils.createFromClass(targetClass, method.getName());
									
			final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			final AccessDecisionManager accessDecisionManager = applicationContext.getBean(AccessDecisionManager.class);			        	        	        	       	        	       
	        final ExpressionBasedAnnotationAttributeFactory attributeFactory = new ExpressionBasedAnnotationAttributeFactory(new DefaultMethodSecurityExpressionHandler());
	        
			Collection<ConfigAttribute> atributi = new ArrayList<ConfigAttribute>();
			atributi.add(attributeFactory.createPreInvocationAttribute(null, null, viewSecured.value()));
			
	        try {
	            accessDecisionManager.decide(authentication, methodInvocation, atributi);
	            return true;
	        } catch (AccessDeniedException | InsufficientAuthenticationException ex) {
	            return false;
	        }
			
		} else {
			return true;
		}
		
	}

}
