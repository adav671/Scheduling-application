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
public class Customer {
    private int customerId; 
    private String customerName; 
    //add in from city, country and address
    //name, addressID
    private String address;
    private String phone;
    private int cityId;
    private int addressId; 
    


    public Customer(int customerId, String customerName, int addressId, String address, int cityId, String phone) {
        this.customerId = customerId; 
        this.customerName = customerName; 
        this.address = address; 
        this.addressId = addressId;
        this.phone = phone; 
        this.cityId = cityId; 
    }

    
    //customerId 
    public int getCustomerId (){
        return customerId; 
    }
    private void setCustomerId(int customerId) {
        this.customerId = customerId; 
    }
    //customerName 
    public String getCustomerName() {
        return customerName; 
    }
    private void setCustomerName(String customerName) {
        this.customerName = customerName; 
    }
    //phone
    public String getPhone(){
        return phone; 
    }
    private void setPhone(String phone) {
        this.phone = phone; 
    }
    //address
     public String getAddress(){
        return address; 
    }
    private void setAddress(String address) {
        this.address = address; 
    }
    //addressID
     public int getAddressId(){
        return addressId; 
    }
    private void setAddressId(int addressId) {
        this.addressId = addressId;
    }
     //city 
    public int CityId() {
        return cityId; 
    }
    private void setCityId(int cityId) {
        this.cityId = cityId;
    }
    
   @Override
    public String toString() {
        return (Integer.toString(customerId) + " " + customerName); 
    }
    
    public static String trueCust(String customerName, String address, int cityId, String phone, String exceptionMessage) {
           if(customerName.length()== 0) {
            exceptionMessage = exceptionMessage + ("You forgot to add the customers name!"); 
    }
              if(cityId == 0) {
            exceptionMessage = exceptionMessage + ("You forgot to add the customers cityId!"); 
    }
                 if(address.length()== 0) {
            exceptionMessage = exceptionMessage + ("You forgot to add the customers address!"); 
    }
                    if(phone.length()== 0) {
            exceptionMessage = exceptionMessage + ("You forgot to add the customers phone!"); 
    }
       return exceptionMessage;
    }
  
}
