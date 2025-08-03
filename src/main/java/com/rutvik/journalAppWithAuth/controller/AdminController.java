package com.rutvik.journalAppWithAuth.controller;

import com.rutvik.journalAppWithAuth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @GetMapping("/get-all-user")
    public ResponseEntity<?> getAllUser(){
        return userService.getAllUser();
    }
    @GetMapping("/get-user/{username}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String username){
        return userService.getUserByUserName(username);
    }
}
