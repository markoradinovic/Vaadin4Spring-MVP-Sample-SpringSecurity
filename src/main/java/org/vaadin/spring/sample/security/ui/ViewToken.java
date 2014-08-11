package org.vaadin.spring.sample.security.ui;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public interface ViewToken extends Serializable {
	
	public static final String HOME="";
	public static final String USER="/user";	
	public static final String ADMIN="/admin";
	public static final String ADMIN_HIDDEN="/hidden";
	public static final String SIGNIN="/signin";
	public static final String SIGNUP="/signup";
	
	public static final List<String> VALID_TOKENS = Arrays.asList(new String[] {HOME, USER, ADMIN, ADMIN_HIDDEN, SIGNIN, SIGNUP});		

}
