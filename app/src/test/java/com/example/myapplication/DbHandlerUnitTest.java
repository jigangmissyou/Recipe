package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import com.example.myapplication.DbHandler;


import androidx.test.core.app.ApplicationProvider;

import java.util.ArrayList;

public class DbHandlerUnitTest {
    private DbHandler dbHandler;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        dbHandler = new DbHandler(context);
    }

    @After
    public void tearDown() {
        dbHandler.close();
    }

    @Test
    public void addPostTest() {
        DbHandler dbHandler = new DbHandler(ApplicationProvider.getApplicationContext());
        Post post = new Post("Test Post", "This is a test post", "John Doe", "image_path", 1);
        post.setThumbUpCounts(0);
        post.setCollectedCounts(0);
        int postId = dbHandler.addPost(post);
        assertTrue(postId > 0);
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DbHandler.TABLE_POSTS + " WHERE ID = " + postId, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("Test Post", cursor.getString(cursor.getColumnIndex("TITLE")));
        assertEquals("This is a test post", cursor.getString(cursor.getColumnIndex("DESCRIPTION")));
        assertEquals("John Doe", cursor.getString(cursor.getColumnIndex("AUTHOR")));
        assertEquals(1, cursor.getInt(cursor.getColumnIndex("CATEGORY")));
        assertEquals(0, cursor.getInt(cursor.getColumnIndex("THUMB_UP_COUNTS")));
        assertEquals(0, cursor.getInt(cursor.getColumnIndex("COLLECTED_COUNTS")));
        assertEquals("image_path", cursor.getString(cursor.getColumnIndex("IMAGE_PATH")));
        cursor.close();
    }

//    @Test
//    public void updatePostTest() {
//        Post post = new Post("Test Post", "This is a test post", "John Doe", "image_path", 1);
//        post.setId(1);
//        post.setTitle("Updated Post");
//        post.setDescription("This is an updated post");
//        post.setImagePath("updated_image_path");
//
//        boolean result = dbHandler.updatePost(post);
//
//        assertTrue(result);
//
//        SQLiteDatabase db = dbHandler.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + DbHandler.TABLE_POSTS + " WHERE ID = 1", null);
//        assertTrue(cursor.moveToFirst());
//        assertEquals("Updated Post", cursor.getString(cursor.getColumnIndex("TITLE")));
//        assertEquals("This is an updated post", cursor.getString(cursor.getColumnIndex("DESCRIPTION")));
//        assertEquals("updated_image_path", cursor.getString(cursor.getColumnIndex("IMAGE_PATH")));
//
//        cursor.close();
//    }

//    @Test
//    public void deleteRecipeTest() {
//        boolean result = dbHandler.deleteRecipe(1);
//        assertTrue(result);
//
//        SQLiteDatabase db = dbHandler.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + DbHandler.TABLE_POSTS + " WHERE ID = 1", null);
//        assertFalse(cursor.moveToFirst());
//        cursor.close();
//    }
//
//    @Test
//    public void registerTest() {
//        boolean result = dbHandler.register("testuser", "testpassword", "testemail@example.com");
//        assertTrue(result);
//
//        SQLiteDatabase db = dbHandler.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM my_user WHERE USERNAME = 'testuser'", null);
//        assertTrue(cursor.moveToFirst());
//        assertEquals("testpassword", cursor.getString(cursor.getColumnIndex("PASSWORD")));
//        assertEquals("testemail@example.com", cursor.getString(cursor.getColumnIndex("EMAIL")));
//
//        cursor.close();
//    }
//
//    @Test
//    public void findUserByUsernameTest() {
//        Boolean ret = dbHandler.findUser("testuser", "testpassword");
//        assertTrue(ret);
//    }
//
//    @Test
//    public void getAllPostsTest() {
//        ArrayList<Recipe> recipes = dbHandler.getAllRecipe("ID DESC");
//        assertNotNull(recipes);
//        assertEquals(1, recipes.size()); // Assuming there are 3 posts in the database
//    }
}

