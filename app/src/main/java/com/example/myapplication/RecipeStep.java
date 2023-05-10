package com.example.myapplication;

import java.io.Serializable;

public class RecipeStep implements Serializable {

    private int postID;
    private String description;
    private int orderNumber;
    private String imagePath;
    public RecipeStep(int postID, String description, int orderNumber, String imagePath) {
        this.postID = postID;
        this.description = description;
        this.orderNumber = orderNumber;
        this.imagePath = imagePath;
    }

    public int getPostID() {
        return postID;
    }

    public String getDescription() {
        return description;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getImagePath() {
        return imagePath;
    }
}

