package ma.emsi.solvefinity.domain.converter;

import ma.emsi.solvefinity.domain.vo.RoleVO;
import ma.emsi.solvefinity.model.Role;

public class RoleConverter {
    public static RoleVO toVO(Role bo) {
        if (bo == null || bo.getId() == null)
            return null;

        RoleVO vo = new RoleVO();

        vo.setId(bo.getId());
        vo.setVersion(bo.getVersion());
        vo.setName(bo.getName());
        vo.setDescription(bo.getDescription());
        // Audit
        vo.setCreatedAt(bo.getCreatedAt());
        vo.setUpdatedAt(bo.getUpdatedAt());

        return vo;
    }

    public static Role toBO(RoleVO vo) {
        if (vo == null)
            return null;

        Role bo = new Role();

        if (vo.getId() != null) {
            bo.setId(vo.getId());
            bo.setVersion(vo.getVersion());
        }

        bo.setName(vo.getName());
        bo.setDescription(vo.getDescription());
        // Audit
        bo.setCreatedAt(vo.getCreatedAt());
        bo.setUpdatedAt(vo.getUpdatedAt());

        return bo;
    }
}
