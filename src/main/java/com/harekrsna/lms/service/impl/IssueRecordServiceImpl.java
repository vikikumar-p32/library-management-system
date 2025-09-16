package com.harekrsna.lms.service.impl;

import com.harekrsna.lms.entity.Book;
import com.harekrsna.lms.entity.IssueRecord;
import com.harekrsna.lms.entity.User;
import com.harekrsna.lms.exception.ResourceNotFoundException;
import com.harekrsna.lms.repository.IssueRecordRepository;
import com.harekrsna.lms.repository.UserRepository;
import com.harekrsna.lms.service.BookService;
import com.harekrsna.lms.service.IssueRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class IssueRecordServiceImpl implements IssueRecordService {

    private final IssueRecordRepository issueRecordRepository;
    private final BookService bookService;
    private final UserRepository userRepository;


    @Override
    public IssueRecord issueBook(Long bookId) {
        Book existingBook = bookService.getBookById(bookId);
        if(existingBook.getAvailableCopies() <= 0) {
            throw new RuntimeException("No copies available for book: " + existingBook.getTitle());
        }

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username);

        IssueRecord issueRecord = new IssueRecord();
        issueRecord.setIssueDate(LocalDate.now());
        issueRecord.setDueDate(LocalDate.now().plusDays(14));
        issueRecord.setIsReturned(false);
        issueRecord.setUser(user);
        issueRecord.setBook(existingBook);

        // Decrease available copies
        existingBook.setAvailableCopies(existingBook.getAvailableCopies() - 1);
        if(existingBook.getAvailableCopies() < 0) {
            existingBook.setIsAvailable(false);
        }

        // update book
        bookService.updateBook(existingBook.getId(), existingBook);
        return issueRecordRepository.save(issueRecord);
    }

    @Override
    public IssueRecord returnBook(Long issueRecordId) {
        IssueRecord issueRecord = issueRecordRepository.findById(issueRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("Issue record not found with id: " + issueRecordId));

        if(issueRecord.getIsReturned()) {
            throw new RuntimeException("Book already returned for issue record id: " + issueRecordId);
        }

        issueRecord.setIsReturned(true);
        issueRecord.setReturnDate(LocalDate.now());

        // Increase available copies
        Book book = issueRecord.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        book.setIsAvailable(true);

        // update book
        bookService.updateBook(book.getId(), book);
        return issueRecordRepository.save(issueRecord);
    }
}
