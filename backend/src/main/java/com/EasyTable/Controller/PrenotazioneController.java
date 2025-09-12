package com.EasyTable.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EasyTable.Dto.ModificaPrenotazioneDto;
import com.EasyTable.Dto.PrenotazioneDto;
import com.EasyTable.Entity.Prenotazione;
import com.EasyTable.Exception.PrenotazioneNonTrovataException;
import com.EasyTable.Exception.UtenteNonTrovatoException;
import com.EasyTable.Response.EasyTableResponse;
import com.EasyTable.Service.PrenotazioneService;
import com.EasyTable.Service.UtenteService;
import com.EasyTable.mapper.PrenotazioneMapper;

import jakarta.validation.Valid;
/**
 * Controller REST che gestisce le operazioni CRUD sulle prenotazioni.
 * Espone end point per l'utente che vuole: 
 * creare, eliminare, modificare e mostrare tutte le prenotazioni 
 * Espone end point per l'admin che vuole: 
 * modificare una prenotazione, eliminare una prenotazione e mostrare tutte le prenotazioni a livello globale
 */
@RestController
@RequestMapping("/api/")
public class PrenotazioneController {

	@Autowired 
	UtenteService utenteService;
	@Autowired
	PrenotazioneService prenotazioneService; 
	@Autowired
	PrenotazioneMapper prenotazioneMapper;
	
	
	/**
	 * Salva la prenotazione di un utente, in base all'utente loggato 
	 * Per essere usato richiede che l'utente abbia 'ROLE_USER'
	 * @param prenotazioneDto contiene i dati della prenotazione da salvare
	 * @param userDetails contiene i dati di sicurezza riguardante l'utente
	 * @return ResponseEntity contenente: 
	 * Il messaggio di confera
	 * stato Http
	 * la prenotazione in caso di successo
	 * @throws UtenteNonTrovatoException se l'utente autenticato non viene trovato
	 * @throws Exception per eventuali errori generici
	 */
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@PostMapping("/salvaPrenotazione")
	public ResponseEntity <EasyTableResponse> creaPrenotazione(@RequestBody @Valid PrenotazioneDto prenotazioneDto, @AuthenticationPrincipal UserDetails userDetails){
		try {
			Prenotazione prenotazione = prenotazioneService.savePrenotazionePerUtente(prenotazioneDto, userDetails.getUsername());
			PrenotazioneDto responseDto = prenotazioneMapper.toDto(prenotazione);
			return ResponseEntity.ok().body(new EasyTableResponse("Salvataggio prenotazione effettuato correttamente",HttpStatus.OK, responseDto));
		}catch(UtenteNonTrovatoException  erroreUtente) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EasyTableResponse(erroreUtente.getMessage(),HttpStatus.NOT_FOUND, null ));
		}catch(Exception erroreGenerico) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EasyTableResponse("Errore imprevisto", HttpStatus.INTERNAL_SERVER_ERROR, null));
		}
	}
	
	/**
	 * Elimina una prenotazione esistente in base al suo id
	 * per essere usato richiede che l'utente abbia  il ruolo "ROLE_USER"
	 * @param id l'id della prenotazione esistente
	 * @param userDetails contiene i dati di sicurezza riguardante l'utente
	 * @return 
	 * @throws UtenteNonTrovatoException se l'utente autenticato non viene trovato
	 * @throws PrenotazioneNonTrovataException quando la prenotazione non viene trovata
	 * @throws Exception per eventuali errori generici
	 */
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@DeleteMapping("/eliminaPrenotazioneUtente/{id}")
	public ResponseEntity <EasyTableResponse> eliminaPrenotazioneUtente( @PathVariable Long id ,  @AuthenticationPrincipal UserDetails userDetails){
		try {
			prenotazioneService.eliminaPrenotazionePerUtente(id, userDetails.getUsername());
			return ResponseEntity.ok().body(new EasyTableResponse("Prenotazione eliminata correttamente",HttpStatus.OK, null));
		}catch(UtenteNonTrovatoException erroreUtente) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EasyTableResponse(erroreUtente.getMessage(),HttpStatus.NOT_FOUND, null ));
		}catch(PrenotazioneNonTrovataException errorePrenotazione){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EasyTableResponse(errorePrenotazione.getMessage(),HttpStatus.NOT_FOUND, null ));
		}catch(Exception erroreGenerico) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EasyTableResponse("Errore imprevisto", HttpStatus.INTERNAL_SERVER_ERROR, null));
		}
	}
	/**
	 * Modifica una prenotazione già esistente in base all'id
	 * per essere usato richiede che l'utente abbia  il ruolo "ROLE_USER"
	 * @param userDetails
	 * @param id della prenotazione esistente
	 * @param modificaPrenotazioneDto
	 * @return ResponseEntity contenete: 
	 * Il messaggio di confera
	 * stato Http
	 * la prenotazione modificata in caso di successo
	 * @throws  UtenteNonTrovatoException se l'utente autenticato non viene trovato
	 * @throws  PrenotazioneNonTrovataException quando la prenotazione non viene trovata
	 * @throws  Exception per eventuali errori generici
	 *  
	 */
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@PutMapping("/modificaPrenotazione/{id}")
	public ResponseEntity <EasyTableResponse> modificaPrenotazioneUtente(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, @RequestBody @Valid ModificaPrenotazioneDto modificaPrenotazioneDto ){
		try {
			Prenotazione prenotazione = prenotazioneService.modificaPrenotazionePerUtente(userDetails.getUsername(), modificaPrenotazioneDto, id);
			PrenotazioneDto responseDto = prenotazioneMapper.toDto(prenotazione);
			return ResponseEntity.ok().body(new EasyTableResponse("Prenotazione modificata con successo!",HttpStatus.OK, responseDto));
		}catch(UtenteNonTrovatoException erroreUtente) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EasyTableResponse(erroreUtente.getMessage(),HttpStatus.NOT_FOUND, null ));
		}catch(PrenotazioneNonTrovataException errorePrenotazione) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EasyTableResponse(errorePrenotazione.getMessage(),HttpStatus.NOT_FOUND, null ));
		}catch(Exception erroreGenerico) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EasyTableResponse("Errore imprevisto", HttpStatus.INTERNAL_SERVER_ERROR, null));
		}
	}
	/**
	 * Mostra tutte le prenotazioni fatte dall'utente loggato
	 * per essere usato richiede che l'utente abbia  il ruolo "ROLE_USER"
	 * @param userDetails contiene i dati di sicurezza riguardante l'utente
	 * @return  ResponseEntity contenente:
	 * Il messaggio di conferma
	 * stato Http
	 * l'intera lista collegata all'utente
	 * @throws UtenteNonTrovatoException se l'utente autenticato non viene trovato
	 * @throws PrenotazioneNonTrovataException quando la prenotazione non viene trovata
	 * @throws Exception per eventuali errori generici
	 */
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping("/mostraListaPrenotazioniUtente")
	public ResponseEntity <EasyTableResponse> mostraListaPrenotazioniUtente(@AuthenticationPrincipal UserDetails userDetails){
		try {
			List <PrenotazioneDto> listaPrenotazioniUtenteDto = prenotazioneService.mostraTuttePrenotazioniPerUtente(userDetails.getUsername());
			return ResponseEntity.ok().body(new EasyTableResponse("Lista prenotazioni in base all'utente recuperata con successo",HttpStatus.OK,listaPrenotazioniUtenteDto ));
		}catch(UtenteNonTrovatoException erroreUtente) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EasyTableResponse(erroreUtente.getMessage(),HttpStatus.NOT_FOUND, null ));
		}catch(PrenotazioneNonTrovataException errorePrenotazione ) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EasyTableResponse(errorePrenotazione.getMessage(),HttpStatus.NOT_FOUND, null ));
		}catch(Exception erroreGenerico) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EasyTableResponse("Errore imprevisto", HttpStatus.INTERNAL_SERVER_ERROR, null));
		}
	}
	/**
	 * Mostra l'intera lista di prenotazioni GLOBALE collegata ad ogni utente
	 * per essere usato richiede che l'utente abbia  il ruolo "ROLE_ADMIN"
	 * @return ResponseEntity contenente: 
	 * il messaggio di conferma
	 * stato Http
	 * l'intera lista globale delle prenotazioni
	 * @throws PrenotazioneNonTrovataException quando la prenotazione non viene trovata
	 * @throws Exception per eventuali errori generici
	 */
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/mostraListaPrenotazioniAdmin")
	public ResponseEntity <EasyTableResponse> mostraListaPrenotazioniAdmin(){
		try {
			List <PrenotazioneDto> listaPrenotazioniAdminDto = prenotazioneService.mostraListaPrenotazioniAdmin();
			return ResponseEntity.ok().body(new EasyTableResponse("Lista prenotazioni in base all'admin recuperata con successo",HttpStatus.OK,listaPrenotazioniAdminDto ));
		}catch(PrenotazioneNonTrovataException errorePrenotazione) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EasyTableResponse(errorePrenotazione.getMessage(),HttpStatus.NOT_FOUND, null ));
		}catch(Exception erroreGenerico) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EasyTableResponse("Errore imprevisto", HttpStatus.INTERNAL_SERVER_ERROR, null));
		}
	}
	/**
	 * Elimina qualsiasi prenotazione in base all'id della prenotazione
	 * per essere usato richiede che l'utente abbia  il ruolo "ROLE_ADMIN"
	 * @param id della prenotazione
	 * @return
	 * @throws PrenotazioneNonTrovataException quando la prenotazione non viene trovata
	 * @throws Exception per eventuali errori generici
	 */
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/eliminaPrenotazioneAdmin/{id}")
	public ResponseEntity <EasyTableResponse> eliminaPrenotazioneAdmin(@PathVariable Long id){
		try {
			prenotazioneService.eliminaPrenotazioneAdmin(id);
			return ResponseEntity.ok().body(new EasyTableResponse("Prenotazione eliminata correttamente.",HttpStatus.OK, null ));
		}catch(PrenotazioneNonTrovataException errorePrenotazione) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EasyTableResponse(errorePrenotazione.getMessage(),HttpStatus.NOT_FOUND, null ));
		}catch(Exception erroreGenerico) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EasyTableResponse("Errore imprevisto", HttpStatus.INTERNAL_SERVER_ERROR, null));
		}
	}
	/**
	 * Modifica qualsiasi prenotazione in base all'id della prenotazione
	 * per essere usato richiede che l'utente abbia  il ruolo "ROLE_ADMIN"
	 * @param id della prenotazione
	 * @param modificaPrenotazioneDto dati che sovrascriranno la prenotazione originale
	 * @return ResponseEntity contenente:
	 * messaggio di conferma
	 * stato Http 
	 * la prenotazione modificata
	 * @throws PrenotazioneNonTrovataException quando la prenotazione non viene trovata
	 * @throws Exception per eventuali errori generici
	 */
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/modificaPrenotazioneAdmin/{id}")
	public ResponseEntity <EasyTableResponse> modificaPrenotazioneAdmin(@PathVariable Long id, @RequestBody @Valid ModificaPrenotazioneDto modificaPrenotazioneDto){
		try{
			Prenotazione prenotazione = prenotazioneService.modificaPrenotazioneAdmin(id, modificaPrenotazioneDto);
			PrenotazioneDto responseDto = prenotazioneMapper.toDto(prenotazione);
			return ResponseEntity.ok().body(new EasyTableResponse("Prenotazione modifica correttamente", HttpStatus.OK, responseDto));
		}catch( PrenotazioneNonTrovataException errorePrenotazione) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EasyTableResponse(errorePrenotazione.getMessage(), HttpStatus.NOT_FOUND, null));
		}catch(Exception erroreGenerico) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EasyTableResponse("Errore imprevisto", HttpStatus.INTERNAL_SERVER_ERROR,null));
		}
	
}
}
