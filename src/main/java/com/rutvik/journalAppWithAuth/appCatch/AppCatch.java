package com.rutvik.journalAppWithAuth.appCatch;

import com.rutvik.journalAppWithAuth.Repositery.ConfigureJournalAppRepo;
import com.rutvik.journalAppWithAuth.entity.ConfigureJournalApp;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class AppCatch {
    public enum keys{
        WEATHER_API;
    }
    @Autowired
    private ConfigureJournalAppRepo configureJournalAppRepo;
    public HashMap<String,String> appCatchMap;
    @PostConstruct
    public void init(){
        appCatchMap=new HashMap<>();
        List<ConfigureJournalApp> all = configureJournalAppRepo.findAll();
        for (ConfigureJournalApp config:all) {
           appCatchMap.put(config.getKey(),config.getValue());
        }
    }
}
