package com.rutvik.journalAppWithAuth.service;

import com.rutvik.journalAppWithAuth.Repositery.UserRepo;
import com.rutvik.journalAppWithAuth.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

   private  static  final PasswordEncoder PASSWORD_ENCODER=new BCryptPasswordEncoder();

    public ResponseEntity<?> getAllUser(){
        List<User> allUser = userRepo.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(allUser);
    }

    public ResponseEntity<?> createUser(@RequestParam User user){
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        User savedUser = userRepo.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    public ResponseEntity<?> getUserByUserName(@PathVariable String username){
        Optional<User> userByUserName = userRepo.findUserByUserName(username);
        if (userByUserName.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(userByUserName.get());
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for this username");
    }
    public ResponseEntity<?> updateUser(String username,User user){
        Optional<User> userByUserName = userRepo.findUserByUserName(username);
        if(userByUserName.isPresent()){
            User userInDB = userByUserName.get();
            userInDB.setUserName(user.getUserName());
            userInDB.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
            userRepo.save(userInDB);
            return  ResponseEntity.status(HttpStatus.OK).body(userInDB);
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for this username");
    }
    public ResponseEntity<?> deleteUser(String username){
        Optional<User> userByUserName = userRepo.findUserByUserName(username);
        if(userByUserName.isPresent()){
            ObjectId id = userByUserName.get().getId();
            userRepo.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
        }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for this username.");
    }
}
