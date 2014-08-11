package org.vaadin.spring.sample.security.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
@Secured({"ROLE_ADMIN"})
public class DummyServiceImpl implements DummyService {

//	@Secured({"ROLE_ADMIN"})
	@Override
	public String heyDummy(String text) {		
		return "Hey " + text;
	}

}
