package com.example.pr_idi.mydatabaseexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ale on 01/01/2017.
 */

public class BookRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> mElems;
    private final int CATEGORY = 0, BOOK = 1;

    // Store the context for easy access
    private Context mContext;

    // Provide a suitable constructor (depends on the kind of dataset)
    public BookRVAdapter(Context context, List<Object> items) {
        this.mElems = items;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mElems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mElems.get(position) instanceof Book) {
            return BOOK;
        } else if (mElems.get(position) instanceof String) {
            return CATEGORY;
        }
        return -1;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case BOOK:
                View bookView = inflater.inflate(R.layout.book_layout, parent, false);
                viewHolder = new BookViewHolder(bookView);
                break;

            case CATEGORY:
                View catView = inflater.inflate(R.layout.book_category_layout, parent, false);
                viewHolder = new CategoryViewHolder(catView);
                break;

            default:
                break;
        }

        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {
            case BOOK:
                BookViewHolder bh = (BookViewHolder) viewHolder;

                Book book = (Book) mElems.get(position);

                if (book != null) {
                    bh.SetText(book.getTitle() + " by " + book.getAuthor());
                    bh.SetId(book.getId());
                }
                // The button remain unchanged for now
                break;

            case CATEGORY:
                CategoryViewHolder ch = (CategoryViewHolder) viewHolder;
                String cat = (String) mElems.get(position);

                ch.SetCategory(cat);
                break;

            default:
                break;
        }
    }
}
