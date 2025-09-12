package com.EasyTable.Exception;

/**Exception PrenotazioneNonTrovataException
 * viene lanciata nel caso in cui non venga trovata una prenotazione
 */
public class PrenotazioneNonTrovataException extends RuntimeException {

	 private static final long serialVersionUID = 1L; // Buona pratica aggiungerlo
	public PrenotazioneNonTrovataException(String message) {
		super(message);
		
	}

	
}
