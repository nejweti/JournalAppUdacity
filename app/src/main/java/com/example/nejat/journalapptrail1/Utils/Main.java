package com.example.nejat.journalapptrail1.Utils;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class Main extends Application {
    public String itemId;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
