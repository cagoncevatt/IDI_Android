package com.example.pr_idi.mydatabaseexample;


import java.util.Calendar;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public final static String ADD_BOOK_BUTTON = "AddBook_FloatingButton";

    private BookData bookData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(ADD_BOOK_BUTTON)) {
                View addButton = findViewById(R.id.floatingActionButtonAdd);
                addButton.setVisibility(View.VISIBLE);
            }
        }

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
            case R.id.add: {
                //String[] newBook = new String[] { "Miguel Strogoff", "Jules Verne", "Ulysses", "James Joyce", "Don Quijote", "Miguel de Cervantes", "Metamorphosis", "Kafka" };
                //int nextInt = new Random().nextInt(4);
                // save the new book to the database
                //book = bookData.createBook(newBook[nextInt*2], newBook[nextInt*2 + 1], "asd", 2000, "F", "RegExp");

                // After I get the book data, I add it to the adapter
                //adapter.add(book);
                FragmentManager mgr = getFragmentManager();
                Fragment frag = mgr.findFragmentById(R.id.fragmentAddBook);

                FragmentTransaction ft = mgr.beginTransaction();
                ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                ft.show(frag);
                ft.commit();

                View addButton = findViewById(R.id.floatingActionButtonAdd);
                Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.abc_slide_in_bottom);
                addButton.setVisibility(View.VISIBLE);
                addButton.setAnimation(fadeIn);
                break;
            }
            case R.id.delete:
                /*if (getListAdapter().getCount() > 0) {
                    book = (Book) getListAdapter().getItem(0);
                    bookData.deleteBook(book);
                    adapter.remove(book);
                }*/
                break;
            case R.id.catList: {
                // For the moment is only implemented de show (the list is initially hidden and doesn't update yet)
                FragmentManager mgr = getFragmentManager();
                Fragment frag = mgr.findFragmentById(R.id.fragmentBooksByCategory);

                FragmentTransaction ft = mgr.beginTransaction();
                ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                ft.show(frag);
                ft.commit();
                break;
            }
        }
        //adapter.notifyDataSetChanged();
    }

    public void registerBook(View view) {
        String result = addBook();

        String res[] = result.split("|");

        if (!res[0].equals("OK")) {
            // Use Toast here to make msg
            System.out.println("ERROR");// Error msg is in result
        }
        else {
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.coordinatorLayoutAddBook), res[2] + " - Registered.", Snackbar.LENGTH_INDEFINITE)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });

            // Changing message text color
            snackbar.setActionTextColor(Color.RED);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.GREEN);

            snackbar.show();

            // Clear fragment views inputs
        }
    }

    public String addBook() {
        String res = "";

        // Check fields
        String title, author, publisher, persEval, category, yearS;
        int year;

        publisher = ((EditText)findViewById(R.id.editTextPublisher)).getText().toString();
        persEval = ((TextView)findViewById(R.id.textViewPersonalEvaluation)).getText().toString();
        category = ((EditText)findViewById(R.id.editTextCategory)).getText().toString();
        author = ((EditText)findViewById(R.id.editTextAuthor)).getText().toString();

        title = ((EditText)findViewById(R.id.editTextTitle)).getText().toString();

        if (title.isEmpty())
            res += "- Error title empty\n";

        yearS = ((EditText)findViewById(R.id.editTextYear)).getText().toString();

        if (yearS.isEmpty()) {
            res += "- Error missing year\n";
            year = -1;
        }
        else {
            year = Integer.parseInt(yearS);

            if (year > Calendar.getInstance().get(Calendar.YEAR))
                res += "- Error invalid year\n";
        }

        if (res.isEmpty()) {
            Book b = bookData.createBook(title, author, publisher, year, category, persEval);

            res += "OK|";
            res += b.getId() + "|" + b.getTitle();
        }

        return res;
    }

    public BookData GetBookData() {
        return bookData;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        View addButton = findViewById(R.id.floatingActionButtonAdd);

        if (addButton != null)
            outState.putBoolean(ADD_BOOK_BUTTON, (addButton.getVisibility() == View.VISIBLE));
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