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
import Model.Customer;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ajd34
 */
public class AddAppointmentController implements Initializable {

    @FXML
    private Button exitAddAppt;
    @FXML
    private Button reportsAddAppt;
    @FXML
    private Button recordsAddAppt;
    @FXML
    private Button menuAddAppt;
    @FXML
    private Button backAddAppt;
    @FXML
    private Button saveAddAppt;
    @FXML
    private ComboBox<User> addConsultant;
    @FXML
    private ComboBox<LocalTime> addTime;
    @FXML
    private ComboBox<String> apptType;
    @FXML
    private ComboBox<Customer> addCust;

    private String exceptionMess = new String();
    @FXML
    private DatePicker datePicker;
    private int appointmentId; 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //userId
        addConsultant.setItems(DBUser.getAllUsers());
        addConsultant.setVisibleRowCount(3);
        addConsultant.getSelectionModel().selectFirst();
        addConsultant.setPromptText("You must choose a Consultant");

        //customer
        addCust.setItems(DBCustomer.getAllCustomers());
        addCust.setVisibleRowCount(3);
        addCust.getSelectionModel().selectFirst();
        addCust.setPromptText("You must choose a Customer");

        //startTime
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 0);

        while (start.isBefore(end.plusSeconds(1))) {
            addTime.getItems().add(start);
            start = start.plusMinutes(60);
        }
        addTime.setVisibleRowCount(4);
        addTime.getSelectionModel().select(LocalTime.of(9, 0));
        datePicker.setValue(LocalDate.now());
        //type of appts
        apptType.setItems(Appointment.types);
        apptType.getSelectionModel().selectFirst();
        
      

    }

    @FXML
    private boolean exitAdd(ActionEvent event) {
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
    private void reportsAdd(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) reportsAddAppt.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/ReportsPage.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void recordsAdd(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) recordsAddAppt.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/CustomerRecords.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void menuAdd(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) menuAddAppt.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/MainMenu.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onBackAdd(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) backAddAppt.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/Appointments.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private boolean saveAdd(ActionEvent event) throws IOException {

        User user = addConsultant.getValue();
        LocalTime startTime = addTime.getValue();
        LocalDate date = datePicker.getValue();
        LocalDateTime start = LocalDateTime.of(date, startTime);
        String type = apptType.getValue();
        Customer customer = addCust.getValue();
        

        //overlap check here 
        if (DBAppointments.overLap(user.getUserId(), 0, start)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Overlap Error");
            alert.setHeaderText("There is an overlap error with this appointment!");
            alert.setContentText("Change times");
            Optional<ButtonType> result = alert.showAndWait();
            
            return false;
        } else {
            DBAppointments.createAppt(customer.getCustomerId(), user.getUserId(), type, start);

            Stage stage;
            Parent root;
            stage = (Stage) saveAddAppt.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/Appointments.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        return false;

    }
}
