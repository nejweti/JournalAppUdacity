package com.example.nejat.journalapptrail1.DataModel;

import java.util.Date;

public class EntryModel {
    public String title;
    public String content;
    public String date;

    public EntryModel() {
    }

    public EntryModel(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
