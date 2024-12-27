package com.myprojects.biblioManager.service;

import com.myprojects.biblioManager.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.myprojects.biblioManager.model.Loan;
import com.myprojects.biblioManager.repository.LoanRepository;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    public Loan createLoan(Loan loan) {
        return loanRepository.save(loan);
    }
    public Optional<Loan> getLoanById(Long id) {
        return loanRepository.findById(id);
    }
    public Loan updateLoan(Long id, Loan loanDetails) {
        Optional<Loan> optionalLoan = loanRepository.findById(id);
        if (optionalLoan.isPresent()) {
            Loan loan = optionalLoan.get();
            loan.setBookId(loanDetails.getBookId());
            loan.setUserId(loanDetails.getUserId());
            loan.setStartDate(loanDetails.getStartDate());
            loan.setReturnDate(loanDetails.getReturnDate());
            loan.setActive(loanDetails.isActive()); // Marquer l'emprunt comme inactif lors du retour
            return loanRepository.save(loan);
        }
        return null; // ou lancer une exception
    }

    public List<Loan> listActiveLoans() {
        return loanRepository.findAll().stream()
            .filter(Loan::isActive)
            .toList();
    }

    public List<Loan> listLoanHistory() {
        return loanRepository.findAll(); // Retourne tous les emprunts, actifs et inactifs
    }
}
