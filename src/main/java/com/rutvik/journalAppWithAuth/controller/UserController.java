package com.rutvik.journalAppWithAuth.controller;

import com.rutvik.journalAppWithAuth.entity.User;
import com.rutvik.journalAppWithAuth.service.UserService;
import com.rutvik.journalAppWithAuth.service.WhetherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private WhetherService  whetherService;
    @GetMapping("/get-whether/{city}")
    public ResponseEntity<?> getWhether(@PathVariable String city){
    return  whetherService.getWhether(city);
    }
    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser( @RequestBody User user){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return  userService.updateUser(username,user);
    }
    @DeleteMapping("delete-user")
    public ResponseEntity<?> deleteUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.deleteUser(username);
    }

}
