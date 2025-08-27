package com.rutvik.journalAppWithAuth.controller;

import com.rutvik.journalAppWithAuth.entity.LoginRequest;
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
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody User user){
        return userService.signUp(user);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        return userService.login(loginRequest);
    }
}
