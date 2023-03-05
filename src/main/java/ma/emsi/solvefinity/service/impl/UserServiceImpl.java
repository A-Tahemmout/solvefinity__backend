package ma.emsi.solvefinity.service.impl;

import lombok.RequiredArgsConstructor;
import ma.emsi.solvefinity.domain.converter.UserConverter;
import ma.emsi.solvefinity.domain.vo.UserVO;
import ma.emsi.solvefinity.model.User;
import ma.emsi.solvefinity.repository.RoleRepository;
import ma.emsi.solvefinity.repository.UserRepository;
import ma.emsi.solvefinity.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<UserVO> getAll() {
        List<User> users = userRepository.findAll();

        return users.stream().map(UserConverter::toVO).toList();
    }

    @Override
    public UserVO getById(UUID id) {
        User user = userRepository.findById(id).orElse(null);

        return UserConverter.toVO(user);
    }

    @Override
    public UserVO save(UserVO baseEntity) {
        // Convert the VO to a BO
        User user = UserConverter.toBO(baseEntity);

        // Save the user
        user = userRepository.save(user);

        // Return the user as a VO
        return UserConverter.toVO(user);
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserVO> search(String value) {
        List<User> users = userRepository.findByCinContainsOrFirstNameContainsOrLastNameContains(value, value, value);

        return users.stream().map(UserConverter::toVO).toList();
    }

    @Override
    public UserVO getByCin(String cin) {
        User user = userRepository.findByCin(cin).orElse(null);

        return UserConverter.toVO(user);
    }

    @Override
    public UserVO getByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);

        return UserConverter.toVO(user);
    }

    @Override
    public UserVO getByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);

        return UserConverter.toVO(user);
    }

    @Override
    public Page<UserVO> getAll(int page, int size, String sortDirection, String sortBy) {
        // Set the sort direction
        sortDirection = sortDirection.equalsIgnoreCase("ASC") ? "ASC" : "DESC";

        // Get the page
        Page<User> users = userRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy)));

        // Convert the page to a page of VOs
        return users.map(UserConverter::toVO);
    }

    @Override
    public Page<UserVO> search(String value, int page, int size, String sortDirection, String sortBy) {
        // Set the sort direction
        sortDirection = sortDirection.equalsIgnoreCase("ASC") ? "ASC" : "DESC";

        Page<User> users;

        // If value is empty, return all users
        if (value == null || value.isEmpty()) {
            users = userRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy)));
        } else {
            users = userRepository.findByCinContainsOrFirstNameContainsOrLastNameContains(value, value, value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy)));
        }

        // Convert the page to a page of VOs
        return users.map(UserConverter::toVO);
    }
}
