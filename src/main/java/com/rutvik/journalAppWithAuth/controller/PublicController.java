package com.rutvik.journalAppWithAuth.controller;

import com.rutvik.journalAppWithAuth.entity.User;
import com.rutvik.journalAppWithAuth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;
    @GetMapping("/get")
    public String getOk(){
        return "ok";
    }
    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user){
        return userService.createUser(user);
    }
}
