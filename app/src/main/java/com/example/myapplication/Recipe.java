package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {
    private String mImgPath;
    private String mTitle;
    private String mDescription;
    private int mThumbUp;
    private int mCollected;
    private String mNickName;
    private ArrayList<RecipeIngredient> mIngredients;
    private ArrayList<RecipeStep> mRecipeSteps;
    public Recipe(String imgPath, String title, String description, int thumbUp, int collected, String nickName, ArrayList<RecipeIngredient> recipeIngredients, ArrayList<RecipeStep> recipeSteps){
        mImgPath = imgPath;
        mTitle = title;
        mDescription = description;
        mThumbUp = thumbUp;
        mCollected = collected;
        mNickName = nickName;
        mIngredients = recipeIngredients;
        mRecipeSteps = recipeSteps;
    }
    public String getImgPath() {
        return mImgPath;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getNickName() {
        return mNickName;
    }

    public ArrayList<RecipeIngredient> getIngredients() {
        return mIngredients;
    }

    public ArrayList<RecipeStep> getRecipeSteps() {
        return mRecipeSteps;
    }

    public int getThumbUp() {
        return mThumbUp;
    }

    public int getCollected() {
        return mCollected;
    }
}
