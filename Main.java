/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import BDAccess.DBConnection;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author ajd34
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception { 
        
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/ViewController/LoginPage.fxml"));
        Parent root = loader.load(); 
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
      
        
        DBConnection.startConn(); 
        launch(args);
        DBConnection.closeConn();
        
        
       // ZoneId.getAvailableZoneIds().stream().forEach(System.out::println); 
        //ZoneId.getAvailableZoneIds().stream().filter(c -> c.contains ("America")).forEach(System.out::println); 
        
       /* LocalDate parisDate = LocalDate.of(2020, 10, 3);
        LocalTime timeParis = LocalTime.of(8, 00); 
        ZoneId parisZoneId = ZoneId.of("Europe/Paris"); 
        ZonedDateTime parisZDT = ZonedDateTime.of(parisDate, timeParis, parisZoneId);
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID()); 
        
        Instant parisToGMTInstant = parisZDT.toInstant(); 
        ZonedDateTime parisToLocalZDT = parisZDT.withZoneSameInstant(localZoneId);
        ZonedDateTime gmtToLocalZDT = parisToGMTInstant.atZone(localZoneId); 
        
        //System.out.println("Local: " + ZonedDateTime.now());
        System.out.println("Paris: " + parisZDT);
        System.out.println("Paris->GMT: " + parisToGMTInstant);
        System.out.println("GMT->Local " + gmtToLocalZDT);
        System.out.println("GMT->LocalDate : "  + gmtToLocalZDT.toLocalDate());
        System.out.println("GMT->LocalTime : "  + gmtToLocalZDT.toLocalTime());
        
        String date = String.valueOf(gmtToLocalZDT.toLocalDate()); 
        String time = String.valueOf(gmtToLocalZDT.toLocalTime());
        String dateTime = date + " " + time ; 
        System.out.println(dateTime);
        */
     
    }
    
}
