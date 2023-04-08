package com.example.myapplication;

public class ContentItem {
    private int mImageResId;
    private String mTitle;
    private String mDescription;
    private boolean mThumbUp;
    private boolean mCollected;

    public ContentItem(int imageResId, String title, String description, boolean thumbUp, boolean collected) {
        mImageResId = imageResId;
        mTitle = title;
        mDescription = description;
        mThumbUp = thumbUp;
        mCollected = collected;
    }

//    public ContentItem(int pic2, String title, String description) {
//    }

    public int getImageResId() {
        return mImageResId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public boolean isThumbUp() {
        return mThumbUp;
    }

    public boolean isCollected() {
        return mCollected;
    }
}
