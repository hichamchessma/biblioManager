package com.myprojects.biblioManager.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.myprojects.biblioManager.model.Loan;
import com.myprojects.biblioManager.model.Book;
import com.myprojects.biblioManager.model.User;
import com.myprojects.biblioManager.service.LoanService;
import java.util.Arrays;
import java.util.Optional;
import java.time.LocalDate;

public class LoanControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loanController).build();
    }

    @Test
    public void testCreateLoan() throws Exception {
        // Arrange
        Book book = new Book("Title", "Author", "ISBN", 2021, 19.99);
        User user = new User("John", "Doe", "john@example.com");
        Loan loan = new Loan(user.getId(), book.getId(), LocalDate.now(), LocalDate.now().plusDays(14));
        when(loanService.createLoan(any(Loan.class))).thenReturn(loan);

        // Act & Assert
        mockMvc.perform(post("/api/loans")
                .contentType(APPLICATION_JSON)
                .content("{\"userId\":1,\"bookId\":1,\"loanDate\":\"" + LocalDate.now() + "\",\"dueDate\":\"" + LocalDate.now().plusDays(14) + "\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllLoans() throws Exception {
        // Arrange
        Book book1 = new Book("Title1", "Author1", "ISBN1", 2021, 19.99);
        Book book2 = new Book("Title2", "Author2", "ISBN2", 2022, 29.99);
        User user = new User("John", "Doe", "john@example.com");
        Loan loan1 = new Loan(user.getId(), book1.getId(), LocalDate.now(), LocalDate.now().plusDays(14));
        Loan loan2 = new Loan(user.getId(), book2.getId(), LocalDate.now(), LocalDate.now().plusDays(14));
        when(loanService.listActiveLoans()).thenReturn(Arrays.asList(loan1, loan2));

        // Act & Assert
        mockMvc.perform(get("/api/loans/active")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetLoanById() throws Exception {
        // Arrange
        Book book = new Book("Title", "Author", "ISBN", 2021, 19.99);
        User user = new User("John", "Doe", "john@example.com");
        Loan loan = new Loan(user.getId(), book.getId(), LocalDate.now(), LocalDate.now().plusDays(14));
        when(loanService.getLoanById(1L)).thenReturn(Optional.of(loan));

        // Act & Assert
        mockMvc.perform(get("/api/loans/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateLoan() throws Exception {
        // Arrange
        Book book = new Book("Title", "Author", "ISBN", 2021, 19.99);
        User user = new User("John", "Doe", "john@example.com");
        Loan updatedLoan = new Loan(user.getId(), book.getId(), LocalDate.now(), LocalDate.now().plusDays(21));
        when(loanService.updateLoan(eq(1L), any(Loan.class))).thenReturn(updatedLoan);

        // Act & Assert
        mockMvc.perform(put("/api/loans/1")
                .contentType(APPLICATION_JSON)
                .content("{\"userId\":1,\"bookId\":1,\"loanDate\":\"" + LocalDate.now() + "\",\"dueDate\":\"" + LocalDate.now().plusDays(21) + "\"}"))
                .andExpect(status().isOk());
    }


}
