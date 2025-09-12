package com.EasyTable.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EasyTable.Dto.LoginDto;
import com.EasyTable.Dto.RegisterDto;
import com.EasyTable.Entity.Utente;
import com.EasyTable.Response.EasyTableResponse;
import com.EasyTable.Service.UtenteService;
import com.EasyTable.mapper.RegisterMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

/**
 * Controller per la gestione dell'autenticazione e registrazione degli utenti 
 * Espone endpoint pubblici per registrazione, accesso e logout
 */
@RestController
@RequestMapping("/auth/")
public class AuthController {
	
	private final UtenteService utenteService;
	private final AuthenticationManager authenticationManager;
	
	
	public AuthController(UtenteService utenteService, AuthenticationManager authenticationManager) {
		super();
		this.utenteService = utenteService;
		this.authenticationManager = authenticationManager;
	}
	
	@Autowired
	RegisterMapper registerMapper;
	
	/**
	 * Registra un utente
	 * Tutti hanno accesso a questo endpoint
	 * @param registerDto sono i dati necessari per registrare un nuovo utente
	 * @return ResponseEntity contenente: 
	 * messaggio di conferma
	 *  stato Http
	 *  Utente salvato
	 *  @throws DataIntegrityViolationException se l'email è già presente nel database
	 *  @throws Exception per errori generici
	 */
	@PostMapping("/register")
	public ResponseEntity<EasyTableResponse> register(@RequestBody @Valid RegisterDto registerDto){ 
		Utente utenteRegistrato;
	try{
		utenteRegistrato = utenteService.registraUtente(registerDto);
		RegisterDto registerFiltro = registerMapper.toDto(utenteRegistrato);
		return ResponseEntity.ok().body(new EasyTableResponse("Utente registrato correttamente", HttpStatus.OK, registerFiltro));
	}catch(DataIntegrityViolationException error){ 
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new EasyTableResponse("Email già registrata", HttpStatus.CONFLICT, null));
	}catch(Exception errore) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EasyTableResponse("Errore durante la registrazione", HttpStatus.INTERNAL_SERVER_ERROR, null));
	}
	}
	/**
	 * Effettua il login dell'utente
	 * @param logindto i dati che serviranno per effettuare il login
	 * @param request request HTTP, utilizzata per creare la sessione
	 * @throws AuthenticationException se l'email è già presente nel database
	 * @throws AuthenticationException se le credenziali non sono valide
	 * @return ResponseEntity contenente: 
	 * messaggio di conferma
	 * stato Http
	 * email dell'utente che ha effettuato il login
	 */
	@PostMapping("/login")
	public ResponseEntity <EasyTableResponse> login(@RequestBody @Valid LoginDto logindto, HttpServletRequest request) {
		try {
			Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(logindto.getEmail(), logindto.getPassword())
	        );
			SecurityContextHolder.getContext().setAuthentication(authentication);
			HttpSession session = request.getSession(true);
	        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
	        return ResponseEntity.ok().body(new EasyTableResponse("Utente loggato correttamente", HttpStatus.OK, logindto.getEmail()));
		}catch(AuthenticationException errore) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new EasyTableResponse("Credenziali errate", HttpStatus.UNAUTHORIZED, null));
		}	
	}
	/**
	 * effettua il logout dell'utente invalidando la sessione corrente
	 * @param request request HTTP, utilizzata per recuperare e invalidare la sessione
	 * @return ResponseEntity contenente: 
	 * messaggio di conferma
	 * stato Http
	 */
	@PostMapping("/logout")
	public ResponseEntity <EasyTableResponse> logout(HttpServletRequest request){
		request.getSession().invalidate();
		return ResponseEntity.ok().body(new EasyTableResponse("Logout effettuato con successo", HttpStatus.OK, null));
	}
}


