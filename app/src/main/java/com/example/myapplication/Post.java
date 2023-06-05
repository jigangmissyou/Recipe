package com.example.myapplication;

public class Post {

    private int id;
    private String title;
    private String description;
    private String author;

    private String imagePath;

    private String category;

    private int thumbUpCounts;

    private int collectedCounts;

    public Post(String title, String description, String author, String imagePath, String category) {
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

    public String getCategory() {
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


    public void setTitle(String test_title) {
        this.title = test_title;
    }

    public void setDescription(String test_description) {
        this.description = test_description;
    }

    public void setAuthor(String test_author) {
        this.author = test_author;
    }

    public void setCategory(String i) {
        this.category = i;
    }

    public void setImagePath(String s) {
        this.imagePath = s;
    }
}

