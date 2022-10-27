package com.se306.youseesoft.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.firebase.firestore.QuerySnapshot;
import com.se306.youseesoft.Adapters.ProductItemAdapter;
import com.se306.youseesoft.Data.DataUtil;
import com.se306.youseesoft.Enums.Category;
import com.se306.youseesoft.Enums.NavigationContentView;
import com.se306.youseesoft.Events.NavigationListener;
import com.se306.youseesoft.Models.IProduct;
import com.se306.youseesoft.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * An activity class used for displaying and controlling all component in search page
 */
public class SearchActivity extends AppCompatActivity {

    List<IProduct> productList = new LinkedList<>();

    /**
     * The View holder of SearchActivity
     */
    private class ViewHolder {
        //Fields
        RecyclerView searchRecycler;
        TextView resultNumber;
        BottomNavigationView navView;
        Toolbar customToolbar;

        // Constructor
        public ViewHolder() {
            searchRecycler = findViewById(R.id.search_recycler_list);
            resultNumber = findViewById(R.id.number_of_fund_results);
            navView = findViewById(R.id.bottom_navigation_bar);
            customToolbar = findViewById(R.id.customActionBar);
        }
    }

    ViewHolder vh;

    // Overridden method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Initialise view holder
        vh = new ViewHolder();

        // Navigation bar set-up
        setSupportActionBar(vh.customToolbar);
        NavigationListener navViewListener = new NavigationListener(NavigationContentView.Search, this);
        vh.navView.setOnItemSelectedListener(navViewListener);
        vh.navView.setOnItemReselectedListener(navViewListener);
        vh.navView.getMenu().getItem(NavigationContentView.Search.ordinal()).setChecked(true);

        // add query text listener to the search bar
        configSearchBar();

    }

    /**
     * This method control all component in search bar
     */
    private void configSearchBar() {
        SearchView searchBar = findViewById(R.id.search_bar);
        searchBar.setOnClickListener(new View.OnClickListener() {
            // Overridden method
            @Override
            public void onClick(View v) {
                searchBar.onActionViewExpanded();

            }
        });

        searchBar.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    // Overridden method
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        if (query.length() > 0) {
                            processSearch(query);
                        }
                        searchBar.setVisibility(View.GONE);
                        searchBar.setVisibility(View.VISIBLE);
                        return true;
                    }
                    // Overridden method
                    @Override
                    public boolean onQueryTextChange(String query) {
                        return false;
                    }
                }
        );

    }

    /**
     * This method start the searching process
     */
    private void processSearch(String input) {
        getResultList(input, Category.Phone);
        getResultList(input, Category.Tablet);
        getResultList(input, Category.Laptop);
    }

    /**
     * This method get the load data for specified category from and compare it with user's input
     * @param  cat is a enum that contains the category information
     * @param  input is the user's text input
     */
    private void getResultList(String input, Category cat) {
        String inputUpper = input.toUpperCase();
        DataUtil.fetchFromCategory(cat, task -> {
            if (task.isSuccessful()) {
                QuerySnapshot results = (QuerySnapshot) task.getResult();
                productList.addAll(DataUtil.querySnapShotToListProduct(results));

                List<IProduct> filteredList = new ArrayList<>();
                for (IProduct p : productList) {
                    if (p.getName().toUpperCase().contains(inputUpper)) {
                        filteredList.add(p);
                    }
                }

                propagateAdaptor(filteredList);
                if (filteredList.size() > 0) {
                    // Once the task is successful and data is fetched, propagate the adaptor
                    vh.resultNumber.setText("  " + filteredList.size() + " result(s) found:");
                } else {
                    vh.resultNumber.setText("No result found for '" + input + "'");
                }
                vh.resultNumber.setVisibility(View.VISIBLE);

            }
        });
   }

    /**
     * This method  propagate all result items to adaptor
     * @param  data is a list of IProduct items that need to display
     */
    private void propagateAdaptor(List<IProduct> data) {
        ProductItemAdapter adapter = new ProductItemAdapter(data, this, NavigationContentView.Search);
        vh.searchRecycler.setAdapter(adapter);
        vh.searchRecycler.setLayoutManager(new LinearLayoutManager(this));
        vh.searchRecycler.setVisibility(View.VISIBLE);
    }

}