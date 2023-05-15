package com.example.myapplication;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class StepItem {
    private View layout;
    private EditText editText;
    private ImageView imageView;
    private Bitmap image;

    public StepItem(EditText editText, ImageView imageView) {
        this.layout = layout;
        this.editText = editText;
        this.imageView = imageView;
    }

    public View getLayout() {
        return layout;
    }

    public EditText getEditText() {
        return editText;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setImagePath(String imagePath) {
    }

    public String getTag() {
        return null;
    }
}
