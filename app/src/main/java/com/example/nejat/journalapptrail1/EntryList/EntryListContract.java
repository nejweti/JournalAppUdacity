package com.example.nejat.journalapptrail1.EntryList;

import android.app.Activity;
import android.app.FragmentManager;
import android.transition.ChangeBounds;

import com.example.nejat.journalapptrail1.DataModel.EntryModel;
import com.example.nejat.journalapptrail1.EntryAddition.EntryAddFragment;
import com.example.nejat.journalapptrail1.Utils.Library;
import com.example.nejat.journalapptrail1.ViewHolder.EntryHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public interface EntryListContract {
    interface EntryListView {
        void attachView(String title, String content, String date, EntryHolder holder);
        void attachRecyclerView(FirebaseRecyclerAdapter adapter);
        void attachActionBar(String title);
        void showAddFragment(EntryAddFragment fragment);
        void showUpdateFragmnet(String id);
        void showDeleteAlertBuilder(String id, String node,FirebaseRecyclerAdapter adapter);
        void expandArror(EntryHolder holder);
        void collapsArrow(EntryHolder holder);
        void startTransition(EntryHolder holder,android.support.transition.ChangeBounds transition,boolean bool);

    }
    interface AlertDialogInterface{
        void exitDecision();
    }

    interface EntryListModel {

        interface onEntryListChanged {
            FirebaseRecyclerAdapter onEntryListChanged(Query query, FirebaseRecyclerOptions<EntryModel> options);
        }
        interface onSetActionBar{
            void onSetActionBar(String tile);
        }
        interface onSetDeletedData{
            void onsetDeletedData(DatabaseReference mDatabase,String node);
        }
        void deleteData(String id, String node,FirebaseRecyclerAdapter adapter,onSetDeletedData onSetDeletedData,onEntryListChanged onEntryListChanged);
        void getQuery(String node, onEntryListChanged onEntryListChanged);


    }

    interface EntryListPresenter {
        void setAdapter();
        void getAdapter(String node);
        void onStart();
        void getAddFragment();
        void updateFragment(String id);
        void deleteFragment(String id,String node,FirebaseRecyclerAdapter adapter);

    }
}
