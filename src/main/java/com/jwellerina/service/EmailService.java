package com.jwellerina.service;

import java.net.PasswordAuthentication;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

	public boolean sendMail(String to, String subject, String message) {
		
		boolean status = false;
		String from ="jwellerina@gmail.com";
		String host="smtp.gmail.com";
		
		Properties properties = System.getProperties();
		System.out.println(properties);
		
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");
		
		//String[] emailList = to.split(",");	
		Session session = Session.getInstance(properties, new Authenticator() {
			
			@Override
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication("jwellerina@gmail.com", "edqenvramqdugrnx");
			}
		});
		
		session.setDebug(true);
		
		MimeMessage m = new MimeMessage(session);
		
		try {
			m.setFrom(from);
			
//			for(String email: emailList) {
//				m.addRecipient(Message.RecipientType.TO, InternetAddress(email));
//			}
			m.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			m.setSubject(subject);
			m.setText(message);
			
			Transport.send(m);
			status = true;
			System.out.println("Sent success");
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return status;
	}
}
