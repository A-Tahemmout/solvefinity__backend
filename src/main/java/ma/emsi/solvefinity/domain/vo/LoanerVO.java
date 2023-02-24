package ma.emsi.solvefinity.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanerVO {
    private UUID id;
    private Long version;

    private String cin;
    private String firstName;
    private String lastName;
    private String avatar;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String zipCode;
    private String country;
    private LocalDate birthDate;

    private String employmentTitle;
    private Integer employmentLength;
    private Double annualIncome;
    private Integer verificationStatus;
    private Integer homeOwnership;

    private List<LoanVO> loans;

    private BankVO bank;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
