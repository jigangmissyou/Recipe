package com.example.myapplication;

import java.io.Serializable;

public class RecipeStep implements Serializable {
    private String description;
    private String imagePath;

    private int stepOrder;

    public RecipeStep(String description, String imagePath) {
        this.description = description;
        this.imagePath = imagePath;
    }

    public void setStepOrder(int stepOrder) {
        this.stepOrder = stepOrder;
    }

    public int getStepOrder() {
        return stepOrder;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }
}

