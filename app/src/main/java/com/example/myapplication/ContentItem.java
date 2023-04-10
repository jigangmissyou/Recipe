package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContentItem implements Serializable {
    private int mImageResId;
    private String mTitle;
    private String mDescription;
    private boolean mThumbUp;
    private boolean mCollected;
    private String mNickName;

    // define field ingredients
    private String[] mIngredients;

    private ArrayList<RecipeStep> mRecipeSteps;

//    private List

    public ContentItem(int imageResId, String title, String description, boolean thumbUp, boolean collected, String nickName, String[] ingredients, ArrayList<RecipeStep> recipeSteps){
        mImageResId = imageResId;
        mTitle = title;
        mDescription = description;
        mThumbUp = thumbUp;
        mCollected = collected;
        mNickName = nickName;
        mIngredients = ingredients;
        mRecipeSteps = recipeSteps;
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

    public ArrayList<RecipeStep> getRecipeSteps() {
        return mRecipeSteps;
    }

    public boolean isThumbUp() {
        return mThumbUp;
    }

    public boolean isCollected() {
        return mCollected;
    }
}
