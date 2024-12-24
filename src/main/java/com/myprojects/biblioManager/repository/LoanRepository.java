package com.myprojects.biblioManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.myprojects.biblioManager.model.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    // Vous pouvez ajouter des méthodes de requête personnalisées ici
}
