package org.allyrx.avisuser.Repository;

import org.allyrx.avisuser.Entites.Validation;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface validationRepository extends JpaRepository<Validation , Long> {
    Optional<Validation> findByCode(String code);
}
