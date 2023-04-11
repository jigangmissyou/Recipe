package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContentListAdapter extends RecyclerView.Adapter<ContentListAdapter.ViewHolder> {

    private List<Recipe> mRecipes;

    public ContentListAdapter(List<Recipe> recipes) {
        mRecipes = recipes;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mTitleTextView;
        private TextView mDescriptionTextView;
        private ImageView mThumbUpImageView;
        private ImageView mCollectImageView;

        public ViewHolder(View view) {
            super(view);

            mImageView = view.findViewById(R.id.content_image_view);
            mTitleTextView = view.findViewById(R.id.content_title_text_view);
            mDescriptionTextView = view.findViewById(R.id.content_description_text_view);
//            mThumbUpImageView = view.findViewById(R.id.content_thumb_up_image_view);
//            mCollectImageView = view.findViewById(R.id.content_collect_image_view);
        }

        public void bind(Recipe recipe) {
            mImageView.setImageResource(recipe.getImageResId());
            mTitleTextView.setText(recipe.getTitle());
            mDescriptionTextView.setText(recipe.getDescription());
//            mThumbUpImageView.setImageResource(contentItem.isThumbUp() ? R.drawable.ic_thumb_up_selected : R.drawable.ic_thumb_up);
//            mCollectImageView.setImageResource(contentItem.isCollected() ? R.drawable.ic_collect_selected : R.drawable.ic_collect);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item, parent, false);
        ContentListAdapter.ViewHolder viewHolder = new ContentListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = mRecipes.get(position);
        holder.mImageView.setImageResource(recipe.getImageResId());
        holder.mTitleTextView.setText(recipe.getTitle());
        holder.mDescriptionTextView.setText(recipe.getDescription());
//        holder.mThumbUpImageView.setImageResource(contentItem.isThumbUp() ? R.drawable.ic_thumb_up_selected : R.drawable.ic_thumb_up);
//        holder.mCollectImageView.setImageResource(contentItem.isCollected() ? R.drawable.ic_collect_selected : R.drawable.ic_collect);

//        holder.bind(contentItem);
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }
}

