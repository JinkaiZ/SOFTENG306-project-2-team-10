package com.se306.youseesoft.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.firestore.QuerySnapshot;
import com.se306.youseesoft.Adapters.ProductItemAdapter;
import com.se306.youseesoft.Data.DataUtil;
import com.se306.youseesoft.Enums.NavigationContentView;
import com.se306.youseesoft.Events.NavigationListener;
import com.se306.youseesoft.Models.IProduct;
import com.se306.youseesoft.R;

import java.util.LinkedList;
import java.util.List;
/**
 * An activity class used for displaying and controlling all component in shopping cart page
 */
public class CartActivity extends AppCompatActivity {

    List<IProduct> productList = new LinkedList<>();

    /**
     * The RecyclerView holder of CartActivity
     */
    private class ViewHolder {
        //Fields
        RecyclerView recyclerView;
        Button clearAll;
        Toolbar customToolbar;
        BottomNavigationView navView;
        ProgressBar progressBar;
        ExtendedFloatingActionButton checkoutBtn;

        // Constructor
        public ViewHolder() {
            recyclerView = findViewById(R.id.cart_recycler_list);
            clearAll = findViewById(R.id.clear_all);
            customToolbar = findViewById(R.id.customActionBar);
            navView = findViewById(R.id.bottom_navigation_bar);
            progressBar = findViewById(R.id.cart_list_progress_bar);
            checkoutBtn = findViewById(R.id.checkout_button);
        }
    }

    ViewHolder vh;

    // Overridden method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialise view holder
        vh = new ViewHolder();

        // Navigation bar set-up
        setSupportActionBar(vh.customToolbar);
        NavigationListener navViewListener = new NavigationListener(NavigationContentView.Cart, this);
        vh.navView.setOnItemSelectedListener(navViewListener);
        vh.navView.setOnItemReselectedListener(navViewListener);
        vh.navView.getMenu().getItem(NavigationContentView.Cart.ordinal()).setChecked(true);

        // Load shopping cart items from database
        loadCartItems();

        // Button listener for clear all items
        vh.clearAll.setOnClickListener(view -> {
            clearAllItems();
        });

        // Button listener for checkout
        vh.checkoutBtn.setOnClickListener(view -> {
            clearAllItems();
            Toast.makeText(this, "Checkout successful", Toast.LENGTH_LONG).show();
        });
    }

    /**
     * This method clear all item in the database
     */
    private void clearAllItems() {
        for (IProduct product : productList) {
            DataUtil.deleteProductFromCart(product);
        }
        productList.clear();
        vh.recyclerView.getAdapter().notifyDataSetChanged();
    }

    /**
     * This method load all cart items from database
     */
    private void loadCartItems() {
        DataUtil.fetchProductFromCart(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot results = (QuerySnapshot) task.getResult();
                productList = DataUtil.querySnapShotToListProduct(results);
                propagateAdaptor(productList);
            } else
                Toast.makeText(getBaseContext(), "Loading numbers collection failed from Firestore!", Toast.LENGTH_LONG).show();
        });
    }

    /**
     * This method propagate all cart items to adaptor
     */
    private void propagateAdaptor(List<IProduct> data) {
        ProductItemAdapter adapter = new ProductItemAdapter(data, this
                , NavigationContentView.Cart, true);

        vh.recyclerView.setAdapter(adapter);
        vh.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        vh.progressBar.setVisibility(View.GONE);
        vh.recyclerView.setVisibility(View.VISIBLE);
    }

}