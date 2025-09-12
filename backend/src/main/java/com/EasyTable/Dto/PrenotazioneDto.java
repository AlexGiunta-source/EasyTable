package com.EasyTable.Dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
/**
 * Data Transfer Object (DTO) utilizzato per rappresentare una prenotazione.
 * Contiene tutti i dati principali associati a una prenotazione
 */
@Component
public class PrenotazioneDto {
	
	
	private Long id;
	@NotBlank
	private String nome;
	@NotBlank
	private String cognome; 
	@NotBlank
	private String numeroTelefono;
	@Min( value = 1 , message = "deve essere almeno una persona, riprova")
	private Integer numeroPersone; 
	@NotNull(message = "Il campo data e ora non può essere vuoto, inserisci data e ora della tua prenotazione")
	@FutureOrPresent(message = "La data deve essere nel futuro")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime prenotazioneGiornoOra;
	
	
	public PrenotazioneDto( Long id,@NotBlank String nome, @NotBlank String cognome, @NotBlank String numeroTelefono,@NotBlank(message = "il campo numero persone non può essere vuoto, inserisci le persone della prenotazione") Integer numeroPersone,@NotBlank(message = "Il campo data e ora non può essere vuoto, inserisci data e ora della tua prenotazione") LocalDateTime prenotazioneGiornoOra) {
		super();
		this.nome = nome; 
		this.cognome = cognome;
		this.numeroTelefono = numeroTelefono;
		this.numeroPersone = numeroPersone;
		this.prenotazioneGiornoOra = prenotazioneGiornoOra;
		this.id = id; 
	}

	public PrenotazioneDto() {
		super();
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
