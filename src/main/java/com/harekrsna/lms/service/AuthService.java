package com.harekrsna.lms.service;

import com.harekrsna.lms.entity.User;

public interface AuthService {
    User registerNormalUser(User user);
    User registerAdminUser(User user);
    String login(User user);
}
