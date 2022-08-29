package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
/**Sets up the Login screen.*/
public class LoginScreen implements Initializable {

    public Label idLabel;
    public Label passwordLabel;
    public Label languageGen;
    public Label languageSpec;
    public Label locationGen;
    public Label locationSpec;
    public TextField idTextBox;
    public TextField passwordTextBox;
    public Button loginButton;
    /**Sets up the login screen
     * @param url the url location
     * @param resourceBundle The resource location */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResourceBundle rs = ResourceBundle.getBundle("main/Nat", Locale.getDefault());
        locationSpec.setText(ZoneId.systemDefault().toString());
        idLabel.setText(rs.getString("userNameLabel"));
        passwordLabel.setText(rs.getString("passwordLabel"));
        loginButton.setText(rs.getString("loginButton"));
        languageGen.setText(rs.getString("language"));
        languageSpec.setText(rs.getString("key*"));
        locationGen.setText(rs.getString("location"));


    }
    /**Goes back to the main Appointment screen.
     * @param actionEvent The action event*/
    public void toMain(ActionEvent actionEvent) throws SQLException, IOException {
        String user = idTextBox.getText();
        String pass = passwordTextBox.getText();
        if(Users.login(user, pass)) {
            Login.setlogonTime();
            Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScreen.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 400);
            stage.setTitle("Home Screen");
            stage.setScene(scene);
            stage.show();
        }
        else{
            ResourceBundle rs = ResourceBundle.getBundle("main/Nat", Locale.getDefault());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rs.getString("loginFailedButton"));
            alert.setContentText(rs.getString("loginFailedButton"));
            alert.showAndWait();
        }
    }
}
