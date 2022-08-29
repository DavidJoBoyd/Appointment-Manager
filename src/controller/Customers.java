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
import models.*;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
/**Sets up the Customer screen.*/

public class Customers implements Initializable {


    public TableView<Customer> custTable;
    public TableColumn<Customer, Integer> idCol;
    public TableColumn<Customer, String> nameCol;
    public TableColumn<Customer, String> phoneCol;
    public TableColumn<Customer, String> stateCol;
    public TableColumn<Customer, String> postCol;
    public TableColumn<Customer, String> addressCol;
    public TableColumn<Customer, String> createDateCol;
    public TableColumn<Customer, String> createByCol;
    public TableColumn<Customer, String> updateCol;
    public TableColumn<Customer, String> updateColBy;
    public Button modifyButton;
    public Button addButton;
    public Button deleteButton;
    public Button backButton;

    private static Customer passCustomer;

    /**Gets a customer. */
    public static Customer getPassCustomer() {
        return passCustomer;
    }
    /**Sets up the Customer Screen
     * @param url the url location
     * @param resourceBundle The resource location */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            CustomerList.update();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        custTable.setItems(CustomerList.getAllCustomers());
        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        postCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("Create_Date"));
        createByCol.setCellValueFactory(new PropertyValueFactory<>("Created_By"));
        updateCol.setCellValueFactory(new PropertyValueFactory<>("Last_Update"));
        updateColBy.setCellValueFactory(new PropertyValueFactory<>("Last_Updated_By"));
    }

    /**Goes to the add customer screen
     * @param actionEvent The action event*/
    public void add(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Add Customers");
        stage.setScene(scene);
        stage.show();
    }
    /**Goes to the update customer screen
     * @param actionEvent The action event*/
    public void update(ActionEvent actionEvent) throws IOException {
        if(custTable.getSelectionModel().getSelectedItem()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No customer to update. Please select a customer.");
            alert.showAndWait();
        }
        else {
            passCustomer = custTable.getSelectionModel().getSelectedItem();
            Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateCustomer.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 400);
            stage.setTitle("Update Customers");
            stage.setScene(scene);
            stage.show();
        }
    }
    /**Deletes a customer
     * @param actionEvent The action event*/

    public void delete(ActionEvent actionEvent) throws SQLException {
        if(custTable.getSelectionModel().getSelectedItem()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No customer to delete. Please select a customer.");
            alert.showAndWait();
        }
        else {
            Customer currId = custTable.getSelectionModel().getSelectedItem();
            int x = currId.getCustomerId();
            String sql = "DELETE FROM customers WHERE Customer_ID = ?";
            String sql2 = "DELETE FROM appointments WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql2);
            PreparedStatement ps1 = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, x);
            ps.executeUpdate();
            ps1.setInt(1, x);
            ps1.executeUpdate();
            CustomerList.update();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deletion Successful");
            alert.setHeaderText("Success");
            alert.setContentText("Customer " + currId.getCustomerName() + " has been successfully deleted.");
            alert.showAndWait();
        }
    }

    /**Goes to the main screen
     * @param actionEvent The action event*/

    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Home Screen");
        stage.setScene(scene);
        stage.show();
    }
}