package com.example.nejat.journalapptrail1.EntryAddition;

import com.example.nejat.journalapptrail1.Utils.Main;

public class EntryFragmentPresenter implements EntryFragmentContract.EntryFragmentPresenter,EntryFragmentContract.EntryFragmentModel.onAddEntry {
    private EntryAddFragment entryAddFragment;
    private EntryFragmentModel entryFragmentModel;

    public EntryFragmentPresenter(EntryAddFragment entryAddFragment) {
        this.entryAddFragment = entryAddFragment;
        entryFragmentModel = new EntryFragmentModel();
    }

    @Override
    public void saveDataEntry(String title, String content,String date) {
        entryFragmentModel.validateDateEntry(title,content,date, null,this);

    }

    @Override
    public void onContentEmpty() {
        entryAddFragment.showContentEmpty();
    }

    @Override
    public void onTitleEmpty() {
        entryAddFragment.showTitleEmpty();
    }

    @Override
    public void onDateEmpty() {
        entryAddFragment.setDefaultDate();
    }

    @Override
    public void onSuccess(String title, String content, String date) {
        entryAddFragment.cancelFragment();

    }

    @Override
    public void updateDataEntry(String title, String content, String date,String itemId) {
        entryFragmentModel.validateDateEntry(title,content,date, itemId,this);

    }
}
