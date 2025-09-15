package com.EasyTable.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EasyTable.Dto.ModificaPrenotazioneDto;
import com.EasyTable.Dto.PrenotazioneDto;
import com.EasyTable.Entity.Prenotazione;
import com.EasyTable.Entity.Utente;
import com.EasyTable.Exception.PrenotazioneNonTrovataException;
import com.EasyTable.Exception.UtenteNonTrovatoException;
import com.EasyTable.Repository.PrenotazioneRepository;
import com.EasyTable.Repository.UtenteRepository;
import com.EasyTable.mapper.PrenotazioneMapper;

import jakarta.transaction.Transactional;

/**
 * Logica per gli endpoint presenti in PrenotazioneController
 */
@Service
public class PrenotazioneService {
	
	final private PrenotazioneMapper prenotazioneMapper; 
	final private EmailService emailService;
	
	
	public PrenotazioneService(PrenotazioneMapper prenotazioneMapper, EmailService emailService) {
		super();
		this.prenotazioneMapper = prenotazioneMapper;
		this.emailService = emailService;
	}

	@Autowired
	PrenotazioneRepository prenotazioneRepository;
	@Autowired
	UtenteRepository utenteRepository; 
	
	/**
	 * Salva la prenotazione in base all'utente loggato
	 * Dopo aver salvato la prenotazione, invia un'email di conferma all'utente
	 * @param prenotazioneDto contiene i dati della prenotazione da salvare
	 * @param email usata per cercare l'utente nel database
	 * @return salvataggio della prenotazione
	 * @throws UtenteNonTrovatoException se l'utente autenticato non viene trovato
	 */
	@Transactional
	public Prenotazione savePrenotazionePerUtente( PrenotazioneDto prenotazioneDto, String email) {
		Utente convalidaUtente = utenteRepository.findByEmail(email)
				.orElseThrow(() -> new UtenteNonTrovatoException("Utente non trovato, impossibile completare l'operazione."));
		Prenotazione nuovaPrenotazione = prenotazioneMapper.fromDto(prenotazioneDto);
		nuovaPrenotazione.setUtente(convalidaUtente);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime oraEGiornoPrenotazione = prenotazioneDto.getPrenotazioneGiornoOra();
		String  prenotazioneFormattata = oraEGiornoPrenotazione.format(formatter);
		emailService.inviaEmail(convalidaUtente.getEmail(), "Riepilogo prenotazione", "La tua prenotazione è stata fissata il giorno e alle ore:" + " " + prenotazioneFormattata);
		return prenotazioneRepository.save(nuovaPrenotazione);
		
	}
	/**
	 * Elimina le prenotazioni collegate all'utente loggato in quel momento.
	 * Cerca la prenotazione in base all'id, se esiste la elimina.
	 * Dopo aver eliminato la prenotazione, invia un'email di conferma all'utente.
	 * @param id l'id della prenotazione da eliminare
	 * @param email usata per cercare l'utente nel database
	 * @return 
	 * @throws UtenteNonTrovatoException se l'utente autenticato non viene trovato
	 */ 
	@Transactional
	public void eliminaPrenotazionePerUtente(Long id, String email) {
		
		Utente convalidaUtente = utenteRepository.findByEmail(email).orElseThrow(() -> new UtenteNonTrovatoException("Utente non trovato, impossibile completare l'operazione."));
		if(prenotazioneRepository.findById(id).isEmpty()) {
			throw new PrenotazioneNonTrovataException("Prenotazione non trovata,impossibile eseguire l'operazione.");
		}
		Prenotazione datiPrenotazione = prenotazioneRepository.findPrenotazioneByIdAndUtente(id, convalidaUtente);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime GiornoEOraPrenotazione = datiPrenotazione.getPrenotazioneGiornoOra();
		String giornoEoraPrenotazioneFormattata = GiornoEOraPrenotazione.format(formatter);
		prenotazioneRepository.deleteByIdAndUtente(id, convalidaUtente);
		emailService.inviaEmail(convalidaUtente.getEmail(), "Prenotazione con id:" + " " + id + "eliminata con successo", "La prenotazione del giorno e alle ore:"+  " " + giornoEoraPrenotazioneFormattata + " " + "è stata eliminata con successo" );
	}
	
