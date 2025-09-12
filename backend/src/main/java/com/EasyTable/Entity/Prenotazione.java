package com.EasyTable.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
/**
 * Entità della prenotazione contenente:
 * id identificativo della prenotazione
 * -nome dell'utente che effettua la prenotazione 
 * -cognome dell'utente che effettua la prenotazione
 * -Il numero di telefono dell'utente che effettua la prenotazione 
 * -Il numero di persone della prenotazione 
 * -La data e l'ora della prenotazione
 * Ogni prenotazione è associata a un utente differente
 */
@Entity
public class Prenotazione {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	@Column(name = "nome", nullable = false)
	private String nome;
	@Column(name = "cognome", nullable = false)
	private String cognome;
	@Column(name = "numeroTelefono", nullable = false)
	private String numeroTelefono;
	@Column(name = "numeroPersone", nullable = false)
	private Integer numeroPersone; 
	@Column(name = "dataOraPrenotazione", nullable = false)
	private LocalDateTime prenotazioneGiornoOra; 

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "utente_id", nullable = false)
	private Utente utente; 
	
	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Prenotazione() {
		super();
		
	}

	public Prenotazione(Long id, String nome, String cognome, String numeroTelefono,  Integer numeroPersone, LocalDateTime prenotazioneGiornoOra) {
		super();
		this.id = id;
		this.nome = nome; 
		this.cognome = cognome;
		this.numeroTelefono = numeroTelefono;
		this.numeroPersone = numeroPersone;
		this.prenotazioneGiornoOra = prenotazioneGiornoOra;
		
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumeroPersone() {
		return numeroPersone;
	}

	public void setNumeroPersone(Integer numeroPersone) {
		this.numeroPersone = numeroPersone;
	}

	public LocalDateTime getPrenotazioneGiornoOra() {
		return prenotazioneGiornoOra;
	}

	public void setPrenotazioneGiornoOra(LocalDateTime prenotazioneGiornoOra) {
		this.prenotazioneGiornoOra = prenotazioneGiornoOra;
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

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}


	
}
