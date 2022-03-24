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
import Model.City;
import Model.Customer;
import Model.User;
import static ViewController.AppointmentsController.getSelectedAppt;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ajd34
 */
public class UpdateAppointmentController implements Initializable {

    @FXML
    private Button exitUpdate;
    @FXML
    private Button reportsUpdate;
    @FXML
    private Button recordsUpdate;
    @FXML
    private Button menuUpdate;
    @FXML
    private Button backUpdate;
    @FXML
    private Button updateAppt;
    @FXML
    private ComboBox comboTime;
    @FXML
    private ComboBox<String> comboType;
    @FXML
    private AnchorPane con;
    @FXML
    private ComboBox<User> conBox;
    @FXML
    private ComboBox<Customer> custBox;

    private int appointmentId;
    private int customerId;
    private int userId;
    private String type;
    private LocalDateTime start;

    @FXML
    private DatePicker datePicker;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Appointment selectedAppt = getSelectedAppt();
        //userId
        conBox.setItems(DBUser.getAllUsers());
        conBox.setVisibleRowCount(3);
        for (User U : conBox.getItems()) {
            if (selectedAppt.getUserId() == U.getUserId()) {
                conBox.setValue(U);
                break;
            }

        }

        userId = selectedAppt.getUserId();

        //customer
        custBox.setItems(DBCustomer.getAllCustomers());
        custBox.setVisibleRowCount(3);
        for (Customer C : custBox.getItems()) {
            if (selectedAppt.getCustomerId() == C.getCustomerId()) {
                custBox.setValue(C);
                break;
            }

        }

        customerId = selectedAppt.getCustomerId();

        //startTime
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 0);

        while (start.isBefore(end.plusSeconds(1))) {
            comboTime.getItems().add(start);
            start = start.plusMinutes(60);
        }
        comboTime.setVisibleRowCount(4);
        comboTime.getSelectionModel().select(LocalTime.of(9, 0));

        LocalDateTime startX = selectedAppt.getStart();
        datePicker.setValue(startX.toLocalDate());
        comboTime.setValue(startX.toLocalTime());
        //type of appt 
        comboType.setItems(Appointment.types);
        type = selectedAppt.getType();
        comboType.setValue(type);

        appointmentId = selectedAppt.getAppointmentId();

    }

    @FXML
    private boolean onExitUpdate(ActionEvent event) {
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
    private void onReportsUpdate(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) reportsUpdate.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/ReportsPage.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onRecordsUpdate(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) recordsUpdate.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/CustomerRecords.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onMenuUpdate(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) menuUpdate.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/MainMenu.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onBackUpdate(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) backUpdate.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/Appointments.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private boolean onUpdateAppt(ActionEvent event) throws IOException {

        User user = conBox.getValue();
        LocalTime startTime = (LocalTime) comboTime.getValue();
        LocalDate date = datePicker.getValue();
        LocalDateTime start = LocalDateTime.of(date, startTime);
        String type = comboType.getValue();
        Customer customer = custBox.getValue();

        //overlap check here 
        if (DBAppointments.overLap(user.getUserId(), appointmentId, start)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Overlap Error");
            alert.setHeaderText("There is an overlap error with this appointment!");
            alert.setContentText("Change times");
            Optional<ButtonType> result = alert.showAndWait();
           
            return false;
        } else {
            DBAppointments.modAppt(appointmentId, customer.getCustomerId(), user.getUserId(), type, start);

            Stage stage;
            Parent root;
            stage = (Stage) updateAppt.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/Appointments.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        return false;
    }
}
