package ma.emsi.solvefinity.model;

import jakarta.persistence.*;
import lombok.*;
import ma.emsi.solvefinity.model.emun.LoanGrade;
import ma.emsi.solvefinity.model.emun.LoanPurpose;
import ma.emsi.solvefinity.model.emun.LoanStatus;
import ma.emsi.solvefinity.model.emun.LoanTerm;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "loan")
public class Loan extends BaseEntity {
    @Column(name = "amount")
    private Double amount;

    @Column(name = "term")
    @Enumerated(EnumType.ORDINAL)
    private LoanTerm term;

    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "installment")
    private Double installment;

    @Column(name = "grade")
    @Enumerated(EnumType.ORDINAL)
    private LoanGrade grade;

    @Column(name = "purpose")
    @Enumerated(EnumType.ORDINAL)
    private LoanPurpose purpose;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private LoanStatus status;

    @ManyToOne
    @JoinColumn(name = "loaner_id")
    private Loaner loaner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Loan loan = (Loan) o;
        return getId() != null && Objects.equals(getId(), loan.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
