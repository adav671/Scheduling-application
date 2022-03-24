/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import BDAccess.DBCity;
import BDAccess.DBCustomer;
import Model.City;
import Model.Customer;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ajd34
 */
public class AddCustomerController implements Initializable {

    @FXML
    private Button saveAddCust;
    @FXML
    private Button exitAddCust;
    @FXML
    private Button apptPage;
    @FXML
    private Button backAddCust;
    @FXML
    private ComboBox <City> combo1;
    @FXML
    private TextField addName;
    @FXML
    private TextField addAddress;
    @FXML
    private TextField addPhone;
    
    private String exceptionMessage = new String(); 
    
   
    
    
  //name, address, phone
    
    public void custName (String name) {
        addName.setText(name);
    }
    public void custAddress (String address) {
        addAddress.setText(address); 
    }
    public void custPhone (String phone) {
        addPhone.setText(phone);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //city combo box
        combo1.setItems(DBCity.getAllCities());
        combo1.setVisibleRowCount(3);
        combo1.getSelectionModel().selectFirst();
        //name, address, phone
        
       
    }    

    @FXML
    private void clickedSave(ActionEvent event) throws IOException {
        
        City city = combo1.getValue(); 
        String customerName = addName.getText(); 
        String address = addAddress.getText(); 
        String phone = addPhone.getText();  
        
        try {
            
        
        exceptionMessage = Customer.trueCust(customerName, address, city.getCityId(), phone, exceptionMessage); 
         
         if (exceptionMessage.length() >0 ) {
              Alert alert = new Alert(Alert.AlertType.INFORMATION); 
              alert.setTitle("ERROR"); 
              alert.setHeaderText("Error adding Customer"); 
              alert.setContentText(exceptionMessage); 
              alert.showAndWait(); 
              exceptionMessage = ""; 
              
              return; 
          }
         else {
            DBCustomer.createCust(customerName, address, city.getCityId(), phone);
            
            
        Stage stage; 
        Parent root; 
        stage=(Stage)saveAddCust.getScene().getWindow(); 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/CustomerRecords.fxml")); 
        root= loader.load(); 
        Scene scene = new Scene(root); 
        stage.setScene(scene); 
        stage.show(); 
            
             
         }
        }
        catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION); 
              alert.setTitle("Error Adding Customer"); 
              alert.setHeaderText("Error"); 
              alert.setContentText("Blank fields OR invalid values detected"); 
              alert.showAndWait(); 
        }
    }

    @FXML
    private boolean clickedExit(ActionEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION); 
        alert.setTitle("Exit Program"); 
        alert.setHeaderText("Are you sure you want to EXIT?"); 
        alert.setContentText("Click to finalize"); 
        Optional<ButtonType> result = alert.showAndWait(); 
        if (result.get() == ButtonType.OK) {
           Platform.exit();  
        }
        return false;
    }

    @FXML
    private void clickedAppt(ActionEvent event) throws IOException {
        Stage stage; 
        Parent root; 
        stage=(Stage)apptPage.getScene().getWindow(); 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/Appointments.fxml")); 
        root= loader.load(); 
        Scene scene = new Scene(root); 
        stage.setScene(scene); 
        stage.show(); 
    }

    @FXML
    private void clickedBack(ActionEvent event) throws IOException {
        Stage stage; 
        Parent root; 
        stage=(Stage)backAddCust.getScene().getWindow(); 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/CustomerRecords.fxml")); 
        root= loader.load(); 
        Scene scene = new Scene(root); 
        stage.setScene(scene); 
        stage.show(); 
    }
    
}
