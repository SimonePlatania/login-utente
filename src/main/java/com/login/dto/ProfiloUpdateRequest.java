package com.login.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ProfiloUpdateRequest {
	
	@NotBlank(message = "Username non può essere vuoto")
	@Size(min = 3, max = 30, message = "")
	private String username;
	
	@NotBlank(message = "L'email non può essere vuota")
	@Email(message = "Email non valida")
	private String email;
	
	public ProfiloUpdateRequest() {}

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
	
	
	

}
