package com.EasyTable.Dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
/**
 * Data Transfer Object (DTO) utilizzato per la modifica di una prenotazione esistente.
 * Questo DTO viene utilizzato nei controller REST per ricevere i dati
 * di modifica di una prenotazione da parte di un utente autenticato o di un amministratore.
 */
public class ModificaPrenotazioneDto {

	
	private Long id;
	@Min( value = 1 , message = "deve essere almeno una persona, riprova")
	private Integer numeroPersone; 
	@NotNull(message = "Il campo data e ora non può essere vuoto, inserisci data e ora della tua prenotazione")
	@FutureOrPresent(message = "La data deve essere nel futuro")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime prenotazioneGiornoOra;
	
	public ModificaPrenotazioneDto() {
		super();
		
	}

	public ModificaPrenotazioneDto(@NotBlank Long id,
			@Min(value = 1, message = "deve essere almeno una persona, riprova") Integer numeroPersone,
			@NotNull(message = "Il campo data e ora non può essere vuoto, inserisci data e ora della tua prenotazione") @FutureOrPresent(message = "La data deve essere nel futuro") LocalDateTime prenotazioneGiornoOra) {
		super();
		this.id = id;
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
}
