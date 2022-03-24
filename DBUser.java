/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BDAccess;

import Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ajd34
 */
public class DBUser {

    //create getter for new users 
    private static User nowUser;

    public static User getNowUser() {
        return nowUser;
    }

    // creating username and passwords 
    public static Boolean connectUser(String userName, String password) throws SQLException {
        try {
            String sqlti = "SELECT * FROM user WHERE userName = '" + userName + "' AND password ='" + password + "'";
            PreparedStatement pw = DBConnection.startConn().prepareStatement(sqlti);
            //execute query 
            ResultSet rs = pw.executeQuery();
            if (rs.next()) {
                nowUser = new User();
                nowUser.setUserName(rs.getString("userName"));
                nowUser.setUserId(rs.getInt("userId"));
                Log.log(userName);
                return true;
            }

        } catch (SQLException e) {
            System.out.println("exception: " + e.getMessage());
            
        }
        return false;

    }

    public static ObservableList<User> getAllUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();

        try {
            String sqlt = "SELECT userId, userName, password FROM user";
            //prepared statement  
            PreparedStatement ps = DBConnection.startConn().prepareStatement(sqlt);
            //execture query/results
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("userId");
                String userName = rs.getString("userName");
                String password = rs.getString("password");

                User c = new User(userId, userName, password);
                userList.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); //always print this 
        }
        return userList;
    }
}
