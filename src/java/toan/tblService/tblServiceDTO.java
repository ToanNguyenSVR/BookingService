/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toan.tblService;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author toann
 */
public class tblServiceDTO implements Serializable{
     private String serviceID, serviceName, color, categoryID;
    private int quanlity;
    private Date createDate;

    public tblServiceDTO(String serviceID, String serviceName, String color, String categoryID, int quanlity, Date createDate) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.color = color;
        this.categoryID = categoryID;
        this.quanlity = quanlity;
        this.createDate = createDate;
    }

    public tblServiceDTO() {
    }

    public String getServiceID() {
        return serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getColor() {
        return color;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public int getQuanlity() {
        return quanlity;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public void setQuanlity(int quanlity) {
        this.quanlity = quanlity;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
}
