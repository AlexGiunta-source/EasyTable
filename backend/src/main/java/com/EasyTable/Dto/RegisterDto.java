package com.EasyTable.Dto;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotBlank;
/**
 * Data Transfer Object (DTO) utilizzato per la registrazione di un nuovo utente.
 * Contiene i dati obbligatori che l’utente deve fornire in fase di registrazione
 */
@Component
public class RegisterDto {
	
	@NotBlank(message = "il campo nome non può essere vuoto, inserisci il nome e riprova.")
	private String nome; 
	@NotBlank(message = "il campo cognome non può essere vuoto, inserisci il cognome e riprova.")
	private String cognome; 
	@NotBlank(message = "il campo email non può essere vuoto, inserisci l'email e riprova.")
	private String email; 
	@NotBlank(message = "il campo password non può essere vuoto, inserisci la password e riprova")
	private String password;
	
	public RegisterDto() {
		super();
		
	}

	public RegisterDto(
			@NotBlank(message = "il campo nome non può essere vuoto, inserisci il nome e riprova.") String nome,
			@NotBlank(message = "il campo cognome non può essere vuoto, inserisci il cognome e riprova.") String cognome,
			@NotBlank(message = "il campo email non può essere vuoto, inserisci l'email e riprova.") String email,
			@NotBlank(message = "il campo password non può essere vuoto, inserisci la password e riprova") String password) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.password = password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
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
