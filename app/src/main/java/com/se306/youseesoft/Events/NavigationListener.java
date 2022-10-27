package com.se306.youseesoft.Events;

import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;
import com.se306.youseesoft.Activities.CartActivity;
import com.se306.youseesoft.Activities.DetailActivity;
import com.se306.youseesoft.Enums.NavigationContentView;
import com.se306.youseesoft.Activities.MainActivity;
import com.se306.youseesoft.R;
import com.se306.youseesoft.Activities.SearchActivity;

/**
 * Event handler for bottom navigation bar
 */
public class NavigationListener implements NavigationBarView.OnItemSelectedListener,
        NavigationBarView.OnItemReselectedListener {

    // Fields
    private AppCompatActivity context;
    private NavigationContentView page;

    // Constructor
    public NavigationListener(NavigationContentView view, AppCompatActivity mainActivity) {
        page = view;
        context = mainActivity;
    }

    // Overridden method
    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        // Ignore reselected event
    }

    // Overridden method
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Check which menu item in navigation bar being selected
        switch (item.getItemId()) {
            // Send intent to the selected page, close the current page, and set the animation
            case R.id.navigation_home:
                Intent mainIntent = new Intent(context, MainActivity.class);
                context.startActivity(mainIntent);

                // Set animation
                context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

                break;

            case R.id.navigation_search:
                Intent searchIntent = new Intent(context, SearchActivity.class);
                context.startActivity(searchIntent);

                // animation type varies based on its previous context
                if (page == NavigationContentView.Home) {
                    context.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                } else {
                    context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                }
                break;

            case R.id.navigation_cart:
                Intent cartIntent = new Intent(context, CartActivity.class);
                context.startActivity(cartIntent);

                // Set animation
                context.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;

            default:
                break;
        }

        // Prevent closing detail activity
        if (!(context instanceof DetailActivity)) {
            context.finish();
        }
        return true;
    }
}
