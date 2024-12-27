package com.myprojects.biblioManager.service;

import com.myprojects.biblioManager.model.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.myprojects.biblioManager.model.User;
import com.myprojects.biblioManager.repository.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    public User updateUser(Long id, User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setRegisteredDate(userDetails.getRegisteredDate());
            return userRepository.save(user);
        }
        return null; // ou lancer une exception
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }
}
