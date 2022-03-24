/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ajd34
 */
public class Appointment {

    public static int getAppointmentId;
    private int appointmentId; 
    private int customerId; 
    private String customerName; 
    public int userId;
    private String userName; 
    private String type;
    private LocalDateTime start; 

    
    
    public Appointment(int appointmentId, int customerId, String customerName, int userId, String userName, String type, LocalDateTime start) {
   
        this.appointmentId = appointmentId; 
        this.customerId = customerId; 
        this.customerName = customerName; 
        this.userId = userId; 
        this.userName = userName; 
        this.type = type; 
        this.start = start; 
      
        
    }
    
    public static ObservableList<String> types = FXCollections.observableArrayList("Introduction Meeting", "Urgent Meeting", "Lunch Meeting"); 

 
    
    //appointmentId 
    public int getAppointmentId(){
        return this.appointmentId; 
    }
    private void setAppointmentId(int appointmentId){
        this.appointmentId = appointmentId; 
    }
    //customerId 
    public int getCustomerId(){
        return this.customerId; 
    }
    private void setCustomerId(int customerId){
        this.customerId = customerId; 
    }
    //userId
    public int getUserId(){
        return this.userId; 
    }
    private void setUserId(int userId){
        this.userId= userId; 
    }
    //title
    public String getCustomerName(){
        return this.customerName; 
    }
   
    //location 
    public String getUserName(){
        return this.userName; 
    }
    //type
    public String getType(){
        return this.type;
    }
    public void setType(String type){
        this.type=type; 
    }
   
    //startTime
    public LocalDateTime getStart(){
        return this.start; 
    }
    
 
}
