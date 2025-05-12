package org.allyrx.avisuser.Entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity(name = "validation")
@Data @NoArgsConstructor @AllArgsConstructor
public class Validation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Instant createdAt;
    private Instant ExpireAt;
    private Instant activatedAt;
    private String code;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}
