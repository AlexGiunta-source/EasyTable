package com.EasyTable.Dto;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotBlank;
/**
 * Data Transfer Object (DTO) utilizzato per gestire i dati di login di un utente.
 * Questo DTO viene utilizzato principalmente nei controller REST per ricevere i dati di login
 * dall'utente e passarli al livello di servizio per l'autenticazione.
 */
@Component
public class LoginDto {
	
	@NotBlank(message = "il campo email non può essere vuoto, inserisci la tua email e riprova.")
	private String email; 
	@NotBlank(message = "il campo della password non può essere vuoto, inserisci la tua password e riprova.")
	private String password;
	
	public LoginDto(
			@NotBlank(message = "il campo email non può essere vuoto, inserisci la tua email e riprova.") String email,
			@NotBlank(message = "il campo della password non può essere vuoto, inserisci la tua password e riprova") String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public LoginDto() {
		super();
		
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
