package com.example.pr_idi.mydatabaseexample;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

// RecyclerView basic guide: https://guides.codepath.com/android/Using-the-RecyclerView
// "Complex" RecyclerView guide: https://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView

public class BooksByCategoryActivity extends Fragment {
    private BookData mBookData;
    private RecyclerView mRV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_books_by_category, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        Activity act = getActivity();
        mRV = (RecyclerView) act.findViewById(R.id.BooksRV);

        // Create adapter passing in the sample user data
        BookRVAdapter adapter = new BookRVAdapter(act, values); // Need to change something, maybe organize the books in sub-lists -> TO THINK

        // Attach the adapter to the recyclerview to populate items
        mRV.setAdapter(adapter);

        // Set layout manager to position the items
        mRV.setLayoutManager(new LinearLayoutManager(act));

        // Should use the same adapter as above but sorting the list based on category?
        // Or can do something like "sub-lists" to contain each category?
    }


    /*// Life cycle methods. Check whether it is necessary to reimplement them
    @Override
    public void onResume() {
        mBookData.open();
        super.onResume();
    }

    // Life cycle methods. Check whether it is necessary to reimplement them

    @Override
    public void onPause() {
        mBookData.close();
        super.onPause();
    }*/
}
