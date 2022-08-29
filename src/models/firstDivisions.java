package models;

import java.sql.Timestamp;
/**First divisions class*/
public class firstDivisions {
    private int divisionId;
    private String state;
    /**firstDivisions constructor*/
    public firstDivisions(int divisionId, String state){
        this.divisionId = divisionId;
        this.state = state;
    }
    /**divisionId setter*/
    public void setDivisionId(int divisionId){
        this.divisionId = divisionId;
    }
    /**state setter*/
    public void setState(String state){this.state = state;}
    /**divisionId getter*/
    public int getDivisionId(){return divisionId;}
    /**state getter*/
    public String getState(){return state;}
    /**Overrides toString to return state.*/
    @Override
    public String toString(){
        return(state);
    }

}
