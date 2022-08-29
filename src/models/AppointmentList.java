package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBC;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
/**Stores all the appointments from your sql database*/
public class AppointmentList {
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
/**Updates the class with appointments from the sql database. Utilizes Lambda 2 to reformat the dates. */
    public static void update() throws SQLException {

        int size = allAppointments.size();
        for(int i = 0;i < size;i++) {
            allAppointments.remove(0);
        }

        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String Title = rs.getString("Title");
            String Description = rs.getString("Description");
            String Location = rs.getString("Location");
            String Type = rs.getString("Type");
            Timestamp Start = rs.getTimestamp("Start");
            Timestamp End = rs.getTimestamp("End");
            Timestamp Create_Date = rs.getTimestamp("Create_Date");
            String Created_By = rs.getString("Created_By");
            Timestamp Last_Update = rs.getTimestamp("Last_Update");
            String Last_Updated_By = rs.getString("Last_Updated_By");
            int Customer_ID = rs.getInt("Customer_ID");
            int User_ID = rs.getInt("User_ID");
            int Contact_ID = rs.getInt("Contact_ID");

            SimpleDateFormat dateformatter = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat timeformatter = new SimpleDateFormat("HH:mm:ss");


            Lambda2 reformat = n-> dateformatter.format(n);
            String StartDate = reformat.timeToInt(Start);
            String EndDate = reformat.timeToInt(End);



            String StartTime = timeformatter.format(Start);
            String EndTime = timeformatter.format(End);

            Appointment app = new Appointment(id,Title,Description,Location,Type,StartDate,StartTime,EndDate,EndTime,
                    Create_Date,Created_By, Last_Update,Last_Updated_By,Customer_ID,User_ID,Contact_ID);

            allAppointments.add(app);
        }

    }
    /**Filters out all appointments to the ones in the current month.*/
    public static void toMonths() throws SQLException {

        int size = allAppointments.size();
        int x = 0;
        for (int i = 0; i < size; i++) {
            Appointment app = allAppointments.get(i-x);


            String rawDate = app.getStartDate();
            String date = rawDate.substring(6,10);
            date = date + "-"+ rawDate.substring(0,2);



            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
            String currentTime = now.format(dateTimeFormatter);


            if(!date.equals(currentTime)){
                allAppointments.remove(app);
                x=x+1;

            }


        }

    }
    /**Filters out all appointments to the ones in the current week.*/
    public static void toWeeks() throws SQLException, ParseException {

        int size = allAppointments.size();
        int x = 0;
        for (int i = 0; i < size; i++) {
            Calendar c = Calendar.getInstance();
            int year1 = c.get(c.YEAR);
            int week1 = c.get(c.WEEK_OF_YEAR);
            Appointment app = allAppointments.get(i-x);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(app.getStartDate());


            Calendar d = Calendar.getInstance();
            c.setTime(date);
            int year2 = c.get(c.YEAR);
            int week2 = c.get(c.WEEK_OF_YEAR);
            if((year1==year2) && (week1==week2)){
            }
            else{
                allAppointments.remove(app);
                x = x + 1;
            }


        }

    }
    /**Gets appointments by type and month.*/
    public static int getAppointments(String x,Appointment y) throws ParseException {
        int counter = 0;
        for (int i = 0; i < allAppointments.size(); i++){


            String month = allAppointments.get(i).getStartDate().substring(0,2);
            String month1 = "";
            if(month.equals("01")){
                month1 = "Jan";
            }
            if(month.equals("02")){
                month1 = "Feb";
            }
            if(month.equals("03")){
                month1 = "Mar";
            }
            if(month.equals("04")){
                month1 = "Apr";
            }
            if(month.equals("05")){
                month1 = "May";
            }
            if(month.equals("06")){
                month1 = "Jun";
            }
            if(month.equals("07")){
                month1 = "Jul";
            }
            if(month.equals("08")){
                month1 = "Aug";
            }
            if(month.equals("09")){
                month1 = "Sep";
            }
            if(month.equals("10")){
                month1 = "Oct";
            }
            if(month.equals("11")){
                month1 = "Nov";
            }
            if(month.equals("12")){
                month1 = "Dec";
            }

            if(month1.equals(x)){
                if(y.getType().equals(allAppointments.get(i).getType())){
                    counter = counter +1;
                }
            }


        }
        return counter;
    }
    /**Gets appointments by contact*/
    public static ObservableList<Appointment> getByContact(int x){
        int size = allAppointments.size();
        ObservableList<Appointment> byContact = FXCollections.observableArrayList();

        for(int i = 0;i<size;i++){
            if(allAppointments.get(i).getContact_ID() == x){
                byContact.add(allAppointments.get(i));
            }
        }
        return byContact;
    }
    /**Gets allAppointments observable list.*/
    public static ObservableList<Appointment> getAllAppointments(){
        return allAppointments;
    }
    /**Checks if customer has an appointment during the current time.*/
    public static boolean checkCustomer(int x,Timestamp start,Timestamp end) throws ParseException, SQLException {
        int size = allAppointments.size();
        for(int i = 0;i<size;i++) {
            if(allAppointments.get(i).getCustomer_ID()==x){
                DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalDate localDate = LocalDate.parse(allAppointments.get(i).getStartDate(), dateformatter);
                LocalTime localTime = LocalTime.parse(allAppointments.get(i).getStartTime(), timeformatter);
                LocalDateTime dt = LocalDateTime.of(localDate, localTime);

                LocalDate localDate1 = LocalDate.parse(allAppointments.get(i).getEndDate(), dateformatter);
                LocalTime localTime1 = LocalTime.parse(allAppointments.get(i).getEndTime(), timeformatter);
                LocalDateTime dt1 = LocalDateTime.of(localDate1, localTime1);

                Timestamp checkStart = Timestamp.valueOf(dt);
                Timestamp checkEnd = Timestamp.valueOf(dt1);
                Timestamp tempStart = start;
                Timestamp tempEnd = end;

                if((tempStart.after(checkStart) || tempStart.equals(checkStart)) && tempStart.before(checkEnd)) {
                    while (tempStart.before(tempEnd)) {
                        if ((tempStart.after(checkStart) || tempStart.equals(checkStart)) && tempStart.before(checkEnd)) {
                            return false;
                        } else {
                            tempStart.setTime(tempStart.getTime() + TimeUnit.MINUTES.toMillis(15));
                        }
                        if (tempStart.after(checkStart) && tempStart.before(checkEnd)) {
                            return false;
                        }
                    }
                }
            }

        }
        return true;
    }
    /**Lambda 2 Interface allows you to reformat your time.*/
    public interface Lambda2{
        String timeToInt(Timestamp x);
    }

}
