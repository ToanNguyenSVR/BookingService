/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toan.tblRequest;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author toann
 */
public class tblRequestDTO implements Serializable{

    private int requestID;
    private Date dateBook;
    private String email, serviceID , statusReq;

    public tblRequestDTO(int requestID, String statusReq, Date dateBook, String email, String serviceID) {
        this.requestID = requestID;
        this.statusReq = statusReq;
        this.dateBook = dateBook;
        this.email = email;
        this.serviceID = serviceID;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public String getStatusReq() {
        return statusReq;
    }

    public void setStatusReqID(String statusReqID) {
        this.statusReq = statusReqID;
    }

    public Date getDateBook() {
        return dateBook;
    }

    public void setDateBook(Date dateBook) {
        this.dateBook = dateBook;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String productID) {
        this.serviceID = productID;
    }

    @Override
    public String toString() {
        return "RequestDTO{" + "requestID=" + requestID + ", statusReqID=" + statusReq + ", dateBook=" + dateBook + ", email=" + email + ", serviceID=" + serviceID + '}';
    }

}
