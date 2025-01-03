package com.myprojects.biblioManager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String registeredDate;

    public User(String username, String email, String password, String registeredDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.registeredDate = registeredDate;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.registeredDate = "CURRENT_TIMESTAMP()";
    }
    public User() {
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", email=" + email + ", registeredDate=" + registeredDate + "]";
    }
}
