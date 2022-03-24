/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import BDAccess.DBCustomer;
import static BDAccess.DBCustomer.getAllCustomers;
import Model.Customer;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ajd34
 */
public class CustomerRecordsController implements Initializable {

    @FXML
    private Button exitCustRec;
    @FXML
    private Button mainCustRec;
    @FXML
    private Button addCustRec;
    @FXML
    private Button updateCust;
    @FXML
    private Button deleteCustRec;
    @FXML
    private Button reportsCustRec;
    @FXML
    private Button appointmentsCustRec;
    @FXML
    private TableColumn nameCol;
    @FXML
    private TableColumn addressCol;
    @FXML
    private TableColumn phoneCol;
    @FXML
    private TableView custTable;
    
    private ObservableList<Customer> custInven = FXCollections.observableArrayList();
    
    private static Customer selectedCust; 
   private static int selectedCustInd; 
   
   public static Customer getSelectedCust() {
       return selectedCust; 
   }
   public static int getSelectedCustInd() {
       return selectedCustInd; 
   }
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //get customers into the table
        //set columns for the customers 
        nameCol.setCellValueFactory(new PropertyValueFactory <>("customerName"));    
        addressCol.setCellValueFactory(new PropertyValueFactory <>("address"));
        phoneCol.setCellValueFactory(new PropertyValueFactory <>("phone"));
        
        genCustTable(); 
    }
    
    private void genCustTable(){
        
        custInven.setAll(DBCustomer.getAllCustomers());
        
        custTable.setItems(custInven);
        custTable.refresh();
        
    }  
    
    
   
    @FXML
    private boolean clickExitCust(ActionEvent event) {
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
    private void clickMainMenu(ActionEvent event)  throws IOException {
        Stage stage; 
        Parent root; 
        stage=(Stage)mainCustRec.getScene().getWindow(); 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/MainMenu.fxml")); 
        root= loader.load(); 
        Scene scene = new Scene(root); 
        stage.setScene(scene); 
        stage.show(); 
    }

    @FXML
    private void clickCustAdd(ActionEvent event)  throws IOException {
        Stage stage; 
        Parent root; 
        stage=(Stage)addCustRec.getScene().getWindow(); 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/AddCustomer.fxml")); 
        root= loader.load(); 
        Scene scene = new Scene(root); 
        stage.setScene(scene); 
        stage.show(); 
    }

    @FXML
    private void clickUpdateCust(ActionEvent event)  throws IOException {
        
        selectedCust = (Customer) custTable.getSelectionModel().getSelectedItem();   
         if(selectedCust == null) {
                 Alert alert = new Alert(Alert.AlertType.ERROR); 
                 alert.setTitle("Modify Error"); 
                 alert.setHeaderText("Customer not able to be modified"); 
                 alert.setContentText("No Customer was Selected!"); 
                 alert.showAndWait();  
                 return; 
         }
            
            selectedCustInd = getAllCustomers().indexOf(selectedCust);

      try {   
        Stage stage; 
        Parent root; 
        stage=(Stage)updateCust.getScene().getWindow(); 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/UpdateCustomer.fxml")); 
        root= loader.load(); 
        Scene scene = new Scene(root); 
        stage.setScene(scene); 
        stage.show(); 
     
         }
      catch (IOException e) {
          e.printStackTrace();
      }
        
   
    }

    @FXML
    private void clickDeleteCust(ActionEvent event) {
            if(custTable.getSelectionModel().getSelectedItem() != null) {
                selectedCust = (Customer) custTable.getSelectionModel().getSelectedItem(); }
            else {
                return; }
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION); 
                 alert.setTitle("Delete"); 
                 alert.setHeaderText("Delete Customer?"); 
                 alert.setContentText("Are you sure you want to delete this customer?"); 
                 alert.showAndWait().ifPresent((response -> {
                     if(response== ButtonType.OK) {
                         DBCustomer.deleteCust(selectedCust.getCustomerId(), selectedCust.getAddressId());
                         custTable.setItems(DBCustomer.getAllCustomers());
                     }
                 }));  
                
  
            }

    @FXML
    private void clickReportsCust(ActionEvent event)  throws IOException {
        Stage stage; 
        Parent root; 
        stage=(Stage)reportsCustRec.getScene().getWindow(); 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/ReportsPage.fxml")); 
        root= loader.load(); 
        Scene scene = new Scene(root); 
        stage.setScene(scene); 
        stage.show(); 
    }

    @FXML
    private void clickApptCust(ActionEvent event)  throws IOException {
        Stage stage; 
        Parent root; 
        stage=(Stage)appointmentsCustRec.getScene().getWindow(); 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/Appointments.fxml")); 
        root= loader.load(); 
        Scene scene = new Scene(root); 
        stage.setScene(scene); 
        stage.show(); 
    }
    
}
