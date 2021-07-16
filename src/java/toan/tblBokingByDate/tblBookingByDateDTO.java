/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toan.tblBokingByDate;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author toann
 */
public class tblBookingByDateDTO implements Serializable {
    private String serviceID , serviceName ;
    private int quantity ;
    private Date dateBooking ;

    public tblBookingByDateDTO() {
    }

    public tblBookingByDateDTO(String serviceID, String serviceName, int quantity, Date dateBooking) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.quantity = quantity;
        this.dateBooking = dateBooking;
    }

    public String getServiceID() {
        return serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public int getQuantity() {
        return quantity;
    }

    public Date getDateBooking() {
        return dateBooking;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDateBooking(Date dateBooking) {
        this.dateBooking = dateBooking;
    }

    @Override
    public String toString() {
        return "tblBookingByDateDTO{" + "serviceID=" + serviceID + ", serviceName=" + serviceName + ", quantity=" + quantity + ", dateBooking=" + dateBooking + '}';
    }

   
    
}
