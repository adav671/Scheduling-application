/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ajd34
 */
public class MainMenuController implements Initializable {

    @FXML
    private Button customerRecords;
    @FXML
    private Button logOff;
    @FXML
    private Button appointmentPage;
    @FXML
    private Button reportsPage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clickCustomer(ActionEvent event) throws IOException {
        Stage stage; 
        Parent root; 
        stage=(Stage)customerRecords.getScene().getWindow(); 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/CustomerRecords.fxml")); 
        root= loader.load(); 
        Scene scene = new Scene(root); 
        stage.setScene(scene); 
        stage.show(); 
    }
//log off program
    @FXML
    private boolean clickLogOff(ActionEvent event) {
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
    private void clickAppt(ActionEvent event) throws IOException {
        Stage stage; 
        Parent root; 
        stage=(Stage)appointmentPage.getScene().getWindow(); 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/Appointments.fxml")); 
        root= loader.load(); 
        Scene scene = new Scene(root); 
        stage.setScene(scene); 
        stage.show(); 
    }

    @FXML
    private void clickReports(ActionEvent event)  throws IOException {
        Stage stage; 
        Parent root; 
        stage=(Stage)reportsPage.getScene().getWindow(); 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/ReportsPage.fxml")); 
        root= loader.load(); 
        Scene scene = new Scene(root); 
        stage.setScene(scene); 
        stage.show(); }
    
    
}
