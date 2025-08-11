package com.rutvik.journalAppWithAuth.scheduler;

import com.rutvik.journalAppWithAuth.Repositery.UserRepoImpl;
import com.rutvik.journalAppWithAuth.entity.Journal;
import com.rutvik.journalAppWithAuth.entity.User;
import com.rutvik.journalAppWithAuth.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MailScheduler {
    @Autowired
    private UserRepoImpl userRepoImpl;
    @Autowired
    private MailService mailService;
    @Scheduled(cron = "0 0 0 ? * SUN")
    public void sentimentAnalysisSendMailServiceScheduler(){
        List<User> allUserWhoHaveSAE=new ArrayList<>();
        allUserWhoHaveSAE = userRepoImpl.getAllUserWhoHaveSAE();
       if(!allUserWhoHaveSAE.isEmpty()){
           for(User user:allUserWhoHaveSAE){
               List<Journal> journalsList = user.getJournalsList();
               if(!journalsList.isEmpty()){
                   List<String> filteredJournalSentimentStringList = journalsList.stream().filter(x -> x.getDate().isAfter(LocalDate.now().minusDays(7))).map(x->x.getSentiment().toString()).toList();
//                 List<String> filteredJournalSentimentString = journalsList.stream().filter(x -> x.getDate().isAfter(LocalDate.now().minusDays(7))).map(Journal::getContent).toList();
                   if(!filteredJournalSentimentStringList.isEmpty()){
                       String finalJournalSentimentString = String.join(" ", filteredJournalSentimentStringList);
                       boolean sentimantSend=mailService.sentimentAnalysisSendMailService(finalJournalSentimentString,user);
                   }
               }
           }
       }
    }
}
