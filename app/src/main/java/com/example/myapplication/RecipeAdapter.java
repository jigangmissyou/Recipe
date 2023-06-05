package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.StepItem;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    // 其他代码...
    private List<StepItem> stepItems;  // 声明stepItems列表

    public RecipeAdapter(List<StepItem> stepItems) {
        this.stepItems = stepItems;
    }
    public void uploadImage(int position) {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.stepImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 调用上传图片的方法
                uploadImage(viewHolder.getAdapterPosition());
            }
        });

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View stepImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    // 其他代码...
}

