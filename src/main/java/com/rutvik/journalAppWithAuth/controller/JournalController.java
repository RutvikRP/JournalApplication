package com.rutvik.journalAppWithAuth.controller;

import com.rutvik.journalAppWithAuth.entity.Journal;
import com.rutvik.journalAppWithAuth.service.JournalService;
import org.bson.types.ObjectId;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/journal")
public class JournalController {
    private static final Logger log= LoggerFactory.getLogger(JournalController.class);
    @Autowired
    private JournalService journalService;
    @GetMapping("/get-all-journal")
    public ResponseEntity<?> getAllJournalOfUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("In getAllJournalOfUser username is:{}",username);
        return journalService.getAllJournalOfUser(username);
    }
    @PostMapping("/create-journal")
    public ResponseEntity<?> addJournal(@RequestBody Journal newJournal){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return journalService.addJournal(username,newJournal);
    }
    @DeleteMapping("/delete-journal/{id}")
    public ResponseEntity<?> deleteJournal(@PathVariable ObjectId id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return journalService.deleteJournal(username,id);
    }
    @GetMapping("/get-journal/{id}")
    public ResponseEntity<?> getJournalById(@PathVariable ObjectId id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return journalService.getJournalById(id,username);
    }
    @PutMapping("/update-journal/{id}")
    public ResponseEntity<?> updateJournal(@RequestBody Journal newJournal,@PathVariable ObjectId id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return journalService.updateJournal(newJournal,id,username);
    }
}
