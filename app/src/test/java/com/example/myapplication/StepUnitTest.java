package com.example.myapplication;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StepUnitTest {

    @Test
    public void testConstructorAndGetters() {
        String description = "Step description";
        String imagePath = "path/to/image.jpg";
        int stepOrder = 1;

        Step step = new Step(description, imagePath, stepOrder);

        assertNotNull(step);
        assertEquals(description, step.getDescription());
        assertEquals(imagePath, step.getImagePath());
        assertEquals(stepOrder, step.getStepOrder());
    }

    @Test
    public void testSettersAndGetters() {
        Step step = new Step("Step description", "path/to/image.jpg", 1);

        String newDescription = "Updated description";
        String newImagePath = "new/path/to/image.jpg";
        int newStepOrder = 2;

        step.setDescription(newDescription);
        step.setImagePath(newImagePath);
        step.setStepOrder(newStepOrder);

        assertEquals(newDescription, step.getDescription());
        assertEquals(newImagePath, step.getImagePath());
        assertEquals(newStepOrder, step.getStepOrder());
    }

    @Test
    public void testToString() {
        Step step = new Step("Step description", "path/to/image.jpg", 1);
        String expectedString = "Step{imagePath='path/to/image.jpg', description='Step description'}";

        assertEquals(expectedString, step.toString());
    }
}
