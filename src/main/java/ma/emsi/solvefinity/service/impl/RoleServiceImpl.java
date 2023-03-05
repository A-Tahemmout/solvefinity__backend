package ma.emsi.solvefinity.service.impl;

import lombok.RequiredArgsConstructor;
import ma.emsi.solvefinity.domain.converter.RoleConverter;
import ma.emsi.solvefinity.domain.vo.RoleVO;
import ma.emsi.solvefinity.model.Role;
import ma.emsi.solvefinity.repository.RoleRepository;
import ma.emsi.solvefinity.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<RoleVO> getAll() {
        List<Role> roles = roleRepository.findAll();

        return roles.stream().map(RoleConverter::toVO).toList();
    }

    @Override
    public RoleVO getById(UUID uuid) {
        Role role = roleRepository.findById(uuid).orElse(null);

        return RoleConverter.toVO(role);
    }

    @Override
    public RoleVO save(RoleVO baseEntity) {
        // Convert the VO to a BO
        Role role = RoleConverter.toBO(baseEntity);

        // Save the role
        role = roleRepository.save(role);

        // Return the role as a VO
        return RoleConverter.toVO(role);
    }

    @Override
    public void delete(UUID uuid) {
        roleRepository.deleteById(uuid);
    }

    @Override
    public String[] getAllName() {
        return roleRepository.findAllName();
    }

    @Override
    public RoleVO getByName(String name) {
        Role role = roleRepository.findByName(name);

        return RoleConverter.toVO(role);
    }

    @Override
    public List<RoleVO> getByNames(List<RoleVO> names) {
        List<Role> roles = roleRepository.findByNameIn(names.stream().map(RoleVO::getName).toList());

        return roles.stream().map(RoleConverter::toVO).toList();
    }
}
