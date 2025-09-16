package com.harekrsna.lms.controller;

import com.harekrsna.lms.dto.BookRequestDTO;
import com.harekrsna.lms.dto.BookResponseDTO;
import com.harekrsna.lms.entity.Book;
import com.harekrsna.lms.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        return ResponseEntity.ok(
                bookService.getAllBooks()
                        .stream()
                        .map(book -> modelMapper.map(book, BookResponseDTO.class))
                        .toList()
        );
    }

    @GetMapping("/{book_id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable(name = "book_id") Long bookId) {
        return ResponseEntity.ok(
                modelMapper.map(bookService.getBookById(bookId), BookResponseDTO.class)
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody @Valid BookRequestDTO bookRequestDTO) {
        return ResponseEntity.ok(
                modelMapper.map(bookService.createBook(modelMapper.map(bookRequestDTO, Book.class)), BookResponseDTO.class)
        );
    }

    @PutMapping("/{book_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponseDTO> updateBook(
            @PathVariable(name = "book_id") Long bookId,
            @RequestBody BookRequestDTO bookRequestDTO) {
        return ResponseEntity.ok(
                modelMapper.map(bookService.updateBook(bookId, modelMapper.map(bookRequestDTO, Book.class)), BookResponseDTO.class)
        );
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable(name = "book_id") Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }
}
