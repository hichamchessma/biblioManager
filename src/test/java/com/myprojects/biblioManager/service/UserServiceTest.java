package com.myprojects.biblioManager.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.myprojects.biblioManager.model.User;
import com.myprojects.biblioManager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        User user = new User("username", "email@example.com", "password", "2024-01-01");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("username", createdUser.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUser() {
        User existingUser = new User("username", "email@example.com", "password", "2024-01-01");
        existingUser.setId(1L);
        User updatedUser = new User("newUsername", "newEmail@example.com", "newPassword", "2024-01-01");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User result = userService.updateUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals("newUsername", result.getUsername());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    public void testDeleteUser() {
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testListUsers() {
        User user1 = new User("username1", "email1@example.com", "password1", "2024-01-01");
        User user2 = new User("username2", "email2@example.com", "password2", "2024-01-01");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.listUsers();

        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }
}
