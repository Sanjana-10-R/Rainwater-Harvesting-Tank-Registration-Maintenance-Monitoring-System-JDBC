package com.rainwater.bean;

import java.sql.Date;

public class MaintVisit {

    private int visitID;
    private String tankID;
    private Date scheduledDate;
    private String timeSlot;
    private String staffID;
    private String visitType;
    private String status;
    private Date createdDate; 
    private String remarks;    

   
    public int getVisitID() { return visitID; }
    public void setVisitID(int visitID) { this.visitID = visitID; }

    public String getTankID() { return tankID; }
    public void setTankID(String tankID) { this.tankID = tankID; }

    public Date getScheduledDate() { return scheduledDate; }
    public void setScheduledDate(Date scheduledDate) { this.scheduledDate = scheduledDate; }

    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }

    public String getStaffID() { return staffID; }
    public void setStaffID(String staffID) { this.staffID = staffID; }

    public String getVisitType() { return visitType; }
    public void setVisitType(String visitType) { this.visitType = visitType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
