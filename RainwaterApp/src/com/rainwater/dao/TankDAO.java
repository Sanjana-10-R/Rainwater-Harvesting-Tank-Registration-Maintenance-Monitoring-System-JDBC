package com.rainwater.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rainwater.bean.Tank;
import com.rainwater.util.DBUtil;

public class TankDAO {

  
    public Tank findTank(String tankID) throws Exception {
        String sql = "SELECT * FROM TANK_TBL4 WHERE TANK_ID=?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tankID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Tank t = new Tank();
                t.setTankID(rs.getString("TANK_ID"));
                t.setPropertyBlock(rs.getString("PROPERTY_BLOCK"));
                t.setLocation(rs.getString("LOCATION"));
                t.setServiceFreq(rs.getString("SERVICE_FREQ"));
                t.setStatus(rs.getString("STATUS"));
                t.setLastServiceDate(rs.getDate("LAST_SERVICE_DATE"));
                return t;
            }
        }
        return null;
    }

    
    public List<Tank> viewAllTanks() throws Exception {
        String sql = "SELECT * FROM TANK_TBL4";
        List<Tank> list = new ArrayList<>();
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Tank t = new Tank();
                t.setTankID(rs.getString("TANK_ID"));
                t.setPropertyBlock(rs.getString("PROPERTY_BLOCK"));
                t.setLocation(rs.getString("LOCATION"));
                t.setServiceFreq(rs.getString("SERVICE_FREQ"));
                t.setStatus(rs.getString("STATUS"));
                t.setLastServiceDate(rs.getDate("LAST_SERVICE_DATE"));
                list.add(t);
            }
        }
        return list;
    }

    
    public boolean insertTank(Tank t) throws Exception {
        String sql = "INSERT INTO TANK_TBL4 (TANK_ID, PROPERTY_BLOCK, LOCATION, SERVICE_FREQ, LAST_SERVICE_DATE, STATUS) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getTankID());
            ps.setString(2, t.getPropertyBlock());
            ps.setString(3, t.getLocation());
            ps.setString(4, t.getServiceFreq());
            ps.setDate(5, t.getLastServiceDate());
            ps.setString(6, t.getStatus());
            return ps.executeUpdate() > 0;
        }
    }

   
    public boolean updateTankStatus(String tankID, String status) throws Exception {
        String sql = "UPDATE TANK_TBL4 SET STATUS=? WHERE TANK_ID=?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, tankID);
            return ps.executeUpdate() > 0;
        }
    }


    public boolean updateLastServiceDate(String tankID, Date lastServiceDate) throws Exception {
        String sql = "UPDATE TANK_TBL4 SET LAST_SERVICE_DATE=? WHERE TANK_ID=?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, lastServiceDate);
            ps.setString(2, tankID);
            return ps.executeUpdate() > 0;
        }
    }
}
