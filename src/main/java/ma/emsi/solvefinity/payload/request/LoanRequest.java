package ma.emsi.solvefinity.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.emsi.solvefinity.domain.vo.LoanVO;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequest {
    private LoanVO loan;
    private UUID loanerId;
}
