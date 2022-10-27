package com.se306.youseesoft.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se306.youseesoft.Models.IProduct;
import com.se306.youseesoft.R;
import com.squareup.picasso.Picasso;

/**
 * An adapter for Pager2 and fragment_image_slide.xml used for image sliding
 */
public class ImageSlidePagerAdapter extends RecyclerView.Adapter<ImageSlidePagerAdapter.ImageSliderViewHolder> {

    // Field
    private IProduct device;

    // Constructor
    public ImageSlidePagerAdapter(IProduct product) {
        device = product;
    }

    // Overridden method
    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageSliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(
           R.layout.fragment_image_slide,
                parent,
                false
        ));
    }

    // Overridden method
    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {

        // Set the image based on the position
        switch (position) {
            case 0:
                Picasso.get().load(device.getImage1()).into(holder.imageView);
                break;
            case 1:
                Picasso.get().load(device.getImage2()).into(holder.imageView);
                break;
            case 2:
                Picasso.get().load(device.getImage3()).into(holder.imageView);
                break;
            default:
                break;
        }
    }

    // Overridden method
    @Override
    public int getItemCount() {
        return 3;
    }

    /**
     * The RecyclerView holder of ProductItem adapter
     */
    protected class ImageSliderViewHolder extends RecyclerView.ViewHolder {
        // Field
        public ImageView imageView;

        // Constructor
        public ImageSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fragment_image_content);
        }
    }
}