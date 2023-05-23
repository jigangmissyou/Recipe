package com.example.myapplication;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RecipeIngredientTest {

    @Test
    public void testGetIngredientName() {
        // Create a RecipeIngredient object with an ingredient name
        RecipeIngredient ingredient = new RecipeIngredient("Salt", "1", "teaspoon");

        // Verify that getIngredientName() returns the correct ingredient name
        assertEquals("Salt", ingredient.getIngredientName());
    }

    @Test
    public void testGetIngredientQuantity() {
        // Create a RecipeIngredient object with an ingredient quantity
        RecipeIngredient ingredient = new RecipeIngredient("Salt", "1", "teaspoon");

        // Verify that getIngredientQuantity() returns the correct ingredient quantity
        assertEquals("1", ingredient.getIngredientQuantity());
    }

    @Test
    public void testGetIngredientUnit() {
        // Create a RecipeIngredient object with an ingredient unit
        RecipeIngredient ingredient = new RecipeIngredient("Salt", "1", "teaspoon");

        // Verify that getIngredientUnit() returns the correct ingredient unit
        assertEquals("teaspoon", ingredient.getIngredientUnit());
    }

}
