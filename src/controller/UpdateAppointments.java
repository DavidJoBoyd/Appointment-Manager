package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.JDBC;
import models.*;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;
/**Sets up the update appointments screen.*/
public class UpdateAppointments implements Initializable {


    public TextField custTxt;
    public TextField userTxt;
    public TextField appTxt;
    public TextArea descTxt;
    public TextField titleTxt;
    public TextField locTxt;
    public TextField typeTxt;

    public DatePicker startDate;
    public DatePicker endDate;

    public ComboBox endTime;
    public ComboBox startTime;
    public ComboBox<Contact> contactField;

    public Button saveButton;
    public Button cancelButton;
    Appointment passedApp;
    /**Sets up the update appointment screen.
     * @param url the url location
     * @param resourceBundle The resource location */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        passedApp = Appointments.getProductToModify();
        appTxt.setText(String.valueOf(passedApp.getAppointment_ID()));
        titleTxt.setText(passedApp.getTitle());
        descTxt.setText(passedApp.getDescription());

        try {
            ContactList.update();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        contactField.setItems(ContactList.getAllContacts());
        contactField.setValue(ContactList.contactById(passedApp.getContact_ID()));
        typeTxt.setText(passedApp.getType());
        locTxt.setText(passedApp.getLocation());

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        LocalDate beginDate = LocalDate.parse(passedApp.getStartDate(), dateFormatter);
        LocalDate finalDate = LocalDate.parse(passedApp.getEndDate(), dateFormatter);

        startDate.setValue(beginDate);
        endDate.setValue(finalDate);

        LocalTime start = LocalTime.of(0,0,0);
        LocalTime end = LocalTime.of(23,0,0);

       LocalTime startTest = LocalTime.parse(passedApp.getStartTime());
        LocalTime endTest = LocalTime.parse(passedApp.getEndTime());

        while(start.isBefore(end.plusSeconds(1))){
            endTime.getItems().add(start);
            startTime.getItems().add(start);
            start = start.plusMinutes(15);
            if(start.toString().equals(startTest.toString())){
                startTime.setValue(start);
            }
            if(start.toString().equals(endTest.toString())){
                endTime.setValue(start);
            }
        }
        endTime.getItems().add(start);
        startTime.getItems().add(start);
        start = start.plusMinutes(15);
        endTime.getItems().add(start);
        startTime.getItems().add(start);
        start = start.plusMinutes(15);
        endTime.getItems().add(start);
        startTime.getItems().add(start);
        start = start.plusMinutes(15);

        custTxt.setText(String.valueOf(passedApp.getCustomer_ID()));
        userTxt.setText(String.valueOf(passedApp.getUser_ID()));

    }
    /**Goes back to the main Appointment screen.
     * @param actionEvent The action event*/
    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    /**Adds an appointment.
     * @param actionEvent The action event*/

    public void addAppointment(ActionEvent actionEvent) throws SQLException, IOException, ParseException {
        String sql = "UPDATE appointments SET Title=?, Description=?, Contact_ID=?, Type=?, Location=?, Start=?, End=?, Customer_ID=?, User_ID=? WHERE Appointment_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,titleTxt.getText());
        ps.setString(2,descTxt.getText());
        Contact temp = contactField.getSelectionModel().getSelectedItem();
        ps.setInt(3,temp.getId());
        ps.setString(4,typeTxt.getText());
        ps.setString(5,locTxt.getText());


        LocalDateTime dt = LocalDateTime.of(startDate.getValue(), (LocalTime) startTime.getValue());

        LocalDateTime dt1 = LocalDateTime.of(endDate.getValue(), (LocalTime) endTime.getValue());

        SimpleDateFormat sdf = new SimpleDateFormat("H");
        sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        AddAppointments.Lambda1 convert = n -> Integer.parseInt(sdf.format(Timestamp.valueOf(n)));
        int x = convert.timeToInt(dt);
        int y = convert.timeToInt(dt1);


        ps.setTimestamp(6, Timestamp.valueOf((dt)));
        ps.setTimestamp(7, Timestamp.valueOf((dt1)));
        try {
            ps.setInt(8, Integer.parseInt(custTxt.getText()));
            ps.setInt(9, Integer.parseInt(userTxt.getText()));
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Customer Id and User Id invalid format, must be an integer.");
            alert.showAndWait();
            return;
        }

        ps.setInt(10, passedApp.getAppointment_ID());


        if(titleTxt.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter a title.");
            alert.showAndWait();
        }
        else if(descTxt.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter a description.");
            alert.showAndWait();
        }
        else if(contactField.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a contact.");
            alert.showAndWait();
        }
        else if(typeTxt.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a type.");
            alert.showAndWait();
        }
        else if(locTxt.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a location.");
            alert.showAndWait();
        }

        else if(startDate.getValue().getDayOfWeek()== DayOfWeek.SATURDAY || startDate.getValue().getDayOfWeek() == DayOfWeek.SUNDAY || endDate.getValue().getDayOfWeek()== DayOfWeek.SATURDAY || endDate.getValue().getDayOfWeek() == DayOfWeek.SUNDAY){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please schedule your appointment between the business hours of 8:00 to 22:00 EST Monday through Friday.");
            alert.showAndWait();
        }
        else if(!startDate.getValue().equals(endDate.getValue())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please schedule your appointment between the business hours of 8:00 to 22:00 EST Monday through Friday.");
            alert.showAndWait();
        }

        else if(x>21 || x<8 || y>21 || y<8){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please schedule your appointment between the business hours of 8:00 to 22:00 EST Monday through Friday.");
            alert.showAndWait();
        }
        else if (y<x) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Start time must be before end time.");
            alert.showAndWait();
        }
        else if(!AppointmentList.checkCustomer(Integer.parseInt(custTxt.getText()),Timestamp.valueOf((dt)),Timestamp.valueOf((dt1)))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Customer is already booked during this time.");
            alert.showAndWait();
        }
        else if(CustomerList.checkCustomer(Integer.parseInt(custTxt.getText()))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Customer Id not found.");
            alert.showAndWait();
        }
        else if(Users.checkUsers(Integer.parseInt(userTxt.getText()))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("User Id not found.");
            alert.showAndWait();
        }

        else{
            ps.executeUpdate();
            Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 600);
            stage.setTitle("Appointments");
            stage.setScene(scene);
            stage.show();
        }

    }
    }