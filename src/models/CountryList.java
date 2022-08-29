package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
/**Creates a list of Countries*/
public class CountryList {
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    /** Updates countrylist with countries in the sql database.*/
    public static void update() throws SQLException {

        int size = allCountries.size();
        for (int i = 0; i < size; i++) {
            allCountries.remove(0);
        }

        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("Country_ID");
            String Title = rs.getString("Country");
            Country app = new Country(id, Title);
            allCountries.add(app);
        }
    }

    /**Gets the name of the country from the id.*/
    public static String getName(int x){
        int size = allCountries.size();
        for (int i = 0; i < size; i++) {
            if(allCountries.get(i).getCountryId()==x) {
                return allCountries.get(i).getCountry();
            }
        }
        return null;
    }
    /**allCountries getter*/
    public static ObservableList<Country> getAllCountries(){
        return allCountries;
    }
}
