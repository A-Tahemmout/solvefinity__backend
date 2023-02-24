package ma.emsi.solvefinity.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.emsi.solvefinity.domain.converter.LoanerConverter;
import ma.emsi.solvefinity.domain.vo.LoanerVO;
import ma.emsi.solvefinity.model.Loaner;
import ma.emsi.solvefinity.repository.LoanerRepository;
import ma.emsi.solvefinity.service.LoanerService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class LoanerServiceImpl implements LoanerService {
    private final LoanerRepository loanerRepository;

    @Override
    public List<LoanerVO> getAll() {
        List<Loaner> loaners = loanerRepository.findAll(
                Sort.by(Sort.Direction.ASC, "firstName", "lastName")
        );

        return loaners.stream().map(LoanerConverter::toVO).toList();
    }

    @Override
    public LoanerVO getById(UUID uuid) {
        Loaner loaner = loanerRepository.findById(uuid).orElse(null);

        return LoanerConverter.toVO(loaner);
    }

    @Override
    public LoanerVO save(LoanerVO baseEntity) {
        // Convert the VO to a BO
        Loaner loaner = LoanerConverter.toBO(baseEntity);

        // Save the loaner
        loaner = loanerRepository.save(loaner);

        // Return the loaner as a VO
        return LoanerConverter.toVO(loaner);
    }

    @Override
    public void delete(UUID uuid) {
        loanerRepository.deleteById(uuid);
    }

    @Override
    public List<LoanerVO> getAllByBankSlug(String slug) {
        List<Loaner> loaners = loanerRepository.findAllByBankSlug(slug);

        return loaners.stream().map(LoanerConverter::toVO).toList();
    }

    @Override
    public LoanerVO getByCin(String cin) {
        Loaner loaner = loanerRepository.findByCin(cin).orElse(null);

        return LoanerConverter.toVO(loaner);
    }

    @Override
    public LoanerVO getByEmail(String email) {
        Loaner loaner = loanerRepository.findByEmail(email).orElse(null);

        return LoanerConverter.toVO(loaner);
    }
}
