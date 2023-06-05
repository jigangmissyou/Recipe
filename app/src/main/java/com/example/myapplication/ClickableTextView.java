package com.example.myapplication;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

public class ClickableTextView extends AppCompatTextView {
    private OnSubCategoryClickListener onSubCategoryClickListener;

    public ClickableTextView(Context context) {
        super(context);
        init();
    }

    public ClickableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClickableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void setOnSubCategoryClickListener(OnSubCategoryClickListener listener) {
        this.onSubCategoryClickListener = listener;
    }

    public interface OnSubCategoryClickListener {
        void onSubCategoryClick(String subCategory);
    }

    public void setTextWithClickableSubCategories(String text) {
        SpannableString spannableString = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                if (onSubCategoryClickListener != null) {
                    onSubCategoryClickListener.onSubCategoryClick(getText().toString());
                }
            }
        };
        spannableString.setSpan(clickableSpan, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(spannableString);
    }
}