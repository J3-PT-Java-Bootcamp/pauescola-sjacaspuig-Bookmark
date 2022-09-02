package com.ironhack.bookmark_app.services.reports;

import com.ironhack.bookmark_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserReportService {

    @Autowired
    UserRepository userRepository;
}
