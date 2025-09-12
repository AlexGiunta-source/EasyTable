package com.EasyTable.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.EasyTable.Entity.Utente;
import com.EasyTable.Repository.UtenteRepository;

/**
 * Implementazione di UserDetailsServiceImpl per la gestione dell'autenticazione degli utenti in EasyTable 
 * questa classe carica i dettagli degli utenti dal database tramite UtenteRepository e costruisce un oggetto 
 * con username, password e ruoli
 * se l'utente non viene trovato in base all'email fornita, viene lanciata UsernameNotFoundException
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UtenteRepository utenteRepository; 

	public UserDetailsServiceImpl(UtenteRepository utenteRepository) {
		super();
		this.utenteRepository = utenteRepository; 
	}
	
	
	public  UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { 
		
		
		
		Optional <Utente> utente = utenteRepository.findByEmail(email); 
		if(utente.isEmpty()) { 
			throw new UsernameNotFoundException("Utente non trovato");
		}
		
		Utente utenti = utente.get(); 
		return User.builder()
				.username(utenti.getEmail())
				.password(utenti.getPassword())
				.authorities(List.of(new SimpleGrantedAuthority(utenti.getRuolo()))) 
				.build();

}
}
