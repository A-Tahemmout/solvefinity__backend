package ma.emsi.solvefinity.repository;

import ma.emsi.solvefinity.model.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BankRepository extends JpaRepository<Bank, UUID> {
    @Query("SELECT b.name, b.slug FROM Bank b ORDER BY b.name ASC")
    List<Object[]> findAllNameAndSlug();

    List<Bank> findByNameContainsOrDescriptionContains(String name, String description);

    Bank findBySlug(String slug);


    Page<Bank> findAll(Pageable pageable);

    Page<Bank> findByNameContainsOrDescriptionContains(String name, String description, Pageable pageable);
}
