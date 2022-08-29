package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.JDBC;
import models.*;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;
/**Sets up the AddAppointments screen.*/
public class AddCustomer implements Initializable {


    public TextField idTxt;
    public TextField nameTxt;
    public TextField phoneTxt;
    public TextField addressTxt;
    public TextField postalTxt;

    public ComboBox<Country> countryBox;
    public ComboBox<firstDivisions> stateBox;
    
    public Button cancelButton;
    public Button saveButton;

    /**Sets up the Add Customer Screen.
     * @param url the url location
     * @param resourceBundle The resource location */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            CountryList.update();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        countryBox.setItems(CountryList.getAllCountries());



    }

    /**Adds the customer
     * @param actionEvent The action event*/

    public void addCustomer(ActionEvent actionEvent) throws SQLException, IOException {
        String sql = "INSERT INTO customers (Customer_Name,Phone,Division_ID,Postal_Code,Address,Create_Date,Last_Update,Created_By,Last_Updated_By)VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,nameTxt.getText());
        ps.setString(2,phoneTxt.getText());

        if(stateBox.getSelectionModel().getSelectedItem() != null) {
            firstDivisions test = stateBox.getSelectionModel().getSelectedItem();
            ps.setInt(3,test.getDivisionId());
        }

        ps.setString(4,postalTxt.getText());
        ps.setString(5,addressTxt.getText());

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        ps.setTimestamp(6,currentTime);
        ps.setTimestamp(7,currentTime);
        ps.setString(8,Users.getCurrentUser());
        ps.setString(9,Users.getCurrentUser());

        if(nameTxt.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter a name.");
            alert.showAndWait();
        }
        else if(phoneTxt.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter a phone number.");
            alert.showAndWait();
        }
        else if(stateBox.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a state or provence");
            alert.showAndWait();
        }
        else if(postalTxt.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter a postal code.");
            alert.showAndWait();
        }
        else if(addressTxt.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter an address.");
            alert.showAndWait();
        }
        else {
            ps.executeUpdate();
            Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 600);
            stage.setTitle("Customers");
            stage.setScene(scene);
            stage.show();
        }


    }

    /**Goes back to the main Customer screen.
     * @param actionEvent The action event*/


    public void toCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    /**Gets the country
     * @param actionEvent The action event*/

    public void onAction(ActionEvent actionEvent) throws SQLException {
        Country temp = countryBox.getSelectionModel().getSelectedItem();
        if(temp!=null) {
            firstDivisionsList.update(temp.getCountryId());
            stateBox.setItems(firstDivisionsList.getDivisions());
        }
    }

}