package com.myprojects.biblioManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.myprojects.biblioManager.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Vous pouvez ajouter des méthodes de requête personnalisées ici
}
