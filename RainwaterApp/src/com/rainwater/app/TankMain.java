package com.rainwater.app;

import java.sql.Date;

import com.rainwater.bean.Tank;
import com.rainwater.service.TankService;
import com.rainwater.service.TankService.ValidationException;
import com.rainwater.service.TankService.VisitConflictException;

public class TankMain {

    private static TankService service = new TankService();

    public static void main(String[] args) {

        System.out.println("--- Rainwater Harvesting Maintenance Console ---");



        try {

            Tank t = new Tank();

            t.setTankID("TN2004");
            t.setPropertyBlock("C-21");
            t.setLocation("Near community hall - left side");
            t.setCapacityLiters(6000);
            t.setInstallationDate(new Date(System.currentTimeMillis()));
            t.setServiceFreq("QUARTERLY");
            t.setLastServiceDate(null);
            t.setStatus("ACTIVE");

            boolean ok = service.registerNewTank(t);

            System.out.println(ok ? "TANK REGISTERED" : "TANK REGISTRATION FAILED");

        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.toString());
            return;
        } catch (Exception e) {
            System.out.println("System Error: " + e.getMessage());
            return;
        }

       

        try {

            Date today = new Date(System.currentTimeMillis());
            Date nextWeek = new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000);

            boolean ok = service.scheduleMaintenanceVisit(
                    "TN2004",
                    nextWeek,
                    "MORNING",
                    "STF201",
                    "REGULAR_CLEANING",
                    today
            );

            System.out.println(ok ? "VISIT SCHEDULED" : "VISIT SCHEDULING FAILED");

        } catch (VisitConflictException e) {
            System.out.println("Visit Conflict: " + e.toString());
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.toString());
        } catch (Exception e) {
            System.out.println("System Error: " + e.getMessage());
        }
    }
}
