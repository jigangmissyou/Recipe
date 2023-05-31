package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private static final int TYPE_INGREDIENT_INTRO = 6;
    private static final int TYPE_INGREDIENT = 7;
    private static final int TYPE_STEP_INTRO = 8;
    private static final int TYPE_STEP = 9;
    private String title;
    private String content;

    private  String author;

    private static ArrayList<RecipeIngredient> recipeIngredients;
    private List<Step> steps;

    private OnShareClickListener shareClickListener;

    // ...

    public interface OnShareClickListener {
        void onShareClick(String title, String author);
    }

    public void setOnShareClickListener(OnShareClickListener listener) {
        // log listener
        Log.d("RecipeStepAdapter xxxxxxxxxxxxxxxxxxxxxx ", "setOnShareClickListener: " + listener);
        this.shareClickListener = listener;
    }

    public RecipeStepAdapter(String title, String content, String author, ArrayList<RecipeIngredient> recipeIngredients, List<Step> steps) {
        this.title = title;
        this.content = content;
        this.author = author;
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
        } else if (position == 5) {
            return TYPE_INGREDIENT_INTRO;
        }else if (position == 6) {
            return TYPE_INGREDIENT;
        }else if (position == 7) {
            return TYPE_STEP_INTRO;
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
            case TYPE_SHARE:
                itemView = inflater.inflate(R.layout.detail_toolbar, parent, false);
                return new ShareViewHolder(itemView);
            case TYPE_AUTHOR:
                itemView = inflater.inflate(R.layout.detail_author, parent, false);
                return new AuthorViewHolder(itemView);
            case TYPE_INGREDIENT_INTRO:
                itemView = inflater.inflate(R.layout.detail_ingredient_intro, parent, false);
                return new IngredientIntroViewHolder(itemView);
            case TYPE_INGREDIENT:
                itemView = inflater.inflate(R.layout.detail_ingredient_item, parent, false);
                return new IngredientViewHolder(itemView);
            case TYPE_STEP_INTRO:
                itemView = inflater.inflate(R.layout.detail_step_intro, parent, false);
                return new StepIntroViewHolder(itemView);
            case TYPE_STEP:
                itemView = inflater.inflate(R.layout.recipe_step, parent, false);
                return new RecipeStepViewHolder(itemView);
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

            case TYPE_IMAGE:
                // Handle image layout
                break;

            case TYPE_AUTHOR:
                // Handle author layout
                ((AuthorViewHolder) holder).contentTextView.setText("Author: " + author);
                break;
            case TYPE_INGREDIENT_INTRO:
                ((IngredientIntroViewHolder) holder).contentTextView.setText("Ingredients:");
                break;
            case TYPE_INGREDIENT:
               String ingredientText = "";
                for (int i = 0; i < recipeIngredients.size(); i++) {
                    RecipeIngredient recipeIngredient = recipeIngredients.get(i);
                    String ingredientName = recipeIngredient.getIngredientName();
                    String ingredientQuantity = recipeIngredient.getIngredientQuantity();
                    String ingredientUnit = recipeIngredient.getIngredientUnit();
                    ingredientText += ingredientName + " " + ingredientQuantity + ingredientUnit;
                    if (i != recipeIngredients.size() - 1) {
                        ingredientText += "\n";
                    }
                }
                ((IngredientViewHolder) holder).contentTextView.setText(ingredientText);
                break;
            case TYPE_SHARE:
                ShareViewHolder shareViewHolder = (ShareViewHolder) holder;
                shareViewHolder.shareImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "Share clicked", Toast.LENGTH_SHORT).show();
                        // log shareClickListener

                        if (shareClickListener != null) {
                            shareClickListener.onShareClick(title, author);
                        }
                    }
                });
                break;
            case TYPE_STEP_INTRO:
                ((StepIntroViewHolder) holder).contentTextView.setText("Steps:");
                break;
            case TYPE_STEP:
                stepView(position-8, (RecipeStepViewHolder) holder);
                break;
            default:
                throw new IllegalArgumentException("Invalid view type");

        }
    }

    public void stepView(int position, RecipeStepViewHolder holder) {
//        position = steps.size() -1;
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


    @Override
    public int getItemCount() {
//        Log.d("record steps.size + ingredients.size", "getItemCount: "+steps.size() + recipeIngredients.size());
//        Log.d("record steps.size", "getItemCount: "+steps.size());
//        Log.d("record ingredients.size", "getItemCount: "+recipeIngredients.size());


        return 8 + steps.size() ;
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

    static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView contentTextView;
        IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            contentTextView = itemView.findViewById(R.id.recipe_item_name);
        }
    }

    static class IngredientIntroViewHolder extends RecyclerView.ViewHolder {
        TextView contentTextView;
        IngredientIntroViewHolder(@NonNull View itemView) {
            super(itemView);
            contentTextView = itemView.findViewById(R.id.ingredient_intro);
        }
    }

    static class StepIntroViewHolder extends RecyclerView.ViewHolder {
        TextView contentTextView;
        StepIntroViewHolder(@NonNull View itemView) {
            super(itemView);
            contentTextView = itemView.findViewById(R.id.step_intro);
        }
    }

}
