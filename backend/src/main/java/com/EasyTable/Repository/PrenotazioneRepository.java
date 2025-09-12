package com.EasyTable.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EasyTable.Entity.Prenotazione;
import com.EasyTable.Entity.Utente;
/**
 * Repository Spring Data JPA per la gestione delle entità Prenotazione
 * Estende JpaRepository, fornendo le operazioni CRUD di base e metodi personalizzati per la ricerca e la gestione delle prenotazioni 
 * associate a un utente specifico.
 * Metodi personalizzati disponibili: 
 * findByIdAndUtente restituisce una prenotazione in base all'id dell'utente e in base all'utente associato alla quella prenotazione 
 * deleteByIdAndUtente elimina una prenotazione identificata da id e utente
 * findAllPrenotazioneByUtente restituisce la lista di prenotazioni associate all'utente
 * findAll restituisce tutte le prenotazioni presenti nel sistema
 */
@Repository
public interface PrenotazioneRepository extends JpaRepository <Prenotazione, Long> { 
	Optional <Prenotazione> findByIdAndUtente(Long idPrenotazione, Utente utente  ); 
	void deleteByIdAndUtente(Long idPrenotazione, Utente utente);
	List  <Prenotazione> findAllPrenotazioneByUtente(Utente utente);
	List <Prenotazione> findAll();

	

}
