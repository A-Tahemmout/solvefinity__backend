package ma.emsi.solvefinity.service;

import ma.emsi.solvefinity.domain.vo.UserVO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface UserService extends BaseService<UserVO, UUID> {
    UserVO getByCin(String cin);

    UserVO getByEmail(String email);

    UserVO getByUsername(String username);

    List<UserVO> search(String value);

    // Pageable
    Page<UserVO> getAll(int page, int size, String sortDirection, String sortBy);

    Page<UserVO> search(String value, int page, int size, String sortDirection, String sortBy);
}
