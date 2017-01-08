package com.example.pr_idi.mydatabaseexample;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ale on 01/01/2017.
 */

public class BookViewHolder extends RecyclerView.ViewHolder {
    private TextView titleTextView;
    private Button addButton;
    private Book mBook;

    BookViewHolder(View itemView) {
        super(itemView);

        titleTextView = (TextView) itemView.findViewById(R.id.bookTitle);
        addButton = (Button) itemView.findViewById(R.id.add_button);
    }

    public void SetBook(Book b) {
        mBook = b;
    }

    public void SetText(String txt) {
        titleTextView.setText(txt);
    }

    public void SetTextView(TextView tv) {
        titleTextView = tv;
    }

    public Book GetBook() {
        return mBook;
    }

    public String GetText() {
        return titleTextView.getText().toString();
    }

    public TextView GetTextView() {
        return titleTextView;
    }
}
