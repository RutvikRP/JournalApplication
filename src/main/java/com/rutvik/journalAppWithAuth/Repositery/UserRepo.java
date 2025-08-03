package com.rutvik.journalAppWithAuth.Repositery;

import com.rutvik.journalAppWithAuth.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, ObjectId> {
    Optional<User> findUserByUserName(String userName);
}
