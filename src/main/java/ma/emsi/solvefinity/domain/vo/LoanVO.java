package ma.emsi.solvefinity.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanVO {
    private UUID id;
    private Long version;

    private Double amount;
    private Integer term;
    private Double interestRate;
    private Double installment;
    private Integer grade;
    private Integer purpose;
    private String note;
    private Integer status;

    private LoanerVO loaner;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
