package com.example.nejat.journalapptrail1.EntryAddition;

public interface EntryFragmentContract {
    interface EntryFragmnetView{
        void showContentEmpty();
        void showTitleEmpty();
        void setDefaultDate();
        void cancelFragment();

    }
    interface EntryFragmentModel{
        interface onAddEntry{
            void onContentEmpty();
            void onTitleEmpty();
            void onDateEmpty();
            void onSuccess(String title, String content, String date);
        }
        void validateDateEntry(String title, String content,String date,String itemId, onAddEntry onAddEntry);

    }
    interface EntryFragmentPresenter{
        void saveDataEntry(String title, String content,String date);
        void updateDataEntry(String title,String content,String date,String itemId);

    }
}
