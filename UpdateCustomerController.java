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
import static ViewController.CustomerRecordsController.getSelectedCust;
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
public class UpdateCustomerController implements Initializable {

    @FXML
    private Button updateCustRec;
    @FXML
    private Button exitUpdateCust;
    @FXML
    private Button updateApptPage;
    @FXML
    private Button backUpdateCust;
    @FXML
    private TextField updateName;
    @FXML
    private TextField updateAddress;
    @FXML
    private TextField updatePhone;

    @FXML
    private ComboBox <City> cityBox;
    
    private int customerId; 
    private int addressId; 
    
    private String exceptionMessage = new String(); 
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cityBox.setItems(DBCity.getAllCities());
        cityBox.setVisibleRowCount(2);
        cityBox.getSelectionModel().selectFirst();
        
        //grabbing info from table on customer records page 
        Customer selectedCust = getSelectedCust(); 
        customerId = selectedCust.getCustomerId(); 
        addressId = selectedCust.getAddressId();
        
        
        int cityId = selectedCust.CityId(); 
        for (City C: cityBox.getItems()) {
            if(cityId == C.getCityId()) {
                cityBox.setValue(C);
                break; 
            }
            
        }
        updateName.setText(selectedCust.getCustomerName()); 
        updateAddress.setText(selectedCust.getAddress());
        updatePhone.setText(selectedCust.getPhone());
        
        
        
    }    

    @FXML
    private void clickedUpdateCust(ActionEvent event)  throws IOException {
        
        City city = cityBox.getValue(); 
        String customerName = updateName.getText(); 
        String address = updateAddress.getText(); 
        String phone = updatePhone.getText();  
        
        try {
            
        
        exceptionMessage = Customer.trueCust(customerName, address, city.getCityId(), phone, exceptionMessage); 
         
         if (exceptionMessage.length() >0 ) {
              Alert alert = new Alert(Alert.AlertType.INFORMATION); 
              alert.setTitle("ERROR"); 
              alert.setHeaderText("Error updating Customer"); 
              alert.setContentText(exceptionMessage); 
              alert.showAndWait(); 
              exceptionMessage = ""; 
              
              return; 
          }
         else {
            DBCustomer.modCust(customerId, customerName, addressId, address, city.getCityId(), phone);
            
        Stage stage; 
        Parent root; 
        stage=(Stage)updateCustRec.getScene().getWindow(); 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/CustomerRecords.fxml")); 
        root= loader.load(); 
        Scene scene = new Scene(root); 
        stage.setScene(scene); 
        stage.show(); 
            
             
         }
        }
        catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION); 
              alert.setTitle("Error updating Customer"); 
              alert.setHeaderText("Error"); 
              alert.setContentText("Blank fields OR invalid values detected"); 
              alert.showAndWait(); 
        }
    }

    @FXML
    private boolean clickedExitUpdateCust(ActionEvent event) {
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
    private void clickedApptUpdate(ActionEvent event) throws IOException {
        Stage stage; 
        Parent root; 
        stage=(Stage)updateApptPage.getScene().getWindow(); 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/Appointments.fxml")); 
        root= loader.load(); 
        Scene scene = new Scene(root); 
        stage.setScene(scene); 
        stage.show(); 
    }

    @FXML
    private void clickedBackUpdate(ActionEvent event)  throws IOException {
        Stage stage; 
        Parent root; 
        stage=(Stage)backUpdateCust.getScene().getWindow(); 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/CustomerRecords.fxml")); 
        root= loader.load(); 
        Scene scene = new Scene(root); 
        stage.setScene(scene); 
        stage.show(); 
    }
    
}
