package com.example.myapplication;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class RecipeTest {

    private Recipe recipe;

    @Before
    public void setUp() {
        int imageResId = 1;
        String title = "Test Recipe";
        String description = "This is a test recipe";
        int thumbUp = 10;
        int collected = 5;
        String nickName = "Test User";
        ArrayList<RecipeIngredient> ingredients = new ArrayList<>();
        ArrayList<Step> recipeSteps = new ArrayList<>();

        recipe = new Recipe(imageResId, title, description, thumbUp, collected, nickName, ingredients, recipeSteps);
    }

    @Test
    public void testGetId() {
        int expectedId = 0;
        int actualId = recipe.getId();
        assertEquals(expectedId, actualId);
    }

    @Test
    public void testGetImageResId() {
        int expectedImageResId = 1;
        int actualImageResId = recipe.getImageResId();
        assertEquals(expectedImageResId, actualImageResId);
    }

    @Test
    public void testGetTitle() {
        String expectedTitle = "Test Recipe";
        String actualTitle = recipe.getTitle();
        assertEquals(expectedTitle, actualTitle);
    }

    @Test
    public void testGetDescription() {
        String expectedDescription = "This is a test recipe";
        String actualDescription = recipe.getDescription();
        assertEquals(expectedDescription, actualDescription);
    }

    @Test
    public void testGetNickName() {
        String expectedNickName = "Test User";
        String actualNickName = recipe.getNickName();
        assertEquals(expectedNickName, actualNickName);
    }

    @Test
    public void testGetIngredients() {
        ArrayList<RecipeIngredient> expectedIngredients = new ArrayList<>();
        ArrayList<RecipeIngredient> actualIngredients = recipe.getIngredients();
        assertEquals(expectedIngredients, actualIngredients);
    }

    @Test
    public void testGetRecipeSteps() {
        ArrayList<Step> expectedRecipeSteps = new ArrayList<>();
        ArrayList<Step> actualRecipeSteps = recipe.getRecipeSteps();
        assertEquals(expectedRecipeSteps, actualRecipeSteps);
    }

    @Test
    public void testGetThumbUp() {
        int expectedThumbUp = 10;
        int actualThumbUp = recipe.getThumbUp();
        assertEquals(expectedThumbUp, actualThumbUp);
    }

    @Test
    public void testGetCollected() {
        int expectedCollected = 5;
        int actualCollected = recipe.getCollected();
        assertEquals(expectedCollected, actualCollected);
    }

}
