package ma.emsi.solvefinity.controller;

import lombok.RequiredArgsConstructor;
import ma.emsi.solvefinity.domain.vo.LoanVO;
import ma.emsi.solvefinity.domain.vo.LoanerVO;
import ma.emsi.solvefinity.payload.request.LoanRequest;
import ma.emsi.solvefinity.service.LoanService;
import ma.emsi.solvefinity.service.LoanerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/loan")
@RequiredArgsConstructor
public class LoanController {
    private final LoanerService loanerService;
    private final LoanService loanService;

    @GetMapping("/all")
    public ResponseEntity<List<LoanVO>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanVO> getLoanById(@PathVariable UUID id) {
        // Get loan by id
        LoanVO loanVo = loanService.getById(id);

        // Check if loan was found
        if (loanVo == null) {
            // Return not found
            return ResponseEntity.notFound().build();
        }

        // Return loan
        return ResponseEntity.ok(loanVo);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoanVO> createLoan(@RequestBody LoanRequest loanRequest) {
        // Get loaner by id
        LoanerVO loaner = loanerService.getById(loanRequest.getLoanerId());

        // Check if loaner was found
        if (loaner == null) {
            // Return not found
            return ResponseEntity.notFound().build();
        }

        // Get loan
        LoanVO loan = loanRequest.getLoan();

        // Set loaner
        loan.setLoaner(loaner);

        // Create loan
        LoanVO createdLoan = loanService.save(loan);

        // Return created loan
        return ResponseEntity.ok(createdLoan);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoanVO> updateLoan(@RequestBody LoanRequest loanRequest) {
        // Get loaner by id
        LoanerVO loaner = loanerService.getById(loanRequest.getLoanerId());

        // Check if loaner was found
        if (loaner == null) {
            // Return not found
            return ResponseEntity.notFound().build();
        }

        // Get loan
        LoanVO loan = loanRequest.getLoan();

        // Set loaner
        loan.setLoaner(loaner);

        // Update loan
        LoanVO updatedLoan = loanService.save(loan);

        // Return updated loan
        return ResponseEntity.ok(updatedLoan);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable UUID id) {
        // Get loan by id
        LoanVO loan = loanService.getById(id);

        // Check if loan was found
        if (loan == null) {
            // Return not found
            return ResponseEntity.notFound().build();
        }

        // Delete loan
        loanService.delete(id);

        // Return ok
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/predict", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> predictLoan(@RequestBody LoanRequest loanRequest) {
        // Get loaner by id
        LoanerVO loaner = loanerService.getById(loanRequest.getLoanerId());

        // Check if loaner was found
        if (loaner == null) {
            // Return not found
            return ResponseEntity.notFound().build();
        }

        // Get loan
        LoanVO loan = loanRequest.getLoan();

        // Set loaner
        loan.setLoaner(loaner);

        // Predict loan
        boolean prediction = loanService.predict(loan);

        // Return prediction
        return ResponseEntity.ok(prediction ? 1.0 : 0.0);
    }
}
