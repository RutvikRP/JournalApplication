package com.rutvik.journalAppWithAuth;

import com.rutvik.journalAppWithAuth.Repositery.UserRepoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UserRepoImplTest {
    @Autowired
    UserRepoImpl userRepoImpl;

    @Test
    public void getUserRepoImpl() {
        userRepoImpl.getAllUserWhoHaveSAE();
    }
}
