/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BDAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ajd34
 */
public class DBConnection {
    
    //JDBC URL parts
    private static final String protocol = "jdbc"; 
    private static final String vendorName = ":mysql:"; 
    private static final String ipAddress = "//3.227.166.251/U062wR"; 
    //jdbc url 
    private static final String jdbcURL = protocol + vendorName + ipAddress; 
    //username and password
    private static final String userName = "U062wR";
    private static final String PASS = "53688675820";
    //driver interface reference
    private static final String DRIVER = "com.mysql.jdbc.Driver"; 
    private static Connection conn = null; 
    
    
    //start connection to database
    public static Connection startConn() {
        if(conn!=null) {
            return conn; 
        }
        try{
            Class.forName(DRIVER); 
            //PASS = Details.getPassword(); 
            conn = (Connection)DriverManager.getConnection(jdbcURL, userName, PASS); 
            System.out.println("Connection Successful!");
    }
        catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
    }
        return conn;
    }
    //close the database
    public static void closeConn() {
        try {
            conn.close();
            System.out.println("Connection closed!"); 
        }
        catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
