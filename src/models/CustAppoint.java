package models;
/**Customer appointment class*/
public class CustAppoint {
    private int id;
    private int count;
    /**Customer appointment constructor*/
    public CustAppoint(int id, int count){
        this.id = id;
        this.count = count;
    }
    /**id setter*/
    public void setId(int id) {
        this.id = id;
    }
    /**count setter*/
    public void setCount(int count) {
        this.count = count;
    }
    /**id getter*/
    public int getId() {
        return id;
    }
    /**count getter*/
    public int getCount() {
        return count;
    }
}
