package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
/**A list of contacts from the sql database.*/
public class ContactList {
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    /**Updates contact list with a list from the sql database.*/
    public static void update() throws SQLException {

        int size = allContacts.size();
        for(int i = 0;i < size;i++) {
            allContacts.remove(0);
        }

        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("Contact_ID");
            String name = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            Contact person = new Contact(id,name,email);
            allContacts.add(person);
        }

    }
    /**Returns contact by id.*/
    public static Contact contactById(int x){
        Contact y = allContacts.get(0);
        int size = allContacts.size();
        for(int i = 0;i < size;i++) {
            if(allContacts.get(i).getId() == x){
                y = allContacts.get(i);
            }
        }
        return y;
    }
    /**allContacts getter*/
    public static ObservableList<Contact> getAllContacts(){
        return allContacts;
    }


}
