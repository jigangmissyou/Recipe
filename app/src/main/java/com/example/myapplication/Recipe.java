package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {

    private int mId;
    private int mImageResId;
    private String mTitle;
    private String mDescription;
    private int mThumbUp;
    private int mCollected;
    private String mNickName;

    // define field ingredients
    private String[] mIngredients;

    private ArrayList<Step> mRecipeSteps;

//    private List

    public Recipe(int imageResId, String title, String description, int thumbUp, int collected, String nickName, String[] ingredients, ArrayList<Step> recipeSteps){
        mImageResId = imageResId;
        mTitle = title;
        mDescription = description;
        mThumbUp = thumbUp;
        mCollected = collected;
        mNickName = nickName;
        mIngredients = ingredients;
        mRecipeSteps = recipeSteps;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public int getImageResId() {
        return mImageResId;
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

    public String[] getIngredients() {
        return mIngredients;
    }

    public ArrayList<Step> getRecipeSteps() {
        return mRecipeSteps;
    }

    public int isThumbUp() {
        return mThumbUp;
    }

    public int isCollected() {
        return mCollected;
    }
}
