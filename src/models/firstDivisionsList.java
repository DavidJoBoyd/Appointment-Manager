package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**A list of firstDivisions*/
public class firstDivisionsList {
    private static ObservableList<firstDivisions> allDivisions = FXCollections.observableArrayList();
    /**Gets state name of first division*/
    public static String getState(int x) throws SQLException {

        int size = allDivisions.size();
        for(int i = 0;i < size;i++) {
            allDivisions.remove(0);
        }

        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("Division_ID");
            String state = rs.getString("Division");
            if(x==id){
                return state;
            }
            firstDivisions person = new firstDivisions(id,state);
            allDivisions.add(person);
        }
        return null;

    }
    /**Updates first divisions list with first divisions from the sql database.*/
    public static void update(int x) throws SQLException {

        int size = allDivisions.size();
        for (int i = 0; i < size; i++) {
            allDivisions.remove(0);
        }
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("Division_ID");
            String Title = rs.getString("Division");
            int idc = rs.getInt("Country_ID");
            if(idc==x) {
                firstDivisions app = new firstDivisions(id, Title);
                allDivisions.add(app);
            }
        }
    }
    /**Gets the country*/
    public static Country getCountry(int x) throws SQLException {

        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int country_id = rs.getInt("Country_ID");
            int division_id = rs.getInt("Division_ID");


            String Title = CountryList.getName(country_id);

            if(division_id==x){
                Country z = new Country(country_id,Title);
                return z;
            }
        }
        return null;
    }
    /**allDivisions getter*/
    public static ObservableList<firstDivisions> getDivisions(){
        return allDivisions;
    }
}
