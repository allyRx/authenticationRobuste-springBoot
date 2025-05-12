package org.allyrx.avisuser.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.allyrx.avisuser.Entites.User;
import org.allyrx.avisuser.Entites.Validation;
import org.allyrx.avisuser.Repository.validationRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Slf4j
@Service
@AllArgsConstructor
public class validationService {

    private validationRepository validationRepository;
    private NotificationService notificationService;

    public void createValidation(User user) {
        Validation validation = new Validation();

        validation.setUser(user);

        //initialisation du temps de creation et expiration
        Instant createdAt = Instant.now();
        Instant expiresAt = createdAt.plus(5, ChronoUnit.MINUTES);

        //initialisation du code
        Random random = new Random();
        int randomInt = random.nextInt(999999);

        String code = String.format("%06d", randomInt);

        //affectation du code et les autres a l'entite  validation
        validation.setCode(code);
        validation.setCreatedAt(createdAt);
        validation.setExpireAt(expiresAt);

        validationRepository.save(validation);

        notificationService.sendNotification(validation);

    }

    //appelle au methode findBycode  du repo pour recuperer les code de confirmation si non code introuvalble
    public Validation verifyCode(String code) {
         return validationRepository.findByCode(code).orElseThrow(()-> new RuntimeException("code not found"));
    }
}
