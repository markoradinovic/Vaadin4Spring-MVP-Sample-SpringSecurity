package org.vaadin.spring.sample.security;

import javax.sql.DataSource;

import org.eclipse.jetty.servlets.GzipFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.vaadin.spring.sample.security.account.JdbcAccountRepository;
import org.vaadin.spring.sample.security.ui.security.HttpResponseFactory;
import org.vaadin.spring.sample.security.ui.security.HttpResponseFilter;
import org.vaadin.spring.sample.security.ui.security.SpringApplicationContext;
import org.vaadin.spring.servlet.SpringAwareVaadinServlet;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application  {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}
	
	@Bean
	public SpringAwareVaadinServlet springAwareVaadinServlet() {
		return new CustomVaadinServlet();
	}
	
	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}
	
	@Bean
    @ConditionalOnMissingBean(RequestContextListener.class)
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
	
	/**
	 * Allow injection of HttpServletResponse
	 * 
	 * @return
	 */
	@Bean
	public HttpResponseFilter httpResponseFilter() {
		return new HttpResponseFilter(); 		
	}
	
	/**
	 * Allow injection of HttpServletResponse
	 * 
	 * @return
	 */
	@Bean
	public HttpResponseFactory httpResponseFactory() {
		return new HttpResponseFactory();
	}
	
	@Bean
	public FilterRegistrationBean hiddenHttpMethodFilter() {
		HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();								
		registrationBean.setFilter(hiddenHttpMethodFilter);					
		return registrationBean;		
	}
	
	@Bean
	public FilterRegistrationBean gzipFilter() {
		GzipFilter gzipFilter = new GzipFilter();		
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();								
		registrationBean.setFilter(gzipFilter);					
		return registrationBean;		
	}
	
	
	@Bean(destroyMethod = "shutdown")
	public DataSource dataSource() {
		EmbeddedDatabaseFactory factory = new EmbeddedDatabaseFactory();
		factory.setDatabaseName("spring-vaadin-security-demo");
		factory.setDatabaseType(EmbeddedDatabaseType.H2);
		factory.setDatabasePopulator(databasePopulator());
		return factory.getDatabase();
	}
	
	private DatabasePopulator databasePopulator() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();		
		populator.addScript(new ClassPathResource("Account.sql", JdbcAccountRepository.class));
		populator.addScript(new ClassPathResource("data.sql", JdbcAccountRepository.class));
		populator.addScript(new ClassPathResource("rememberme.sql", JdbcAccountRepository.class));
		return populator;
	}
	
	
}
