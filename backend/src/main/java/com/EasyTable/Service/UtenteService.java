package com.EasyTable.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.EasyTable.Dto.RegisterDto;
import com.EasyTable.Entity.Utente;
import com.EasyTable.Repository.UtenteRepository;
import com.EasyTable.mapper.RegisterMapper;

import jakarta.transaction.Transactional;
/**
 * Logica per gli endpoint presenti in AuthController
 */
@Service
public class UtenteService {
	
	private final UtenteRepository utenteRepository; 
	private final RegisterMapper registerMapper; 
	private final PasswordEncoder passwordEncoder;

	public UtenteService( UtenteRepository utenteRepository, RegisterMapper registerMapper,  PasswordEncoder passwordEncoder) {
		super();
		this.utenteRepository = utenteRepository;
		this.registerMapper = registerMapper;
		this.passwordEncoder = passwordEncoder; 
	}

	@Transactional
	public Utente registraUtente( RegisterDto registerDto) {
		
		if(utenteRepository.findByEmail(registerDto.getEmail()).isPresent()) {
			throw new RuntimeException("Email già regitrata");
		}
		
		Utente utente = registerMapper.fromDto(registerDto);
		utente.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		utente.setRuolo("ROLE_USER");
		return utenteRepository.save(utente);	
	}
}
