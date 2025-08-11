package com.rutvik.journalAppWithAuth.service;

import com.rutvik.journalAppWithAuth.entity.Sentiment;
import com.rutvik.journalAppWithAuth.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public boolean sentimentAnalysisSendMailService(String stringOfSentiments, User user) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(user.getEmail());
            mail.setSubject("Sentiment Analysis Mail JA");
            List<String> sentementList = Arrays.asList(stringOfSentiments.split("\\s+"));
            String finalSentiment="";
            if (!sentementList.isEmpty()) {
                int goodCount=0;
                int averageCount=0;
                int badCount=0;
                for (String s :sentementList) {
                    if(s.equalsIgnoreCase(Sentiment.Good.toString())){
                        goodCount=goodCount+1;
                    }else if(s.equalsIgnoreCase(Sentiment.Bad.toString())){
                        badCount=badCount+1;
                    }else{
                      averageCount=averageCount+1;
                    }
                }
                if(goodCount>=badCount && goodCount >= averageCount){
                    finalSentiment=Sentiment.Good.toString();
                }else if(badCount>=goodCount && badCount >=averageCount){
                    finalSentiment=Sentiment.Bad.toString();
                }else {
                    finalSentiment=Sentiment.Average.toString();
                }
            }
            mail.setText("Your final sentiment for this week is :: "+finalSentiment);
            javaMailSender.send(mail);
            return true;
        } catch (Exception e) {
            log.error("Exception in MailService methode :: sentimentAnalysisSendMailService e:", e);
        }
        return false;
    }
}
