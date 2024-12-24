package com.myprojects.biblioManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.myprojects.biblioManager.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Vous pouvez ajouter des méthodes de requête personnalisées ici
}
