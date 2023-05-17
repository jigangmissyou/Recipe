package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{
    ImageView image;
    TextView text;
    List<Recipe> recipes;
    Context context;
    private HomeAdapter.onItemClickListener onItemClickListener;
    private OnEditClickListener onEditClickListener;
    private OnDeleteClickListener onDeleteClickListener;


    public HomeAdapter(Context context, List<Recipe> recipe) {
        this.context = context;
        this.recipes = recipe;
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
        String title = recipe.getTitle();
        String description = recipe.getDescription();

        // Load the image from the file path
//        String imagePath = recipe.getImagePath();
//        File imageFile = new File(imagePath);
//        if (imageFile.exists()) {
//            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
//            // Set the image bitmap
//            holder.images.setImageBitmap(bitmap);
//        } else {
            // Set a default image or handle the case when the file doesn't exist
            holder.images.setImageResource(R.drawable.image2);
//        }

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
//        holder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "You have edited this recipe!", Toast.LENGTH_SHORT).show();
//            }
//        });
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

