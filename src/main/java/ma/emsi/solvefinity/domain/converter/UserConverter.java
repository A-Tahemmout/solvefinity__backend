package ma.emsi.solvefinity.domain.converter;

import ma.emsi.solvefinity.domain.vo.UserVO;
import ma.emsi.solvefinity.model.User;

import java.util.stream.Collectors;

public class UserConverter {
    public static UserVO toVO(User bo) {
        if (bo == null || bo.getId() == null)
            return null;

        UserVO vo = new UserVO();

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
        // Bank
        vo.setBank(BankConverter.toVO(bo.getBank()));
        // Security
        vo.setUsername(bo.getUsername());
        vo.setPassword(bo.getPassword());
        vo.setRoles(bo.getRoles().stream().map(RoleConverter::toVO).collect(Collectors.toList()));
        // Audit
        vo.setCreatedAt(bo.getCreatedAt());
        vo.setUpdatedAt(bo.getUpdatedAt());

        return vo;
    }

    public static User toBO(UserVO vo) {
        if (vo == null)
            return null;

        User bo = new User();

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
        // Bank
        bo.setBank(BankConverter.toBO(vo.getBank()));
        // Security
        bo.setUsername(vo.getUsername());
        bo.setPassword(vo.getPassword());
        bo.setRoles(vo.getRoles().stream().map(RoleConverter::toBO).collect(Collectors.toList()));
        // Audit
        bo.setCreatedAt(vo.getCreatedAt());
        bo.setUpdatedAt(vo.getUpdatedAt());

        return bo;
    }
}
