package com.rainwater.service;

import java.sql.Connection;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import com.rainwater.bean.Tank;
import com.rainwater.bean.MaintVisit;
import com.rainwater.bean.MaintReport;
import com.rainwater.dao.*;
import com.rainwater.util.DBUtil;

public class TankService {

    private TankDAO tankDAO = new TankDAO();
    private MaintVisitDAO visitDAO = new MaintVisitDAO();
    private MaintReportDAO reportDAO = new MaintReportDAO();

    private final List<String> allowedFreq =
            Arrays.asList("MONTHLY","QUARTERLY","HALF_YEARLY","YEARLY");


    public Tank viewTankDetails(String tankID) throws Exception {
        return tankDAO.findTank(tankID);
    }

    public List<Tank> viewAllTanks() throws Exception {
        return tankDAO.viewAllTanks();
    }

    

    public boolean registerNewTank(Tank t) throws Exception {

        if(t.getTankID().isBlank() ||
           t.getPropertyBlock().isBlank() ||
           t.getLocation().isBlank())
            throw new ValidationException("Mandatory fields missing");

        if(!allowedFreq.contains(t.getServiceFreq()))
            throw new ValidationException("Invalid frequency");

        if(tankDAO.findTank(t.getTankID()) != null)
            throw new ValidationException("Tank exists");

        t.setStatus("ACTIVE");

        return tankDAO.insertTank(t);
    }

    

    public List<MaintVisit> listVisitsByTank(String tankID) throws Exception {
        return visitDAO.findVisitsByTank(tankID);
    }

    public List<MaintVisit> listVisitsByDateRange(Date f, Date t) throws Exception {
        return visitDAO.findVisitsByDateRange(f,t);
    }


    public List<MaintReport> listReportsByTank(String tankID) throws Exception {
        return reportDAO.findReportsByTank(tankID);
    }

 

    public boolean scheduleMaintenanceVisit(String tankID,
                                            Date scheduledDate,
                                            String timeSlot,
                                            String staffID,
                                            String visitType,
                                            Date createdDate)
            throws Exception {

        Tank tank = tankDAO.findTank(tankID);

        if(tank==null || !"ACTIVE".equals(tank.getStatus()))
            return false;

        if(visitDAO.findConflictVisit(tankID,scheduledDate,timeSlot)!=null)
            throw new VisitConflictException("Conflict");

        Connection con = DBUtil.getDBConnection();
        con.setAutoCommit(false);

        try{
            int id = visitDAO.generateVisitID();

            MaintVisit v = new MaintVisit();
            v.setVisitID(id);
            v.setTankID(tankID);
            v.setScheduledDate(scheduledDate);
            v.setTimeSlot(timeSlot);
            v.setStaffID(staffID);
            v.setVisitType(visitType);
            v.setStatus("PLANNED");

            visitDAO.insertVisit(v);

            con.commit();
            return true;

        }catch(Exception e){
            con.rollback();
            return false;
        }
    }

    

    public boolean recordMaintenanceReport(int visitID,
                                           String notes) throws Exception {

        MaintVisit v = visitDAO.findVisit(visitID);

        if(v==null || !"PLANNED".equals(v.getStatus()))
            return false;

        Connection con = DBUtil.getDBConnection();
        con.setAutoCommit(false);

        try{

            int rid = reportDAO.generateReportID();

            MaintReport r = new MaintReport();
            r.setReportID(rid);
            r.setVisitID(visitID);
            r.setStatus("SUBMITTED");

            reportDAO.insertReport(r);

            visitDAO.updateVisitStatus(visitID,"COMPLETED");

            tankDAO.updateLastServiceDate(v.getTankID(),v.getScheduledDate());

            con.commit();
            return true;

        }catch(Exception e){
            con.rollback();
            return false;
        }
    }

    

    public boolean disableTank(String tankID,Date ref)
            throws Exception{

        List<MaintVisit> list =
            visitDAO.findFuturePlannedVisitsForTank(tankID,ref);

        if(!list.isEmpty())
            throw new ActiveVisitsExistException("Active visits");

        return tankDAO.updateTankStatus(tankID,"INACTIVE");
    }

 

    public static class ValidationException extends Exception{
        public ValidationException(String m){super(m);}
    }

    public static class VisitConflictException extends Exception{
        public VisitConflictException(String m){super(m);}
    }

    public static class ActiveVisitsExistException extends Exception{
        public ActiveVisitsExistException(String m){super(m);}
    }
}
