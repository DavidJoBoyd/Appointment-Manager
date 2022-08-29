package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
/**Sets up the reports screen.*/
public class Reports implements Initializable {


    public ComboBox<Appointment> typeBox;
    public ComboBox<String> monthBox;
    public Label totalLabel;

    public ComboBox<Contact> contactBox;

    public TableView<Appointment> contactTable;
    public TableColumn<Appointment, Integer> appIdCol;
    public TableColumn<Appointment, Integer> customerCol;
    public TableColumn<Appointment, String> titleCol;
    public TableColumn<Appointment, String> typeCol;
    public TableColumn<Appointment, String> descCol;
    public TableColumn<Appointment, String> startDateCol;
    public TableColumn<Appointment, String> startTimeCol;
    public TableColumn<Appointment, String> endDateCol;
    public TableColumn<Appointment, String> endTimeCol;

    public TableView<CustAppoint> customerTable;
    public TableColumn<CustAppoint, Integer> custIdCol;
    public TableColumn<CustAppoint, Integer> countCol;
    public Button backButton;
    public Contact contactInput;
    /**Sets up the reports screen
     * @param url the url location
     * @param resourceBundle The resource location */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            AppointmentList.update();
            CustomerList.update();
            ContactList.update();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        typeBox.setItems(AppointmentList.getAllAppointments());
        monthBox.setItems(Months.getAllMonths());

        try {
            ContactList.update();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        contactBox.setItems(ContactList.getAllContacts());


        try {
            customerTable.setItems(CustomerByCount.update());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        custIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("Count"));


    }
    /**Checks how many appointments there are with a particular type and month.
     * @param actionEvent The action event*/
    public void calculate(ActionEvent actionEvent) throws ParseException {
        String x = monthBox.getValue();
        Appointment y = typeBox.getValue();
        totalLabel.setText(String.valueOf(AppointmentList.getAppointments(x,y)));
    }
    /**Goes back to the main screen.
     * @param actionEvent The action event*/
    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Home Screen");
        stage.setScene(scene);
        stage.show();
    }
    /**Checks how many appointments a contact has.
     * @param actionEvent The action event*/
    public void contactCheck(ActionEvent actionEvent) throws SQLException {
        AppointmentList.update();
        contactInput = contactBox.getSelectionModel().getSelectedItem();
        if(contactInput!=null) {
            contactTable.setItems(AppointmentList.getByContact(contactInput.getId()));
            appIdCol.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
            descCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startDateCol.setCellValueFactory(new PropertyValueFactory<>("StartDate"));
            startTimeCol.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
            endDateCol.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
            endTimeCol.setCellValueFactory(new PropertyValueFactory<>("EndTime"));
            customerCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        }
    }
}
