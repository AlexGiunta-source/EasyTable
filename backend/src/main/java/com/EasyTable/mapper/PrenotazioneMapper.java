package com.EasyTable.mapper;

import org.springframework.stereotype.Component;

import com.EasyTable.Dto.PrenotazioneDto;
import com.EasyTable.Entity.Prenotazione;
/**
 * Mapper manuale per la gestione della conversione tra Entità Prenotazione e PrentazioneDto.
 * viene usato quando un utente vuole creare una prenotazione e si occupa di: 
 * convertire PrenotazioneDto in un'entità  Prenotazione
 * convertire un'entità Prenotazione in un dto PrenotazioneDto
 */
@Component
public class PrenotazioneMapper {

	public PrenotazioneDto toDto(Prenotazione prenotazione) { 
		
		PrenotazioneDto prenotazioneDto = new PrenotazioneDto();
		
		prenotazioneDto.setId(prenotazione.getId());
		prenotazioneDto.setNome(prenotazione.getNome());
		prenotazioneDto.setCognome(prenotazione.getCognome());
		prenotazioneDto.setNumeroTelefono(prenotazione.getNumeroTelefono());
		prenotazioneDto.setNumeroPersone(prenotazione.getNumeroPersone());
		prenotazioneDto.setPrenotazioneGiornoOra(prenotazione.getPrenotazioneGiornoOra());
		
		return prenotazioneDto; 
	}
	
	public Prenotazione fromDto(PrenotazioneDto prenotazioneDto) { 
		
		Prenotazione prenotazione = new Prenotazione();
		
		prenotazione.setNome(prenotazioneDto.getNome());
		prenotazione.setCognome(prenotazioneDto.getCognome());
		prenotazione.setNumeroTelefono(prenotazioneDto.getNumeroTelefono());
		prenotazione.setPrenotazioneGiornoOra(prenotazioneDto.getPrenotazioneGiornoOra());
		prenotazione.setNumeroPersone(prenotazioneDto.getNumeroPersone());
		
		return prenotazione;
	}
	
		
		
		
}
