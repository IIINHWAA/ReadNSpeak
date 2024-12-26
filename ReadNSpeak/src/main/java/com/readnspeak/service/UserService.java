package com.readnspeak.service;

import com.readnspeak.entity.User;
import com.readnspeak.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 사용자 추가 메서드
    public User addUser(User user) {
        return userRepository.save(user);
    }
}