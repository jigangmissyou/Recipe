package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_SHARE = 1;
    private static final int TYPE_IMAGE = 2;
    private static final int TYPE_TITLE = 3;
    private static final int TYPE_CONTENT = 4;
    private static final int TYPE_AUTHOR = 5;
    private static final int TYPE_INGREDIENT = 6;
    private static final int TYPE_STEP = 7;


    private String title;
    private String content;

    private  String author;

    private ArrayList<RecipeIngredient> recipeIngredients;
    private List<Step> steps;

    public RecipeStepAdapter(String title, String content, String author, ArrayList<RecipeIngredient> recipeIngredients, List<Step> steps) {
        this.title = title;
        this.content = content;
        this.recipeIngredients = recipeIngredients;
        this.steps = steps;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_SHARE;
        } else if (position == 1) {
            return TYPE_IMAGE;
        } else if (position == 2) {
            return TYPE_TITLE;
        } else if (position == 3) {
            return TYPE_CONTENT;
        } else if (position == 4) {
            return TYPE_AUTHOR;
        } else if (position == 5 + steps.size()) {
            return TYPE_INGREDIENT;
        } else if (position == 6 + steps.size()){
            return TYPE_STEP;
        } else {
            return TYPE_STEP;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;
        switch (viewType) {
            case TYPE_TITLE:
                itemView = inflater.inflate(R.layout.detail_title, parent, false);
                return new TitleViewHolder(itemView);
            case TYPE_CONTENT:
                itemView = inflater.inflate(R.layout.detail_content, parent, false);
                return new ContentViewHolder(itemView);
            case TYPE_IMAGE:
                itemView = inflater.inflate(R.layout.detail_image, parent, false);
                return new ImageViewHolder(itemView);
            case TYPE_STEP:
                itemView = inflater.inflate(R.layout.recipe_step, parent, false);
                return new RecipeStepViewHolder(itemView);
            case TYPE_SHARE:
                itemView = inflater.inflate(R.layout.detail_toolbar, parent, false);
                return new ShareViewHolder(itemView);
            case TYPE_AUTHOR:
                itemView = inflater.inflate(R.layout.detail_author, parent, false);
                return new AuthorViewHolder(itemView);
            case TYPE_INGREDIENT:
                itemView = inflater.inflate(R.layout.ingredient_item, parent, false);
                return new IngredientViewHolder(itemView);
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        Log.d("RecipeStepAdapter log is:", "onBindViewHolder: " + viewType);
        Log.d("RecipeStepAdapter position is:", "onBindViewHolder: " + position);
        switch (viewType) {
            case TYPE_TITLE:
                ((TitleViewHolder) holder).titleTextView.setText(title);
                break;
            case TYPE_CONTENT:
                ((ContentViewHolder) holder).contentTextView.setText(content);
                break;
            case TYPE_STEP:
                int stepPosition = position - 6 - steps.size() - 1;
                if (stepPosition >= 0 && stepPosition < steps.size()) {
                    stepView(stepPosition, (RecipeStepViewHolder) holder);
                }
                break;

//                stepView(position, (RecipeStepViewHolder) holder);
//                break;
            case TYPE_IMAGE:
                // Handle image layout
                break;
            case TYPE_SHARE:
//                ((ShareViewHolder) holder).shareImageView.setImageResource(R.drawable.ic_menu_share);
                // Handle share layout
                break;
            case TYPE_AUTHOR:
                // Handle author layout
                ((AuthorViewHolder) holder).contentTextView.setText("Author: " + author);
                break;
            case TYPE_INGREDIENT:
                int ingredientPosition = position - 7 - steps.size() - 1;
                if (ingredientPosition >= 0 && ingredientPosition < recipeIngredients.size()) {
                    ingredientView(ingredientPosition, (IngredientViewHolder) holder);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid view type");

        }
    }

    public void stepView(int position, RecipeStepViewHolder holder) {
        Step step = steps.get(position);
        String imagePath = step.getImagePath();
        if (imagePath == null) {
            holder.stepImageView.setImageResource(R.drawable.baseline_panorama_24);
        } else {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                holder.stepImageView.setImageBitmap(bitmap);
            } else {
                holder.stepImageView.setImageResource(R.drawable.image2);
            }
        }
        String description = step.getDescription();
        holder.stepTextView.setText(description);
    }

    public void ingredientView(int position, IngredientViewHolder holder) {
        RecipeIngredient recipeIngredient = recipeIngredients.get(position);
        String ingredientName = recipeIngredient.getIngredientName();
        String ingredientQuantity = recipeIngredient.getIngredientQuantity();
        String ingredientUnit = recipeIngredient.getIngredientUnit();
        holder.ingredientNameTextView.setText(ingredientName);
        holder.ingredientQuantityTextView.setText(ingredientQuantity + " " + ingredientUnit);
    }

    @Override
    public int getItemCount() {
        Log.d("record steps.size + ingredients.size", "getItemCount: "+steps.size() + recipeIngredients.size());
        Log.d("record steps.size", "getItemCount: "+steps.size());
        Log.d("record ingredients.size", "getItemCount: "+recipeIngredients.size());


        return 5 + steps.size() + recipeIngredients.size();
    }

    static class TitleViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;

        TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.recipe_title);
        }
    }

    static class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView contentTextView;

        ContentViewHolder(@NonNull View itemView) {
            super(itemView);
            contentTextView = itemView.findViewById(R.id.recipe_desc);
        }
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.banner_image);
        }
    }

    static class RecipeStepViewHolder extends RecyclerView.ViewHolder {
        ImageView stepImageView;
        TextView stepTextView;

        RecipeStepViewHolder(@NonNull View itemView) {
            super(itemView);
            stepImageView = itemView.findViewById(R.id.step1_image);
            stepTextView = itemView.findViewById(R.id.step1_text);
        }
//        public RecipeStepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_step, parent, false);
//            return new RecipeStepViewHolder(itemView);
//        }
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientNameTextView;
        TextView ingredientQuantityTextView;
        TextView ingredientUnitTextView;
        IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientNameTextView = itemView.findViewById(R.id.ingredient_name);
            ingredientQuantityTextView = itemView.findViewById(R.id.ingredient_quantity);
            ingredientUnitTextView = itemView.findViewById(R.id.ingredient_unit);
        }
    }

    static class ShareViewHolder extends RecyclerView.ViewHolder {
        ImageView shareImageView;

        ShareViewHolder(@NonNull View itemView) {
            super(itemView);
            shareImageView = itemView.findViewById(R.id.share_button);
        }
    }

    static class AuthorViewHolder extends RecyclerView.ViewHolder {
        TextView contentTextView;
        AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            contentTextView = itemView.findViewById(R.id.recipe_author);
        }
    }



}
