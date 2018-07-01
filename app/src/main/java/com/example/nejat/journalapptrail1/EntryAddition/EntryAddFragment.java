package com.example.nejat.journalapptrail1.EntryAddition;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.nejat.journalapptrail1.DataModel.EntryModel;
import com.example.nejat.journalapptrail1.Utils.Main;
import com.example.nejat.journalapptrail1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class EntryAddFragment extends DialogFragment implements View.OnClickListener, EntryFragmentContract.EntryFragmnetView {

    private DatabaseReference myRef;
    private EditText mTitleET, mContentET, mDateET;
    private Button mAddBtn;
    private DatePickerDialog datePickerDialog;
    private String mItemId;
    private Main main;
    private int mYear; // current year
    private int mMonth; // current month
    private int mDay; // current Day
    private EntryFragmentPresenter entryFragmentPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_entry_add, container, false);

        entryFragmentPresenter = new EntryFragmentPresenter(this);

        mTitleET = view.findViewById(R.id.etTitle);
        mContentET = view.findViewById(R.id.etcontent);
        mDateET = view.findViewById(R.id.etDate);
        mAddBtn = view.findViewById(R.id.btnAdd);

        mDateET.setText(getCurrentDate());// set current date by default

        mDateET.setOnClickListener(this);
        mAddBtn.setOnClickListener(this);

        return view;
    }

    public String getCurrentDate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR); // current year
        mMonth = c.get(Calendar.MONTH); // current month
        mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        return mDay + "/" + (mMonth + 1) + "/" + mYear;
    }

    private void addEntry(String title, String content, String date) {
        EntryModel entry = new EntryModel(title, content, date);
        if (mItemId != null) {
            myRef.setValue(entry);
        } else {
            myRef.push().setValue(entry);
            Log.i("insertion", "success");

        }

    }

    public void updateItem(String mItemId) {
        myRef = myRef.child(mItemId);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                EntryModel model = dataSnapshot.getValue(EntryModel.class);
                mTitleET.setText(model.getTitle());
                mContentET.setText(model.getContent());
                mDateET.setText(model.getDate());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dismiss();
    }

    @Override
    public void onClick(View v) {
        if (v == mDateET) {
            getCurrentDate();
            datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    mDateET.setText(dayOfMonth + "/"
                            + (month + 1) + "/" + year);
                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == mAddBtn) {
            String title = mTitleET.getText().toString();
            String content = mContentET.getText().toString();
            String dateStr = mDateET.getText().toString();


            main = (Main) getActivity().getApplicationContext();
            mItemId = main.getItemId();
            Log.i("updatemit", mItemId + "");

            if (mItemId == null) {
                entryFragmentPresenter.saveDataEntry(title, content, dateStr);
            } else {
                entryFragmentPresenter.updateDataEntry(title, content, dateStr, mItemId);
            }
        }
    }

    @Override
    public void showContentEmpty() {
        mContentET.setError("Empty content");
    }

    @Override
    public void showTitleEmpty() {
        mTitleET.setError("title empty");

    }

    @Override
    public void setDefaultDate() {
        mDateET.setText(getCurrentDate());
    }

    @Override
    public void cancelFragment() {
        getDialog().cancel();
        Main main = (Main) getActivity().getApplicationContext();
        main.setItemId(null);
    }
}