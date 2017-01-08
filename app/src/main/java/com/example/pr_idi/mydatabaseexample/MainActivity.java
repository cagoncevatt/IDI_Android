package com.example.pr_idi.mydatabaseexample;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public final static String ADD_BOOK_BUTTON = "AddBook_FloatingButton";
    public final static String SHARED_PREFERENCE_KEY = "MyBookDatabase_SharedPreference";
    public final static String SHARED_PREFERENCE_FIRST_RUN = "SharedPreference_AppFirstRun";

    private BookData bookData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SharedPreferences pref = getSharedPreferences(SHARED_PREFERENCE_KEY, MODE_PRIVATE);
        bookData = new BookData(this);

        bookData.open();

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(ADD_BOOK_BUTTON)) {
                View addButton = findViewById(R.id.floatingActionButtonAdd);
                addButton.setVisibility(View.VISIBLE);
            }
        }

        if (pref.getBoolean(SHARED_PREFERENCE_FIRST_RUN, true)) {

            bookData.createBook("El dios asesinado en el servicio de caballeros", "Sergio S. Morán", "Fantascy", 2016, "Fantasy", "Good");
            bookData.createBook("World of Warcraft: Arthas: Rise of the Lich King", "Christie Golden", "Pocket Books", 2009, "Fantasy", "Very Good");
            bookData.createBook("The Iron Druid Chronicles: Hounded", "Kevin Hearne", "Del Rey Books", 2011, "Urban Fantasy", "Very Bad");
            bookData.createBook("The Black Cat", "Edgar Allan Poe", "United States Saturday Post", 1843, "Horror", "Good");

            pref.edit().putBoolean(SHARED_PREFERENCE_FIRST_RUN, false).commit();
        }

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

    public void resetAddFields(View view) {
        EditText publisherET, catET, authorET, titleET, yearET;
        RatingBar peRB = (RatingBar) findViewById(R.id.ratingBarPersonalEvaluation);

        publisherET = (EditText)findViewById(R.id.editTextPublisher);
        catET = (EditText)findViewById(R.id.editTextCategory);
        authorET = (EditText)findViewById(R.id.editTextAuthor);
        titleET = (EditText)findViewById(R.id.editTextTitle);
        yearET = (EditText)findViewById(R.id.editTextYear);

        peRB.setRating(3.0f);
        publisherET.setText("");
        authorET.setText("");
        titleET.setText("");
        catET.setText("");
        yearET.setText("");
    }

    public void registerBook(View view) {
        String res = "";

        // Check fields
        final MainActivity inst = this;
        final EditText publisherET, catET, authorET, titleET, yearET;
        final String title, author, publisher, persEval, category, yearS;
        int year = -1;

        publisherET = (EditText)findViewById(R.id.editTextPublisher);
        catET = (EditText)findViewById(R.id.editTextCategory);
        authorET = (EditText)findViewById(R.id.editTextAuthor);
        titleET = (EditText)findViewById(R.id.editTextTitle);
        yearET = (EditText)findViewById(R.id.editTextYear);

        publisher = publisherET.getText().toString();
        persEval = ((TextView)findViewById(R.id.textViewPersonalEvaluation)).getText().toString();
        category = catET.getText().toString();
        author = authorET.getText().toString();
        title = titleET.getText().toString();

        if (title.isEmpty())
                res += "> Error: Missing Title.";

        yearS = yearET.getText().toString();

        if (yearS.isEmpty()) {
            if (res.isEmpty())
                res += "> Error: Missing Year.";
            else
                res += "\n> Error: Missing Year.";
        }
        else {
            year = Integer.parseInt(yearS);

            if (year > Calendar.getInstance().get(Calendar.YEAR)) {
                if (res.isEmpty())
                    res += "> Error: Invalid Year. Must be lesser or equal than current year.";
                else
                    res += "\n> Error: Invalid Year. Must be lesser or equal than current year.";
            }
        }

        if (res.isEmpty()) {
            BooksByCategoryFragment booksCat = (BooksByCategoryFragment)getFragmentManager().findFragmentById(R.id.fragmentBooksByCategory);
            final BookRVAdapter adapter = (BookRVAdapter)booksCat.GetRecyclerView().getAdapter();
            final List<Object> initList = adapter.GetElements();

            final RatingBar peRB = (RatingBar) findViewById(R.id.ratingBarPersonalEvaluation);
            String auxAuth, auxPublisher;

            if (author.isEmpty())
                auxAuth = "Unknown Author";
            else
                auxAuth = author;

            if (publisher.isEmpty())
                auxPublisher = "Unknown Publisher";
            else
                auxPublisher = publisher;

            final Book b = bookData.createBook(title, auxAuth, auxPublisher, year, category, persEval);

            // Clear all fields
            peRB.setRating(3.0f);
            publisherET.setText("");
            authorET.setText("");
            titleET.setText("");
            catET.setText("");
            yearET.setText("");

            // Update Recycler View of Category (should update all other lists, like the one of tittles also!)
            List<Object> newList = bookData.CreateListByCategory();
            final int pos = newList.indexOf(b);
            adapter.SetElements(newList);
            adapter.notifyItemInserted(pos);

            // Snackbar with Undo action
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.coordinatorLayoutAddBook), title + " has been registered.", Snackbar.LENGTH_INDEFINITE)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bookData.deleteBook(b.getId());
                            Toast.makeText(inst, b.getTitle() + " has been removed.", Toast.LENGTH_LONG).show();

                            boolean onlyInCat;

                            if (b.getCategory().isEmpty())
                                onlyInCat = initList.indexOf("No-category") == -1;
                            else
                                onlyInCat = initList.indexOf(b.getCategory()) == -1;

                            adapter.SetElements(initList);

                            adapter.notifyItemRemoved(pos);

                            if (onlyInCat) {
                                adapter.notifyItemRemoved(pos - 1);
                                adapter.notifyItemRangeChanged(pos - 1, initList.size() - pos - 1);
                            }
                            else
                                adapter.notifyItemRangeChanged(pos, initList.size() - pos);

                            // Snackbar with Restore action
                            Snackbar snackbar = Snackbar
                                    .make(findViewById(R.id.coordinatorLayoutAddBook),"Restore Add fields with previous data?", Snackbar.LENGTH_INDEFINITE)
                                    .setAction("YES", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            // Clear all fields

                                            switch (persEval) {
                                                case "Very Bad":
                                                    peRB.setRating(1.0f);
                                                    break;

                                                case "Bad":
                                                    peRB.setRating(2.0f);
                                                    break;

                                                case "Regular":
                                                    peRB.setRating(3.0f);
                                                    break;

                                                case "Good":
                                                    peRB.setRating(4.0f);
                                                    break;

                                                case "Very Good":
                                                    peRB.setRating(5.0f);
                                                    break;
                                            }

                                            publisherET.setText(publisher);
                                            authorET.setText(author);
                                            titleET.setText(title);
                                            catET.setText(category);
                                            yearET.setText(yearS);
                                        }
                                    });

                            // Changing message text color
                            snackbar.setActionTextColor(Color.GRAY);

                            // Changing action button text color
                            View sbView = snackbar.getView();
                            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(Color.WHITE);
                            snackbar.show();
                        }
                    });

            // Changing message text color
            snackbar.setActionTextColor(Color.RED);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }
        else
            Toast.makeText(inst, res, Toast.LENGTH_LONG).show();
    }

    public BookData GetBookData() {
        return bookData;
    }

    public void DeleteBookList(final List<Book> books) {
        final boolean multDelete = books.size() > 1;

        bookData.deleteBooks(books);

        // Make snackbar to allow the UNDO action, registering again all the books removed, those books are in books (so the listener will use the received list
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinatorLayoutAddBook),"Element/s removed", Snackbar.LENGTH_INDEFINITE)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bookData.createBooks(books);
                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.LTGRAY);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
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