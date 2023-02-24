package ma.emsi.solvefinity.service.impl;

import lombok.AllArgsConstructor;
import ma.emsi.solvefinity.domain.converter.LoanConverter;
import ma.emsi.solvefinity.domain.vo.LoanVO;
import ma.emsi.solvefinity.model.Loan;
import ma.emsi.solvefinity.repository.LoanRepository;
import ma.emsi.solvefinity.service.LoanService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;

    @Override
    public List<LoanVO> getAll() {
        List<Loan> loans = loanRepository.findAll();

        return loans.stream().map(LoanConverter::toVO).toList();
    }

    @Override
    public LoanVO getById(UUID uuid) {
        Loan loan = loanRepository.findById(uuid).orElse(null);

        return LoanConverter.toVO(loan);
    }

    @Override
    public LoanVO save(LoanVO baseEntity) {
        // Convert the VO to a BO
        Loan loan = LoanConverter.toBO(baseEntity);

        // Save the loan
        loan = loanRepository.save(loan);

        // Return the loan as a VO
        return LoanConverter.toVO(loan);
    }

    @Override
    public void delete(UUID uuid) {
        loanRepository.deleteById(uuid);
    }
}
