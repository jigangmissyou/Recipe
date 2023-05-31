package com.example.myapplication;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_category_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView parentCategoryTitle;
        private ClickableTextView subCategoryTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            parentCategoryTitle = itemView.findViewById(R.id.parent_category_title);
            subCategoryTitle = itemView.findViewById(R.id.subcategory_title);
        }

        public void bind(Category category) {
            parentCategoryTitle.setText(category.getParentCategory());
            subCategoryTitle.setTextWithClickableSubCategories(TextUtils.join(", ", category.getSubCategories()));
            subCategoryTitle.setOnSubCategoryClickListener(new ClickableTextView.OnSubCategoryClickListener() {
                @Override
                public void onSubCategoryClick(String subCategory) {
                    // log subcategory, for debugging
                    Log.d("subcateogryxx", "onSubCategoryClick: " + subCategory + " clicked");

                    // 处理子分类点击事件，跳转到对应分类下的菜谱列表
                    Intent intent = new Intent(itemView.getContext(), HomeActivity.class);
                    intent.putExtra("category", category.getParentCategory());
                    intent.putExtra("subcategory", subCategory);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }

}
