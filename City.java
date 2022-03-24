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
public class City {
    private int cityId; 
    private String city; 

    
    public City (int cityId, String city) {
        this.cityId = cityId; 
        this.city = city; 
       
    }

 
    
    public int getCityId() {
        return this.cityId; 
    }
    
    @Override
    public String toString() {
        return (Integer.toString(cityId) + " " + city); 
    }
    
}
