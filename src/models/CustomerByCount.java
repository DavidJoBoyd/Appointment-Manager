package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
/**Gets appointment counts of customers*/
public class CustomerByCount {
    private static ObservableList<CustAppoint> custAppoints = FXCollections.observableArrayList();
    /**Gets appointment counts of customers*/
    public static ObservableList<CustAppoint> update() throws SQLException {
        CustomerList.update();
        AppointmentList.update();

        int x = CustomerList.getAllCustomers().size();
        for(int i = 0;i < x;i++) {
            int count = 0;
            Customer y = CustomerList.getAllCustomers().get(i);
            int z = AppointmentList.getAllAppointments().size();
            for(int j = 0; j < z;j++){
                if(y.getCustomerId()== AppointmentList.getAllAppointments().get(j).getCustomer_ID()){
                    count = count + 1;
                }
            }

            CustAppoint temp = new CustAppoint(y.getCustomerId(),count);
            custAppoints.add(temp);
        }
        return custAppoints;
    }
}
