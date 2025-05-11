package org.allyrx.avisuser.Repository;

import org.allyrx.avisuser.Entites.User;
import org.springframework.data.repository.CrudRepository;

public interface userRepository extends CrudRepository<User , Long> {
    User findByEmail(String email);
}
