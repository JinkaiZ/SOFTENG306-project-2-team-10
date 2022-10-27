package com.se306.youseesoft.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.se306.youseesoft.Adapters.ProductItemAdapter;
import com.se306.youseesoft.Data.DataUtil;
import com.se306.youseesoft.Enums.Category;
import com.se306.youseesoft.Enums.NavigationContentView;
import com.se306.youseesoft.Events.NavigationListener;
import com.se306.youseesoft.Models.IProduct;
import com.se306.youseesoft.R;


import java.util.LinkedList;
import java.util.List;
/**
 * An activity class used for displaying and controlling all component in category list page
 */
public class ListActivity extends AppCompatActivity {

    List<IProduct> productList = new LinkedList<IProduct>();

    /**
     * The View holder of ListActivity
     */
    private class ViewHolder {
        //Fields
        RecyclerView categoryRecycler;
        TextView resultNumber;
        BottomNavigationView navView;
        Toolbar customToolbar;
        ProgressBar progressBar;

        // Constructor
        public ViewHolder() {
            categoryRecycler = findViewById(R.id.category_recycler_list);
            resultNumber = findViewById(R.id.category_number_found);
            navView = findViewById(R.id.bottom_navigation_bar);
            customToolbar = findViewById(R.id.customActionBar);
            progressBar = findViewById(R.id.category_list_progress_bar);
        }
    }

    ViewHolder vh;

    // Overridden method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Initialise view holder
        vh = new ViewHolder();

        // Navigation bar set-up
        NavigationListener navViewListener = new NavigationListener(NavigationContentView.Home, this);
        vh.navView.setOnItemSelectedListener(navViewListener);
        vh.navView.setOnItemReselectedListener(navViewListener);

        // Get the category type from intent
        Category cat = (Category) getIntent().getSerializableExtra("Category");
        // Start loading & display products
        fetchProductData(cat);

        // Update the customToolbar's state
        setSupportActionBar(vh.customToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        vh.customToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }

    /**
     * This method load all product with specified category from database
     * @param  cat is a enum that contains the category information
     */
    private void fetchProductData(Category cat) {

        // Set the text to the current category
        TextView titleText = findViewById(R.id.category_title);
        titleText.setText("Category: " + cat);

        // Getting products collection from Firestore
        DataUtil.fetchFromCategory(cat, task -> {
            if (task.isSuccessful()) {
                QuerySnapshot results = (QuerySnapshot) task.getResult();
                for (DocumentSnapshot product : results) {
                    productList.add(DataUtil.snapshotToProduct(product));
                }

                vh.resultNumber.setText(productList.size() + " result(s) found:");
                propagateAdaptor(productList);

            } else
                Toast.makeText(getBaseContext(), "Loading numbers collection failed from Firestore!", Toast.LENGTH_LONG).show();
        });
    }

    /**
     * This method  propagate all category items to adaptor
     * @param  data is a list of IProduct items that need to display
     */
    private void propagateAdaptor(List<IProduct> data) {
        ProductItemAdapter adapter = new ProductItemAdapter(data, this, NavigationContentView.Home);
        vh.categoryRecycler.setAdapter(adapter);
        vh.categoryRecycler.setLayoutManager(new LinearLayoutManager(this));

        vh.progressBar.setVisibility(View.GONE);
        vh.categoryRecycler.setVisibility(View.VISIBLE);
    }

    // Overridden method
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.page_slide_to_bottom);
    }

    // Overridden method
    @Override
    public void onBackPressed() {
        finish();
    }
}