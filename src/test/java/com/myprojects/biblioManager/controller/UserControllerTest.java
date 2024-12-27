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
import com.myprojects.biblioManager.model.User;
import com.myprojects.biblioManager.service.UserService;
import java.util.Arrays;
import java.util.Optional;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testCreateUser() throws Exception {
        // Arrange
        User user = new User("John",  "john@example.com" ,"123");
        when(userService.createUser(any(User.class))).thenReturn(user);

        // Act & Assert
        mockMvc.perform(post("/api/users")
                .contentType(APPLICATION_JSON)
                .content("{\"username\":\"John\",\"email\":\"john@example.com\", \"password\":\"123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // Arrange
        User user1 = new User("John", "john@example.com","123");
        User user2 = new User("Jane",  "jane@example.com","256");
        when(userService.listUsers()).thenReturn(Arrays.asList(user1, user2));

        // Act & Assert
        mockMvc.perform(get("/api/users")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("John"))
                .andExpect(jsonPath("$[1].username").value("Jane"));
    }

    @Test
    public void testGetUserById() throws Exception {
        // Arrange
        User user = new User("John", "Doe", "john@example.com");
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        // Act & Assert
        mockMvc.perform(get("/api/users/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("John"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        // Arrange
        User updatedUser = new User("John Updated", "Doe Updated", "john.updated@example.com");
        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(updatedUser);

        // Act & Assert
        mockMvc.perform(put("/api/users/1")
                .contentType(APPLICATION_JSON)
                .content("{\"firstName\":\"John Updated\",\"lastName\":\"Doe Updated\",\"email\":\"john.updated@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("John Updated"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/users/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(userService, times(1)).deleteUser(1L);
    }
}
