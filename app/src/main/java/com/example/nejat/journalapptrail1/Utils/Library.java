package com.example.nejat.journalapptrail1.Utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;

public class Library {

    public static Activity activity;

    public Library(Activity activity) {
        this.activity = activity;
    }

    public void AlertDialogInterface(String Message, String Title, final exitDecision exitDecision) {
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(activity);

        } else {
            builder = new AlertDialog.Builder(activity);
        }

        final AlertDialog dialog = builder.setTitle(Message)
                .setMessage(Title)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        exitDecision.decision(true);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        exitDecision.decision(false);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
            }
        });
    }

    public interface exitDecision {
        void decision(boolean bool);
    }
}

