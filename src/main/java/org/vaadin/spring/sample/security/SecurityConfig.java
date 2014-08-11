package org.vaadin.spring.sample.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.vaadin.spring.sample.security.account.JdbcUserDetailsService;
import org.vaadin.spring.sample.security.ui.security.PreAuthorizeSpringViewProviderAccessDelegate;
import org.vaadin.spring.sample.security.ui.security.VaadinPersistentTokenBasedRememberMeServices;
import org.vaadin.spring.security.Security;


@Configuration
@ComponentScan
public class SecurityConfig   {
			
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private Security security;
	
	@Bean
	public PreAuthorizeSpringViewProviderAccessDelegate preAuthorizeSpringViewProviderAccessDelegate() {
		return new PreAuthorizeSpringViewProviderAccessDelegate(security, context);
	}
	
	@Configuration
	@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
	public static class GlobalMethodSecurity extends GlobalMethodSecurityConfiguration {
		
		@Bean
		@Override
		protected AccessDecisionManager accessDecisionManager() {
			return super.accessDecisionManager();
		}
	}
	
	/**
	 * WebMvcSecurity configuration
	 * @author marko
	 *
	 */
	@Configuration
	@EnableWebMvcSecurity	
	public static class WebMvcSecurityConfig extends WebSecurityConfigurerAdapter {
		
		@Autowired
		JdbcUserDetailsService userDetailsService;
		
		@Autowired
		DataSource dataSource;
		
		
		@Bean
		public PasswordEncoder passwordEncoder() {
			return NoOpPasswordEncoder.getInstance();
		}

		@Bean
		public TextEncryptor textEncryptor() {
			return Encryptors.noOpText();
		}
		
		@Bean
		public PersistentTokenRepository jdbcTokenRepository() {
			JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
			repository.setCreateTableOnStartup(false);
			repository.setDataSource(dataSource);			
			return repository;
		}
		
		@Bean
		public RememberMeServices persistentTokenBasedRememberMeServices() {
			VaadinPersistentTokenBasedRememberMeServices services = new VaadinPersistentTokenBasedRememberMeServices(
					"vaadin4spring", 
					userDetailsService, 
					jdbcTokenRepository());
			services.setCookieName("REMEMBERME");
			return services;
		}
		
		/*
		 * (non-Javadoc)
		 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.WebSecurity)
		 */
		@Override
		public void configure(WebSecurity web) throws Exception {
			//Ignoring static resources
			web.ignoring().antMatchers("/VAADIN/**");
		}
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth)
				throws Exception {
			auth.userDetailsService(userDetailsService);
				
		}
		
		@Bean(name="authenticationManager")
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
		
		
		/*
		 * (non-Javadoc)
		 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
		 */
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
			http.exceptionHandling()			
				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
					.and()
				.authorizeRequests()								
					.antMatchers("/**").permitAll()
					.and()		
				.rememberMe()
					.key("vaadin4spring")
					.rememberMeServices(persistentTokenBasedRememberMeServices())
					.and()				
				.csrf().disable();
		}
		
		
		
		
	}
	

}
