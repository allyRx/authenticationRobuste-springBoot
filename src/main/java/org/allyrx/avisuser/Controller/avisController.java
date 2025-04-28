package org.allyrx.avisuser.Controller;

import lombok.AllArgsConstructor;
import org.allyrx.avisuser.Entites.Avis;
import org.allyrx.avisuser.Service.avisService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("avis") @AllArgsConstructor
public class avisController {

    private avisService avisService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addAvis(@RequestBody Avis avis){
        avisService.addAvis(avis);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Avis> getAvis(){
        return avisService.getAvis();
    }
}
