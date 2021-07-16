/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toan.tblNotification;

/**
 *
 * @author toann
 */
public class tblNotificationDTO {
    private String content , userID ;
    

    public tblNotificationDTO(String content, String userID) {
        this.content = content;
        this.userID = userID;
    }

    public tblNotificationDTO() {
    }

    public String getContent() {
        return content;
    }

    public String getUserID() {
        return userID;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "tblNotificationDTO{" + "content=" + content + ", userID=" + userID + '}';
    }
    
    
}
