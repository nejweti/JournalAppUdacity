package com.example.nejat.journalapptrail1.EntryAddition;

import android.text.TextUtils;
import android.util.Log;

import com.example.nejat.journalapptrail1.DataModel.EntryModel;
import com.example.nejat.journalapptrail1.Utils.Main;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EntryFragmentModel implements EntryFragmentContract.EntryFragmentModel {
    private DatabaseReference myRef;
    private Main main;

    @Override
    public void validateDateEntry(String title, String content, String date, String itemId, onAddEntry onAddEntry) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("Entry").child(firebaseAuth.getUid());


        if (TextUtils.isEmpty(title)) {
            onAddEntry.onTitleEmpty();
        } else if (TextUtils.isEmpty(content)) {
            onAddEntry.onContentEmpty();
        } else if (TextUtils.isEmpty(date)) {
            onAddEntry.onDateEmpty();
        } else {
            EntryModel entry = new EntryModel(title, content, date);

            if (itemId != null) {
                myRef.child(itemId).setValue(entry);
                Log.i("updation", "success" + itemId);

            } else {
                myRef.push().setValue(entry);
                Log.i("insertion", "success" + itemId);

            }
            onAddEntry.onSuccess(title, content, date);
        }
    }
}
