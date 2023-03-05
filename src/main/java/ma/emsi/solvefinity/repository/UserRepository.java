package ma.emsi.solvefinity.repository;

import ma.emsi.solvefinity.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByCin(String cin);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findByCinContainsOrFirstNameContainsOrLastNameContains(String cin, String firstName, String lastName);

    Page<User> findAll(Pageable pageable);

    Page<User> findByCinContainsOrFirstNameContainsOrLastNameContains(String cin, String firstName, String lastName, Pageable pageable);
}
