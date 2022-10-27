package com.se306.youseesoft.Models;

import com.se306.youseesoft.R;

/**
 * A class representing the category of phone
 */
public class Phone extends MobileDevice {

    // Constructor
    public Phone(long id, String name, String screenSize, String memory, double price, String band, String category, String system,
                  String cellularConnectivity, String supportedNetwork, String rearCamera, String frontCamera, String image1, String image2, String image3, double rating, String availability) {
        this.id = id;
        this.name = name;
        this.screenSize = screenSize;
        this.memory = memory;
        this.price = price;
        this.brand = band;
        this.category = category;
        this.system = system;
        this.cellularConnectivity = cellularConnectivity;
        this.supportedNetworks = supportedNetwork;
        this.rearCamera = rearCamera;
        this.frontCamera = frontCamera;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.availability = availability;
        this.rating = rating;
    }

    // Constructor
    public Phone() {

    }

    /**
     *
     * @return Id of the color for background according to the category
     */
    @Override
    public int getBackgroundColorId() {
        return R.color.phone_category_color;
    }
}
