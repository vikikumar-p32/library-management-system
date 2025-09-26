package com.harekrsna.lms.controller;

import com.harekrsna.lms.entity.IssueRecord;
import com.harekrsna.lms.service.IssueRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/issuerecords")
@RequiredArgsConstructor
public class IssueRecordController {

    private final IssueRecordService issueRecordService;

    @PostMapping("/issue/{id}")
    public ResponseEntity<IssueRecord> issueBook(@PathVariable(name = "id") Long bookId) {
        IssueRecord issueRecord = issueRecordService.issueBook(bookId);
        return ResponseEntity.ok(issueRecord);
    }

    @PostMapping("/return/{id}")
    public ResponseEntity<IssueRecord> returnBook(@PathVariable(name = "id") Long issueRecordId) {
        return ResponseEntity.ok(issueRecordService.returnBook(issueRecordId));
    }
}
