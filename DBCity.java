/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BDAccess;

import Model.City;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ajd34
 */
public class DBCity {
       public static ObservableList<City> getAllCities() {
        ObservableList<City> cityList = FXCollections.observableArrayList(); 
        
        
       try {
           String sqli = "SELECT cityId, city FROM city";
           //prepared statement  
           PreparedStatement ps = DBConnection.startConn().prepareStatement(sqli);
           //execture query/results
           ResultSet rs = ps.executeQuery(); 
           
           while(rs.next()){
               int cityId = rs.getInt("cityId");
               String city = rs.getString("city"); 
               
               
               City c = new City(cityId, city); 
               cityList.add(c); 
           }
       }
        catch(SQLException ex){
            ex.printStackTrace(); //always print this 
        }
        return cityList; 
    }
}
