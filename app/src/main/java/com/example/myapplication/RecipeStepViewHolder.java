package com.example.myapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
public class RecipeStepViewHolder extends RecyclerView.ViewHolder {
    public ImageView stepImageView;
    public TextView stepTextView;
    public RecipeStepViewHolder(View itemView) {
        super(itemView);
        stepImageView = itemView.findViewById(R.id.step1_image);
        stepTextView = itemView.findViewById(R.id.step1_text);
    }

//    public void bind(RecipeStep recipeStep) {
//        stepImageView.setImageResource(recipeStep.getImageResourceId());
//        stepTextView.setText(recipeStep.getDescription());
//    }
}