	/**
	 * Modifica le prenotazioni collegate all'utente loggato in quel momento.
	 * Cerca la prenotazione, se esiste la modifica.
	 * @param modificaPrenotazioneDto i dati della prenotazione che andranno a sovrascrivere la prenotazione da modificare
	 * @param email usata per cercare l'utente nel database
	 * @param id della prenotazione da modificare
	 * @return 
	 * @throws UtenteNonTrovatoException se l'utente autenticato non viene trovato
	 * @throws PrenotazioneNonTrovataException se la prenotazione non viene trovata
	 */ 
	@Transactional
	public Prenotazione modificaPrenotazionePerUtente(String email, ModificaPrenotazioneDto modificaPrenotazioneDto, Long id) {
		Utente convalidaUtente = utenteRepository.findByEmail(email)
				.orElseThrow(() -> new UtenteNonTrovatoException("Utente non trovato, impossibile completare l'operazione."));
		Prenotazione prenotazione = prenotazioneRepository.findByIdAndUtente(id, convalidaUtente)
				.orElseThrow(() -> new PrenotazioneNonTrovataException("Prenotazione non trovata,impossibile eseguire l'operazione."));
		prenotazione.setPrenotazioneGiornoOra(modificaPrenotazioneDto.getPrenotazioneGiornoOra());
		prenotazione.setNumeroPersone(modificaPrenotazioneDto.getNumeroPersone());
		return prenotazione;	
	}
	/**
	 * Mostra tutte le prenotazioni in base all'utente loggato in quel momento
	 * @param email usata per cercare l'utente nel database
	 * @throws PrenotazioneNonTrovataException in caso la prenotazione non viene trovata
	 * @throws UtenteNonTrovatoException in caso l'utente non viene trovato
	 * @return listaPrenotazioniDto l'intera lista di prenotazioni collegate all'utente loggato
	 */
	@Transactional
	public List <PrenotazioneDto> mostraTuttePrenotazioniPerUtente(String email){
		Utente convalidaUtente = utenteRepository.findByEmail(email)
				.orElseThrow(()-> new UtenteNonTrovatoException("Utente non trovato, impossibile esegure l'operazione."));
		List <Prenotazione> listaPrenotazioni = prenotazioneRepository.findAllPrenotazioneByUtente(convalidaUtente);
		if(listaPrenotazioni.isEmpty()) {
			throw new PrenotazioneNonTrovataException("Nessuna prenotazione presente nel sistema, impossibile completare l'operazione.");
		}
		List <PrenotazioneDto> listaPrenotazioniDto = new ArrayList <>();
		for(Prenotazione prenotazione : listaPrenotazioni) {
			listaPrenotazioniDto.add(prenotazioneMapper.toDto(prenotazione));
		}
		return listaPrenotazioniDto;
	}
	/**
	 * Mostra l'intera lista di prenotazioni collegata ad ogni utente.
	 * Prima viene creata una lista(listaPrenotazioniDto), vengono aggiunte tutte le prenotazioni e ogni prenotazione viene convertita in dto
	 * Se non sono presenti prenotazioni, lancia errore, altrimenti mostra l'intera lista di prenotazioni
	 * @throws PrenotazioneNonTrovataException in caso la prenotazione non viene trovata
	 * @return listaPrenotazioniDto l'intera lista di prenotazioni 
	 */
	@Transactional
	public List <PrenotazioneDto> mostraListaPrenotazioniAdmin(){
		List <Prenotazione> listaPrenotazioni = prenotazioneRepository.findAll();
		if(listaPrenotazioni.isEmpty()) {
			throw new PrenotazioneNonTrovataException("Nessuna prenotazione presente nel sistema, impossibile completare l'operazione");
		}
		List <PrenotazioneDto> listaPrenotazioniDto = new ArrayList<>();
		for(Prenotazione prenotazione : listaPrenotazioni) {
			listaPrenotazioniDto.add(prenotazioneMapper.toDto(prenotazione));
		}
		return listaPrenotazioniDto;
	}
	
	/**
	 * Elimina qualsiasi prenotazione in base all'id della prenotazione
	 * Prima la prenotazione viene cercata per id, se non esiste, lancia errore, altrimenti elimina la prenotazione con quell'id
	 * @param id della prenotazione da eliminare
	 * @throws PrenotazioneNonTrovataException in caso la prenotazione non venga trovata 
	 */
	@Transactional
	public void eliminaPrenotazioneAdmin(Long id) {
		 Prenotazione prenotazioneElimina = prenotazioneRepository.findById(id).orElseThrow(() -> new PrenotazioneNonTrovataException("Prenotazione non trovata,impossibile eseguire l'operazione."));
		 prenotazioneRepository.delete(prenotazioneElimina);
	}
	/**
	 * Modifica qualsiasi prenotazione in base all'id
	 * Prima la prenotazione viene cercata, in caso non esiste lancia errore.In caso la prenotazione esista, vengono presi i parametri del dto e assegnati all'entità Prenotazione
	 * @param id della prenotazione da modificare
	 * @param modificaPrenotazioneDto i dati della prenotazione che andranno a sovrascrivere i parametri dell'entità
	 * @throws PrenotazioneNonTrovataException in caso la prenotazione non viene trovata
	 * @return prenotazione
	 */
	@Transactional
	public Prenotazione modificaPrenotazioneAdmin(Long id, ModificaPrenotazioneDto modificaPrenotazioneDto){
		
		Prenotazione prenotazione = prenotazioneRepository.findById(id).orElseThrow(()-> new PrenotazioneNonTrovataException("Prenotazione non trovata,impossibile eseguire l'operazione."));
		prenotazione.setNumeroPersone(modificaPrenotazioneDto.getNumeroPersone());
		prenotazione.setPrenotazioneGiornoOra(modificaPrenotazioneDto.getPrenotazioneGiornoOra());
		return prenotazione;
	}
}


	
