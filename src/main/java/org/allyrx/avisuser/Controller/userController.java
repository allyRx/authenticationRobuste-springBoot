package org.allyrx.avisuser.Controller;

import lombok.AllArgsConstructor;
import org.allyrx.avisuser.Entites.User;
import org.allyrx.avisuser.Service.userService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
public class userController {

    private final userService userService;
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
}
