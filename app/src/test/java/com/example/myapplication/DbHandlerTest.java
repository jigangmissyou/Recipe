package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DbHandlerTest {

    @Mock
    private Context mockContext;
    @Mock
    private SQLiteDatabase mockDatabase;
    @Mock
    private SQLiteOpenHelper mockOpenHelper;

    private DbHandler dbHandler;

    @Before
    public void setUp() {
//        when(mockContext.getApplicationContext()).thenReturn(mockContext);
//        dbHandler = new DbHandler(mockContext);
    }

    @Test
    public void onCreate_ShouldCreateTables() {
        dbHandler = new DbHandler(mockContext);
        dbHandler.onCreate(mockDatabase);

        verify(mockDatabase).execSQL("create table " + DbHandler.TABLE_POSTS + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT,DESCRIPTION TEXT,STEPS TEXT,AUTHOR TEXT,CATEGORY INTEGER,THUMB_UP_COUNTS INTEGER,COLLECTED_COUNTS INTEGER, IMAGE_PATH TEXT)");
        verify(mockDatabase).execSQL("create table " + DbHandler.TABLE_CATEGORIES + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT)");
        verify(mockDatabase).execSQL("create table " + DbHandler.TABLE_STEPS + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,DESCRIPTION TEXT,POST_ID INTEGER, STEP_ORDER INTEGER, IMAGE_PATH TEXT)");
        verify(mockDatabase).execSQL("create table " + DbHandler.TABLE_INGREDIENTS + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,POST_ID INTEGER, NAME TEXT, QUANTITY TEXT, UNIT TEXT)");
        verify(mockDatabase).execSQL("create table " + DbHandler.TABLE_USERS + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,PASSWORD TEXT,EMAIL TEXT,IMAGE_PATH TEXT)");
    }

    @Test
    public void onUpgrade_ShouldDropTables() {
        dbHandler = new DbHandler(mockContext);
        dbHandler.onUpgrade(mockDatabase, 1, 2);

        verify(mockDatabase).execSQL("DROP TABLE IF EXISTS " + DbHandler.TABLE_POSTS);
        verify(mockDatabase).execSQL("DROP TABLE IF EXISTS " + DbHandler.TABLE_CATEGORIES);
        verify(mockDatabase).execSQL("DROP TABLE IF EXISTS " + DbHandler.TABLE_STEPS);
        verify(mockDatabase).execSQL("DROP TABLE IF EXISTS " + DbHandler.TABLE_INGREDIENTS);
        verify(mockDatabase).execSQL("DROP TABLE IF EXISTS " + DbHandler.TABLE_USERS);
    }

    @Test
    public void addPostTest() {
        dbHandler = new DbHandler(mockContext);
        Post post = new Post("Test Post", "This is a test post", "John Doe", "image_path", 1);
        assertEquals(1, post.getCategory());
        assertEquals("Test Post", post.getTitle());
        assertEquals("This is a test post", post.getDescription());
        assertEquals("John Doe", post.getAuthor());
        assertEquals("image_path", post.getImagePath());
        assertEquals(0, post.getThumbUpCounts());
        assertEquals(0, post.getCollectedCounts());
        SQLiteOpenHelper mockHelper = mock(SQLiteOpenHelper.class);
        SQLiteDatabase mockDatabase = mock(SQLiteDatabase.class);
        when(mockHelper.getWritableDatabase()).thenReturn(mockDatabase);
//        DbHandler dbHandler = new DbHandlerWithDatabaseHelper(mockContext);
//        int postId = dbHandler.addPost(post);
//        assertTrue(postId > 0);
//        post.setThumbUpCounts(0);
//        post.setCollectedCounts(0);
//        int postId = dbHandler.addPost(post);
//        assertTrue(postId > 0);
//        SQLiteDatabase db = dbHandler.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + DbHandler.TABLE_POSTS + " WHERE ID = " + postId, null);
//        assertTrue(cursor.moveToFirst());
//        assertEquals("Test Post", cursor.getString(cursor.getColumnIndex("TITLE")));
//        assertEquals("This is a test post", cursor.getString(cursor.getColumnIndex("DESCRIPTION")));
//        assertEquals("John Doe", cursor.getString(cursor.getColumnIndex("AUTHOR")));
//        assertEquals(1, cursor.getInt(cursor.getColumnIndex("CATEGORY")));
//        assertEquals(0, cursor.getInt(cursor.getColumnIndex("THUMB_UP_COUNTS")));
//        assertEquals(0, cursor.getInt(cursor.getColumnIndex("COLLECTED_COUNTS")));
//        assertEquals("image_path", cursor.getString(cursor.getColumnIndex("IMAGE_PATH")));
//
//        cursor.close();
    }

//    @Test
//    public void updatePostTest() {
//        dbHandler = new DbHandler(mockContext);
//        Post post = new Post("Test Post", "This is a test post", "John Doe", "image_path", 1);
//        post.setThumbUpCounts(0);
//        post.setCollectedCounts(0);
//        int postId = dbHandler.addPost(post);
//        post.setId(postId);
//        post.setThumbUpCounts(10);
//        post.setCollectedCounts(5);
//        dbHandler.updatePost(post);
//        SQLiteDatabase db = dbHandler.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + DbHandler.TABLE_POSTS + " WHERE ID = " + postId, null);
//        assertTrue(cursor.moveToFirst());
//        assertEquals("Test Post", cursor.getString(cursor.getColumnIndex("TITLE")));
//        assertEquals("This is a test post", cursor.getString(cursor.getColumnIndex("DESCRIPTION")));
//        assertEquals("John Doe", cursor.getString(cursor.getColumnIndex("AUTHOR")));
//        assertEquals(1, cursor.getInt(cursor.getColumnIndex("CATEGORY")));
//        assertEquals(10, cursor.getInt(cursor.getColumnIndex("THUMB_UP_COUNTS")));
//        assertEquals(5, cursor.getInt(cursor.getColumnIndex("COLLECTED_COUNTS")));
//        assertEquals("image_path", cursor.getString(cursor.getColumnIndex("IMAGE_PATH")));
//        cursor.close();
//    }
//    @Test
//    public void deletePostTest() {
//        dbHandler = new DbHandler(mockContext);
//        Post post = new Post("Test Post", "This is a test post", "John Doe", "image_path", 1);
//        post.setThumbUpCounts(0);
//        post.setCollectedCounts(0);
//        int postId = dbHandler.addPost(post);
//        dbHandler.addSteps(postId, "Step 1", 0, "image_path");
//        dbHandler.addSteps(postId, "Step 2", 1, "image_path");
//        dbHandler.addSteps(postId, "Step 3", 2, "image_path");
//        dbHandler.deleteRecipe(postId);
//        SQLiteDatabase db = dbHandler.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + DbHandler.TABLE_POSTS + " WHERE ID = " + postId, null);
//        assertTrue(!cursor.moveToFirst());
//        cursor.close();
//    }
}
