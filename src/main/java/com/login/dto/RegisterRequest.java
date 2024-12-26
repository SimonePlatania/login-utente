package com.login.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterRequest {
	
	@NotBlank(message = "Username non può essere vuoto")
	@Size(min = 3, max = 30, message = "Username deve essere compreso tra i 3 ed i 30 caratteri")
	private String username;
	
	@NotBlank(message = "Email non può essere vuota")
	@Email(message = "Email non valida")
	private String email;
	
	@NotBlank(message = "La password non può essere vuota")
	@Size(min = 6, message = "La password deve contenere almeno 6 caratteri")
	private String password;
	
	public RegisterRequest() {}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}
