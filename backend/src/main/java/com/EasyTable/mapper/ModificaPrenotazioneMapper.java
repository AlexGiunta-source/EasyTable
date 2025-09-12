package com.EasyTable.mapper;
/**
 * Mapper manuale per la gestione della conversione tra entità Prenotazione e ModificaPrenotazioneDto.
 * Viene usato quando un utente vuole modificare una prenotazione e si occupa di: 
 * Convertire ModificaPrenotazioneDto in un'entità 
 * Oppure convertire l'entità Prenotazione in un ModificaPrenotazioneDto
 */
import com.EasyTable.Dto.ModificaPrenotazioneDto;
import com.EasyTable.Entity.Prenotazione;

public class ModificaPrenotazioneMapper {

	public ModificaPrenotazioneDto toDto(Prenotazione prenotazione) {
		
		ModificaPrenotazioneDto modificaPrenotazioneDto = new ModificaPrenotazioneDto();
		modificaPrenotazioneDto.setId(prenotazione.getId());
		modificaPrenotazioneDto.setNumeroPersone(prenotazione.getNumeroPersone());
		modificaPrenotazioneDto.setPrenotazioneGiornoOra(prenotazione.getPrenotazioneGiornoOra());
		return modificaPrenotazioneDto;
	}
	
	public Prenotazione fromDto(ModificaPrenotazioneDto modificaPrenotazioneDto) {
		
		Prenotazione prenotazione = new Prenotazione();
		prenotazione.setId(modificaPrenotazioneDto.getId());
		prenotazione.setNumeroPersone(modificaPrenotazioneDto.getNumeroPersone());
		prenotazione.setPrenotazioneGiornoOra(modificaPrenotazioneDto.getPrenotazioneGiornoOra());
		return prenotazione; 
	}
}
