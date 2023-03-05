package ma.emsi.solvefinity.service;

import ma.emsi.solvefinity.domain.vo.RoleVO;

import java.util.List;
import java.util.UUID;

public interface RoleService extends BaseService<RoleVO, UUID> {
    String[] getAllName();

    RoleVO getByName(String name);

    List<RoleVO> getByNames(List<RoleVO> names);
}
