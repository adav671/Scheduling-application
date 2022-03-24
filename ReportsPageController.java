/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import BDAccess.DBAppointments;
import BDAccess.DBCustomer;
import BDAccess.DBUser;
import Model.Appointment;
import static Model.Appointment.types;
import Model.Customer;
import Model.User;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ajd34
 */
public class ReportsPageController implements Initializable {

    @FXML
    private Button mainReports;
    @FXML
    private Button exitReports;
    @FXML
    private Button apptRep;
    @FXML
    private Button custRecRep;
    @FXML
    private ComboBox comboR;
    @FXML
    private TableView<Appointment> reportsTable;
    @FXML
    private TableColumn timCol;
    @FXML
    private TableColumn typCol;
    @FXML
    private Label numTypes;
    @FXML
    private Label numCust;
    @FXML
    private TableView<Customer> custTable;
    @FXML
    private TableColumn nameCol;
    @FXML
    private TableColumn custCol;

    
    public static ObservableList<String>Months = FXCollections.observableArrayList("1: Jan", "2: Feb", "3: March", "4: April", "5: May", "6: June", "7: July", "8: Aug", "9: Sept", "10: Oct", "11: Nov", "12: Dec");
    @FXML
    private ComboBox comboType;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //load users 
        comboR.setItems(DBUser.getAllUsers());
        comboR.setVisibleRowCount(2);
        comboR.setPromptText("You must choose a Consultant");
        
        
        
        //table
        typCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        timCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        custCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        

        //customer count from the database 
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        ObservableList<Customer> numberCust = DBCustomer.getAllCustomers();
        custTable.setItems(numberCust);
        numCust.setText(Integer.toString(numberCust.size()) + " customers in the database.");
        
        //get type of appointments by month 
      //  ObservableList<String> numberTypes = Appointment.types(); 
        comboType.setItems(Months);
        comboType.setPromptText("Choose a Month");
      //  numTypes.setText(Integer.toString(numberTypes.size()) + " types of appointments this month");

    }


   
    

    @FXML
    private void clickedMainRep(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) mainReports.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/MainMenu.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private boolean clickedExitRep(ActionEvent event) {
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
    private void clickedApptRep(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) apptRep.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/Appointments.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void clickedCustRecRep(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) custRecRep.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/CustomerRecords.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void conCombo(ActionEvent event) {
       
        User user = (User) comboR.getValue(); 
        //filtered List using llambda expression to display appointment for each consultant using ID 
        ObservableList<Appointment> filReport = DBAppointments.getAllAppointments().filtered(row -> {
            return (user.getUserId() == row.getUserId()); 
        });
        reportsTable.setItems(filReport); 
    }

    @FXML
    private void typCombo(ActionEvent event) {
        int Month = comboType.getSelectionModel().getSelectedIndex()+1; 
        
       
        numTypes.setText(Integer.toString(DBAppointments.getAllTypes(Month)) + " type(s) this month.");
        
    }

}
