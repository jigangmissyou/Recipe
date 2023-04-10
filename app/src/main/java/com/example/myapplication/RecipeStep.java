package com.example.myapplication;

import java.io.Serializable;

public class RecipeStep implements Serializable {
    private String description;
    private int imageResourceId;

    public RecipeStep(String description, int imageResourceId) {
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}

