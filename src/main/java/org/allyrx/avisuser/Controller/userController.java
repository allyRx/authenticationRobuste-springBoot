package org.allyrx.avisuser.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.allyrx.avisuser.DTO.AuthenticationDto;
import org.allyrx.avisuser.Entites.User;
import org.allyrx.avisuser.Service.userService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Data
@RestController
@AllArgsConstructor
public class userController {

    private  userService userService;

    //Injection pour gerer l'authentification
    private AuthenticationManager authenticationManager;

    @PostMapping(path = "/inscription", consumes = APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @PostMapping("{id}")
    public  void setAdmin(@PathVariable("id") User user){
        userService.setAdmin(user);
    }


    //activation
    @PostMapping(path =  "/activation" , consumes = APPLICATION_JSON_VALUE)
    public void activateUser(@RequestBody Map<String,String> activation){
        userService.activateUser(activation);
    }

    //connextion
    @PostMapping(path = "login")
    public Map<String , String> login(@RequestBody AuthenticationDto authenticationDto){
       Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDto.email() , authenticationDto.password())
        );
       log.info("authenticated user: {}", authentication.isAuthenticated());
      return  null;
    }
}
