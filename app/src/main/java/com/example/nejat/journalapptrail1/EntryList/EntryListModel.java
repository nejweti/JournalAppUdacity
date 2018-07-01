package com.example.nejat.journalapptrail1.EntryList;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.res.TypedArray;
import android.graphics.Color;

import com.example.nejat.journalapptrail1.DataModel.EntryModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class EntryListModel implements EntryListContract.EntryListModel {
    private Query query;
    private FirebaseRecyclerOptions<EntryModel> options;



    @Override
    public void getQuery(String node, onEntryListChanged onEntryListChanged) {
        query = FirebaseDatabase.getInstance()
                .getReference(node)
                .child(getCurrentUser());
        query.keepSynced(true);

        options =
                new FirebaseRecyclerOptions.Builder<EntryModel>()
                        .setQuery(query, EntryModel.class)
                        .build();

        onEntryListChanged.onEntryListChanged(query, options);
    }


    public String getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public void deleteData(String id, String node, FirebaseRecyclerAdapter adapter, onSetDeletedData onSetDeletedData,onEntryListChanged onEntryListChanged) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(node).child(FirebaseAuth.getInstance().getUid());
        mDatabase.child(id).removeValue();
        mDatabase.keepSynced(true);

        adapter.notifyDataSetChanged();
        onEntryListChanged.onEntryListChanged(query,options);



        onSetDeletedData.onsetDeletedData(mDatabase, node);

    }

//    public int getRandomMaterialColorandLetter(String typeColor, int arrayId) {
//        int returnColor = Color.GRAY;
////        int arrayId = getResources().getIdentifier("mdcolor_" + typeColor, "array", getPackageName());
//
//        if (arrayId != 0) {
//            TypedArray colors = activity.getResources().obtainTypedArray(arrayId);
//            int index = (int) (Math.random() * colors.length());
//            returnColor = colors.getColor(index, Color.GRAY);
//            colors.recycle();
//        }
//        return returnColor;
//    }
}
