package ma.emsi.solvefinity.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.emsi.solvefinity.domain.converter.BankConverter;
import ma.emsi.solvefinity.domain.vo.BankVO;
import ma.emsi.solvefinity.model.Bank;
import ma.emsi.solvefinity.repository.BankRepository;
import ma.emsi.solvefinity.service.BankService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {
    private final BankRepository bankRepository;

    @Override
    public List<BankVO> getAll() {
        List<Bank> banks = bankRepository.findAll();

        return banks.stream().map(BankConverter::toVO).toList();
    }

    @Override
    public BankVO getById(UUID id) {
        Bank bank = bankRepository.findById(id).orElse(null);

        return BankConverter.toVO(bank);
    }

    @Override
    public BankVO save(BankVO bankVo) {
        // Convert the VO to a BO
        Bank bank = BankConverter.toBO(bankVo);

        // Save the bank
        bank = bankRepository.save(bank);

        // Return the bank as a VO
        return BankConverter.toVO(bank);
    }

    @Override
    public void delete(UUID id) {
        bankRepository.deleteById(id);
    }

    @Override
    public List<Object[]> getAllName() {
        return bankRepository.findAllNameAndSlug();
    }

    @Override
    public List<BankVO> search(String value) {
        List<Bank> banks = bankRepository.findByNameContainsOrDescriptionContains(value, value);

        return banks.stream().map(BankConverter::toVO).toList();
    }

    @Override
    public BankVO getBySlug(String slug) {
        Bank bank = bankRepository.findBySlug(slug);

        return BankConverter.toVO(bank);
    }

    @Override
    public Page<BankVO> getAll(int page, int size, String sortDirection, String sortBy) {
        // Set the sort direction
        sortDirection = sortDirection.equalsIgnoreCase("ASC") ? "ASC" : "DESC";

        // Get the page
        Page<Bank> banks = bankRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy)));

        // Convert the page to a page of VOs
        return banks.map(BankConverter::toVO);
    }

    @Override
    public Page<BankVO> search(String value, int page, int size, String sortDirection, String sortBy) {
        // Set the sort direction
        sortDirection = sortDirection.equalsIgnoreCase("ASC") ? "ASC" : "DESC";

        Page<Bank> banks;

        // If value is empty, return all banks
        if (value == null || value.isEmpty()) {
            banks = bankRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy)));
        } else {
            banks = bankRepository.findByNameContainsOrDescriptionContains(value, value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy)));
        }

        // Convert the page to a page of VOs
        return banks.map(BankConverter::toVO);
    }
}
