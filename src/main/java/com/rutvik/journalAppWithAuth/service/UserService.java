package com.rutvik.journalAppWithAuth.service;

import com.rutvik.journalAppWithAuth.Repositery.UserRepo;
import com.rutvik.journalAppWithAuth.entity.LoginRequest;
import com.rutvik.journalAppWithAuth.entity.User;
import com.rutvik.journalAppWithAuth.utils.JwtUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtUtils jwtUtils;

    //   private  static  final PasswordEncoder PASSWORD_ENCODER=new BCryptPasswordEncoder();
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> getAllUser() {
        List<User> allUser = userRepo.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(allUser);
    }

    public ResponseEntity<?> signUp(@RequestBody User user) {
        if (userRepo.findUserByUserName(user.getUserName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User of this name is present in system.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRoles().isEmpty() || user.getRoles() == null) {
            user.setRoles(List.of("USER"));
        }
        User savedUser = userRepo.save(user);
        //String token=jwtUtils.generateToken(user.getUserName());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    public ResponseEntity<?> getUserByUserName(@PathVariable String username) {
        Optional<User> userByUserName = userRepo.findUserByUserName(username);
        if (userByUserName.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(userByUserName.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for this username");
    }

    public ResponseEntity<?> updateUser(String username, User user) {
        Optional<User> userByUserName = userRepo.findUserByUserName(username);
        if (userByUserName.isPresent()) {
            User userInDB = userByUserName.get();
            userInDB.setUserName(user.getUserName());
            if (user.getPassword() != null && !user.getPassword().isBlank()) {
                userInDB.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            if (user.getEmail() != null) {
                userInDB.setEmail(user.getEmail());
            }
            userRepo.save(userInDB);
            return ResponseEntity.status(HttpStatus.OK).body(userInDB);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for this username");
    }

    public ResponseEntity<?> deleteUser(String username) {
        Optional<User> userByUserName = userRepo.findUserByUserName(username);
        if (userByUserName.isPresent()) {
            ObjectId id = userByUserName.get().getId();
            userRepo.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for this username.");
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Optional<User> userByUserName = userRepo.findUserByUserName(loginRequest.getUsername());
        if (userByUserName.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username and password.");
        }
        User user = userByUserName.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username and password.");
        }
        String jwtToken = jwtUtils.generateToken(user.getUserName());
        return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
    }
}
