package com.harekrsna.lms.service;

import com.harekrsna.lms.entity.IssueRecord;

public interface IssueRecordService {
    IssueRecord issueBook(Long bookId);
    IssueRecord returnBook(Long issueRecordId);
}
