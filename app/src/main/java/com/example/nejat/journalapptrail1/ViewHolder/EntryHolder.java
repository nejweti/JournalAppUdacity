package com.example.nejat.journalapptrail1.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nejat.journalapptrail1.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class EntryHolder extends RecyclerView.ViewHolder {
    public TextView title, content, date, letterTV;
    public ImageButton entryUpdate, entryDelete;
    public LinearLayout expandContent,expandArrow;
    public ImageView arrow;
    public CircleImageView circleImage;

    public EntryHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        content = itemView.findViewById(R.id.content);
        date = itemView.findViewById(R.id.date);
        entryDelete = itemView.findViewById(R.id.entry_delete);
        entryUpdate = itemView.findViewById(R.id.entry_update);
        expandArrow = itemView.findViewById(R.id.expandArrow);
        expandContent = itemView.findViewById(R.id.expandContent);
        arrow = itemView.findViewById(R.id.arrow);
        letterTV = itemView.findViewById(R.id.letterTV);
        circleImage = itemView.findViewById(R.id.circle_image);


    }
}
