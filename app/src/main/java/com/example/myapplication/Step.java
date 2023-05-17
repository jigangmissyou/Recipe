package com.example.myapplication;

import android.graphics.Bitmap;

public class Step implements java.io.Serializable {
    private String description;
    private String imagePath;
    private int stepOrder;

    public Step(String description, String imagePath, int stepOrder) {
        this.description = description;
        this.imagePath = imagePath;
        this.stepOrder = stepOrder;
    }
    public void setStepOrder(int stepOrder) {
        this.stepOrder = stepOrder;
    }

    public int getStepOrder() {
        return stepOrder;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}

