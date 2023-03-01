package ma.emsi.solvefinity.service;

import ma.emsi.solvefinity.domain.vo.LoanVO;

import java.util.UUID;

public interface LoanService extends BaseService<LoanVO, UUID> {
    boolean predict(LoanVO loanVO);
}
