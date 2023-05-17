package com.example.myapplication;

import java.util.List;

public class Post {

    private int id;
    private String title;
    private String description;
    private String author;

    private String imagePath;

    private int category;

    private int thumbUpCounts;

    private int collectedCounts;

    public Post(String title, String description, String author, String imagePath, int category) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.imagePath = imagePath;
        this.category = category;
        this.thumbUpCounts = 0;
        this.collectedCounts = 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setThumbUpCounts(int thumbUpCounts) {
        this.thumbUpCounts = thumbUpCounts;
    }

    public void setCollectedCounts(int collectedCounts) {
        this.collectedCounts = collectedCounts;
    }

    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getCategory() {
        return category;
    }

    public int getThumbUpCounts() {
        return thumbUpCounts;
    }

    public int getCollectedCounts() {
        return collectedCounts;
    }

    public int getId() {
        return id;
    }


}

