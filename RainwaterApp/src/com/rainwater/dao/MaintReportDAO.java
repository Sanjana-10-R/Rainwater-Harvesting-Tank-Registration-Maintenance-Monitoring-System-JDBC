package com.rainwater.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rainwater.bean.MaintReport;
import com.rainwater.util.DBUtil;

public class MaintReportDAO {

   
    public boolean insertReport(MaintReport r) throws Exception {
        String sql = "INSERT INTO MAINT_REPORT (REPORT_ID, VISIT_ID, STATUS) VALUES (?, ?, ?)";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, r.getReportID());
            ps.setInt(2, r.getVisitID());
            ps.setString(3, "SUBMITTED");
            return ps.executeUpdate() > 0;
        }
    }

  
    public List<MaintReport> findReportsByTank(String tankID) throws Exception {
        String sql = "SELECT R.* FROM MAINT_REPORT R " +
                     "JOIN MAINT_VISIT V ON R.VISIT_ID = V.VISIT_ID " +
                     "WHERE V.TANK_ID=?";
        List<MaintReport> list = new ArrayList<>();
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tankID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MaintReport r = new MaintReport();
                r.setReportID(rs.getInt("REPORT_ID"));
                r.setVisitID(rs.getInt("VISIT_ID"));
                r.setStatus(rs.getString("STATUS"));
                list.add(r);
            }
        }
        return list;
    }

   
    public int generateReportID() throws Exception {
        String sql = "SELECT NVL(MAX(REPORT_ID),0)+1 AS NEW_ID FROM MAINT_REPORT";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt("NEW_ID");
        }
        return 1;
    }
}
