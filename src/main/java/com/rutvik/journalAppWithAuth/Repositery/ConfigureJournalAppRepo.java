package com.rutvik.journalAppWithAuth.Repositery;

import com.rutvik.journalAppWithAuth.entity.ConfigureJournalApp;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigureJournalAppRepo extends MongoRepository<ConfigureJournalApp, ObjectId> {
}
