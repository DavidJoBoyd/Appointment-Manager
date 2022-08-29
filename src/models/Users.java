package models;

import main.JDBC;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
/**Users class*/
public class Users {
    private static String currentUser;
    /**Checks for a valid username and password from the sql database.*/
    public static boolean login(String userInput, String passInput) throws SQLException, IOException {
        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String filename = "login_activity.txt", item;
        FileWriter fwriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fwriter);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        while(rs.next()){
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            if(userName.equals(userInput) && password.equals(passInput)){
                System.out.println("Login successful");
                currentUser = userInput;
                item = "" + timestamp + " Username: " + userInput + " Login Attempt: Success";
                outputFile.println(item);
                outputFile.close();

                return true;
            }
        }
        item = "" + timestamp + " Username: " + userInput + " Login Attempt: Fail";
        outputFile.println(item);
        outputFile.close();
        return false;
    }
    /**Checks users.*/
    public static boolean checkUsers(int x) throws SQLException {
        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("User_ID");
            if(id==x){
                return false;
            }
        }
        return true;
    }

    /**currentUser getter*/
    public static String getCurrentUser(){
        return currentUser;
    }

}
