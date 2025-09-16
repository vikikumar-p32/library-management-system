package com.harekrsna.lms.service.impl;

import com.harekrsna.lms.entity.Book;
import com.harekrsna.lms.exception.ResourceNotFoundException;
import com.harekrsna.lms.repository.BookRepository;
import com.harekrsna.lms.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long bookId, Book book) {
        Book existingBook = getBookById(bookId);
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setIsbn(book.getIsbn());
        existingBook.setAvailableCopies(book.getAvailableCopies());
        existingBook.setIsAvailable(book.getIsAvailable());
        return bookRepository.save(existingBook);
    }

    @Override
    public void deleteBook(Long id) {
        Book existingBook = getBookById(id);
        bookRepository.delete(existingBook);
    }
}
