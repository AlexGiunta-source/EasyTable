package com.EasyTable.mapper;

import org.springframework.stereotype.Component;

import com.EasyTable.Dto.RegisterDto;
import com.EasyTable.Entity.Utente;
/**
 *Mapper manuale per la gestione della conversione tra Entità Utente e RegisterDto.
 *viene usato quando un utente vuole registrarsi e si occupa di: 
 *convertire RegisterDto in un'entità  Utente
 *convertire un'entità Utente in un dto RegisterDto
 */
@Component
public class RegisterMapper {

	public RegisterDto toDto(Utente utente) { // lettura "GET" 
		
		RegisterDto registerDto  = new RegisterDto();
		
		registerDto.setNome(utente.getNome());
		registerDto.setCognome(utente.getCognome());
		registerDto.setEmail(utente.getEmail());
		
		
		return registerDto;
		
		
	}
	
	public Utente fromDto(RegisterDto registerDto) { // salvataggio "POST"
		
		Utente utente = new Utente();
		
		utente.setNome(registerDto.getNome());
		utente.setCognome(registerDto.getCognome());
		utente.setEmail(registerDto.getEmail());
		utente.setPassword(registerDto.getPassword());
		
		return utente; 
	}
}
