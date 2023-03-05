package ma.emsi.solvefinity.domain.converter;

import ma.emsi.solvefinity.domain.vo.LoanerVO;
import ma.emsi.solvefinity.model.Loaner;
import ma.emsi.solvefinity.model.emun.EmploymentLength;
import ma.emsi.solvefinity.model.emun.HomeOwnership;
import ma.emsi.solvefinity.model.emun.VerificationStatus;

import java.util.stream.Collectors;

public class LoanerConverter {
    public static LoanerVO toVO(Loaner bo) {
        if (bo == null || bo.getId() == null)
            return null;

        LoanerVO vo = new LoanerVO();

        vo.setId(bo.getId());
        vo.setVersion(bo.getVersion());

        vo.setFirstName(bo.getFirstName());
        vo.setLastName(bo.getLastName());
        vo.setCin(bo.getCin());
        vo.setAvatar(bo.getAvatar());
        vo.setEmail(bo.getEmail());
        vo.setPhone(bo.getPhone());
        vo.setAddress(bo.getAddress());
        vo.setCity(bo.getCity());
        vo.setZipCode(bo.getZipCode());
        vo.setCountry(bo.getCountry());
        vo.setBirthDate(bo.getBirthDate());
        // Employment
        vo.setEmploymentLength(bo.getEmploymentLength().getId());
        vo.setEmploymentTitle(bo.getEmploymentTitle());
        vo.setAnnualIncome(bo.getAnnualIncome());
        vo.setVerificationStatus(bo.getVerificationStatus().getId());
        vo.setHomeOwnership(bo.getHomeOwnership().getId());
        // Loans
        vo.setLoans(bo.getLoans().stream().map(LoanConverter::toVO).collect(Collectors.toList()));
        // Bank
        vo.setBank(BankConverter.toVO(bo.getBank()));
        // Audit
        vo.setCreatedAt(bo.getCreatedAt());
        vo.setUpdatedAt(bo.getUpdatedAt());

        return vo;
    }

    public static Loaner toBO(LoanerVO vo) {
        if (vo == null)
            return null;

        Loaner bo = new Loaner();

        if (vo.getId() != null) {
            bo.setId(vo.getId());
            bo.setVersion(vo.getVersion());
        }

        bo.setFirstName(vo.getFirstName());
        bo.setLastName(vo.getLastName());
        bo.setCin(vo.getCin());
        bo.setAvatar(vo.getAvatar());
        bo.setEmail(vo.getEmail());
        bo.setPhone(vo.getPhone());
        bo.setAddress(vo.getAddress());
        bo.setCity(vo.getCity());
        bo.setZipCode(vo.getZipCode());
        bo.setCountry(vo.getCountry());
        bo.setBirthDate(vo.getBirthDate());
        // Employment
        bo.setEmploymentLength(EmploymentLength.getById(vo.getEmploymentLength()));
        bo.setEmploymentTitle(vo.getEmploymentTitle());
        bo.setAnnualIncome(vo.getAnnualIncome());
        bo.setVerificationStatus(VerificationStatus.getById(vo.getVerificationStatus()));
        bo.setHomeOwnership(HomeOwnership.getById(vo.getHomeOwnership()));
        // Loans
        bo.setLoans(vo.getLoans().stream().map(LoanConverter::toBO).collect(Collectors.toList()));
        // Bank
        bo.setBank(BankConverter.toBO(vo.getBank()));
        // Audit
        bo.setCreatedAt(vo.getCreatedAt());
        bo.setUpdatedAt(vo.getUpdatedAt());

        return bo;
    }
}
