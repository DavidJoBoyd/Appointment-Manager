package models;

import java.sql.Timestamp;
/**Customer class*/
public class Customer {
    private int customerId;
    private String customerName;
    private String phoneNumber;
    private String state;
    private String postalCode;
    private String address;
    private Timestamp Create_Date;
    private String Created_By;
    private Timestamp Last_Update;
    private String Last_Updated_By;
    /**Customer constructor*/
    public Customer(int customerId, String customerName, String phoneNumber, String state, String postalCode, String address,
    Timestamp Create_Date, String Created_By, Timestamp Last_Update, String Last_Updated_By){
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.state = state;
        this.postalCode = postalCode;
        this.address = address;
        this.Create_Date = Create_Date;
        this.Created_By = Created_By;
        this.Last_Update = Last_Update;
        this.Last_Updated_By = Last_Updated_By;

    }
    /**id setter*/
    public void setCustomerId(int customerId){this.customerId = customerId;}
    /**name setter*/
    public void setCustomerName(String customerName){this.customerName = customerName;}
    /**phone number setter*/
    public void setPhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber;}
    /**state setter*/
    public void setState(String state){this.state = state;}
    /**postal code setter*/
    public void setPostalCode(String postalCode){this.postalCode = postalCode;}
    /**address setter*/
    public void setAddress(String address){this.address = address;}
    /**create date setter*/
    public void setCreate_Date(Timestamp Create_Date){this.Create_Date = Create_Date;}
    /**created by setter*/
    public void setCreated_By(String Created_By){
        this.Created_By = Created_By;
    }
    /**last update setter*/
    public void setLast_Update(Timestamp Last_Update){
        this.Last_Update = Last_Update;
    }
    /**last updated by setter*/
    public void setLast_Updated_By(String Last_Updated_By){this.Last_Updated_By = Last_Updated_By;}
    /**id getter*/
    public int getCustomerId(){return customerId;}
    /**name getter*/
    public String getCustomerName(){return customerName;}
    /**phone number getter*/
    public String getPhoneNumber(){return phoneNumber;}
    /**state getter*/
    public String getState(){return state;}
    /**postal code getter*/
    public String getPostalCode(){return postalCode;}
    /**address getter*/
    public String getAddress(){return address;}
    /**create date getter*/
    public Timestamp getCreate_Date(){return Create_Date;}
    /**created by getter*/
    public String getCreated_By(){return Created_By;}
    /**last update getter*/
    public Timestamp getLast_Update(){return Last_Update;}
    /**last updated by getter*/
    public String getLast_Updated_By(){return Last_Updated_By;}



}
