package ma.emsi.solvefinity.repository;

import ma.emsi.solvefinity.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {
}
