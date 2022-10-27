package com.se306.youseesoft.Models;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.common.collect.Sets;
import com.google.firebase.firestore.Exclude;
import com.se306.youseesoft.R;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Abstract class representing electronic devices
 */
public abstract class ElectronicDevice implements IProduct {

    //Fields
    protected long id;
    protected String name;
    protected String screenSize;
    protected String memory;
    protected double price;
    protected String brand;
    protected String category;
    protected String system;
    protected String image1;
    protected String image2;
    protected String image3;
    protected double rating;
    protected String availability;

    // Getters

    public double getRating() {
        return rating;
    }

    public String getAvailability() {
        return availability;
    }

    public String getImage1() {
        return image1;
    }

    public String getImage2() {
        return image2;
    }

    public String getImage3() {
        return image3;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getScreenSize() {
        return this.screenSize;
    }

    public String getMemory() {
        return this.memory;
    }

    public double getPrice() {
        return this.price;
    }

    public String getBrand() {
        return this.brand;
    }

    public String getCategory() {
        return this.category;
    }

    public String getSystem() {
        return this.system;
    }

    // Getter only available for some subclasses
    @Exclude
    public String getCellularConnectivity() {
        throw new RuntimeException(this.getClass().getSimpleName() + " doesn't have this method");
    }

    // Getter only available for some subclasses
    @Exclude
    public String getSupportedNetworks() {
        throw new RuntimeException(this.getClass().getSimpleName() + " doesn't have this method");
    }

    // Getter only available for some subclasses
    @Exclude
    public String getRearCamera() {
        throw new RuntimeException(this.getClass().getSimpleName() + " doesn't have this method");
    }

    // Getter only available for some subclasses
    @Exclude
    public String getFrontCamera() {
        throw new RuntimeException(this.getClass().getSimpleName() + " doesn't have this method");
    }

    // Getter only available for some subclasses
    @Exclude
    public String getStorageSize() {
        throw new RuntimeException(this.getClass().getSimpleName() + " doesn't have this method");
    }

    // Getter only available for some subclasses
    @Exclude
    public String getCpu() {
        throw new RuntimeException(this.getClass().getSimpleName() + " doesn't have this method");
    }

    // Getter only available for some subclasses
    @Exclude
    public String getGpu() {
        throw new RuntimeException(this.getClass().getSimpleName() + " doesn't have this method");
    }

    // Getter only available for some subclasses
    @Exclude
    public String getCamera() {
        throw new RuntimeException(this.getClass().getSimpleName() + " doesn't have this method");
    }

    /**
     * Populate all the specifications title and value to the table layout
     *
     * @param tb TableLayout that the specifications is displayed
     */
    @Exclude
    @Override
    public void populateSpecsTable(TableLayout tb) {
        tb.removeAllViews();
        populateSpecsTableFromParent(tb, this.getClass());
    }

    /**
     * Recursively find the specifications required from the parent
     *
     * @param tb    TableLayout that the specifications is displayed
     * @param clazz The class that contains the required fields
     */
    @Exclude
    private void populateSpecsTableFromParent(TableLayout tb, Class clazz) {
        // Base case, return
        if (clazz == IProduct.class || clazz == Object.class) {
            return;
        }

        // If this is not the top of the hierarchy, find more fields from parent
        populateSpecsTableFromParent(tb, clazz.getSuperclass());

        // Initialise fields that are not required to present
        Set<String> excludeFields = Sets.newHashSet(new String[]{"image1", "image2", "image3", "name", "price"});

        // Get all the field names
        for (Field f : clazz.getDeclaredFields()) {
            String specsName = f.getName();
            String specsValue = "";

            // Ignore unnecessary fields
            if (excludeFields.contains(specsName)) {
                continue;
            }

            // Separate the camel case field(specs) name and capitalise the first letter
            String specsNameSeparated = "";
            for (String s : specsName.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
                specsNameSeparated += s.substring(0, 1).toUpperCase() + s.substring(1) + " ";
            }

            // Try to get the field value
            try {
                specsValue = f.get(this).toString();
            } catch (Exception e) {
            }

            // Create table rows for specification field name and value, then set the color
            Context context = tb.getContext();
            TableRow trSpecsName = new TableRow(tb.getContext());
            TableRow trSpecsVal = new TableRow(tb.getContext());
            trSpecsName.setBackgroundColor(context.getColor(R.color.color_code_200));

            // Set the field/specs name with styling
            TextView tvSpecsName = new TextView(tb.getContext());
            tvSpecsName.setTextColor(context.getColor(R.color.black));
            tvSpecsName.setText(specsNameSeparated);
            tvSpecsName.setTextSize(16);
            trSpecsName.addView(tvSpecsName);

            // Set the field/specs value with styling
            TextView tvSpecsVal = new TextView(tb.getContext());
            tvSpecsVal.setTextColor(context.getColor(R.color.black));
            tvSpecsVal.setTypeface(Typeface.DEFAULT_BOLD);
            tvSpecsVal.setTextSize(16);
            tvSpecsVal.setText(specsValue);
            trSpecsVal.addView(tvSpecsVal);

            // Add both row to table
            tb.addView(trSpecsName);
            tb.addView(trSpecsVal);
        }
    }

    /**
     * @return Id of the color for background according to the category
     */
    @Exclude
    public abstract int getBackgroundColorId();
}
