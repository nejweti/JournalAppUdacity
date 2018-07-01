package com.example.nejat.journalapptrail1.EntryList;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.transition.ChangeBounds;
import android.support.transition.TransitionManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.nejat.journalapptrail1.DataModel.EntryModel;
import com.example.nejat.journalapptrail1.EntryAddition.EntryAddFragment;
import com.example.nejat.journalapptrail1.GoogleSignIn.GSigninActivity;
import com.example.nejat.journalapptrail1.Utils.Library;
import com.example.nejat.journalapptrail1.Utils.Main;
import com.example.nejat.journalapptrail1.R;
import com.example.nejat.journalapptrail1.ViewHolder.EntryHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class EntryListActivity extends AppCompatActivity implements View.OnClickListener,EntryListContract.EntryListView{

    private FloatingActionButton addEntryBtn;
    private ImageButton entryEditBtn, entryDeleteBtn;
    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter adapter;
    private EntryListPresenter entryPresenter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_list);
        new EntryListPresenter(this);
        entryPresenter = new EntryListPresenter(this);

        entryPresenter.onSetActionBar("Hi,Welcome to your Journal");

        addEntryBtn = findViewById(R.id.fab);
        mRecyclerView = findViewById(R.id.recyclerview);
        entryDeleteBtn = findViewById(R.id.entry_delete);
        entryEditBtn = findViewById(R.id.entry_update);


        addEntryBtn.setOnClickListener(this);

        entryPresenter.getAdapter("Entry");
        entryPresenter.setAdapter();
        entryPresenter.onStart();

    }
    @Override
    public void onClick(View v) {
        if(v == addEntryBtn){
            entryPresenter.getAddFragment();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        entryPresenter.passRecyclerAdapter().startListening();

    }


    @Override
    protected void onStop() {
        super.onStop();
        entryPresenter.passRecyclerAdapter().stopListening();

    }
    private int getRandomMaterialColor(String typeColor) {
        int returnColor = Color.GRAY;
        int arrayId = getResources().getIdentifier("mdcolor_" + typeColor, "array", getPackageName());

        if (arrayId != 0) {
            TypedArray colors = getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(EntryListActivity.this, GSigninActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void attachView(String title, String content, String date, EntryHolder holder) {
        holder.content.setText(content);
        holder.title.setText(title);
        holder.date.setText(date);
        holder.letterTV.setText((title+"X").charAt(0)+"");
    }

    @Override
    public void attachRecyclerView(FirebaseRecyclerAdapter adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    public void attachActionBar(String title) {
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void showAddFragment(EntryAddFragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        fragment.show(manager,"Fragment");
    }

    @Override
    public void showUpdateFragmnet(String id) {
        Main main = (Main) getApplicationContext();
        main.setItemId(id);
        EntryAddFragment fragment = new EntryAddFragment();
        showAddFragment(fragment);
//        main.setItemId(null);

    }

    @Override
    public void showDeleteAlertBuilder(final String id, final String node, final FirebaseRecyclerAdapter adapter) {
        Library library = new Library(this);
        library.AlertDialogInterface("Are you sure about deleting?", "Confirm", new Library.exitDecision() {
                            @Override
                            public void decision(boolean bool) {
                                if(bool){
                                    entryPresenter.deleteFragment(id,node,adapter);
                                    entryPresenter.passRecyclerAdapter().notifyDataSetChanged();
                                    entryPresenter.passRecyclerAdapter().startListening();

//                                    mDatabase.child(adapter.getRef(position).getKey()).removeValue();
//                                    adapter.notifyDataSetChanged();

                                }
                                else{
                                }
                            }
                        });
    }

    @Override
    public void expandArror(EntryHolder holder) {
        holder.expandContent.setVisibility(View.VISIBLE);
        holder.arrow.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
        holder.expandArrow.setBackground(getResources().getDrawable(R.drawable.curved_linear_shape));
        holder.expandArrow.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        holder.expandContent.setBackgroundColor(getResources().getColor(R.color.expandcolor));

    }

    @Override
    public void collapsArrow(EntryHolder holder) {
        holder.expandContent.setVisibility(View.GONE);
        holder.expandContent.setBackgroundColor(getResources().getColor(R.color.expandcolor));
        holder.expandArrow.setBackgroundColor(getResources().getColor(R.color.collapsecolor));
        holder.expandArrow.setBackground(getResources().getDrawable(R.drawable.curved_linear_shape));
        holder.arrow.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);


    }

    @Override
    public void startTransition(EntryHolder holder,ChangeBounds transition,boolean shouldExpand) {
        TransitionManager.beginDelayedTransition(mRecyclerView, transition);
        holder.expandArrow.setActivated(shouldExpand);
    }


}
