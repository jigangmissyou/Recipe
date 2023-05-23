package com.example.myapplication;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CategoryTest {

    @Test
    public void testGetParentCategory() {
        // Create a Category object with a parent category
        Category category = new Category("Parent", Arrays.asList("Subcategory1", "Subcategory2"));

        // Verify that getParentCategory() returns the correct parent category
        assertEquals("Parent", category.getParentCategory());
    }

    @Test
    public void testGetSubCategories() {
        // Create a Category object with a list of subcategories
        List<String> subcategories = Arrays.asList("Subcategory1", "Subcategory2");
        Category category = new Category("Parent", subcategories);

        // Verify that getSubCategories() returns the correct list of subcategories
        assertEquals(subcategories, category.getSubCategories());
    }

}
