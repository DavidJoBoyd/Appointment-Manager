package models;

import javafx.scene.control.Alert;
import main.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
/**Login class*/
public class Login {
    private static String upcomingAppointment;
    private static LocalDateTime logonTime;
    /**Checks if there is an appointment within 15 minutes of logging in.*/
    public static void checkDate() throws SQLException {
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String id = rs.getString("Appointment_ID");
            Timestamp time = rs.getTimestamp("Start");
            LocalDateTime now = LocalDateTime.now();
            long timeDifference = ChronoUnit.MINUTES.between(time.toLocalDateTime(), logonTime);


            if (timeDifference <= 15) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Upcoming Appointment");
                alert.setContentText("You have an upcoming appointment. Appointment " + id + " taking place at " + time );
                alert.showAndWait();
                upcomingAppointment = "Appointment " + id + " taking place at " + time;
            }

        }

    }
    /**upcoming appointment getter*/
    public static String getUpcomingAppointment(){
        return upcomingAppointment;
    }
    /**log on time setter*/
    public static void setlogonTime(){
        logonTime = LocalDateTime.now();
    }

}
