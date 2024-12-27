package com.myprojects.biblioManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.myprojects.biblioManager.model.Book;
import com.myprojects.biblioManager.service.BookService;
import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Books", description = "Endpoints pour gérer les livres")
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Operation(summary = "Créer un livre", description = "Ajoute un nouveau livre à la bibliothèque.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livre créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @Operation(summary = "Lister tous les livres", description = "Renvoie une liste de tous les livres disponibles dans la bibliothèque.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    })
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @Operation(summary = "Obtenir un livre par son ID", description = "Récupère un livre spécifique grâce à son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livre trouvé"),
            @ApiResponse(responseCode = "404", description = "Livre non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(
            @Parameter(description = "ID du livre à récupérer", required = true)
            @PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Mettre à jour un livre", description = "Met à jour les détails d'un livre existant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livre mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Livre non trouvé"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @Parameter(description = "ID du livre à mettre à jour", required = true)
            @PathVariable Long id,
            @RequestBody Book book) {
        try {
            Book updatedBook = bookService.updateBook(id, book);
            return ResponseEntity.ok(updatedBook);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Book not found")) {
                return ResponseEntity.notFound().build();
            }
            throw e;
        }
    }

    @Operation(summary = "Supprimer un livre", description = "Supprime un livre spécifique de la bibliothèque.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Livre supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Livre non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID du livre à supprimer", required = true)
            @PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Book not found")) {
                return ResponseEntity.notFound().build();
            }
            throw e;
        }
    }
}
