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
public class Country {
    private int countryId =0; 
    private String country; 
    private Date createDate; 
    private String createdBy; 
    private Timestamp lastUpdate; 
    private String lastUpdateBy; 
    
    
 
    public Country (int countryId, String country, Date createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy) {
      this.countryId = countryId; 
      this.country = country; 
      this.createDate = createDate;  
      this.createdBy = createdBy; 
      this.lastUpdateBy = lastUpdateBy; 
      this.lastUpdate = lastUpdate;  
    }
    
    //countryId
    public int getCountryId() {
        return this.countryId; 
    }
    private void setCountryId (int countryId) {
        this.countryId = 0; 
    }
    //country 
    public String getCountry() {
        return this.country; 
    }
    private void setCountry(String country) {
        this.country = country; 
    }
    //createDate
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
