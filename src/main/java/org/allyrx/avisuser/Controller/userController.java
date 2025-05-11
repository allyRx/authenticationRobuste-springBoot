package org.allyrx.avisuser.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.allyrx.avisuser.Entites.User;
import org.allyrx.avisuser.Service.userService;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class userController {

    private final userService userService;
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @PostMapping("{id}")
    public  void setAdmin(@PathVariable("id") User user){
        userService.setAdmin(user);
    }
}
