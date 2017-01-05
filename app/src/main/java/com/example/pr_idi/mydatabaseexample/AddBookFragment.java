package com.example.pr_idi.mydatabaseexample;


import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;


public class AddBookFragment extends Fragment {
    public static final String ADD_BOOK_TITLE = "AddBook_Title";
    public static final String ADD_BOOK_HIDDEN = "AddBook_Hidden";
    public static final String ADD_BOOK_AUTHOR = "AddBook_Author";
    public static final String ADD_BOOK_PUBLISHER = "AddBook_Publisher";
    public static final String ADD_BOOK_YEAR = "AddBook_Year";
    public static final String ADD_BOOK_PE = "AddBook_PersonalEvaluation";
    public static final String ADD_BOOK_CATEGORY = "AddBook_Category";

    private BookData mBookData;

    public AddBookFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity parent = getActivity();

        if (savedInstanceState != null) {
            // Restore taking values form the bundle, need to make constant keys for them

            ((EditText)parent.findViewById(R.id.editTextTitle)).setText(savedInstanceState.getString(ADD_BOOK_TITLE));
            ((EditText)parent.findViewById(R.id.editTextAuthor)).setText(savedInstanceState.getString(ADD_BOOK_AUTHOR));
            ((EditText)parent.findViewById(R.id.editTextCategory)).setText(savedInstanceState.getString(ADD_BOOK_CATEGORY));
            ((EditText)parent.findViewById(R.id.editTextPublisher)).setText(savedInstanceState.getString(ADD_BOOK_PUBLISHER));
            ((EditText)parent.findViewById(R.id.editTextYear)).setText(savedInstanceState.getString(ADD_BOOK_YEAR));
        }
        else {
            RatingBar peRatingBar = (RatingBar) getActivity().findViewById(R.id.ratingBarPersonalEvaluation);
            peRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    TextView t = (TextView) getActivity().findViewById(R.id.textViewPersonalEvaluation);

                    switch ((int) rating) {
                        case 1:
                            t.setText("Very Bad");
                            break;

                        case 2:
                            t.setText("Bad");
                            break;

                        case 3:
                            t.setText("Regular");
                            break;

                        case 4:
                            t.setText("Good");
                            break;

                        case 5:
                            t.setText("Very Good");
                            break;
                    }

                    getView().requestFocus();
                }
            });
        }

        ArrayList<View> views = new ArrayList<>();
        View view = getView();
        views.add(view);
        views.add(parent.findViewById(R.id.editTextAuthor));
        views.add(parent.findViewById(R.id.editTextTitle));
        views.add(parent.findViewById(R.id.editTextCategory));
        views.add(parent.findViewById(R.id.editTextYear));
        views.add(parent.findViewById(R.id.editTextPublisher));
        views.add(parent.findViewById(R.id.ratingBarPersonalEvaluation));

        view.setFocusableInTouchMode(true);
        view.requestFocus();

        for (View v : views) {
            v.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                        if (!isHidden())
                            changeVisibility(true);

                        return true;
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null)
            changeVisibility(savedInstanceState.getBoolean(ADD_BOOK_HIDDEN));
        else
            changeVisibility(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_book, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Activity parent = getActivity();

        outState.putBoolean(ADD_BOOK_HIDDEN, isHidden());
        outState.putString(ADD_BOOK_TITLE, ((EditText)parent.findViewById(R.id.editTextTitle)).getText().toString());
        outState.putString(ADD_BOOK_AUTHOR, ((EditText)parent.findViewById(R.id.editTextAuthor)).getText().toString());
        outState.putString(ADD_BOOK_CATEGORY, ((EditText)parent.findViewById(R.id.editTextCategory)).getText().toString());
        outState.putString(ADD_BOOK_PUBLISHER, ((EditText)parent.findViewById(R.id.editTextPublisher)).getText().toString());
        outState.putString(ADD_BOOK_YEAR, ((EditText)parent.findViewById(R.id.editTextYear)).getText().toString());
    }

    private void changeVisibility(boolean hide) {
        if (isHidden() != hide) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

            if (hide)
                ft.hide(this);
            else
                ft.show(this);

            ft.commit();

            View addButton = getActivity().findViewById(R.id.floatingActionButtonAdd);

            if (addButton != null) {
                Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.abc_slide_out_bottom);

                addButton.setAnimation(fadeOut);
                addButton.setVisibility(View.INVISIBLE);
            }
        }
    }
}
