/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BDAccess;

import Model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ajd34
 */
public class DBCustomer {
    //get all customers for database 
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> custList = FXCollections.observableArrayList(); 
        
        
       try {
           String sql = "SELECT customerId, customerName, customer.addressId, address, address.cityId, phone FROM address, city, customer"
                   + " WHERE customer.addressId = address.addressId AND address.cityId = city.cityId";
           //prepared statement  
           PreparedStatement ps = DBConnection.startConn().prepareStatement(sql);
           //execture query/results
           ResultSet rs = ps.executeQuery(); 
           
           while(rs.next()){
               int customerId = rs.getInt("customerId"); 
               String customerName = rs.getString("customerName"); 
               int addressId = rs.getInt("addressId");
               String address = rs.getString("address"); 
               int cityId = rs.getInt("cityId");
               String phone = rs.getString("phone"); 
               
               
               Customer c = new Customer(customerId, customerName, addressId, address, cityId, phone); 
               custList.add(c); 
           }
       }
        catch(SQLException ex){
            ex.printStackTrace(); //always print this 
        }
        return custList; 
    }
    //create a new Customer first in address table 
    //add all id from address table (in order)
    public static void createCust(String customerName, String address, int cityId, String phone){
       try {
           //sql statement 
           String sqlnc = "INSERT INTO address VALUES(NULL,?, '', ?, '', ?, NOW(), '', NOW(),  '') "; 
           
           PreparedStatement ptci = DBConnection.startConn().prepareStatement(sqlnc, Statement.RETURN_GENERATED_KEYS);
           ptci.setString(1, address); 
           ptci.setInt(2, cityId); 
           ptci.setString(3, phone);
           
            ptci.execute(); 
            
            ResultSet rs = ptci.getGeneratedKeys(); 
            rs.next(); 
            int addressId = rs.getInt(1); 
            
            
             //customer table sql statement for customer info from address info 
        //customerId, addressId, custaddressId
       String sqlt = "INSERT INTO customer VALUES(NULL,?,?, 1, NOW(), '', NOW(),  '' )";
        //prepared statement 
        PreparedStatement pst = DBConnection.startConn().prepareStatement(sqlt); 
        //add info 
        pst.setString(1, customerName); 
        pst.setInt(2, addressId); 
        
        pst.execute(); 
            
       }
       catch(SQLException ex) {
           ex.printStackTrace();
       }
          
           
    }
    
     public static void modCust(int customerId, String customerName, int addressId, String address, int cityId, String phone) {
         
         try {
         
         //sql statement 
         String sqmc = "UPDATE customer set customerName = ? WHERE customerId = ?"; 
         //prepared statement to modify 
         PreparedStatement mct = DBConnection.startConn().prepareStatement(sqmc); 
         //questions 
         mct.setString(1, customerName); 
         mct.setInt(2, customerId);
         
         mct.execute(); 
         
         
         //sql statement 
         String sqma = "UPDATE address set  address= ?, cityId = ?, phone = ? WHERE addressId = ?"; 
         //prepared statement to modify 
         PreparedStatement mat = DBConnection.startConn().prepareStatement(sqma); 
         //questions 
         mat.setString(1, address); 
         mat.setInt(2, cityId);
         mat.setString(3, phone); 
         mat.setInt(4, addressId);
         
        mat.execute(); 
         
     }
         catch(SQLException ex) {
             ex.printStackTrace();
         }
   
}
     
     public static void deleteCust(int customerId, int addressId) {
         
         try {
             
         String sqmce = "DELETE from appointment WHERE customerId = ?"; 
         //prepared statement to modify 
         PreparedStatement mcto = DBConnection.startConn().prepareStatement(sqmce); 
         //questions 
         mcto.setInt(1, customerId);
         
         mcto.execute(); 
         
         //sql statement 
         String sqmc = "DELETE from customer WHERE customerId = ?"; 
         //prepared statement to modify 
         PreparedStatement mct = DBConnection.startConn().prepareStatement(sqmc); 
         //questions 
         mct.setInt(1, customerId);
         
         mct.execute(); 
         
         
         //sql statement 
         String sqma = "DELETE from address WHERE addressId = ?"; 
         //prepared statement to modify 
         PreparedStatement mat = DBConnection.startConn().prepareStatement(sqma); 
         //questions 
         mat.setInt(1, addressId);
         
        mat.execute(); 
         
     }
         catch(SQLException ex) {
             ex.printStackTrace();
         
     }
     
}
//figure this out 
  //  public static void deleteCust(Customer removeCust) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //}
}
