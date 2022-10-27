package com.se306.youseesoft.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.se306.youseesoft.Activities.DetailActivity;
import com.se306.youseesoft.Enums.NavigationContentView;
import com.se306.youseesoft.Models.IProduct;
import com.se306.youseesoft.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * An adapter for RecyclerView and top_pick_list_item.xml used for image sliding
 */
public class TopPickListAdapter extends RecyclerView.Adapter<TopPickListAdapter.TopPickListViewHolder> {

    // Fields
    private List<IProduct> productList;
    private AppCompatActivity context;

    // Constructor
    public TopPickListAdapter(List<IProduct> products, AppCompatActivity activityContext) {
        productList = products;
        context = activityContext;
    }


    // Overridden method
    @NonNull
    @Override
    public TopPickListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopPickListAdapter.TopPickListViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.top_pick_list_item,
                parent,
                false
        ));
    }

    // Overridden method
    @Override
    public void onBindViewHolder(@NonNull TopPickListViewHolder holder, int position) {
        // Get the product at current position
        IProduct currentProduct = productList.get(position);

        // Load the image for the card view
        Picasso.get().load(currentProduct.getImage1()).into(holder.itemImage);

        // Set the product details and background color (depends on the category) for the card view
        holder.itemName.setText(currentProduct.getName());
        holder.itemPrice.setText("$ " + currentProduct.getPrice());
        holder.itemCard.setCardBackgroundColor(context.getColor(currentProduct.getBackgroundColorId()));

        // Event handler for the card
        holder.itemCard.setOnClickListener(view -> {
            //create intent and send data to the detail view
            Intent detailIntent = new Intent(context, DetailActivity.class);
            detailIntent.putExtra("Product", currentProduct);
            detailIntent.putExtra("PageIndex", NavigationContentView.Home);
            context.startActivity(detailIntent);

            // Animation
            context.overridePendingTransition(R.anim.page_slide_from_bottom, R.anim.hold);
        });
    }

    // Overridden method
    @Override
    public int getItemCount() {
        return productList.size();
    }

    /**
     * The RecyclerView holder of TopPickList adapter
     */
    protected class TopPickListViewHolder extends RecyclerView.ViewHolder {
        //Fields
        public ImageView itemImage;
        public TextView itemName;
        public TextView itemPrice;
        public CardView itemCard;

        // Constructor
        public TopPickListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.top_pick_image);
            itemName = itemView.findViewById(R.id.top_pick_item_name);
            itemPrice = itemView.findViewById(R.id.top_pick_item_price);
            itemCard = itemView.findViewById(R.id.top_pick_card);
        }
    }
}