/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BDAccess;

import Model.Appointment;
import static ViewController.ReportsPageController.Months;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ajd34
 */
public class DBAppointments {

    //get all customers for database 
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> apptList = FXCollections.observableArrayList();

        try {
            String sqlti = "SELECT appointmentId, appointment.customerId, customerName, appointment.userId, userName, type, start FROM appointment, user, customer"
                    + " WHERE appointment.customerId= customer.customerId AND appointment.userId = user.userId";
            //prepared statement  
            PreparedStatement ps = DBConnection.startConn().prepareStatement(sqlti);
            //execture query/results
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("appointmentId");
                int customerId = rs.getInt("customerId");
                String customerName = rs.getString("customerName");
                int userId = rs.getInt("userId");
                String userName = rs.getString("userName");
                String type = rs.getString("type");

                Timestamp ts = rs.getTimestamp("start");
                ZoneId newZID = ZoneId.systemDefault();
                ZonedDateTime newStart = ts.toLocalDateTime().atZone(ZoneId.of("UTC"));
                ZonedDateTime newLocSt = newStart.withZoneSameInstant(newZID);
                System.out.println("From database in UTC: " + newStart);
                System.out.println("From database in local time: " + newLocSt);

                LocalDateTime start = newLocSt.toLocalDateTime();

                Appointment c = new Appointment(appointmentId, customerId, customerName, userId, userName, type, start);
                apptList.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); //always print this 
        }
        return apptList;
    }

    //create a new Appt first 
    public static void createAppt(int customerId, int userId, String type, LocalDateTime start) {
        try {

            //customer table sql statement for customer info from address info 
            //customerId, addressId, custaddressId
            String sqlt = "INSERT INTO appointment VALUES(NULL,?,?, '', '', '', '', ?, '', ?, NOW(), NOW(), '', NOW(),  '' )";
            //prepared statement 
            PreparedStatement pst = DBConnection.startConn().prepareStatement(sqlt);
            //add info 
            pst.setInt(1, customerId);
            pst.setInt(2, userId);
            pst.setString(3, type);

            ZoneId zid = ZoneId.systemDefault();
            ZonedDateTime zdtStart = start.atZone(zid);
            System.out.println("Local Time: " + zdtStart);
            ZonedDateTime utcStart = zdtStart.withZoneSameInstant(ZoneId.of("UTC"));
            System.out.println("Zoned time: " + utcStart);
            start = utcStart.toLocalDateTime();
            Timestamp ts = Timestamp.valueOf(start);
            pst.setTimestamp(4, ts);

            pst.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void modAppt(int appointmentId, int customerId, int userId, String type, LocalDateTime start) {

        try {

            //sql statement 
            String sqmc = "UPDATE appointment set customerId = ?, userId = ?, type = ?, start = ? WHERE appointmentId = ?";
            //prepared statement to modify 
            PreparedStatement mct = DBConnection.startConn().prepareStatement(sqmc);
            //questions 
            mct.setInt(1, customerId);
            mct.setInt(2, userId);
            mct.setString(3, type);

            ZoneId zid = ZoneId.systemDefault();
            ZonedDateTime zdtSt = start.atZone(zid);
            System.out.println("Local Time: " + zdtSt);
            ZonedDateTime utcStart = zdtSt.withZoneSameInstant(ZoneId.of("UTC"));
            System.out.println("Zoned time: " + utcStart);
            start = utcStart.toLocalDateTime();
            Timestamp ts = Timestamp.valueOf(start);
            mct.setTimestamp(4, ts);

            mct.setInt(5, appointmentId);

            mct.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void deleteAppt(int appointmentId) {

        try {

            //sql statement 
            String sqmce = "DELETE from appointment WHERE appointmentId = ?";
            //prepared statement to modify 
            PreparedStatement mcte = DBConnection.startConn().prepareStatement(sqmce);
            //questions 
            mcte.setInt(1, appointmentId);

            mcte.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }

    public static int getAllTypes(int Month) {

        try {

            String typ = "SELECT count(distinct type) as total FROM appointment WHERE month(start) = ?";

            PreparedStatement pt = DBConnection.startConn().prepareStatement(typ);

            pt.setInt(1, Month);

            ResultSet rs = pt.executeQuery();

            while (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;

    }
    //make sure appointments do not overlap 

    public static boolean overLap(int userId, int appointmentId, LocalDateTime start) {
        try {
            String lap = "SELECT appointmentId FROM appointment WHERE start = ? AND appointmentId <> ? AND userId = ?";

            PreparedStatement ol = DBConnection.startConn().prepareStatement(lap);
            
            ol.setInt(3, userId);
            ol.setInt(2, appointmentId);

            //timezone conversion 
            ZoneId zid = ZoneId.systemDefault();
            ZonedDateTime zdtSt = start.atZone(zid);
            System.out.println("Local Time: " + zdtSt);
            ZonedDateTime utcStart = zdtSt.withZoneSameInstant(ZoneId.of("UTC"));
            System.out.println("Zoned time: " + utcStart);
            start = utcStart.toLocalDateTime();
            Timestamp ts = Timestamp.valueOf(start);
            ol.setTimestamp(1, ts);
            

            ResultSet rs = ol.executeQuery();

            return rs.next(); 

        } catch (SQLException ex) {
            ex.printStackTrace();
             
        }
        return false;
       
        

    }
}
