package org.allyrx.avisuser.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.allyrx.avisuser.Entites.Avis;
import org.allyrx.avisuser.Repository.avisRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class avisService {

    private avisRepository avisRepository;

    public void addAvis(Avis avis){
        avisRepository.save(avis);
    }

    public List<Avis> getAvis(){
       return avisRepository.findAll();
    }
}
