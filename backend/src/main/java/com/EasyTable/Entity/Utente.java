package com.EasyTable.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
/**
 * Entità dell'utente contenente: 
 * -id identificativo dell'utente
 * -nome dell'utente 
 * -cognome dell'utente
 * -Email utilizzata per accedere a EasyTable
 * -password dell'account
 * -Ruolo per accedere a determinati endpoint
 * Ogni utente può avere più prenotazioni associate tramite la relazione
 * 
 */
@Entity
@Table(name = "utente")
public class Utente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; 
	@Column(nullable = false, name = "nome")
	private String nome; 
	@Column(nullable = false, name = "cognome")
	private String cognome; 
	@Column(unique = true , nullable = false , name = "email")
	private String email; 
	@Column (nullable = false, name = "password")
	private String password; 
	@Column(name = "role")
	private String ruolo;
	
	@OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, orphanRemoval = true)
	private List <Prenotazione> prenotazioni = new ArrayList <>();
	
	public List<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}

	public void setPrenotazioni(List<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}

	public Utente() {
		super();
		
	}

	public Utente(Long id, String nome, String cognome, String email, String password, String ruolo) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.password = password;
		this.ruolo = ruolo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	
}
