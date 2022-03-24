/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import BDAccess.DBAppointments;
import static BDAccess.DBAppointments.getAllAppointments;
import BDAccess.DBCustomer;
import Model.Appointment;
import Model.Customer;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ajd34
 */
public class AppointmentsController implements Initializable {

    @FXML
    private Button addApptSch;
    @FXML
    private Button updateApptSch;
    @FXML
    private Button deleteAppt;
    @FXML
    private Button mainMenu;
    @FXML
    private Button custRecords;
    @FXML
    private Button exitButton;
    @FXML
    private Button reportsSch;
    @FXML
    private RadioButton allCaldenar;
    @FXML
    private ToggleGroup calendarView;
    @FXML
    private RadioButton monthView;
    @FXML
    private RadioButton weekView;
    @FXML
    private TableColumn custCol;
    @FXML
    private TableColumn timeCol;
    @FXML
    private TableColumn conCol;
    @FXML
    private TableView apptTable;

    public static int DOALL = 0;
    public static int DOMON = 1;
    public static int DOWK = 2;

    private int isMonCalendar = DOALL;

    private static Appointment selectedAppt;
    private static int selectedApptInd;

    public static Appointment getSelectedAppt() {
        return selectedAppt;
    }

    public static int getSelectedApptInd() {
        return selectedApptInd;
    }

    private ObservableList<Appointment> apptInven = FXCollections.observableArrayList();
    @FXML
    private TableColumn typeCol;

    //date and timezones are pulled from apptDB
    private final DateTimeFormatter dtF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        custCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        conCol.setCellValueFactory(new PropertyValueFactory<>("userName"));

        genApptTable();

        allCaldenar.setSelected(true);

    }

    private void genApptTable() {

        apptInven.setAll(DBAppointments.getAllAppointments());
        apptTable.setItems(apptInven);
        apptTable.refresh();

        //filter appts here 
        if (isMonCalendar == DOMON) {
            genMonthlyTable();
        } else if (isMonCalendar == DOWK) {
            genWeekTable();
        }
    }

    //monthly table 
    private void genMonthlyTable() {

        LocalDate now = LocalDate.now();

        //filtered List using llambda expression to display appointment in a month including the week 
        ObservableList<Appointment> filMonth = apptInven.filtered(row -> {
            LocalDate monDT = row.getStart().toLocalDate();
            return now.getMonth() == monDT.getMonth() && now.getYear() == monDT.getYear();
        });

        apptTable.setItems(filMonth);

    }

    //weekly table 
    private void genWeekTable() {
        LocalDateTime now = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));

        while (now.getDayOfWeek() != DayOfWeek.MONDAY) {
            now = now.minusDays(1);
        }
        LocalDateTime monday = now;
        LocalDateTime endWeek = now.plusDays(7);

        //lambda to effiecently display weekly appointments only!
        ObservableList<Appointment> filWeek = apptInven.filtered(row -> {
            LocalDateTime weekDT = row.getStart();
            return weekDT.isAfter(monday) && weekDT.isBefore(endWeek);
        });

        apptTable.setItems(filWeek);
    }

    @FXML
    private void selectAdd(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) addApptSch.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/AddAppointment.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void selectUpdate(ActionEvent event) throws IOException {

        selectedAppt = (Appointment) apptTable.getSelectionModel().getSelectedItem();

        if (selectedAppt == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Updating Error");
            alert.setHeaderText("Appointment not able to be updated");
            alert.setContentText("No Appointment was Selected!");
            alert.showAndWait();
            return;
        }

        selectedApptInd = getAllAppointments().indexOf(selectedAppt);

        try {

            Stage stage;
            Parent root;
            stage = (Stage) updateApptSch.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/UpdateAppointment.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectDeleteAppt(ActionEvent event) {

        if (apptTable.getSelectionModel().getSelectedItem() != null) {
            selectedAppt = (Appointment) apptTable.getSelectionModel().getSelectedItem();
        } else {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Appointment?");
        alert.setContentText("Are you sure you want to delete this appointment?");
        alert.showAndWait().ifPresent((response -> {
            if (response == ButtonType.OK) {
                DBAppointments.deleteAppt(selectedAppt.getAppointmentId());
                apptTable.setItems(DBAppointments.getAllAppointments());
            }
        }));

    }

    @FXML
    private void selectMain(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) mainMenu.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/MainMenu.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void selectCustRecords(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) custRecords.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/CustomerRecords.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private boolean selectExitSch(ActionEvent event) {
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
    private void selectReports(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) reportsSch.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/ReportsPage.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void selectAll(ActionEvent event) {
        isMonCalendar = DOALL;
        genApptTable();

    }

    @FXML
    private void selectMonth(ActionEvent event) {
        isMonCalendar = DOMON;
        genApptTable();

    }

    @FXML
    private void selectWeek(ActionEvent event) {
        isMonCalendar = DOWK;
        genApptTable();
    }

}
