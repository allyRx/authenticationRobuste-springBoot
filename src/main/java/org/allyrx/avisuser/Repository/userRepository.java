package org.allyrx.avisuser.Repository;

import org.allyrx.avisuser.Entites.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface userRepository extends CrudRepository<User , Long> {
   Optional <User> findByEmail(String email);
}
