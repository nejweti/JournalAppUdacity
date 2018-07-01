package com.example.nejat.journalapptrail1.EntryList;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.transition.ChangeBounds;
import android.support.transition.TransitionManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nejat.journalapptrail1.DataModel.EntryModel;
import com.example.nejat.journalapptrail1.EntryAddition.EntryAddFragment;
import com.example.nejat.journalapptrail1.R;
import com.example.nejat.journalapptrail1.Utils.Main;
import com.example.nejat.journalapptrail1.ViewHolder.EntryHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class EntryListPresenter implements EntryListContract.EntryListPresenter,EntryListContract.EntryListModel.onEntryListChanged,EntryListContract.EntryListModel.onSetActionBar,EntryListContract.EntryListModel.onSetDeletedData{

    private EntryListActivity entryView;
    private EntryListModel entryModel;
    private  FirebaseRecyclerAdapter adapter;
    private DatabaseReference mDatabase;

    public EntryListPresenter(EntryListActivity entryView) {
        this.entryView = entryView;
        entryModel = new EntryListModel();
    }
    @Override
    public void getAdapter(String node) {
        entryModel.getQuery(node,this);
    }

    @Override
    public FirebaseRecyclerAdapter onEntryListChanged(Query query, FirebaseRecyclerOptions<EntryModel> options) {

        adapter = new FirebaseRecyclerAdapter<EntryModel, EntryHolder>(options) {
            @NonNull
            @Override
            public EntryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.entry_item_view,parent,false);


                return new EntryHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final EntryHolder holder, final int position, @NonNull EntryModel model) {
                entryView.attachView(model.getTitle(),model.getContent(),model.getDate(),holder);

                holder.entryUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateFragment(adapter.getRef(position).getKey());
                    }
                });

                holder.entryDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDatabase = FirebaseDatabase.getInstance().getReference("Entry").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(adapter.getRef(position).getKey());
                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                dataSnapshot.getRef().removeValue();
                                entryView.attachRecyclerView(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                });
                holder.expandArrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean shouldExpand = holder.expandContent.getVisibility() == View.GONE;
                        ChangeBounds transition = new ChangeBounds();
                        transition.setDuration(200);

                        if(shouldExpand){
                            entryView.expandArror(holder);
                        }
                        else {
                            entryView.collapsArrow(holder);
                        }
                        entryView.startTransition(holder,transition,shouldExpand);



                    }
                });
            }
        };
        return adapter;

    }
    public FirebaseRecyclerAdapter passRecyclerAdapter() {
        return adapter;
    }



    @Override
    public void setAdapter() {
        entryView.attachRecyclerView(adapter);

    }

    @Override
    public void onStart() {
        entryView.onStart();
    }



    @Override
    public void getAddFragment() {
        EntryAddFragment fragment = new EntryAddFragment();
        entryView.showAddFragment(fragment);
    }

    @Override
    public void updateFragment(String id) {
        entryView.showUpdateFragmnet(id);
        Log.i("updatepresentfrag",id+"");
    }




    @Override
    public void onSetActionBar(String tile) {
        entryView.attachActionBar(tile);
    }

    @Override
    public void onsetDeletedData(DatabaseReference mDatabase,String node) {
        entryModel.getQuery(node,this);
        adapter.notifyDataSetChanged();

    }
    @Override
    public void deleteFragment(String id,String node,FirebaseRecyclerAdapter adapter) {
        entryModel.deleteData(id,node,adapter,this,this);
    }

    @Override
    public void notifyAdapter(FirebaseRecyclerAdapter adapter) {
        entryView.attachRecyclerView(adapter);
    }
}