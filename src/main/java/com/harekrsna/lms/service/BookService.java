package com.harekrsna.lms.service;

import com.harekrsna.lms.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(Long bookId);
    Book createBook(Book book);
    Book updateBook(Long bookId, Book book);
    void deleteBook(Long bookId);
}
