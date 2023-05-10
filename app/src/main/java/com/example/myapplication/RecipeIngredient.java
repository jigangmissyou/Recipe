package com.example.myapplication;

public class RecipeIngredient {
    private int postID;
    private String ingredientName;
    private String ingredientQuantity;
    private String ingredientUnit;
    public RecipeIngredient(int postID, String ingredientName, String ingredientQuantity, String ingredientUnit) {
        this.postID = postID;
        this.ingredientName = ingredientName;
        this.ingredientQuantity = ingredientQuantity;
        this.ingredientUnit = ingredientUnit;
    }

    public int getPostID() {
        return postID;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public String getIngredientQuantity() {
        return ingredientQuantity;
    }

    public String getIngredientUnit() {
        return ingredientUnit;
    }
}
