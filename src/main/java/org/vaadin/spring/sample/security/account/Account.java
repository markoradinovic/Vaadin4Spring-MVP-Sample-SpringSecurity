package org.vaadin.spring.sample.security.account;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class Account {

	@NotEmpty(message="Username is required")
	private String username;

	@Size(min = 6, message = "Password must be at least 6 characters")
	private String password;

	@NotEmpty(message="First name is required")
	private String firstName;

	@NotEmpty(message="Last name is required")
	private String lastName;
	
	private String role;

	public Account(String username, String password, String firstName, String lastName, String role) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public String getRole() {
		return role;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}
