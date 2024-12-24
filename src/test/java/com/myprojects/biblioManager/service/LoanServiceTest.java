package com.myprojects.biblioManager.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.myprojects.biblioManager.model.Loan;
import com.myprojects.biblioManager.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class LoanServiceTest {

    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanRepository loanRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateLoan() {
        Loan loan = new Loan(1L, 1L, LocalDate.now(), LocalDate.now().plusDays(14));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        Loan createdLoan = loanService.createLoan(loan);

        assertNotNull(createdLoan);
        assertEquals(1L, createdLoan.getUserId());
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    public void testUpdateLoan() {
        Loan existingLoan = new Loan(1L, 1L, LocalDate.now(), LocalDate.now().plusDays(14));
        existingLoan.setId(1L);
        existingLoan.setActive(true);
        Loan updatedLoanDetails = new Loan(1L, 1L, LocalDate.now(), LocalDate.now().plusDays(7));

        when(loanRepository.findById(1L)).thenReturn(Optional.of(existingLoan));
        when(loanRepository.save(any(Loan.class))).thenReturn(existingLoan);

        Loan updatedLoan = loanService.updateLoan(1L, updatedLoanDetails);

        assertNotNull(updatedLoan);
        assertFalse(updatedLoan.isActive()); // L'emprunt doit être inactif après retour
        verify(loanRepository, times(1)).save(existingLoan);
    }

    @Test
    public void testListActiveLoans() {
        Loan loan1 = new Loan(1L, 1L, LocalDate.now(), LocalDate.now().plusDays(14));
        loan1.setActive(true);
        Loan loan2 = new Loan(2L, 2L, LocalDate.now(), LocalDate.now().plusDays(14));
        loan2.setActive(false);
        when(loanRepository.findAll()).thenReturn(Arrays.asList(loan1, loan2));

        List<Loan> activeLoans = loanService.listActiveLoans();

        assertEquals(1, activeLoans.size());
        assertEquals(1L, activeLoans.get(0).getUserId());
        verify(loanRepository, times(1)).findAll();
    }

    @Test
    public void testListLoanHistory() {
        Loan loan1 = new Loan(1L, 1L, LocalDate.now(), LocalDate.now().plusDays(14));
        Loan loan2 = new Loan(2L, 2L, LocalDate.now(), LocalDate.now().plusDays(14));
        when(loanRepository.findAll()).thenReturn(Arrays.asList(loan1, loan2));

        List<Loan> loanHistory = loanService.listLoanHistory();

        assertEquals(2, loanHistory.size());
        verify(loanRepository, times(1)).findAll();
    }
}
