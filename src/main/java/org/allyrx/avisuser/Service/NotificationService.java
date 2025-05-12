package org.allyrx.avisuser.Service;

import lombok.AllArgsConstructor;
import org.allyrx.avisuser.Entites.Validation;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class NotificationService {
    JavaMailSender mailSender;

    public void sendNotification(Validation validation) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@allyrx.com");
        message.setTo(validation.getUser().getEmail());
        message.setSubject("Code de confirmation");
        String texte = String.format("Votre code de confirmation est %s" , validation.getCode());
        message.setText(texte);
        mailSender.send(message);

    }
}
