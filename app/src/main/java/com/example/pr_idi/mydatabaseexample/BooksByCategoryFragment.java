package com.example.pr_idi.mydatabaseexample;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

// RecyclerView basic guide: https://guides.codepath.com/android/Using-the-RecyclerView
// "Complex" RecyclerView guide: https://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView

public class BooksByCategoryFragment extends Fragment {
    private BookData mBookData;
    private RecyclerView mRV;

    public BooksByCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        ft.hide(this);
        ft.commit();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainActivity act = (MainActivity)getActivity();
        mBookData = act.GetBookData();

        if (mBookData != null) {
            List<Book> books = mBookData.getAllBooksByCategory();
            List<Object> values = mBookData.CreateListByCategory();

            // Working with a Recycler View
            // Lookup the recyclerview in activity layout
            mRV = (RecyclerView) act.findViewById(R.id.BooksRV);

            // Create adapter passing in the sample user data
            BookRVAdapter adapter = new BookRVAdapter(act, values); // Need to change something, maybe organize the books in sub-lists -> TO THINK

            // Attach the adapter to the recyclerview to populate items
            mRV.setAdapter(adapter);

            // Set layout manager to position the items
            mRV.setLayoutManager(new LinearLayoutManager(act));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_books_by_category, container, false);
    }

    public RecyclerView GetRecyclerView() {
        return mRV;
    }
}
