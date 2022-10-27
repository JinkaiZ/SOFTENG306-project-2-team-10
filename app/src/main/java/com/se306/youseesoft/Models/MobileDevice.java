package com.se306.youseesoft.Models;

/**
 * An abstract class representing mobile devices
 */
public abstract class MobileDevice extends ElectronicDevice {

    // Fields
    protected String cellularConnectivity;
    protected String supportedNetworks;
    protected String rearCamera;
    protected String frontCamera;

    // Getters

    @Override
    public String getCellularConnectivity(){return this.cellularConnectivity;};
    @Override
    public String getSupportedNetworks(){return this.supportedNetworks;};
    @Override
    public String getRearCamera(){return this.rearCamera;};
    @Override
    public String getFrontCamera(){return this.frontCamera;};

    /**
     *
     * @return Id of the color for background according to the category
     */
    @Override
    public abstract int getBackgroundColorId();
}
