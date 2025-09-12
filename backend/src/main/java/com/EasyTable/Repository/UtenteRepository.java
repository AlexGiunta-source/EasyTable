package com.EasyTable.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EasyTable.Entity.Utente;
/**
 * Repository Spring Data JPA per la gestione delle entità Utente
 * Estende JpaRepository, fornendo le operazioni CRUD di base e metodi personalizzati per la ricerca di un utente tramite email
 * metodi personalizzati disponibili: 
 * findByEmail restituisce un utente a partire dalla sua email
 */
@Repository
public interface UtenteRepository extends JpaRepository <Utente, Long> {

	Optional <Utente> findByEmail(String email); 
	
	
}
