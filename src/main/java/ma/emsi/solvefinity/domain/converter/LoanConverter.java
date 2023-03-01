package ma.emsi.solvefinity.domain.converter;

import ma.emsi.solvefinity.domain.vo.LoanVO;
import ma.emsi.solvefinity.model.Loan;
import ma.emsi.solvefinity.model.emun.LoanGrade;
import ma.emsi.solvefinity.model.emun.LoanPurpose;
import ma.emsi.solvefinity.model.emun.LoanStatus;
import ma.emsi.solvefinity.model.emun.LoanTerm;

public class LoanConverter {
    public static LoanVO toVO(Loan bo) {
        if (bo == null || bo.getId() == null)
            return null;

        LoanVO vo = new LoanVO();

        vo.setId(bo.getId());
        vo.setVersion(bo.getVersion());

        vo.setAmount(bo.getAmount());
        vo.setTerm(bo.getTerm().getId());
        vo.setInterestRate(bo.getInterestRate());
        vo.setInstallment(bo.getInstallment());
        vo.setGrade(bo.getGrade().getId());
        vo.setPurpose(bo.getPurpose().getId());
        vo.setNote(bo.getNote());
        vo.setStatus(bo.getStatus().getId());
        // Audit
        vo.setCreatedAt(bo.getCreatedAt());
        vo.setUpdatedAt(bo.getUpdatedAt());

        return vo;
    }

    public static Loan toBO(LoanVO vo) {
        if (vo == null)
            return null;

        Loan bo = new Loan();

        if (vo.getId() != null) {
            bo.setId(vo.getId());
            bo.setVersion(vo.getVersion());
        }

        bo.setAmount(vo.getAmount());
        bo.setTerm(LoanTerm.getById(vo.getTerm()));
        bo.setInterestRate(vo.getInterestRate());
        bo.setInstallment(vo.getInstallment());
        bo.setGrade(LoanGrade.getById(vo.getGrade()));
        bo.setPurpose(LoanPurpose.getById(vo.getPurpose()));
        bo.setNote(vo.getNote());
        bo.setStatus(LoanStatus.getById(vo.getStatus()));
        // Loaner
        bo.setLoaner(LoanerConverter.toBO(vo.getLoaner()));
        // Audit
        bo.setCreatedAt(vo.getCreatedAt());
        bo.setUpdatedAt(vo.getUpdatedAt());

        return bo;
    }
}
