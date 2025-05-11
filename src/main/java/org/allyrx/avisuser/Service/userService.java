package org.allyrx.avisuser.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.allyrx.avisuser.Entites.Role;
import org.allyrx.avisuser.Entites.User;
import org.allyrx.avisuser.Enum.roleUser;
import org.allyrx.avisuser.Repository.userRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@AllArgsConstructor
public class userService {
    private final userRepository userRepository;

    //Injection du methode de decryptage
    private BCryptPasswordEncoder passwordEncoder;


    public void createUser(User user){

        //Verification d'email
        if(!user.getEmail().contains("@") || !user.getEmail().contains(".") ){
            throw new RuntimeException("Email not valid");
        }

        User findUser = userRepository.findByEmail(user.getEmail());
        if(findUser != null){
            throw new RuntimeException("User already exists");
        }

        //on met par defaut le role en utilisateur
        Role roleUser = new Role();
        roleUser.setLibelle(org.allyrx.avisuser.Enum.roleUser.UTILISATEUR);
        user.setRole(roleUser);

        String mapCrypt = passwordEncoder.encode(user.getPassword());
        user.setPassword(mapCrypt);
        userRepository.save(user);
    }

    public  void setAdmin(User user){
        Role userRole = new Role();
        userRole.setLibelle(roleUser.ADMINISTRATEUR);
        user.setRole(userRole);
        userRepository.save(user);
    }

}
