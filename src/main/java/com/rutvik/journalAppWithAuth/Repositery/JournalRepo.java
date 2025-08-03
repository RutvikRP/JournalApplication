package com.rutvik.journalAppWithAuth.Repositery;

import com.rutvik.journalAppWithAuth.entity.Journal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepo extends MongoRepository<Journal, ObjectId> {
}
