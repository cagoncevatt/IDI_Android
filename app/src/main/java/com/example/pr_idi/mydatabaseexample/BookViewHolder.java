package com.example.pr_idi.mydatabaseexample;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Ale on 01/01/2017.
 */

public class BookViewHolder extends RecyclerView.ViewHolder {
    private TextView titleTextView;
    private Button addButton;
    private long mId;

    BookViewHolder(View itemView) {
        super(itemView);

        titleTextView = (TextView) itemView.findViewById(R.id.bookTitle);
        addButton = (Button) itemView.findViewById(R.id.add_button);
    }

    public void SetId(long id) {
        mId = id;
    }

    public void SetText(String txt) {
        titleTextView.setText(txt);
    }

    public void SetTextView(TextView tv) {
        titleTextView = tv;
    }

    public long GetId() {
        return mId;
    }

    public String GetText() {
        return titleTextView.getText().toString();
    }

    public TextView GetTextView() {
        return titleTextView;
    }
}
