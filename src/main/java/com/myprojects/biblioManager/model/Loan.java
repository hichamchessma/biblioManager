package com.myprojects.biblioManager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long bookId;
    private LocalDate startDate;
    private LocalDate returnDate;
    private boolean isActive = true; // Indique si l'emprunt est actif

    public Loan(Long userId, Long bookId, LocalDate startDate, LocalDate returnDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.startDate = startDate;
        this.returnDate = returnDate;
        this.isActive = true; // L'emprunt est actif par défaut
    }
}
