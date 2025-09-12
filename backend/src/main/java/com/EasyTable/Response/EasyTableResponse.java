package com.EasyTable.Response;

import java.util.Map;

import org.springframework.http.HttpStatus;
/**
 * Oggetto di risposta generico utilizzato da EasyTable per restituire dati al client
 * contiene: 
 * message un messaggio descrittivo dell'esito dell'operazione 
 * HttpStatus lo status associato alla risposta
 * data il contenuto della risposta
 * errors mappatura degli errori
 * può essere utilizzata sia per risposte di successo che di errore
 */
public class EasyTableResponse {

	private String message; 
	private HttpStatus statusCode; 
	private Object data; 
	private Map <String, String> errors;
	
	public EasyTableResponse() {
		super();
		
	}
	
	// Risposta in caso di errore

	public EasyTableResponse(String message, HttpStatus statusCode, Object data, Map<String, String> errors) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.data = data;
		this.errors = errors;
	}
	
	// Risposta in caso di successo

	public EasyTableResponse(String message, HttpStatus statusCode, Object data) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	} 
	
}
