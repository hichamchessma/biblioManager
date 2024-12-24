package com.myprojects.biblioManager.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.myprojects.biblioManager.model.Book;
import com.myprojects.biblioManager.repository.BookRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateBook() {
        Book book = new Book("Title", "Author", "ISBN", 2021, 19.99);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book createdBook = bookService.createBook(book);
        assertEquals("Title", createdBook.getTitle());
    }

    @Test
    public void testGetAllBooks() {
        Book book1 = new Book("Title1", "Author1", "ISBN1", 2021, 19.99);
        Book book2 = new Book("Title2", "Author2", "ISBN2", 2022, 29.99);
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.getAllBooks();
        assertEquals(2, books.size());
    }

    @Test
    public void testGetBookById() {
        Book book = new Book("Title", "Author", "ISBN", 2021, 19.99);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> foundBook = bookService.getBookById(1L);
        assertTrue(foundBook.isPresent());
        assertEquals("Title", foundBook.get().getTitle());
    }

    @Test
    public void testUpdateBook() {
        Book existingBook = new Book("Old Title", "Old Author", "Old ISBN", 2020, 15.99);
        Book updatedBook = new Book("New Title", "New Author", "New ISBN", 2021, 19.99);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        Book result = bookService.updateBook(1L, updatedBook);
        assertEquals("New Title", result.getTitle());
    }

    @Test
    public void testDeleteBook() {
        doNothing().when(bookRepository).deleteById(1L);
        bookService.deleteBook(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }
}
