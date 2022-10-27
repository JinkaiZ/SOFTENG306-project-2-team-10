package com.se306.youseesoft.Models;

import android.widget.TableLayout;

import java.io.Serializable;

/**
 * An interface representing the product provided by the application
 */
public interface IProduct extends Serializable {

    // Getters

    public long getId();

    public String getName();

    public double getPrice();

    public String getCategory();

    public String getImage1();
    public String getImage2();
    public String getImage3();

    public double getRating();

    public String getAvailability();

    /**
     * Populate all the specifications title and value to the table layout
     *
     * @param tb TableLayout that the specifications is displayed
     */
    public void populateSpecsTable(TableLayout tb);

    /**
     *
     * @return Id of the color for background according to the category
     */
    public int getBackgroundColorId();
}
