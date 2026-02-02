package com.rainwater.bean;

import java.sql.Date;

public class Tank {

    private String tankID;
    private String propertyBlock;
    private String location;
    private double capacityLiters;
    private Date installationDate;
    private String serviceFreq;
    private Date lastServiceDate;
    private String status;

    public String getTankID() {
        return tankID;
    }

    public void setTankID(String tankID) {
        this.tankID = tankID;
    }

    public String getPropertyBlock() {
        return propertyBlock;
    }

    public void setPropertyBlock(String propertyBlock) {
        this.propertyBlock = propertyBlock;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getCapacityLiters() {
        return capacityLiters;
    }

    public void setCapacityLiters(double capacityLiters) {
        this.capacityLiters = capacityLiters;
    }

    public Date getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(Date installationDate) {
        this.installationDate = installationDate;
    }

    public String getServiceFreq() {
        return serviceFreq;
    }

    public void setServiceFreq(String serviceFreq) {
        this.serviceFreq = serviceFreq;
    }

    public Date getLastServiceDate() {
        return lastServiceDate;
    }

    public void setLastServiceDate(Date lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
