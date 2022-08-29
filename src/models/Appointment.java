package models;
import java.sql.Timestamp;
/**Appointment class*/
public class Appointment {
    private int Appointment_ID;
    private String Title;
    private String Description;
    private String Location;
    private String type;

    private String StartDate;
    private String StartTime;
    private String EndDate;
    private String EndTime;
    private Timestamp Create_Date;
    private String Created_By;
    private Timestamp Last_Update;
    private String Last_Updated_By;
    private int Customer_ID;
    private int User_ID;
    private int Contact_ID;
    /**Appointment constructor
     * @param Appointment_ID The appointment id
     * @param Title the title
     * @param Description a description
     * @param Location a location
     * @param type the type of meeting
     * @param StartDate the start date
     * @param StartTime the start time
     * @param EndDate the end date
     * @param EndTime the end time
     * @param Create_Date creation date
     * @param Created_By the creator
     * @param Last_Update the last update
     * @param Last_Updated_By who updated it last
     * @param Customer_ID the customer id
     * @param User_ID the user id
     * @param Contact_ID the contact id*/
    public Appointment(int Appointment_ID, String Title, String Description, String Location, String type, String StartDate,
                       String StartTime, String EndDate, String EndTime, Timestamp Create_Date, String Created_By,
                       Timestamp Last_Update,String Last_Updated_By,int Customer_ID, int User_ID, int Contact_ID){

        this.Appointment_ID = Appointment_ID;
        this.Title = Title;
        this.Description = Description;
        this.Location = Location;
        this.type = type;
        this.StartDate = StartDate;
        this.StartTime = StartTime;
        this.EndDate = EndDate;
        this.EndTime = EndTime;
        this.Create_Date = Create_Date;
        this.Created_By = Created_By;
        this.Last_Update = Last_Update;
        this.Last_Updated_By = Last_Updated_By;
        this.Customer_ID = Customer_ID;
        this.User_ID = User_ID;
        this.Contact_ID = Contact_ID;

    }
    /**appointment_ID setter
     * @param Appointment_ID the appointment id*/
    public void setAppointment_ID(int Appointment_ID){this.Appointment_ID = Appointment_ID;}
    /**title setter
     * @param Title the title*/
    public void setTitle(String Title){
        this.Title = Title;
    }
    /**description setter
     * @param Description the description*/
    public void setDescription(String Description){
        this.Description = Description;
    }
    /**location setter
     * @param Location the location*/
    public void setLocation(String Location){
        this.Location = Location;
    }
    /**type setter
     * @param type the type*/
    public void setType(String type){
        this.type = type;
    }
    /**start date setter
     * @param StartDate the start date*/
    public void setStartDate(String StartDate){
        this.StartDate = StartDate;
    }
    /**start time setter
     * @param StartTime the start time*/
    public void setStartTime(String StartTime){
        this.StartTime = StartTime;
    }
    /**end date setter
     * @param EndDate the end date*/
    public void setEndDate(String EndDate){
        this.EndDate = EndDate;
    }
    /**end time setter
     * @param EndTime the end time*/
    public void setEndTime(String EndTime){
        this.EndTime = EndTime;
    }
    /**create date setter
     * @param Create_Date creation date*/
    public void setCreate_Date(Timestamp Create_Date){
        this.Create_Date = Create_Date;
    }
    /**created by setter
     * @param Created_By who created it*/
    public void setCreated_By(String Created_By){
        this.Created_By = Created_By;
    }
    /**last update setter
     * @param Last_Update last update*/
    public void setLast_Update(Timestamp Last_Update){
        this.Last_Update = Last_Update;
    }
    /**last updated by setter
     * @param Last_Updated_By who last updated it*/
    public void setLast_Updated_By(String Last_Updated_By){
        this.Last_Updated_By = Last_Updated_By;
    }
    /**customer setter
     * @param Customer_ID customer id*/
    public void setCustomer_ID(int Customer_ID){
        this.Customer_ID = Customer_ID;
    }
    /**user id setter
     * @param User_ID user id*/
    public void setUser_ID(int User_ID){
        this.User_ID = User_ID;
    }
    /**contact id setter
     * @param Contact_ID contact id*/
    public void setContact_ID(int Contact_ID){
        this.Contact_ID = Contact_ID;
    }
    /**appointment id getter
     * @return Appointment_ID*/
    public int getAppointment_ID() {
        return Appointment_ID;
    }
    /**title getter
     * @return Title*/
    public String getTitle(){
        return Title;
    }
    /**description getter
     * @return Description*/
    public String getDescription(){
        return Description;
    }
    /**location getter
     * @return Location*/
    public String getLocation(){return Location;}
    /**type getter
     * @return type */
    public String getType(){return type;}
    /**start date getter
     * @return StartDate*/
    public String getStartDate(){return StartDate;}
    /**start time getter
     * @return StartTime*/
    public String getStartTime(){return StartTime;}
    /**end date getter
     * @return EndDate*/
    public String getEndDate(){return EndDate;}
    /**end time getter
     * @return EndTime*/
    public String getEndTime(){return EndTime;}
    /**create date getter
     * @return Create_Date*/
    public Timestamp getCreate_Date(){return Create_Date;}
    /**created by getter
     * @return Created_By*/
    public String getCreated_By(){return Created_By;}
    /**last update getter
     * @return Last_Update*/
    public Timestamp getLast_Update(){return Last_Update;}
    /**last updated by getter
     * @return Last_Updated_By*/
    public String getLast_Updated_By(){return Last_Updated_By;}
    /**user id getter
     * @return User_ID*/
    public int getUser_ID(){return User_ID;}
    /**customer id getter
     * @return Customer_ID*/
    public int getCustomer_ID(){return Customer_ID;}
    /**contact getter
     * @return Contact_ID*/
    public int getContact_ID(){return Contact_ID;}

    /**Updates string to string to return the type*/
    @Override
    public String toString(){
        return(type);
    }
}
