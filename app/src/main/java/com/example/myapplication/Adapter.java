package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    ImageView images;
    TextView text;
//    List image, title, description;
//    ContentItem contentItem;
    // how to pass in a list of ContentItem objects?
    List<ContentItem> contentItems;

    Context context;
//    ArrayList title;
//    String description;
//    int image;

    // data is passed into the constructor
    public Adapter(Context context, List<ContentItem> contentItem) {
        this.context = context;
        this.contentItems = contentItem;
    }


//    public Adapter2(Context context, ArrayList courseImag, ArrayList courseName) {
//        this.context = context;
//        this.courseImag = courseImag;
//        this.courseName = courseName;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // define the data to be displayed
        ContentItem contentItem = contentItems.get(position);
        // get the image from the ContentItem object
        int image = contentItem.getImageResId();
        // get the title from the ContentItem object
        String title = contentItem.getTitle();
        // get the description from the ContentItem object
        String description = contentItem.getDescription();
        // set the image
        holder.images.setImageResource(image);
        // set the title
        holder.text.setText(title);
        // set the description
        holder.text.setText(description);

////        int image = this.contentItems.get(position);
//        holder.images.setImageResource(image);
//        holder.text.setText((CharSequence) this.contentItem.getTitle());
//        holder.text.setText((CharSequence) this.contentItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return contentItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView images;
        public TextView text;

        public TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.courseImage);
            text = itemView.findViewById(R.id.courseName);
            description = itemView.findViewById(R.id.courseDescription);
        }
    }
}
