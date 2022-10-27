package com.se306.youseesoft.Adapters;

import android.annotation.SuppressLint;
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

import com.se306.youseesoft.Data.DataUtil;
import com.se306.youseesoft.Activities.DetailActivity;
import com.se306.youseesoft.Enums.NavigationContentView;
import com.se306.youseesoft.Models.IProduct;
import com.se306.youseesoft.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * An adapter for RecyclerView and product_list_view_item.xml used for image sliding
 */
public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ProductItemViewHolder> {

    // Fields
    private List<IProduct> products;
    private AppCompatActivity context;
    private boolean isCartItem;
    private NavigationContentView navPage;

    // Constructors
    public ProductItemAdapter(List<IProduct> objects, AppCompatActivity activityContext, NavigationContentView page) {
        this(objects, activityContext, page,false);
    }

    public ProductItemAdapter(List<IProduct> objects, AppCompatActivity activityContext, NavigationContentView page, boolean isRemovable) {
        products = objects;
        context = activityContext;
        isCartItem = isRemovable;
        navPage = page;
    }

    // Overridden method
    @NonNull
    @Override
    public ProductItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductItemAdapter.ProductItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.product_list_view_item,
                parent,
                false
        ));
    }

    // Overridden method
    @Override
    public void onBindViewHolder(@NonNull ProductItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Get the product at current position
        IProduct product = products.get(holder.getAdapterPosition());

        // Load the image for the card view
        Picasso.get().load(product.getImage1()).into(holder.productIconImageView);

        // Set the product details and background color (depends on the category) for the card view
        holder.productName.setText(product.getName());
        holder.productPrice.setText("$ " + product.getPrice());
        holder.productRating.setText("Rating: " + product.getRating());
        holder.productAvailability.setText("Availability: " + product.getAvailability());
        holder.productCard.setCardBackgroundColor(context.getColor(product.getBackgroundColorId()));

        // Event handler for the card
        holder.productCard.setOnClickListener(view -> {
            //create intent and send data to the detail view
            Intent detailIntent = new Intent(context, DetailActivity.class);
            detailIntent.putExtra("Product", product);
            detailIntent.putExtra("PageIndex", navPage);
            context.startActivity(detailIntent);

            // Animation
            context.overridePendingTransition(R.anim.page_slide_from_bottom, R.anim.hold);
        });

        // Check if the card is for cart and make it removable if it is
        if (isCartItem) {
            // Make remove icon available and remove availability (not needed if added to cart)
            holder.productBin.setVisibility(View.VISIBLE);
            holder.productAvailability.setVisibility(View.GONE);

            // Event for removing the product
            holder.productBin.setOnClickListener(view -> {
                DataUtil.deleteProductFromCart(product);
                products.remove(position);

                notifyItemRemoved(position);
                notifyItemRangeChanged(position, products.size());
            });
        } else {
            // Remove the functionality to remove product
            holder.productBin.setVisibility(View.GONE);
        }
    }

    // Overridden method
    @Override
    public int getItemCount() {
        return products.size();
    }

    /**
     * The RecyclerView holder of ProductItem adapter
     */
    protected class ProductItemViewHolder extends RecyclerView.ViewHolder {
        //Fields
        ImageView productIconImageView;
        TextView productName;
        TextView productPrice;
        TextView productRating;
        TextView productAvailability;
        CardView productCard;
        ImageView productBin;

        // Constructor
        public ProductItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productIconImageView = itemView.findViewById(R.id.category_product_Icon_view);
            productName = itemView.findViewById(R.id.category_product_item_name);
            productPrice = itemView.findViewById(R.id.category_product_item_price);
            productRating = itemView.findViewById(R.id.category_product_item_rating);
            productAvailability = itemView.findViewById(R.id.category_product_item_avaliablity);
            productCard = itemView.findViewById(R.id.product_list_card_view);
            productBin = itemView.findViewById(R.id.category_product_bin);

        }
    }

}
