package com.example.myapplication;

import java.util.List;

public class Recipe2 {
    private String title;
    private String description;
    private List<String> ingredients;
    private List<Step> steps;

    public Recipe2(String title, String description, List<String> ingredients, List<Step> steps) {
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }
}

