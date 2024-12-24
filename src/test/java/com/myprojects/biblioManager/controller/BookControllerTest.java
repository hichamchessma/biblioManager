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
import com.myprojects.biblioManager.model.Book;
import com.myprojects.biblioManager.service.BookService;
import java.util.Arrays;
import java.util.Optional;

public class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void testCreateBook() throws Exception {
        // Arrange
        Book book = new Book("Title", "Author", "ISBN", 2021, 19.99);
        when(bookService.createBook(any(Book.class))).thenReturn(book);

        // Act & Assert
        mockMvc.perform(post("/api/books")
                        .contentType(APPLICATION_JSON)
                        .content("{\"title\":\"Title\",\"author\":\"Author\",\"isbn\":\"ISBN\",\"publishedYear\":2021,\"price\":19.99}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title"));
    }
    @Test
    public void testGetAllBooks() throws Exception {
        Book book1 = new Book("Title1", "Author1", "ISBN1", 2021, 19.99);
        Book book2 = new Book("Title2", "Author2", "ISBN2", 2022, 29.99);
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book1, book2));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Title1"))
                .andExpect(jsonPath("$[1].title").value("Title2"));
    }

    @Test
    public void testGetBookById() throws Exception {
        Book book = new Book("Title", "Author", "ISBN", 2021, 19.99);
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title"));
    }

    @Test
    public void testUpdateBook() throws Exception {
        Book book = new Book("Updated Title", "Updated Author", "Updated ISBN", 2021, 29.99);

        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/api/books/1")
                        .contentType(APPLICATION_JSON)
                        .content("{\"title\":\"Updated Title\",\"author\":\"Updated Author\",\"isbn\":\"Updated ISBN\",\"publishedYear\":2021,\"price\":29.99}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }
    @Test
    public void testDeleteBook() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }
}
