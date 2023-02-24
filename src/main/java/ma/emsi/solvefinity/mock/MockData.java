package ma.emsi.solvefinity.mock;

import lombok.AllArgsConstructor;
import ma.emsi.solvefinity.model.*;
import ma.emsi.solvefinity.model.emun.*;
import ma.emsi.solvefinity.repository.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class MockData {
    private final BankRepository bankRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final LoanerRepository loanerRepository;
    private final LoanRepository loanRepository;

    public void init() {
        banks();
        roles();
        users();
        loaners();
        loans();
    }

    public void destroy() {
        bankRepository.deleteAll();
        roleRepository.deleteAll();
        userRepository.deleteAll();
        loanerRepository.deleteAll();
        loanRepository.deleteAll();
    }

    public void banks() {
        // Solvefinity
        var solvefinity = Bank.builder()
                .name("Solvefinity")
                .description("Solvefinity is a fintech company")
                .slug("solvefinity")
                .logo("1674241415205_solvefinity.png")
                .address("Rue 1, Rabat, Maroc")
                .phone("+212 6 00 00 00 00")
                .email("noreply@solvefinity.ma")
                .build();

        // Banque Populaire
        var banquePopulaire = Bank.builder()
                .name("Banque Populaire")
                .description("Bank Populaire is a bank")
                .slug("banque-populaire")
                .logo("1674129407634_bp.jpg")
                .address("Rue 2, Rabat, Maroc")
                .phone("+212 6 00 00 00 01")
                .email("noreply@bpnet.ma")
                .build();

        // CIH banque
        var cih = Bank.builder()
                .name("CIH Bank")
                .description("CIH Bank is a bank")
                .slug("cih-bank")
                .logo("1674273073754_cih-bank.png")
                .address("Rue 3, Rabat, Maroc")
                .phone("+212 6 00 00 00 02")
                .email("noreply@cih.ma")
                .build();

        // BMCE banque
        var bmce = Bank.builder()
                .name("BMCE Bank")
                .description("BMCE Bank is a bank")
                .slug("bmce")
                .logo("bank-icon.png")
                .address("Rue 4, Rabat, Maroc")
                .phone("+212 6 00 00 00 03")
                .email("noreply@bmce.ma")
                .build();

        // Banque Centrale Populaire
        var bcp = Bank.builder()
                .name("Banque Centrale Populaire")
                .description("Banque Centrale Populaire is a bank")
                .slug("bcp")
                .logo("bank-icon.png")
                .address("Rue 5, Rabat, Maroc")
                .phone("+212 6 00 00 00 04")
                .email("noreply@bcp.ma")
                .build();

        // Add banks to database
        bankRepository.saveAll(List.of(solvefinity, banquePopulaire, cih, bmce, bcp));
    }

    private void roles() {
        // Create roles
        Role appAdminRole = new Role("APP_ADMIN", "Application Admin");
        Role bankAdminRole = new Role("BANK_ADMIN", "Bank Admin");
        Role agentRole = new Role("BANK_AGENT", "Bank Agent");

        // Save roles in database
        roleRepository.saveAll(List.of(appAdminRole, bankAdminRole, agentRole));
    }

    private void users() {
        // App admin user
        User appAdmin = User.builder()
                .firstName("Admin")
                .lastName("Solvefinity")
                .cin("A000000")
                .avatar("user-icon.png")
                .email("root@solvefinity.ma")
                .phone("+212 6 00 00 00 00")
                .address("Rue 1, Rabat, Maroc")
                .username("root@solvefinity.ma")
                .password(new BCryptPasswordEncoder().encode("root"))
                .roles(Arrays.asList(roleRepository.findByName("APP_ADMIN")))
                .bank(bankRepository.findBySlug("solvefinity"))
                .build();

        // Bank admin user
        User bankAdmin = User.builder()
                .firstName("Bank")
                .lastName("Admin")
                .cin("A000001")
                .avatar("user-icon.png")
                .email("bank@solvefinity.ma")
                .phone("+212 6 00 00 00 01")
                .address("Rue 2, Rabat, Maroc")
                .username("bank@solvefinity.ma")
                .password(new BCryptPasswordEncoder().encode("bank"))
                .roles(Arrays.asList(roleRepository.findByName("BANK_ADMIN")))
                .bank(bankRepository.findBySlug("solvefinity"))
                .build();

        // Bank agent user
        User bankAgent = User.builder()
                .firstName("User")
                .lastName("Solvefinity")
                .cin("U000000")
                .avatar("user-icon.png")
                .email("agent@solvefinity.ma")
                .phone("+212 6 00 00 00 02")
                .address("Rue 3, Rabat, Maroc")
                .username("agent@solvefinity.ma")
                .password(new BCryptPasswordEncoder().encode("agent"))
                .roles(Arrays.asList(roleRepository.findByName("BANK_AGENT")))
                .bank(bankRepository.findBySlug("solvefinity"))
                .build();

        // Save users in database
        userRepository.saveAll(List.of(appAdmin, bankAdmin, bankAgent));
    }

    private void loaners() {
        // Loaner 1
        Loaner loaner1 = Loaner.builder()
                .cin("L000001")
                .firstName("Loaner")
                .lastName("Solvefinity")
                .avatar("loaner-icon.png")
                .email("loaner1@solevefinity.ma")
                .phone("+212 6 00 00 00 03")
                .address("Rue 4, Rabat, Maroc")
                .employmentTitle("Software Engineer")
                .employmentLength(EmploymentLength.LESS_THAN_ONE_YEAR)
                .annualIncome(10000.0)
                .verificationStatus(VerificationStatus.VERIFIED)
                .homeOwnership(HomeOwnership.RENT)
                .bank(bankRepository.findBySlug("solvefinity"))
                .build();

        // Loaner 2
        Loaner loaner2 = Loaner.builder()
                .cin("L000002")
                .firstName("Loaner")
                .lastName("Solvefinity")
                .avatar("loaner-icon.png")
                .email("loaner2@solevefinity.ma")
                .phone("+212 6 00 00 00 04")
                .address("Rue 5, Rabat, Maroc")
                .employmentTitle("Software Engineer")
                .employmentLength(EmploymentLength.TWO_YEARS)
                .annualIncome(10000.0)
                .verificationStatus(VerificationStatus.SOURCE_VERIFIED)
                .homeOwnership(HomeOwnership.RENT)
                .bank(bankRepository.findBySlug("solvefinity"))
                .build();

        // Loaner 3
        Loaner loaner3 = Loaner.builder()
                .cin("L000003")
                .firstName("Loaner")
                .lastName("Solvefinity")
                .avatar("loaner-icon.png")
                .email("loaner3@solevefinity.ma")
                .phone("+212 6 00 00 00 05")
                .address("Rue 6, Rabat, Maroc")
                .employmentTitle("Software Engineer")
                .employmentLength(EmploymentLength.TWO_YEARS)
                .annualIncome(10000.0)
                .verificationStatus(VerificationStatus.NOT_VERIFIED)
                .homeOwnership(HomeOwnership.RENT)
                .bank(bankRepository.findBySlug("solvefinity"))
                .build();

        // Loaner 4
        Loaner loaner4 = Loaner.builder()
                .cin("L000004")
                .firstName("Loaner")
                .lastName("Solvefinity")
                .avatar("loaner-icon.png")
                .email("loaner4@solevefinity.ma")
                .phone("+212 6 00 00 00 06")
                .address("Rue 7, Rabat, Maroc")
                .employmentTitle("Software Engineer")
                .employmentLength(EmploymentLength.EIGHT_YEARS)
                .annualIncome(10000.0)
                .verificationStatus(VerificationStatus.VERIFIED)
                .homeOwnership(HomeOwnership.RENT)
                .bank(bankRepository.findBySlug("solvefinity"))
                .build();

        // Loaner 5
        Loaner loaner5 = Loaner.builder()
                .cin("L000005")
                .firstName("Loaner")
                .lastName("Solvefinity")
                .avatar("loaner-icon.png")
                .email("loaner5@solevefinity.ma")
                .phone("+212 6 00 00 00 07")
                .address("Rue 8, Rabat, Maroc")
                .employmentTitle("Software Engineer")
                .employmentLength(EmploymentLength.MORE_THAN_TEN_YEARS)
                .annualIncome(10000.0)
                .verificationStatus(VerificationStatus.NOT_VERIFIED)
                .homeOwnership(HomeOwnership.RENT)
                .bank(bankRepository.findBySlug("solvefinity"))
                .build();

        // Save loaners in database
        loanerRepository.saveAll(List.of(loaner1, loaner2, loaner3, loaner4, loaner5));
    }

    private void loans() {
        // Loan 1
        Loan loan1 = Loan.builder()
                .loaner(loanerRepository.findByCin("L000001").orElse(null))
                .amount(10000.0)
                .term(LoanTerm.THIRTY_SIX)
                .interestRate(0.05)
                .installment(10000.0 / 12)
                .grade(LoanGrade.A)
                .purpose(LoanPurpose.HOME_IMPROVEMENT)
                .note("This is a test loan")
                .status(LoanStatus.PENDING)
                .build();

        // Loan 2
        Loan loan2 = Loan.builder()
                .loaner(loanerRepository.findByCin("L000002").orElse(null))
                .amount(10000.0)
                .term(LoanTerm.SIXTY)
                .interestRate(0.05)
                .installment(10000.0 / 12)
                .grade(LoanGrade.A)
                .purpose(LoanPurpose.HOME_IMPROVEMENT)
                .note("This is a test loan")
                .status(LoanStatus.PENDING)
                .build();

        // Loan 3
        Loan loan3 = Loan.builder()
                .loaner(loanerRepository.findByCin("L000003").orElse(null))
                .amount(10000.0)
                .term(LoanTerm.THIRTY_SIX)
                .interestRate(0.05)
                .installment(10000.0 / 12)
                .grade(LoanGrade.A)
                .purpose(LoanPurpose.HOUSE)
                .note("This is a test loan")
                .status(LoanStatus.PENDING)
                .build();

        // Loan 4
        Loan loan4 = Loan.builder()
                .loaner(loanerRepository.findByCin("L000004").orElse(null))
                .amount(10000.0)
                .term(LoanTerm.SIXTY)
                .interestRate(0.05)
                .installment(10000.0 / 12)
                .grade(LoanGrade.A)
                .purpose(LoanPurpose.WEDDING)
                .note("This is a test loan")
                .status(LoanStatus.PENDING)
                .build();

        // Loan 5
        Loan loan5 = Loan.builder()
                .loaner(loanerRepository.findByCin("L000005").orElse(null))
                .amount(10000.0)
                .term(LoanTerm.THIRTY_SIX)
                .interestRate(0.05)
                .installment(10000.0 / 12)
                .grade(LoanGrade.A)
                .purpose(LoanPurpose.DEBT_CONSOLIDATION)
                .note("This is a test loan")
                .status(LoanStatus.PENDING)
                .build();

        // Save loans in database
        loanRepository.saveAll(List.of(loan1, loan2, loan3, loan4, loan5));
    }
}
