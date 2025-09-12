package com.EasyTable.Exception;
/**
 * Exception UtenteNonTrovatoException
 * viene lanciata quando un utente non viene trovato
 */
public class UtenteNonTrovatoException extends RuntimeException  {

	 private static final long serialVersionUID = 1L; // Buona pratica aggiungerlo
	public UtenteNonTrovatoException(String message) {
		super(message);
		
	}

	
}
