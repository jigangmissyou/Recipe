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

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder>{
    ImageView image;
    TextView text;
    List<ContentItem> contentItems;
    Context context;
    private DashboardAdapter.onItemClickListener onItemClickListener;

    public DashboardAdapter(Context context, List<ContentItem> contentItem) {
        this.context = context;
        this.contentItems = contentItem;
    }

    public interface onItemClickListener {
        void onItemClick(ContentItem item);
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
        holder.title.setText(title);
        // set the description
        holder.description.setText(description);
    }

    @Override
    public int getItemCount() {
        return contentItems.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView images;
        public TextView title;
        // add the icon
        public ImageView thumb_up_icon;

        public ImageView collect_icon;

        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.content_image_view);
            title = itemView.findViewById(R.id.content_title_text_view);
            description = itemView.findViewById(R.id.content_description_text_view);
            thumb_up_icon = itemView.findViewById(R.id.thumb_up_button);
            collect_icon = itemView.findViewById(R.id.collect_button);
            images.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//
                    if (onItemClickListener != null) {
                        int position = getBindingAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            ContentItem item = contentItems.get(position);
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
        }
    }
}
