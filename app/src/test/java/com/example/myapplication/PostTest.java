package com.example.myapplication;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PostTest {

    private Post post;

    @Before
    public void setup() {
        String title = "Test Title";
        String description = "Test Description";
        String author = "Test Author";
        String imagePath = "test/image/path";
        String category = "Bakery";
        post = new Post(title, description, author, imagePath, category);
    }

    @Test
    public void testGetters() {
        assertEquals("Test Title", post.getTitle());
        assertEquals("Test Description", post.getDescription());
        assertEquals("Test Author", post.getAuthor());
        assertEquals("test/image/path", post.getImagePath());
        assertEquals(1, post.getCategory());
        assertEquals(0, post.getThumbUpCounts());
        assertEquals(0, post.getCollectedCounts());
    }

    @Test
    public void testSetters() {
        post.setId(1);
        post.setThumbUpCounts(10);
        post.setCollectedCounts(5);

        assertEquals(1, post.getId());
        assertEquals(10, post.getThumbUpCounts());
        assertEquals(5, post.getCollectedCounts());
    }
}
