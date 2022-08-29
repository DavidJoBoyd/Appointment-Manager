package models;
/**Contact class*/
public class Contact {
    private int id;
    private String name;
    private String email;
    /**Contact constructor*/
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    /**id setter*/
    public void setId(int id) {
        this.id = id;
    }
    /**name setter*/
    public void setName(String name) {
        this.name = name;
    }
    /**email setter*/
    public void setEmail(String email) {
        this.email = email;
    }
    /**id getter*/
    public int getId() {
        return id;
    }
    /**name getter*/
    public String getName() {
        return name;
    }
    /**email getter*/
    public String getEmail() {
        return email;
    }
    /**Overrides string to string to return the name.*/
    @Override
    public String toString(){
        return(name);
    }
}
