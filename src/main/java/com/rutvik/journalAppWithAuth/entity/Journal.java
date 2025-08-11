package com.rutvik.journalAppWithAuth.entity;

import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
public class Journal {
    @Id
    private ObjectId id;
    @NonNull
    private String title;



    private String content;
    private Sentiment sentiment;
    private LocalDate date;
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }
    public Journal(ObjectId id, @NonNull String title, String content, LocalDate date,Sentiment sentiment) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.sentiment = sentiment;
    }

    public Journal(){

    }
    
}
