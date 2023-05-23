package com.example.myapplication;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.Serializable;

public class StepItem implements Serializable {
    private View layout;
    private EditText editText;
    private ImageView imageView;
    private Bitmap image;

    private String imagePath;

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

    public String getImagePath() {
        // from imgView get path
        return imagePath;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTag() {
        return null;
    }
}
