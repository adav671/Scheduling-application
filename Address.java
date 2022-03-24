/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author ajd34
 */
public class Address {
    private int addressID = 0; 
    private String address; 
    private String address2; 
    private int cityID = 0; 
    private String postalCode; 
    private String phone; 
    private Date createDate; 
    private String createdBy; 
    private Timestamp lastUpdate; 
    private String lastUpdateBy; 
    
    
    public Address (int addressID, String address, String address2, int cityID, String postalCode, String phone, Date createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy) {
        this.addressID = addressID; 
        this.address = address; 
        this.address2 = address2; 
        this.cityID = cityID; 
        this.postalCode = postalCode; 
        this.phone = phone; 
        this.createdBy = createdBy; 
        this.lastUpdateBy = lastUpdateBy; 
        this.lastUpdate = lastUpdate; 
    }
    
    //addressID 
    public int getAddressID(){
        return this.addressID; 
    }
    private void setAddressID(int addressID) {
        this.addressID = addressID; 
    } 
    //address
     public String getAddress(){
        return this.address; 
    }
    private void setAddress(String address) {
        this.address = address; 
    } 
    //address2
     public String getAddress2(){
        return this.address2; 
    }
    private void setAddress2(String address2) {
        this.address2 = address2; 
    }
    //cityID
    public int getCityID(){
        return this.cityID; 
    }
    private void setCityID(int cityID) {
        this.cityID = cityID; 
    } 
    //postal code 
     public String getPostalCode(){
        return this.postalCode; 
    }
    private void setPostalCode(String postalCode) {
        this.postalCode = postalCode; 
    } 
    //phone
    public String getPhone(){
        return this.phone; 
    }
    private void setPhone(String phone) {
        this.phone = phone; 
    }
    //createdate
    public Date getCreateDate(){
        return this.createDate; 
    }
    private void setCreateDate(Date createDate) {
        this.createDate = createDate; 
    }
    //createdBy
     public String getCreatedBy(){
        return this.createdBy; 
    }
    private void setCreatedby(String createdBy) {
        this.createdBy = createdBy; 
    }
    //lastUpdateby
    public String getLastUpdateBy() {
        return this.lastUpdateBy; 
    }
    private void setLastUpdateBy (String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy; 
    }
    //lastupdate
    public Timestamp getLastUpdate () {
        this.lastUpdate = Timestamp.valueOf(LocalDateTime.of(lastUpdate.toLocalDateTime().toLocalDate(), lastUpdate.toLocalDateTime().toLocalTime())); 
        return this.lastUpdate;
    } 
    private void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate; 
    }
    
}
    
    

