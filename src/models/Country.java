package models;
/**Country class*/
public class Country {
    private int countryId;
    private String country;
    /**Country constructor*/
    public Country(int countryId, String country){
        this.countryId = countryId;
        this.country = country;
    }
    /**id setter*/
    public void setCountryId(int countryId){this.countryId = countryId;}
    /**country setter*/
    public void setCountry(String country){this.country = country;}
    /**id getter*/
    public int getCountryId(){return countryId;}
    /**country getter*/
    public String getCountry(){return country;}

    /**Overrides toString to return country.*/
    @Override
    public String toString(){
        return(country);
    }
}
