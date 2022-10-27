package com.se306.youseesoft.Models;

/**
 * An abstract class representing personal computer
 */
public abstract class PersonalComputer extends ElectronicDevice {

    // Fields
    protected String cpu;
    protected  String gpu;
    protected String camera;
    protected  String storageSize;

    // Getters

    @Override
    public String getStorageSize(){return this.storageSize;};
    @Override
    public String getCpu(){return this.cpu;};
    @Override
    public String getGpu(){return this.gpu;};
    @Override
    public String getCamera(){return this.camera;};

    /**
     *
     * @return Id of the color for background according to the category
     */
    @Override
    public abstract int getBackgroundColorId();

}
