package com.example.myapplication;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;
public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepViewHolder> {
    private List<Step> steps;
    public RecipeStepAdapter(List<Step> steps) {
        this.steps = steps;
    }
    @Override
    public RecipeStepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_step, parent, false);
        return new RecipeStepViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(RecipeStepViewHolder holder, int position) {
        for (Step step : steps) {
            Log.d("RecipeStepAdapter", "Step: " + step.toString());
        }
        Step step = steps.get(position);
        //log the step
        String imagePath = step.getImagePath();
        if (imagePath == null) {
            // Set a default image or handle the case when the file doesn't exist
            holder.stepImageView.setImageResource(R.drawable.baseline_panorama_24);
        }else{
            // log the image path
            Log.d("RecipeStepAdapter", "imagePath: " + imagePath);
            // Load the image from the file path
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                // Set the image bitmap
                holder.stepImageView.setImageBitmap(bitmap);
            } else {
                // Set a default image or handle the case when the file doesn't exist
                holder.stepImageView.setImageResource(R.drawable.image2);
            }
        }


        String description = step.getDescription();
        holder.stepTextView.setText(description);
    }


    @Override
    public int getItemCount() {
        return steps.size();
    }
}
