package org.allyrx.avisuser.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.allyrx.avisuser.Entites.Role;
import org.allyrx.avisuser.Entites.User;
import org.allyrx.avisuser.Entites.Validation;
import org.allyrx.avisuser.Enum.roleUser;
import org.allyrx.avisuser.Repository.userRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Service
@AllArgsConstructor
public class userService {
    private final userRepository userRepository;
    private validationService validationService;
    //Injection du methode de decryptage
    private BCryptPasswordEncoder passwordEncoder;


    public void createUser(User user){

        //Verification d'email
        if(!user.getEmail().contains("@") || !user.getEmail().contains(".") ){
            throw new RuntimeException("Email not valid");
        }

       Optional <User> findUser = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));
        if(findUser.isPresent()){
            throw new RuntimeException("User already exists");
        }

        //on met par defaut le role en utilisateur
        Role roleUser = new Role();
        roleUser.setLibelle(org.allyrx.avisuser.Enum.roleUser.UTILISATEUR);
        user.setRole(roleUser);

        //Cryptage du password
        String mapCrypt = passwordEncoder.encode(user.getPassword());
        user.setPassword(mapCrypt);
        userRepository.save(user);

        //apres l'enregistrement , on envoie au validation service
        validationService.createValidation(user);
    }

    public  void setAdmin(User user){
        Role userRole = new Role();
        userRole.setLibelle(roleUser.ADMINISTRATEUR);
        user.setRole(userRole);
        userRepository.save(user);
    }

    public void activateUser(Map<String , String> activation){
       Validation validation = validationService.verifyCode(activation.get("code"));

       //verification de l'expiration et l'activation
        if(Instant.now().isAfter(validation.getExpireAt())){
            log.info("code  expired");
            throw new RuntimeException("code  expired");
        }

        //recuperation de l'utilisateur
        User userFound = userRepository.findById(validation.getUser().getId()).orElseThrow(()->new RuntimeException("user not found"));

        //changer en true l'enabled de l'user
        userFound.setEnabled(true);
        userRepository.save(userFound);
    }

}
