package ma.emsi.solvefinity.service;

import ma.emsi.solvefinity.domain.vo.BankVO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface BankService extends BaseService<BankVO, UUID> {
    List<Object[]> getAllName();

    List<BankVO> search(String value);

    BankVO getBySlug(String slug);

    // Pageable
    Page<BankVO> getAll(int page, int size, String sortDirection, String sortBy);

    Page<BankVO> search(String value, int page, int size, String sortDirection, String sortBy);
}
