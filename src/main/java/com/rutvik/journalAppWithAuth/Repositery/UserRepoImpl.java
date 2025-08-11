package com.rutvik.journalAppWithAuth.Repositery;

import com.rutvik.journalAppWithAuth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserRepoImpl {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getAllUserWhoHaveSAE(){
        Query query=new Query();
        query.addCriteria(Criteria.where("sentimentAnalysisEnable").is(true));
        query.addCriteria(Criteria.where("email").exists(true).ne(null).ne("").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));
//        query.addCriteria(Criteria.where("email").ne(null).ne(""));
//        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));
        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }
}
