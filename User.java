/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author ajd34
 */
public class User {
    private int userId; 
    private String userName; 
    private String password; 
    
    public User(int userId, String userName, String password) {
        this.userId = userId; 
        this.userName = userName; 
        this.password = password; 
    }

    public User() {
    }
    
    //userid
    public int getUserId(){
        return this.userId; 
    }
    public void setUserId(int userId) {
        this.userId = userId; 
    }
    //userName
    public String getUserName(){
        return this.userName; 
    }
    public void setUserName(String userName){
        this.userName=userName;
    }
    //password
    public String getPassword(){
        return this.password; 
    }
    public void setPassword (String password) {
        this.password = password; 
    }
    
@Override
    public String toString() {
        return (Integer.toString(userId) + " " + userName); 
    }
}
