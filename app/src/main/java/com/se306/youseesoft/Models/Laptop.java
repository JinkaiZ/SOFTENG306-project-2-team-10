package com.se306.youseesoft.Models;

import com.se306.youseesoft.R;

/**
 * A class representing the category of laptop
 */
public class Laptop extends PersonalComputer {

    // Constructor
    public Laptop(long id, String name, String screenSize, String memory, double price, String band, String category, String system,
                  String cpu, String gpu, String camera, String storageSize, String image1, String image2, String image3, double rating, String availability) {
        this.id = id;
        this.name = name;
        this.screenSize = screenSize;
        this.memory = memory;
        this.price = price;
        this.brand = band;
        this.category = category;
        this.system = system;
        this.cpu = cpu;
        this.gpu = gpu;
        this.camera = camera;
        this.storageSize = storageSize;
        this.image1 = image1;
        this.image1 = image2;
        this.image1 = image3;
        this.availability = availability;
        this.rating = rating;
    }

    // Constructor
    public Laptop() {

    }

    /**
     *
     * @return Id of the color for background according to the category
     */
    @Override
    public int getBackgroundColorId() {
        return R.color.laptop_category_color;
    }
}
