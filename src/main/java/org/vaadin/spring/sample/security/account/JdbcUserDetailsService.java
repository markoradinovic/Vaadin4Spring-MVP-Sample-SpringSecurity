package org.vaadin.spring.sample.security.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JdbcUserDetailsService implements UserDetailsService {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	AccountRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		try {
			Account account = repository.findAccountByUsername(username);
			User user = new User(account.getUsername(), account.getPassword(), AuthorityUtils.createAuthorityList(account.getRole()));
			return user;
		} catch (DataAccessException e) {
			LOG.debug("Account not found", e);
			throw new UsernameNotFoundException("Username not found.");
		}
		
	}

}
