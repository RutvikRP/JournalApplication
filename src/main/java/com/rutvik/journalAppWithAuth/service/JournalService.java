package com.rutvik.journalAppWithAuth.service;

import com.rutvik.journalAppWithAuth.Repositery.JournalRepo;
import com.rutvik.journalAppWithAuth.Repositery.UserRepo;
import com.rutvik.journalAppWithAuth.entity.Journal;
import com.rutvik.journalAppWithAuth.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class JournalService {
    @Autowired
    private JournalRepo journalRepo;
    @Autowired
    private UserRepo userRepo;

    public ResponseEntity<?> getAllJournalOfUser(String username) {
        ArrayList<Journal> allJournalList = new ArrayList<>();
        Optional<User> userByUserName = userRepo.findUserByUserName(username);
        if (userByUserName.isPresent()) {
            allJournalList = (ArrayList<Journal>) userByUserName.get().getJournalsList();
            return ResponseEntity.status(HttpStatus.OK).body(allJournalList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for this name.");

    }

    @Transactional
    public ResponseEntity<?> addJournal(String username, Journal newJournal) {
        Optional<User> userByUserName = userRepo.findUserByUserName(username);
        if (userByUserName.isPresent()) {
            newJournal.setDate(LocalDate.now());
            Journal saved = journalRepo.save(newJournal);
            User user = userByUserName.get();
            user.getJournalsList().add(saved);
            userRepo.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newJournal);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for this name.");
    }

    @Transactional
    public ResponseEntity<?> deleteJournal(String username, ObjectId id) {
        Optional<Journal> findedJournalEntry = journalRepo.findById(id);
        Optional<User> userByUserName = userRepo.findUserByUserName(username);
        if (findedJournalEntry.isPresent() && userByUserName.isPresent()) {
            User user = userByUserName.get();
            var list = user.getJournalsList().stream().filter(x -> x.getId().equals(id)).toList();
            if (!list.isEmpty()) {
                journalRepo.deleteById(id);
                user.getJournalsList().removeIf(x -> x.getId().equals(id));
                userRepo.save(user);
                return ResponseEntity.status(HttpStatus.OK).body("deleted");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal For This ID Or User Is Not Found Or this journal is not liked to Logged User.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal For This ID Or User Is Not Found");
    }


    @Transactional
    public ResponseEntity<?> updateJournal(Journal newJournal, ObjectId id,String username) {
        Journal oldJournal = journalRepo.findById(id).orElse(null);
        var userByUserName = userRepo.findUserByUserName(username);
        if (oldJournal != null && userByUserName.isPresent()) {
            var b = userByUserName.get().getJournalsList().stream().anyMatch(x -> x.getId().equals(id));
            if(b){
            oldJournal.setTitle(newJournal.getTitle() != null && !newJournal.getTitle().isEmpty() ? newJournal.getTitle() : oldJournal.getTitle());
            oldJournal.setContent(newJournal.getContent() != null && !newJournal.getContent().isEmpty() ? newJournal.getContent() : oldJournal.getContent());
            journalRepo.save(oldJournal);
            return ResponseEntity.status(HttpStatus.OK).body(oldJournal);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal For This ID Is Not Found For Logged In User");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal For This ID Is Not Found ");
    }

    public ResponseEntity<?> getJournalById(ObjectId id, String username) {
        Journal journal = journalRepo.findById(id).orElse(null);
        var userByUserName = userRepo.findUserByUserName(username);
        if (journal != null && userByUserName.isPresent()) {
            var b = userByUserName.get().getJournalsList().stream().anyMatch(x -> x.getId().equals(id));
            if(b){
            return ResponseEntity.status(HttpStatus.OK).body(journal);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal For This ID Is Not Found Or Journal For This ID Is Not Linked To Logged In User");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal For This ID Is Not Found Or Journal For This ID Is Not Linked To Logged In User");
    }
}
