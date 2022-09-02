package com.ironhack.bookmark_app.services.reports;

import com.ironhack.bookmark_app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookReportService {

    @Autowired
    BookRepository bookRepository;
}
