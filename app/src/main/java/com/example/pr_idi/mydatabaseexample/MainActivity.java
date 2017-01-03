package com.example.pr_idi.mydatabaseexample;


import java.util.List;
import java.util.Random;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.ArrayAdapter;

public class MainActivity extends FragmentActivity /*ListActivity*/ {
    private BookData bookData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        bookData = new BookData(this);
        bookData.open();

        List<Book> values = bookData.getAllBooks();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Book> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, values);
        //setListAdapter(adapter);
    }

    // Basic method to add pseudo-random list of books so that
    // you have an example of insertion and deletion

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        //ArrayAdapter<Book> adapter = (ArrayAdapter<Book>) getListAdapter();
        Book book;
        switch (view.getId()) {
            case R.id.add:
                String[] newBook = new String[] { "Miguel Strogoff", "Jules Verne", "Ulysses", "James Joyce", "Don Quijote", "Miguel de Cervantes", "Metamorphosis", "Kafka" };
                int nextInt = new Random().nextInt(4);
                // save the new book to the database
                book = bookData.createBook(newBook[nextInt*2], newBook[nextInt*2 + 1], "asd", 2000, "F", "RegExp");

                // After I get the book data, I add it to the adapter
                //adapter.add(book);
                break;
            case R.id.delete:
                /*if (getListAdapter().getCount() > 0) {
                    book = (Book) getListAdapter().getItem(0);
                    bookData.deleteBook(book);
                    adapter.remove(book);
                }*/
                break;
            case R.id.catList:
                // For the momento is only implemented de show (the list is initially hidden and doesn't update yet)
                FragmentManager mgr = getFragmentManager();
                Fragment frag = mgr.findFragmentById(R.id.fragmentBooksByCategory);

                FragmentTransaction ft = mgr.beginTransaction();
                ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                ft.show(frag);
                ft.commit();
                break;
        }
        //adapter.notifyDataSetChanged();
    }

    public BookData GetBookData() {
        return bookData;
    }

    // Life cycle methods. Check whether it is necessary to reimplement them
    @Override
    protected void onResume() {
        bookData.open();
        super.onResume();
    }

    // Life cycle methods. Check whether it is necessary to reimplement them

    @Override
    protected void onPause() {
        bookData.close();
        super.onPause();
    }

}