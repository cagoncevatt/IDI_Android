package com.example.pr_idi.mydatabaseexample;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Ale on 01/01/2017.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    private TextView categoryTextView;

    CategoryViewHolder(View itemView) {
        super(itemView);

        categoryTextView = (TextView) itemView.findViewById(R.id.categoryTextView);
    }

    public void SetCategory(String category) {
        categoryTextView.setText(category);
    }

    public void SetCategoryTextView(TextView tv) {
        categoryTextView = tv;
    }

    public String GetCategory() {
        return categoryTextView.getText().toString();
    }

    public TextView GetCategoryTextView() {
        return categoryTextView;
    }

}
