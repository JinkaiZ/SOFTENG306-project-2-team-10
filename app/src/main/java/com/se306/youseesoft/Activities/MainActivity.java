package com.se306.youseesoft.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.QuerySnapshot;
import com.se306.youseesoft.Adapters.TopPickListAdapter;
import com.se306.youseesoft.Data.DataUtil;
import com.se306.youseesoft.Enums.Category;
import com.se306.youseesoft.Enums.NavigationContentView;
import com.se306.youseesoft.Events.NavigationListener;
import com.se306.youseesoft.Models.IProduct;
import com.se306.youseesoft.R;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
/**
 * An activity class used for displaying and controlling all component in main page
 */
public class MainActivity extends AppCompatActivity {

    List<IProduct> productList = new LinkedList<IProduct>();

    /**
     * The View holder of ListActivity
     */
    private class ViewHolder {
        //Fields
        BottomNavigationView navView;
        Toolbar customToolbar;
        ProgressBar topPickProgressBar;
        RecyclerView topPickList;

        // Constructor
        public ViewHolder() {
            navView = findViewById(R.id.bottom_navigation_bar);
            customToolbar = findViewById(R.id.customActionBar);
            topPickProgressBar = findViewById(R.id.top_pick_list_progress_bar);
            topPickList = findViewById(R.id.top_pick_recycler_view);
        }
    }

    ViewHolder vh;

    // Overridden method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise view holder
        vh = new ViewHolder();

        // Navigation bar set-up
        setSupportActionBar(vh.customToolbar);
        NavigationListener navViewListener = new NavigationListener(NavigationContentView.Home, this);
        vh.navView.setOnItemSelectedListener(navViewListener);
        vh.navView.setOnItemReselectedListener(navViewListener);
        vh.navView.getMenu().getItem(0).setChecked(true);

        setCategoryListeners();

        getTopPickList(Category.Phone);
        getTopPickList(Category.Tablet);
        getTopPickList(Category.Laptop);
   }

    /**
     * This method is a listener for category button
     */
    private void setCategoryListeners() {
        ArrayList<Integer> ids = new ArrayList<>(Arrays.asList(R.id.tablet_category, R.id.laptop_category, R.id.phone_category));
        for (int id : ids) {
            CardView v = findViewById(id);
            v.setOnClickListener(new View.OnClickListener() {
                // Overridden method
                @Override
                public void onClick(View view) {
                    Intent categoryIntent = new Intent(getBaseContext(), ListActivity.class);
                    categoryIntent.putExtra("Category", Category.valueOf(v.getTag().toString()));
                    startActivity(categoryIntent);

                    // Add animation when clicking on the category list
                    overridePendingTransition(R.anim.page_slide_from_bottom, R.anim.hold);
                }
            });
        }
    }

    /**
     * This method get the items form database for top pick items
     * @param  cat is a enum that contains the category information
     */
    private void getTopPickList(Category cat){
        DataUtil.fetchWithRatingFromCategory(cat, 4.6, (OnCompleteListener<QuerySnapshot>) task -> {
            productList.addAll(DataUtil.querySnapShotToListProduct(Objects.requireNonNull(task.getResult())));
            propagateTopPickAdaptor(productList);
        });
    }

    /**
     * This method  propagate all category items to adaptor
     * @param  data is a list of IProduct items that need to display
     */
    private void propagateTopPickAdaptor(List<IProduct> data) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        vh.topPickList.setLayoutManager(linearLayoutManager);
        TopPickListAdapter topPickAdaptor = new TopPickListAdapter(data, this);
        vh.topPickList.setAdapter(topPickAdaptor);

        vh.topPickProgressBar.setVisibility(View.GONE);
        vh.topPickList.setVisibility(View.VISIBLE);
    }
}