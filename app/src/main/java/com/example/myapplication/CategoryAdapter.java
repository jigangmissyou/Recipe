package com.example.myapplication;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> categories;

    public CategoryAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_category_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView parentCategoryTitle;
        private TextView subCategoryTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            parentCategoryTitle = itemView.findViewById(R.id.parent_category_title);
            subCategoryTitle = itemView.findViewById(R.id.subcategory_title);
        }

        public void bind(Category category) {
            parentCategoryTitle.setText(category.getParentCategory());
            subCategoryTitle.setText(TextUtils.join(", ", category.getSubCategories()));
        }
    }
}
