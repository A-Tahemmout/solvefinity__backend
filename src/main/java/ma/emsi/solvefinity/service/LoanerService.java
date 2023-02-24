package ma.emsi.solvefinity.service;

import ma.emsi.solvefinity.domain.vo.LoanerVO;

import java.util.List;
import java.util.UUID;

public interface LoanerService extends BaseService<LoanerVO, UUID> {
    List<LoanerVO> getAllByBankSlug(String slug);

    LoanerVO getByCin(String cin);

    LoanerVO getByEmail(String email);
}
