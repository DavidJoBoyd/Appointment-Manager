package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
/**A list of customers*/
public class CustomerList {
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
/**Updates list of customers with the customers from the sql database.*/
    public static void update() throws SQLException {

        int size = allCustomers.size();
        for(int i = 0;i < size;i++) {
            allCustomers.remove(0);
        }

        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String phone = rs.getString("Phone");

            int x = rs.getInt("Division_ID");

            String state = firstDivisionsList.getState(x);

            String postalCode = rs.getString("Postal_Code");
            String address = rs.getString("Address");
            Timestamp Create_Date = rs.getTimestamp("Create_Date");
            String Created_By = rs.getString("Created_By");
            Timestamp Last_Update = rs.getTimestamp("Last_Update");
            String Last_Updated_By = rs.getString("Last_Updated_By");
            Customer person = new Customer(id,name,phone,state,postalCode,address,Create_Date,Created_By,Last_Update,Last_Updated_By);
            allCustomers.add(person);
        }

    }
    /**Gets a state*/
    public static firstDivisions getState(String z ) throws SQLException {

        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String phone = rs.getString("Phone");

            int x = rs.getInt("Division_ID");

            String state = firstDivisionsList.getState(x);

            String postalCode = rs.getString("Postal_Code");
            String address = rs.getString("Address");
            Timestamp Create_Date = rs.getTimestamp("Create_Date");
            String Created_By = rs.getString("Created_By");
            Timestamp Last_Update = rs.getTimestamp("Last_Update");
            String Last_Updated_By = rs.getString("Last_Updated_By");

            if (state.equals(z)) {
                firstDivisions a = new firstDivisions(x, state);
                return a;
                }
            }
        return null;
    }
    /**Checks for a customer id.*/
    public static boolean checkCustomer(int x) throws SQLException {
        CustomerList.update();
        int size = allCustomers.size();
        for(int i = 0;i < size;i++) {
            if(allCustomers.get(i).getCustomerId()==x){
                return false;
            }
        }
        return true;
    }
    /**allCustomers getter*/
    public static ObservableList<Customer> getAllCustomers(){
        return allCustomers;
    }



}
