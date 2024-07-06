package com.rogue.tictactoefr.controller;

import com.rogue.tictactoefr.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String getCurrentUser() {
        return userService.getCurrentUser();
    }
}
