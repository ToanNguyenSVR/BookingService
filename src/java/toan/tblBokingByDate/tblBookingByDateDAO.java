/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toan.tblBokingByDate;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;
import toan.utils.DBHelper;

/**
 *
 * @author toann
 */
public class tblBookingByDateDAO implements Serializable {

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

    public tblBookingByDateDTO searchBookingDay(String serviceID , Date date) throws SQLException, NamingException {
        tblBookingByDateDTO BookingByDate = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "select serviceID , quantityService , dateBooking\n"
                        + "from tblBookingByDate  \n"
                        + "where dateBooking = ? and serviceID = ?";
                pstm = cn.prepareStatement(sql);
                pstm.setDate(1, date);
                pstm.setString(2, serviceID);
                rs = pstm.executeQuery();

                while (rs.next()) {
                    BookingByDate = new  tblBookingByDateDTO(rs.getString("serviceID"), null ,rs.getInt("quantityService"), rs.getDate("dateBooking"));
                }
            }
        } finally {
            close();
        }
        return BookingByDate;
    }
    public boolean updateQuantity(int quantity , String serviceID) throws NamingException , SQLException{
        boolean result = false;
        int res = 0 ;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "update tblBookingByDate set quantityService = ? "
                        + "where serviceID = ?";
                pstm = cn.prepareStatement(sql);
                pstm.setInt(1, quantity);
                pstm.setString(2, serviceID);
                res = pstm.executeUpdate();
                if(res > 0 ){
                    result = true;
                }
                
            }
        } finally {
            close();
        }
        return result;
    }
    public int  insertBookingDate(tblBookingByDateDTO dto) throws NamingException , SQLException{
        int result = 0 ;
        try {
            cn = DBHelper.makeConnection();
            
            if (cn != null) {
                String sql = "Insert tblBookingByDate(serviceID , quantityService , dateBooking) Values(?,?,?) ";
                pstm = cn.prepareStatement(sql);
                pstm.setString(1, dto.getServiceID());
                pstm.setInt(2, dto.getQuantity());
                pstm.setDate(3,dto.getDateBooking());
                result = pstm.executeUpdate();
                if(result>0){
                    System.out.println("update BookingDate done");
                }
            }

        }finally{
            close();
        }
        return result;
    }
}
