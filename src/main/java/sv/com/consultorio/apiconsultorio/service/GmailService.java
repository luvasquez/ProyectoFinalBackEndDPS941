package sv.com.consultorio.apiconsultorio.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GmailService {

	@Value("${spring.mail.username}")
	private String correoOrigen;
	
	@NonNull
	private final JavaMailSender javaMailSender;
	
	public void sendNewMail(String to, String subject, String body) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(correoOrigen);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		
		javaMailSender.send(message);
	}
	
}