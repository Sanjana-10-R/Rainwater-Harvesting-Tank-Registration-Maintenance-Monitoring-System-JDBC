package com.rainwater.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rainwater.bean.MaintVisit;
import com.rainwater.util.DBUtil;

public class MaintVisitDAO {

 
    public boolean insertVisit(MaintVisit v) throws Exception {
        String sql = "INSERT INTO MAINT_VISIT_TBL4 "
                   + "(VISIT_ID, TANK_ID, SCHEDULED_DATE, TIME_SLOT, STAFF_ID, VISIT_TYPE, VISIT_STATUS, CREATED_DATE, REMARKS) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, v.getVisitID());
            ps.setString(2, v.getTankID());
            ps.setDate(3, v.getScheduledDate());
            ps.setString(4, v.getTimeSlot());
            ps.setString(5, v.getStaffID());
            ps.setString(6, v.getVisitType());
            ps.setString(7, v.getStatus());
            ps.setDate(8, v.getCreatedDate());
            ps.setString(9, v.getRemarks());
            return ps.executeUpdate() > 0;
        }
    }

   
    public int generateVisitID() throws Exception {
        String sql = "SELECT NVL(MAX(VISIT_ID),0)+1 AS NEW_ID FROM MAINT_VISIT_TBL4";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt("NEW_ID");
        }
        return 1; 
    }

   
    public MaintVisit findVisit(int visitID) throws Exception {
        String sql = "SELECT * FROM MAINT_VISIT_TBL4 WHERE VISIT_ID=?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, visitID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapVisit(rs);
        }
        return null;
    }

   
    public MaintVisit findConflictVisit(String tankID, Date scheduledDate, String timeSlot) throws Exception {
        String sql = "SELECT * FROM MAINT_VISIT_TBL4 WHERE TANK_ID=? AND SCHEDULED_DATE=? AND TIME_SLOT=?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tankID);
            ps.setDate(2, scheduledDate);
            ps.setString(3, timeSlot);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapVisit(rs);
        }
        return null;
    }

    public boolean updateVisitStatus(int visitID, String status) throws Exception {
        String sql = "UPDATE MAINT_VISIT_TBL4 SET VISIT_STATUS=? WHERE VISIT_ID=?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, visitID);
            return ps.executeUpdate() > 0;
        }
    }

  
    public List<MaintVisit> findVisitsByTank(String tankID) throws Exception {
        String sql = "SELECT * FROM MAINT_VISIT_TBL4 WHERE TANK_ID=?";
        List<MaintVisit> list = new ArrayList<>();
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tankID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapVisit(rs));
            }
        }
        return list;
    }

   
    public List<MaintVisit> findVisitsByDateRange(Date from, Date to) throws Exception {
        String sql = "SELECT * FROM MAINT_VISIT_TBL4 WHERE SCHEDULED_DATE BETWEEN ? AND ?";
        List<MaintVisit> list = new ArrayList<>();
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, from);
            ps.setDate(2, to);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapVisit(rs));
            }
        }
        return list;
    }

  
    public List<MaintVisit> findFuturePlannedVisitsForTank(String tankID, Date refDate) throws Exception {
        String sql = "SELECT * FROM MAINT_VISIT_TBL4 WHERE TANK_ID=? AND VISIT_STATUS='PLANNED' AND SCHEDULED_DATE>?";
        List<MaintVisit> list = new ArrayList<>();
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tankID);
            ps.setDate(2, refDate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapVisit(rs));
            }
        }
        return list;
    }


    private MaintVisit mapVisit(ResultSet rs) throws Exception {
        MaintVisit v = new MaintVisit();
        v.setVisitID(rs.getInt("VISIT_ID"));
        v.setTankID(rs.getString("TANK_ID"));
        v.setScheduledDate(rs.getDate("SCHEDULED_DATE"));
        v.setTimeSlot(rs.getString("TIME_SLOT"));
        v.setStaffID(rs.getString("STAFF_ID"));
        v.setVisitType(rs.getString("VISIT_TYPE"));
        v.setStatus(rs.getString("VISIT_STATUS"));
        v.setCreatedDate(rs.getDate("CREATED_DATE"));
        v.setRemarks(rs.getString("REMARKS"));
        return v;
    }
}
