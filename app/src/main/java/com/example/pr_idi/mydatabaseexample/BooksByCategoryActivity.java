package com.example.pr_idi.mydatabaseexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// RecyclerView basic guide: https://guides.codepath.com/android/Using-the-RecyclerView

public class BooksByCategoryActivity extends AppCompatActivity {
    private BookData mBookData;
    private RecyclerView mRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_by_category);

        mBookData = BookData.GetInstance();
        mBookData.open();

        List<Book> books = mBookData.getAllBooksByCategory();
        List<Object> values = new ArrayList<>();

        if (!books.isEmpty()) {
            String cat = "";

            for (int i = 0; i < books.size(); ++i) {
                Book b = books.get(i);

                if (!cat.equals(b.getCategory())) {
                    cat = b.getCategory();
                    values.add(cat);
                }

                values.add(b);
            }
        }

        // Working with a Recycler View
        // Lookup the recyclerview in activity layout
        mRV = (RecyclerView)findViewById(R.id.BooksRV);

        // Create adapter passing in the sample user data
        BookRVAdapter adapter = new BookRVAdapter(this, values); // Need to change something, maybe organize the books in sub-lists -> TO THINK

        // Attach the adapter to the recyclerview to populate items
        mRV.setAdapter(adapter);

        // Set layout manager to position the items
        mRV.setLayoutManager(new LinearLayoutManager(this));

        // Should use the same adapter as above but sorting the list based on category?
        // Or can do something like "sub-lists" to contain each category?
    }


    // Life cycle methods. Check whether it is necessary to reimplement them
    @Override
    protected void onResume() {
        mBookData.open();
        super.onResume();
    }

    // Life cycle methods. Check whether it is necessary to reimplement them

    @Override
    protected void onPause() {
        mBookData.close();
        super.onPause();
    }
}
