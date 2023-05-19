package com.example.myapplication;

import java.io.Serializable;

public class RecipeIngredient implements Serializable {
    private String mIngredientName;
    private String mIngredientQuantity;
    private String mIngredientUnit;
    public RecipeIngredient(String ingredientName, String ingredientQuantity, String ingredientUnit){
        mIngredientName = ingredientName;
        mIngredientQuantity = ingredientQuantity;
        mIngredientUnit = ingredientUnit;
    }

    public String getIngredientName(){
        return mIngredientName;
    }
    public String getIngredientQuantity(){
        return mIngredientQuantity;
    }
    public String getIngredientUnit(){
        return mIngredientUnit;
    }
}
