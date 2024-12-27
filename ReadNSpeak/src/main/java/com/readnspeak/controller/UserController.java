package com.readnspeak.controller;

import com.readnspeak.entity.User;
import com.readnspeak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // POST 요청으로 새로운 사용자 추가
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.addUser(user);
    }
}