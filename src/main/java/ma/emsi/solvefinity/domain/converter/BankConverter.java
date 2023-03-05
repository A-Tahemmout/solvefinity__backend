package ma.emsi.solvefinity.domain.converter;

import ma.emsi.solvefinity.domain.vo.BankVO;
import ma.emsi.solvefinity.model.Bank;

public class BankConverter {
    public static BankVO toVO(Bank bo) {
        if (bo == null || bo.getId() == null)
            return null;

        BankVO vo = new BankVO();

        vo.setId(bo.getId());
        vo.setVersion(bo.getVersion());

        vo.setName(bo.getName());
        vo.setDescription(bo.getDescription());
        vo.setSlug(bo.getSlug());
        vo.setLogo(bo.getLogo());
        vo.setEmail(bo.getEmail());
        vo.setPhone(bo.getPhone());
        vo.setAddress(bo.getAddress());
        vo.setWebsite(bo.getWebsite());
        // Audit
        vo.setCreatedAt(bo.getCreatedAt());
        vo.setUpdatedAt(bo.getUpdatedAt());

        if (bo.getUsers() != null)
            vo.setUsersCount(Long.valueOf(bo.getUsers().size()));

        return vo;
    }

    public static Bank toBO(BankVO vo) {
        if (vo == null)
            return null;

        Bank bo = new Bank();

        if (vo.getId() != null) {
            bo.setId(vo.getId());
            bo.setVersion(vo.getVersion());
        }

        bo.setName(vo.getName());
        bo.setDescription(vo.getDescription());
        bo.setSlug(vo.getSlug());
        bo.setLogo(vo.getLogo());
        bo.setEmail(vo.getEmail());
        bo.setPhone(vo.getPhone());
        bo.setAddress(vo.getAddress());
        bo.setWebsite(vo.getWebsite());
        // Audit
        bo.setCreatedAt(vo.getCreatedAt());
        bo.setUpdatedAt(vo.getUpdatedAt());

        return bo;
    }
}
