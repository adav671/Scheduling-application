/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import BDAccess.DBAppointments;
import BDAccess.DBUser;
import Model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ajd34
 */
public class LoginPageController implements Initializable {

    @FXML
    private Button exitLogIn;
    @FXML
    private Button logIn;
    @FXML
    private PasswordField passwordLabel;
    @FXML
    private TextField userLabel;
    @FXML
    private Label passwordField;
    @FXML
    private Label usernameField;
    @FXML
    private Label signOn;

    private String alertTi;
    private String alertHe;
    private String alerttx;
    private String alTi;
    private String alHe;
    private String alTx;

    private DateTimeFormatter dTF = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    //properties and lang changes
    private final ResourceBundle bundle = ResourceBundle.getBundle("Properties/Lang");

    private void apptAlert() {
        
        //create the alert for appointment in 15 minutes 
        LocalDateTime startTi = LocalDateTime.now();
        LocalDateTime fifteenFrom = startTi.plusMinutes(15);
        
        //use filtered list to create popup for only that user and the upcoming appointments 
       ObservableList<Appointment> filNotice = DBAppointments.getAllAppointments().filtered(row -> {
           if(DBUser.getNowUser().getUserId() != row.getUserId()) {
               return false; 
           }
           
            LocalDateTime checkDT = row.getStart();
            return checkDT.isAfter(startTi.minusMinutes(1)) && checkDT.isBefore(fifteenFrom);
        });
        if (filNotice.isEmpty()) {
            System.out.println("No upcoming appointments.");
        } else {
            String type = filNotice.get(0).getType();
            String cust = filNotice.get(0).getCustomerName();
            LocalDateTime time = filNotice.get(0).getStart();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upcoming Appointment");
            alert.setHeaderText("You have an appointment soon!");
            alert.setContentText("Appointment with " + cust +" at "+ time +".");
            Optional<ButtonType> result = alert.showAndWait();
            
            } 

        }

        @Override
        public void initialize
        (URL url, ResourceBundle rb) {
      

        //if (Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("es")) {
        usernameField.setText(bundle.getString("username"));
            passwordField.setText(bundle.getString("password"));
            logIn.setText(bundle.getString("login"));
            exitLogIn.setText(bundle.getString("exit"));
            signOn.setText(bundle.getString("signOn"));

            //log off settings to change lang 
            alertTi = bundle.getString("alertTi");
            alertHe = bundle.getString("alertHe");
            alerttx = bundle.getString("alerttx");

            //wrong username/password lang change 
            alTi = bundle.getString("alTi");
            alHe = bundle.getString("alHe");
            alTx = bundle.getString("alTx");

        }

        @FXML
        private boolean clickExitLogIn
        (ActionEvent event
        
            ) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(alertTi);
            alert.setHeaderText(alertHe);
            alert.setContentText(alerttx);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Platform.exit();
            }
            return false;
        }

        @FXML
        private void clickLogIn
        (ActionEvent event) throws IOException, Exception {

            String usernameTxt = userLabel.getText();
            String passwordTxt = passwordLabel.getText();
            boolean validED = DBUser.connectUser(usernameTxt, passwordTxt);

            if (validED) {
                System.out.println("Welcome");
                Stage stage;
                Parent root;
                stage = (Stage) logIn.getScene().getWindow();
                stage.hide(); 
                 
                //check 15 min alert 
                apptAlert(); 
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewController/MainMenu.fxml"));
                root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                System.out.println("Wrong username/password");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(alTi);
                alert.setHeaderText(alHe);
                alert.setContentText(alTx);
                Optional<ButtonType> result = alert.showAndWait();
            }
        }
    }
