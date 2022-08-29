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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**Sets up the AddAppointments screen.*/
public class AddAppointments implements Initializable {


    public TextField custTxt;
    public TextField userTxt;
    public TextField appTxt;
    public TextArea descTxt;
    public TextField titleTxt;
    public TextField locTxt;
    public TextField typeTxt;

    public DatePicker startDate;
    public DatePicker endDate;

    public ComboBox<LocalTime> endTime;
    public ComboBox<LocalTime> startTime;
    public ComboBox<Contact> contactField;

    public Button saveButton;
    public Button cancelButton;


    /**Sets up the Add Appointment Screen.
     * @param url the url location
     * @param resourceBundle The resource location */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DateTimeFormatter timeDTF = DateTimeFormatter.ofPattern("HH:mm:ss");
        try {
            ContactList.update();
            contactField.setItems(ContactList.getAllContacts());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        LocalTime start = LocalTime.of(0,0);
        LocalTime end = LocalTime.of(23,0);

        while(start.isBefore(end.plusSeconds(1))){
            endTime.getItems().add(start);
            startTime.getItems().add(start);
            start = start.plusMinutes(15);
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
    /**Adds the appointment
     * Uses Lambda 1 to convert time to int to ensure that the appointment is within proper business hours.
     * @param actionEvent The action event*/

    public void addAppointment(ActionEvent actionEvent) throws SQLException, IOException, ParseException {

        String sql = "INSERT INTO appointments (Title, Description, Contact_ID, Type, Location, Start, End, Customer_ID, User_ID)VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,titleTxt.getText());
        ps.setString(2,descTxt.getText());
        if(contactField.getSelectionModel().getSelectedItem() != null) {
            ps.setInt(3, contactField.getSelectionModel().getSelectedItem().getId());
        }
        ps.setString(4,typeTxt.getText());
        ps.setString(5,locTxt.getText());


        LocalDate date = startDate.getValue();
        LocalTime time = startTime.getValue();
        LocalDate date1 = endDate.getValue();
        LocalTime time1 = endTime.getValue();

        if(date == null || time == null || date1 == null || time1 == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Missing time or date");
            alert.showAndWait();
            return;
        }

        LocalDateTime dt = LocalDateTime.of(date, time);
        LocalDateTime dt1 = LocalDateTime.of(date1, time1);

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


        SimpleDateFormat sdf = new SimpleDateFormat("H");
        sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));

        Lambda1 convert = n -> Integer.parseInt(sdf.format(Timestamp.valueOf(n)));
        int x = convert.timeToInt(dt);
        int y = convert.timeToInt(dt1);


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

        else if(date1.getDayOfWeek()== DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY || date.getDayOfWeek()== DayOfWeek.SATURDAY || date1.getDayOfWeek() == DayOfWeek.SUNDAY){
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
    /**Lambda 1 Interface allows you to take your time and transfer it to an integer.*/
    public interface Lambda1{
        int timeToInt(LocalDateTime x);
    }



}