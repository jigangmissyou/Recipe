package com.example.myapplication;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RecipeFormActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private LinearLayout ingredientsLayout;
    private LinearLayout stepsLayout;
    private Button addIngredientBtn;
    private Button addStepBtn;
    private Button submitBtn;

    private List<EditText> ingredientEditTexts;
    private List<StepItem> stepItems;

    private Map<String, Integer> stepIndexMap = new HashMap<>();
    private Map<String, String> stepImagePathMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_form);

        ingredientsLayout = findViewById(R.id.ingredients_layout);
        stepsLayout = findViewById(R.id.steps_layout);
        addIngredientBtn = findViewById(R.id.add_ingredient_button);
        addStepBtn = findViewById(R.id.add_step_button);
        submitBtn = findViewById(R.id.submit_button);

        ingredientEditTexts = new ArrayList<>();
        stepItems = new ArrayList<>();

        addIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredientEditText();
            }
        });

        addStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStepItem();
            }
        });

//        ImageView stepImageView = findViewById(R.id.step_image_view);
//        stepImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                uploadImage(v);
//            }
//        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRecipe();
            }
        });
    }

    private void addIngredientEditText() {
        Context context = getApplicationContext();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        EditText editText = new EditText(context);
        editText.setLayoutParams(layoutParams);
        editText.setHint("Enter ingredient");
        ingredientsLayout.addView(editText);
        ingredientEditTexts.add(editText);
    }

    private void addStepItem() {
        Context context = getApplicationContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout stepItemLayout = (LinearLayout) inflater.inflate(R.layout.step_item, stepsLayout, false);

        EditText stepEditText = stepItemLayout.findViewById(R.id.step_edit_text);
        ImageView imageView = stepItemLayout.findViewById(R.id.step_image_view);
        Button removeStepBtn = stepItemLayout.findViewById(R.id.remove_step_button);

        removeStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeStepItem(stepItemLayout);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(v);
            }
        });

        imageView.setTag("step_image_" + stepItems.size());
        stepIndexMap.put(imageView.getTag().toString(), stepItems.size());


        stepItems.add(new StepItem(stepEditText, imageView));

        stepsLayout.addView(stepItemLayout);
    }

    private void removeStepItem(View stepItemLayout) {
        stepsLayout.removeView(stepItemLayout);
        StepItem stepItem = findStepItem(stepItemLayout);
        if (stepItem != null) {
            stepItems.remove(stepItem);

        }
    }

    private StepItem findStepItem(View stepItemLayout) {
        for (StepItem stepItem : stepItems) {
            if (stepItem.getLayout() == stepItemLayout) {
                return stepItem;
            }
        }
        return null;
    }

    private void submitRecipe() {
        String title = ((EditText) findViewById(R.id.title_edit_text)).getText().toString();
        String description = ((EditText) findViewById(R.id.description_edit_text)).getText().toString();

        List<String> ingredients = new ArrayList<>();
        for (EditText editText : ingredientEditTexts) {
            String ingredient = editText.getText().toString();
            if (!TextUtils.isEmpty(ingredient)) {
                ingredients.add(ingredient);
            }
        }

        List<Step> steps = new ArrayList<>();
        for (StepItem stepItem : stepItems) {
            String stepText = stepItem.getEditText().getText().toString();
            Bitmap stepImage = stepItem.getImage();
            if (!TextUtils.isEmpty(stepText)) {
                Step step = new Step(stepText, stepImage);
                steps.add(step);
            }
        }

        Post post = new Post(title, description, "author", "", 1);

        // add posts to sqlite
        DbHandler dbHandler = new DbHandler(this);
        int lastId = dbHandler.addPost(post);
        // log the last id
        Log.d("lastId", String.valueOf(lastId));
        if(lastId > 0){
            Step[] stepArray = steps.toArray(new Step[0]);
            for(int i=0; i<stepArray.length; i++) {
                Step step = stepArray[i];
                long id = dbHandler.addSteps(lastId, step.getText(), i, stepImagePathMap.get("step_image_" + i));
                // log the last id
                Log.d("lastId2", String.valueOf(id));
            }
        }
        // 这里可以执行提交操作，将菜谱数据发送到服务器或执行其他逻辑
        // 示例：输出菜谱数据
//        StringBuilder sb = new StringBuilder();
//        sb.append("Title: ").append(recipe.getTitle()).append("\n");
//        sb.append("Description: ").append(recipe.getDescription()).append("\n");
//        sb.append("Ingredients: ").append("\n");
//        for (String ingredient : recipe.getIngredients()) {
//            sb.append("- ").append(ingredient).append("\n");
//        }
//        sb.append("Steps: ").append("\n");


        //check sb content is correct

//        Log.d("RecipeFormActivity", sb.toString());
        // according to /data/user/0/com.example.myapplication/files/images/image_1684236600088.jpg, to get the image


        // 示例：显示菜谱数据
//        Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
    }

    public void uploadImage(View view) {
        String viewTag = (String) view.getTag();
        int stepIndex = getStepIndexFromTag(viewTag);
        startActivityForResult(getImageSelectionIntent(), PICK_IMAGE_REQUEST + stepIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode >= PICK_IMAGE_REQUEST && requestCode < PICK_IMAGE_REQUEST + stepItems.size()) {
                int stepIndex = requestCode - PICK_IMAGE_REQUEST;
                Uri imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    String imagePath = saveBitmapToLocal(bitmap);

                    StepItem stepItem = stepItems.get(stepIndex);
                    stepItem.setImagePath(imagePath);
                    // set image path to tag
                    stepImagePathMap.put(stepItem.getImageView().getTag().toString(), imagePath);
                    stepItem.getImageView().setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private String saveBitmapToLocal(Bitmap bitmap) {
        // 创建图片文件
        File imagesDir = new File(getFilesDir(), "images");
        if (!imagesDir.exists()) {
            imagesDir.mkdirs();
        }
        String fileName = "image_" + System.currentTimeMillis() + ".jpg";
        File imageFile = new File(imagesDir, fileName);

        try {
            // 将Bitmap保存到文件
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private int getStepIndexFromTag(String tag) {
        // TODO: 根据标识符找到对应的步骤索引
        // 在添加步骤布局时，为每个步骤项的视图设置一个唯一的标识符，这里需要根据标识符找到对应的步骤索引
        //log tag
        Log.d("tag_to_see", tag);
        // 返回对应的步骤索引
        Integer stepIndex = stepIndexMap.get(tag);
        // log stepIndex
        if (stepIndex != null) {
            return stepIndex;
        }

        return 0;
    }

    private Intent getImageSelectionIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        return Intent.createChooser(intent, "选择图片");
    }
}
