package com.example.myapplication;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepViewHolder> {
    private List<RecipeStep> steps;
    public RecipeStepAdapter(List<RecipeStep> steps) {
        this.steps = steps;
    }
    @Override
    public RecipeStepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_step, parent, false);
        return new RecipeStepViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(RecipeStepViewHolder holder, int position) {
//        holder.bind(steps.get(position));
        // define the data to be displayed
        RecipeStep step = steps.get(position);
        // get the image from the ContentItem object
//        int image = step.getImageResourceId();
        int image = R.drawable.pic1;
        // get the description from the ContentItem object
        String description = step.getDescription();
        // set the image
        holder.stepImageView.setImageResource(image);
        // set the description
        holder.stepTextView.setText(description);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }
}
