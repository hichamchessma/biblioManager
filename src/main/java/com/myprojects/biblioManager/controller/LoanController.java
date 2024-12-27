package com.myprojects.biblioManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.myprojects.biblioManager.model.Loan;
import com.myprojects.biblioManager.service.LoanService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long id) {
        Optional<Loan> loan = loanService.getLoanById(id);
        if (loan.isPresent()) {
            return ResponseEntity.ok(loan.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Loan createLoan(@RequestBody Loan loan) {
        return loanService.createLoan(loan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Loan> updateLoan(@PathVariable Long id, @RequestBody Loan loanDetails) {
        Loan updatedLoan = loanService.updateLoan(id, loanDetails);
        if (updatedLoan != null) {
            return ResponseEntity.ok(updatedLoan);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.listLoanHistory();
    }

    @GetMapping("/active")
    public List<Loan> listActiveLoans() {
        return loanService.listActiveLoans();
    }

    @GetMapping("/history")
    public List<Loan> listLoanHistory() {
        return loanService.listLoanHistory();
    }
}
