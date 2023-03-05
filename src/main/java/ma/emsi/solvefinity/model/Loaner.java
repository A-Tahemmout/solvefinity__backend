package ma.emsi.solvefinity.model;

import jakarta.persistence.*;
import lombok.*;
import ma.emsi.solvefinity.model.emun.EmploymentLength;
import ma.emsi.solvefinity.model.emun.HomeOwnership;
import ma.emsi.solvefinity.model.emun.VerificationStatus;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "loaner")
public class Loaner extends Person {
    @Column(name = "employment_title")
    private String employmentTitle;

    @Column(name = "employment_length")
    @Enumerated(EnumType.ORDINAL)
    private EmploymentLength employmentLength;

    @Column(name = "annual_income")
    private Double annualIncome;

    @Column(name = "verification_status")
    @Enumerated(EnumType.ORDINAL)
    private VerificationStatus verificationStatus;

    @Column(name = "home_ownership")
    @Enumerated(EnumType.ORDINAL)
    private HomeOwnership homeOwnership;

    @OneToMany(mappedBy = "loaner")
    private List<Loan> loans;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @Builder
    public Loaner(String cin, String firstName, String lastName, String avatar, String email, String phone, String address, String city, String zipCode, String country, LocalDate birthDate, String employmentTitle, EmploymentLength employmentLength, Double annualIncome, VerificationStatus verificationStatus, HomeOwnership homeOwnership, List<Loan> loans, Bank bank) {
        super(cin, firstName, lastName, avatar, email, phone, address, city, zipCode, country, birthDate);
        this.employmentTitle = employmentTitle;
        this.employmentLength = employmentLength;
        this.annualIncome = annualIncome;
        this.verificationStatus = verificationStatus;
        this.homeOwnership = homeOwnership;
        this.loans = loans;
        this.bank = bank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Loaner loaner = (Loaner) o;
        return getId() != null && Objects.equals(getId(), loaner.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
