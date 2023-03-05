package ma.emsi.solvefinity.repository;

import ma.emsi.solvefinity.model.Loaner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoanerRepository extends JpaRepository<Loaner, UUID> {
    List<Loaner> findAllByBankSlug(String slug);

    Optional<Loaner> findByCin(String cin);

    Optional<Loaner> findByEmail(String email);
}