package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.Login;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
/**Sets up Home-screen.*/
public class HomeScreen implements Initializable {


    public Label homeLabel;
    public Label message;
    public Button appButton;
    public Button custButton;
    public Button reportButton;
    public Button logoutButton;

    /**Sets up the Home-screen.
     * @param url the url location
     * @param resourceBundle The resource location */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Login.checkDate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        message.setText(Login.getUpcomingAppointment());

    }
    /**Switches to the Appointments screen.
     * @param actionEvent The action event*/

    public void toAppointments(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }
    /**Switches to the customers screen.
     * @param actionEvent The action event*/

    public void toCustomers(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }
    /**Switches to the reports screen.
     * @param actionEvent The action event*/

    public void toReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Reports Screen");
        stage.setScene(scene);
        stage.show();
    }
    /**Logs out
     * @param actionEvent The action event*/

    public void toLogout(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Login Screen");
        stage.setScene(scene);
        stage.show();
    }
}