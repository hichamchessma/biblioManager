package com.myprojects.biblioManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.myprojects.biblioManager.model.Loan;
import com.myprojects.biblioManager.service.LoanService;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

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

    @GetMapping("/active")
    public List<Loan> listActiveLoans() {
        return loanService.listActiveLoans();
    }

    @GetMapping("/history")
    public List<Loan> listLoanHistory() {
        return loanService.listLoanHistory();
    }
}
