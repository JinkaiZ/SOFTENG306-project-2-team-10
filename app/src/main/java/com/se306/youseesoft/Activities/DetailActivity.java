package com.se306.youseesoft.Activities;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.se306.youseesoft.Adapters.ImageSlidePagerAdapter;
import com.se306.youseesoft.Data.DataUtil;
import com.se306.youseesoft.Enums.NavigationContentView;
import com.se306.youseesoft.Events.NavigationListener;
import com.se306.youseesoft.Models.IProduct;
import com.se306.youseesoft.R;

/**
 * An activity class used for displaying and controlling all component in product detail page
 */
public class DetailActivity extends AppCompatActivity {

    /**
     * The View holder of DetailActivity
     */
    private class ViewHolder {
        //Fields
        BottomNavigationView navView;
        Toolbar customToolbar;
        Button addToCartButton;
        TableLayout specsTable;
        ViewPager2 pager;
        LinearLayout tabLayout;
        TextView tvProductName;
        TextView tvProductPrice;

        // Constructor
        public ViewHolder() {
            navView = findViewById(R.id.bottom_navigation_bar);
            customToolbar = findViewById(R.id.customActionBar);
            addToCartButton = findViewById(R.id.add_to_cart_button);
            specsTable = findViewById(R.id.specs_table);
            pager = findViewById(R.id.image_slider);
            tabLayout = findViewById(R.id.dots_indicator_container);
            tvProductName = findViewById(R.id.detail_product_name);
            tvProductPrice = findViewById(R.id.detail_product_price);
        }
    }

    ViewHolder vh;

    // Overridden method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Initialise view holder
        vh = new ViewHolder();

        // Get current navigation bar index
        NavigationContentView page = (NavigationContentView) getIntent().getSerializableExtra("PageIndex");

        // Tool bar set-up
        setSupportActionBar(vh.customToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        vh.customToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        // Navigation bar set-up
        NavigationListener navViewListener = new NavigationListener(page, this);
        vh.navView.setOnItemSelectedListener(navViewListener);
        vh.navView.setOnItemReselectedListener(navViewListener);
        vh.navView.getMenu().getItem(page.ordinal()).setChecked(true);

        // Get the IProduct object from intent
        IProduct product = (IProduct) getIntent().getSerializableExtra("Product");
        // Adjust the information displayed format
        product.populateSpecsTable(vh.specsTable);
        // Set-up the image slider adapter
        vh.pager.setAdapter(new ImageSlidePagerAdapter(product));
        setupSliderPositionIndicator(vh.tabLayout);

        // Set the labels
        vh.tvProductName.setText(product.getName());
        vh.tvProductPrice.setText("$" + product.getPrice());

        vh.pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            // Overridden method
            @Override
            public void onPageSelected(int position) {
                int count = vh.tabLayout.getChildCount();
                for (int i = 0; i < count; i++) {
                    TextView dot = (TextView) vh.tabLayout.getChildAt(i);
                    if (position == i) {
                        dot.setTextColor(getColor(R.color.primary));
                    } else {
                        dot.setTextColor(getColor(R.color.grey));
                    }
                }
                super.onPageSelected(position);
            }
        });

        // Button listener for clear all items
        vh.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IProduct product = (IProduct) getIntent().getSerializableExtra("Product");
                addToCart(product);
            }
        });
    }

    /**
     * This method setup the slider position
     */
    private void setupSliderPositionIndicator(LinearLayout tabLayout) {
        for (int i = 0; i < 3; i++) {
            TextView dot = new TextView(this);
            dot.setText(Html.fromHtml("&#9679;"));
            tabLayout.addView(dot);
        }
    }

    /**
     * This method retrieves data from database and add product into shopping cart
     */
    private void addToCart(IProduct product) {
        DataUtil.addProductToCart(product);
        Toast.makeText(this, "Added " + product.getName() + " to cart!", Toast.LENGTH_LONG).show();
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