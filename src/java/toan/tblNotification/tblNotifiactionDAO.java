/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toan.tblNotification;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.naming.NamingException;
import toan.utils.DBHelper;

/**
 *
 * @author toann
 */
public class tblNotifiactionDAO implements Serializable {

    private PreparedStatement pstm = null;
    private Connection cn = null;
    private ResultSet rs = null;

    public void close() throws SQLException {
        if (pstm != null) {
            pstm.close();
        }
        if (cn != null) {
            cn.close();
        }
        if (rs != null) {
            rs.close();
        }
    }

    public ArrayList<tblNotificationDTO> getNotification(String email) throws CloneNotSupportedException ,NamingException , SQLException{
        ArrayList<tblNotificationDTO> list = new ArrayList<>();
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "Select  contentNotification , email "
                        + "From tblNotification "
                        + "Where email = ? ";
                pstm = cn.prepareStatement(sql);
                pstm.setString(1, email.trim());
                rs = pstm.executeQuery();
                while (rs.next()) {
                    tblNotificationDTO dto = new tblNotificationDTO(rs.getString("contentNotification"), rs.getString("email"));
                    list.add(dto);
                }
            }

        }finally{
            close();
        }
        Collections.reverse(list);
        return (list);
    }
    public int  insertNotification(String email , String content) throws CloneNotSupportedException ,NamingException , SQLException{
        int result = 0 ;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "Insert tblNotification Values(?,?) ";
                pstm = cn.prepareStatement(sql);
                pstm.setString(1, content.trim());
                pstm.setString(2, email.trim());
                result = pstm.executeUpdate();
                if(result>0){
                    System.out.println("update Notification done");
                }
            }

        }finally{
            close();
        }
        return result;
    }
}
