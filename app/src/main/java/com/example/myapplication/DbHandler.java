package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import com.example.myapplication.RecipeStep;
import com.example.myapplication.Category;
import com.example.myapplication.Recipe;
//import com.example.myapplication.RecipeIngredient;
public class DbHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "recipe";
    // define table name
    public static final String TABLE_POSTS = "my_posts";

    public static final String TABLE_CATEGORIES = "my_categories";

    public static final String TABLE_STEPS = "my_steps";

    public static final String TABLE_INGREDIENTS = "my_ingredients";
    private static final String TABLE_USERS = "my_user";

    // define table columns
    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      // db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,LOCATION TEXT,DESIGNATION TEXT)");
        db.execSQL("create table " + TABLE_POSTS + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT,DESCRIPTION TEXT,STEPS TEXT,AUTHOR TEXT,CATEGORY INTEGER,THUMB_UP_COUNTS INTEGER,COLLECTED_COUNTS INTEGER, IMAGE_PATH TEXT)");
        db.execSQL("create table " + TABLE_CATEGORIES + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT)");
        db.execSQL("create table " + TABLE_STEPS + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,DESCRIPTION TEXT,POST_ID INTEGER, STEP_ORDER INTEGER, IMAGE_PATH TEXT)");
        db.execSQL("create table " + TABLE_INGREDIENTS + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,POST_ID INTEGER, NAME TEXT, QUANTITY TEXT, UNIT TEXT)");
        db.execSQL("create table " + TABLE_USERS + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,PASSWORD TEXT,EMAIL TEXT,IMAGE_PATH TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_STEPS);
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
         db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
       onCreate(db);
    }

    // insert data into TABLE_POSTS
    public int addPost(Post post){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TITLE",post.getTitle());
        contentValues.put("DESCRIPTION",post.getDescription());
        contentValues.put("AUTHOR",post.getAuthor());
        contentValues.put("CATEGORY",post.getCategory());
        contentValues.put("THUMB_UP_COUNTS",post.getThumbUpCounts());
        contentValues.put("COLLECTED_COUNTS",post.getCollectedCounts());
        contentValues.put("IMAGE_PATH",post.getImagePath());
        long result = db.insert(TABLE_POSTS,null,contentValues);
        if(result == -1){
            return 0;
        }else{
            String query = "SELECT MAX(ID) FROM " + TABLE_POSTS;
            Cursor cursor = db.rawQuery(query,null);
            int lastId = 0;
            if(cursor.moveToFirst()){
                do{
                    lastId = cursor.getInt(0);
                }while(cursor.moveToNext());
                return lastId;
            }
        }
        return 0;
    }

    // insert data into TABLE_STEP
    public long addSteps(int postId, String description, int stepOrder, String imagePath){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("POST_ID",postId);
        contentValues.put("DESCRIPTION",description);
        contentValues.put("STEP_ORDER",stepOrder);
        contentValues.put("IMAGE_PATH",imagePath);
        long id = db.insert(TABLE_STEPS,null,contentValues);
        return id;
    }

    // update data in TABLE_POSTS
     public boolean updatePost(Post post){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TITLE",post.getTitle());
        contentValues.put("DESCRIPTION",post.getDescription());
//        contentValues.put("AUTHOR",post.getAuthor());
//        contentValues.put("CATEGORY",post.getCategory());
//        contentValues.put("THUMB_UP_COUNTS",post.getThumbUpCounts());
//        contentValues.put("COLLECTED_COUNTS",post.getCollectedCounts());
        contentValues.put("IMAGE_PATH",post.getImagePath());
        db.update(TABLE_POSTS,contentValues,"ID = ?",new String[]{String.valueOf(post.getId())});
        // update steps
        db.delete(TABLE_STEPS,"POST_ID = ?",new String[]{String.valueOf(post.getId())});
        return true;
    }

    // delete data in TABLE_POSTS
    public boolean deleteRecipe(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_POSTS,"ID = ?",new String[]{String.valueOf(id)});
        db.delete(TABLE_STEPS,"POST_ID = ?",new String[]{String.valueOf(id)});
        return true;
    }

    public ArrayList<Recipe> getAllRecipe(String orderBy){
        ArrayList<Recipe> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_POSTS + " ORDER BY " + orderBy + " DESC";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                String author = cursor.getString(4);
                int category = cursor.getInt(5);
                int thumbUpCounts = cursor.getInt(6);
                int collectedCounts = cursor.getInt(7);
                String imagePath = cursor.getString(8);
                String query1 = "SELECT * FROM " + TABLE_STEPS + " WHERE POST_ID = " + id;
                Cursor cursor1 = db.rawQuery(query1,null);
                ArrayList<Step> recipeStep = new ArrayList<>();
                if(cursor1.moveToFirst()){
                    do{
                        int postId = cursor1.getInt(2);
                        String stepDesc = cursor1.getString(1);
                        int stepOrder = cursor1.getInt(3);
                        String stepImgPath = cursor1.getString(4);
                        recipeStep.add(new Step(stepDesc,stepImgPath, stepOrder));
                    }while(cursor1.moveToNext());
                }
                cursor1.close();
                //    public Recipe(int imageResId, String title, String description, boolean thumbUp, boolean collected, String nickName, String[] ingredients, ArrayList<RecipeStep> recipeSteps){
                Recipe recipe = new Recipe(1, title, description, thumbUpCounts,collectedCounts, author, null, recipeStep);
                recipe.setId(id);
                arrayList.add(recipe);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }

    //get one recipe
    public Recipe getRecipe(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_POSTS + " WHERE ID = " + id;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            int id1 = cursor.getInt(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            String author = cursor.getString(3);
            int category = cursor.getInt(4);
            int thumbUpCounts = cursor.getInt(5);
            int collectedCounts = cursor.getInt(6);
            String imagePath = cursor.getString(7);
            String query1 = "SELECT * FROM " + TABLE_STEPS + " WHERE POST_ID = " + id;
            Cursor cursor1 = db.rawQuery(query1,null);
            ArrayList<Step> recipeStep = new ArrayList<>();
            if(cursor1.moveToFirst()){
                do{
                    int postId = cursor1.getInt(2);
                    String stepDesc = cursor1.getString(1);
                    int stepOrder = cursor1.getInt(3);
                    String stepImgPath = cursor1.getString(4);
                    recipeStep.add(new Step(stepDesc,stepImgPath, stepOrder));
                }while(cursor1.moveToNext());
            }
            cursor1.close();
            //    public Recipe(int imageResId, String title, String description, boolean thumbUp, boolean collected, String nickName, String[] ingredients, ArrayList<RecipeStep> recipeSteps){
            Recipe recipe = new Recipe(1, title, description, thumbUpCounts,collectedCounts, author, null, recipeStep);
            recipe.setId(id);
            cursor.close();
            return recipe;
        }
        cursor.close();
        return null;
    }

    // register
    public boolean register(String username, String password, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USERNAME",username);
        contentValues.put("PASSWORD",password);
        contentValues.put("EMAIL",email);
        long result = db.insert(TABLE_USERS,null,contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    // login
    public boolean findUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE USERNAME = '" + username + "' AND PASSWORD = '" + password + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }


}
