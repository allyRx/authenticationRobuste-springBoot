package org.allyrx.avisuser.Entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "avis")
public class Avis {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String Status;
}
