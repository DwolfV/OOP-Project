package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);

    List<User> findByRole(String role);

    Optional<User> findByUsername(String name);
}
