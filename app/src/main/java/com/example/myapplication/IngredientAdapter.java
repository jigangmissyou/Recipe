package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<RecipeIngredient> ingredients;

    public IngredientAdapter(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        RecipeIngredient ingredient = ingredients.get(position);
        holder.bindData(ingredient);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        private TextView ingredientNameTextView;
        private TextView ingredientQuantityTextView;
        private TextView ingredientUnitTextView;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientNameTextView = itemView.findViewById(R.id.ingredient_name);
            ingredientQuantityTextView = itemView.findViewById(R.id.ingredient_quantity);
            ingredientUnitTextView = itemView.findViewById(R.id.ingredient_unit);
        }

        public void bindData(RecipeIngredient ingredient) {
            ingredientNameTextView.setText(ingredient.getIngredientName());
            ingredientQuantityTextView.setText(String.valueOf(ingredient.getIngredientQuantity()));
            ingredientUnitTextView.setText(ingredient.getIngredientUnit());
        }
    }
}
