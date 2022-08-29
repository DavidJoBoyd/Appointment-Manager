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
import main.JDBC;
import models.AppointmentList;
import models.Appointment;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

/**Sets up the AddAppointments screen.*/
public class Appointments implements Initializable {


    public TableView<Appointment> appTable;
    public TableColumn<Appointment, Integer> appIdCol;
    public TableColumn<Appointment, String> titleCol;
    public TableColumn<Appointment, String> descCol;
    public TableColumn<Appointment, String> locCol;

    public TableColumn<Appointment, String> typeCol;
    public TableColumn<Appointment, String> startDateCol;
    public TableColumn<Appointment, String> startTimeCol;
    public TableColumn<Appointment, String> endDateCol;
    public TableColumn<Appointment, String> endTimeCol;
    public TableColumn<Appointment, Integer> custCol;
    public TableColumn<Appointment, Integer> userCol;
    public TableColumn<Appointment, Integer> contactCol;

    public RadioButton allRadio;
    public RadioButton monthRadio;
    public RadioButton weekRadio;
    public Button addButton;
    public Button modifyButton;
    public Button deleteButton;
    public Button backButton;

    public static int x;
    private static Appointment passApp;
    /**Gets the appointment*/
    public static Appointment getProductToModify() {
        return passApp;
    }

    /**Sets up the Appointment screen.
     * @param url the url location
     * @param resourceBundle The resource location */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            AppointmentList.update();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

            appTable.setItems(AppointmentList.getAllAppointments());
            appIdCol.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
            descCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
            locCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startDateCol.setCellValueFactory(new PropertyValueFactory<>("StartDate"));
            startTimeCol.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
            endDateCol.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
            endTimeCol.setCellValueFactory(new PropertyValueFactory<>("EndTime"));
            custCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
            userCol.setCellValueFactory(new PropertyValueFactory<>("User_ID"));
            contactCol.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));

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
    /**Moves you to the add appointment screen
     * @param actionEvent The action event*/

    public void toAdd(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointments.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Add Appointments");
        stage.setScene(scene);
        stage.show();
    }
    /**Moves you to the update appointment screen
     * @param actionEvent The action event*/

    public void toUpdate(ActionEvent actionEvent) throws IOException {
        if(appTable.getSelectionModel().getSelectedItem()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No appointment to update. Please select an appointment.");
            alert.showAndWait();
        }
        else {
            passApp = appTable.getSelectionModel().getSelectedItem();
            Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateAppointments.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 400);
            stage.setTitle("Update Appointments");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**Deletes the selected appointment
     * @param actionEvent The action event*/

    public void delete(ActionEvent actionEvent) throws SQLException, ParseException {
        if(appTable.getSelectionModel().getSelectedItem()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No appointment to delete. Please select an appointment.");
            alert.showAndWait();
        }
        else {
            Appointment currId = appTable.getSelectionModel().getSelectedItem();
            int x = currId.getAppointment_ID();
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, x);
            ps.executeUpdate();

            AppointmentList.update();
            if (x == 1) {
                AppointmentList.toMonths();
            }
            if (x == 2) {
                AppointmentList.toWeeks();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deletion Successful");
            alert.setHeaderText("Success");
            alert.setContentText("Appointment " + currId.getAppointment_ID() + " of type " + currId.getType() + " has been successfully deleted.");
            alert.showAndWait();
        }
    }
    /**Shows all appointments
     * @param actionEvent The action event*/
    public void allApps(ActionEvent actionEvent) throws SQLException {
        AppointmentList.update();
        x=0;
    }

    /**Shows appointments in the month
     * @param actionEvent The action event*/

    public void monthlyApps(ActionEvent actionEvent) throws SQLException {
        AppointmentList.update();
        AppointmentList.toMonths();
        x=1;

    }
    /**Shows all appointments in the week
     * @param actionEvent The action event*/

    public void weeklyApps(ActionEvent actionEvent) throws SQLException, ParseException {
        AppointmentList.update();
        AppointmentList.toWeeks();
        x=2;
    }
}