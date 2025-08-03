package com.rutvik.journalAppWithAuth.service;

import com.rutvik.journalAppWithAuth.Repositery.UserRepo;
import com.rutvik.journalAppWithAuth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserSecurityService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userByUserName = userRepo.findUserByUserName(username);
        if(userByUserName.isPresent()){
            User user=userByUserName.get();
            return org.springframework.security.core.userdetails.User.builder().username(user.getUserName()).password(user.getPassword()).roles(user.getRoles().toArray(new String[0])).build();
        }
        throw  new UsernameNotFoundException("User Not Found Exception");
    }
}
