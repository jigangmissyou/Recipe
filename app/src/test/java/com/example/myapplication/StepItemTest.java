package com.example.myapplication;

import android.graphics.Bitmap;
import android.widget.EditText;
import android.widget.ImageView;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StepItemTest {

    private StepItem stepItem;
    private EditText editText;
    private ImageView imageView;

    @Before
    public void setUp() {
        editText = new EditText(null);
        imageView = new ImageView(null);
        stepItem = new StepItem(editText, imageView);
    }

    @Test
    public void testGetLayout() {
        assertNull(stepItem.getLayout());
    }

    @Test
    public void testGetEditText() {
        assertEquals(editText, stepItem.getEditText());
    }

    @Test
    public void testGetImageView() {
        assertEquals(imageView, stepItem.getImageView());
    }

    @Test
    public void testGetImage() {
        assertNull(stepItem.getImage());
    }

    @Test
    public void testSetImagePath() {
        String imagePath = "path/to/image.jpg";
        stepItem.setImagePath(imagePath);
        assertEquals(imagePath, stepItem.getImagePath());
    }

    @Test
    public void testGetImagePath() {
        assertNull(stepItem.getImagePath());
    }

    @Test
    public void testGetTag() {
        assertNull(stepItem.getTag());
    }

}
