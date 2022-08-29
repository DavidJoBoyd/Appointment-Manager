package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**A list of all the months and their abbreviations.*/
public class Months {
    private static ObservableList<String> allMonths = FXCollections.observableArrayList();
    /**Creates a list of all the 12 months.*/
    public static ObservableList<String> getAllMonths(){
        String January = "Jan";
        String February = "Feb";
        String March = "Mar";
        String April = "Apr";
        String May = "May";
        String June = "Jun";
        String July = "Jul";
        String August = "Aug";
        String September = "Sep";
        String October = "Oct";
        String November = "Nov";
        String December = "Dec";

        allMonths.add(January);
        allMonths.add(February);
        allMonths.add(March);
        allMonths.add(April);
        allMonths.add(May);
        allMonths.add(June);
        allMonths.add(July);
        allMonths.add(August);
        allMonths.add(September);
        allMonths.add(October);
        allMonths.add(November);
        allMonths.add(December);
        return allMonths;
    }

}
