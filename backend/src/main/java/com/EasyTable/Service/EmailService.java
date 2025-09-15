package com.EasyTable.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
/**
 * Service per l'invio di email utilizzando JavaMailSender
 */
@Service
public class EmailService {

	private JavaMailSender javaMailSender;

	public EmailService(JavaMailSender javaMailSender) {
		super();
		this.javaMailSender = javaMailSender;
	} 
	
	public void inviaEmail(String to, String subject, String text) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		
		javaMailSender.send(message);
	}
	
}



	
