package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{
    ImageView image;
    TextView text;
    List<Recipe> recipes;
    Context context;
    private HomeAdapter.onItemClickListener onItemClickListener;
    private OnEditClickListener onEditClickListener;
    private OnDeleteClickListener onDeleteClickListener;

    private boolean isLoggedIn = false;


    public HomeAdapter(Context context, List<Recipe> recipe, boolean isLoggedIn){
        this.context = context;
        this.recipes = recipe;
        this.isLoggedIn = isLoggedIn;
    }

    public interface onItemClickListener {
        void onItemClick(Recipe item);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        int imageResId = recipe.getImageResId();
        ArrayList<Step> stepList = recipe.getRecipeSteps();
        // find the last step as the image
        Step lastStep = stepList.get(stepList.size()-1);
        String imagePath = lastStep.getImagePath();
        if (imagePath == null) {
            // Set a default image or handle the case when the file doesn't exist
            holder.images.setImageResource(R.drawable.baseline_panorama_24);
        }else{
            // log the image path
            // Load the image from the file path
            holder.images.setImageBitmap(BitmapFactory.decodeFile(imagePath));
        }

        String title = recipe.getTitle();
        String description = recipe.getDescription();
        holder.title.setText(title);
        holder.description.setText(description);
        holder.thumb_up_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You have liked this recipe!", Toast.LENGTH_SHORT).show();
            }
        });
        holder.collect_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You have collected this recipe!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView images;
        public TextView title;
        // add the icon
        public ImageView thumb_up_icon;

        public ImageView collect_icon;

        public ImageView edit;

        public ImageView delete;

        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.content_image_view);
            title = itemView.findViewById(R.id.content_title_text_view);
            description = itemView.findViewById(R.id.content_description_text_view);
            thumb_up_icon = itemView.findViewById(R.id.thumb_up_button);
            collect_icon = itemView.findViewById(R.id.collect_button);
            edit = itemView.findViewById(R.id.edit_button);
            delete = itemView.findViewById(R.id.delete_button);
            Log.d("HomeAdapter", "isLoggedIn: " + isLoggedIn);
            if (isLoggedIn) {
                edit.setVisibility(itemView.VISIBLE);
                delete.setVisibility(itemView.VISIBLE);
            } else {
                edit.setVisibility(itemView.GONE);
                delete.setVisibility(itemView.GONE);
            }

            images.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//
                    if (onItemClickListener != null) {
                        int position = getBindingAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Recipe item = recipes.get(position);
                            onItemClickListener.onItemClick(item);
                        }
                    }
                }
            });
            thumb_up_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getBindingAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Toast.makeText(context, "Thumb up", Toast.LENGTH_SHORT).show();
//                            ContentItem item = contentItems.get(position);
//                            onItemClickListener.onItemClick(item);
                        }
                    }
                }
            });
            collect_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getBindingAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Toast.makeText(context, "Collected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // redirect to edit page, and pass the recipe id
                    if (onItemClickListener != null) {
                        int position = getBindingAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Recipe item = recipes.get(position);
                            onEditClickListener.onEditClick(item);                        }
                        // redirect to edit page
                    }

                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // redirect to edit page, and pass the recipe id
                    if (onItemClickListener != null) {
                        int position = getBindingAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Recipe item = recipes.get(position);
                            onDeleteClickListener.onDeleteClick(item);                        }
                        // redirect to edit page
                    }

                }
            });



        }
    }

    public void setOnEditClickListener(OnEditClickListener listener) {
        this.onEditClickListener = listener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.onDeleteClickListener = listener;
    }
    public interface OnEditClickListener {
        void onEditClick(Recipe recipe);
    }
    public interface OnDeleteClickListener {
        void onDeleteClick(Recipe recipe);
    }



}

