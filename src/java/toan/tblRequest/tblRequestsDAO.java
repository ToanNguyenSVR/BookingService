/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toan.tblRequest;

import java.io.Serializable;
import toan.utils.DBHelper;
import toan.utils.MyConstants;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author toann
 */
public class tblRequestsDAO implements Serializable{

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

    public boolean bookingResource(tblRequestDTO requestBooking) throws NamingException, SQLException {
        boolean isBook = false;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "insert  into tblRequest(dateBook, statusReq, email, serviceID) values(?,?,?,?)";
                pstm = cn.prepareStatement(sql);
                pstm.setDate(1, requestBooking.getDateBook());
                pstm.setString(2, requestBooking.getStatusReq());
                pstm.setString(3, requestBooking.getEmail());
                pstm.setString(4, requestBooking.getServiceID());
                isBook = pstm.executeUpdate() > 0;
            }
        } finally {
            close();
        }
        return isBook;
    }

    public boolean updateStatusRequest(int requestID , String Status) throws NamingException, SQLException {
        boolean flag = false;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "update tblrequest set statusReq = ? "
                        + "where requestID = ?";
                pstm = cn.prepareStatement(sql);

                pstm.setString(1, Status);
                pstm.setInt(2, requestID);
                flag = pstm.executeUpdate() > 0 ? true : false;
            }
        } finally {
            close();
        }
        return flag;
    }

    public List<tblRequestDTO> getListBookingWAIT() throws NamingException, SQLException {
        int count = 0;
         tblRequestDTO request ;
         List<tblRequestDTO> list = new ArrayList<>() ;
        try {

            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "select requestID, dateBook, statusReq, email, serviceID "
                        + "from tblrequest "
                        + " Where statusReq = 'NEW'";
                pstm = cn.prepareStatement(sql);

                rs = pstm.executeQuery();
                while (rs.next()) {
                    request = new tblRequestDTO(rs.getInt("requestID"), rs.getString("statusReq"), rs.getDate("dateBook"), rs.getString("email"), rs.getString("serviceID"));
                    list.add(request);
                    
                }
            }
        } finally {
            close();
        }
        return list;
    }
    public List<tblRequestDTO> getListBookingHistory() throws NamingException, SQLException {
        int count = 0;
         tblRequestDTO request ;
         List<tblRequestDTO> list = new ArrayList<>() ;
        try {

            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "select requestID, dateBook, statusReq, email, serviceID "
                        + "from tblrequest "
                         + " Where statusReq != 'NEW'";
                pstm = cn.prepareStatement(sql);

                rs = pstm.executeQuery();
                while (rs.next()) {
                    request = new tblRequestDTO(rs.getInt("requestID"), rs.getString("statusReq"), rs.getDate("dateBook"), rs.getString("email"), rs.getString("serviceID"));
                    list.add(request);
                    
                }
            }
        } finally {
            close();
        }
        return list;
    }

    public tblRequestDTO getDetailRequest(int requestID) throws SQLException, NamingException {
        tblRequestDTO request = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "select requestID, dateBook, statusReq, email, serviceID "
                        + "from tblrequest where requestID = ? ";
                pstm = cn.prepareStatement(sql);
                pstm.setInt(1, requestID);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    request = new tblRequestDTO(rs.getInt("requestID"), rs.getString("statusReq"), rs.getDate("dateBook"), rs.getString("email"), rs.getString("serviceID"));
                
                }
            }
        } finally {
            close();
        }
        return request;
    }
    public ArrayList<tblRequestDTO> getRequestByUser(String email ) throws SQLException, NamingException {
        tblRequestDTO request = null;
         ArrayList<tblRequestDTO> list = new ArrayList<>();
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "select requestID, dateBook, statusReq, email, serviceID "
                        + "from tblrequest where email = ? ";
                pstm = cn.prepareStatement(sql);
                pstm.setString(1, email.trim());
                rs = pstm.executeQuery();
                while (rs.next()) {
                    request = new tblRequestDTO(rs.getInt("requestID"), rs.getString("statusReq"), rs.getDate("dateBook"), rs.getString("email"), rs.getString("serviceID"));
                    list.add(request);
                }
            }
        } finally {
            close();
        }
        return list;
    }
    public ArrayList<tblRequestDTO> getRequestByDate(String serviceID , Date date) throws SQLException, NamingException {
        tblRequestDTO request = null;
         ArrayList<tblRequestDTO> list = new ArrayList<>();
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "select requestID, dateBook, statusReq, email, serviceID "
                        + "from tblrequest where serviceID = ? and dateBook = ? and  statusReq = 'NEW       '";
                pstm = cn.prepareStatement(sql);
                pstm.setString(1, serviceID);
                pstm.setDate(2, date);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    request = new tblRequestDTO(rs.getInt("requestID"), rs.getString("statusReq"), rs.getDate("dateBook"), rs.getString("email"), rs.getString("serviceID"));
                    list.add(request);
                }
            }
        } finally {
            close();
        }
        return list;
    }
    public ArrayList<tblRequestDTO> searchRequestByDate( Date date) throws SQLException, NamingException {
        tblRequestDTO request = null;
         ArrayList<tblRequestDTO> list = new ArrayList<>();
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "select requestID, dateBook, statusReq, email, serviceID "
                        + "from tblrequest where dateBook = ? and statusReq='NEW'";
                pstm = cn.prepareStatement(sql);
                
                pstm.setDate(1, date);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    request = new tblRequestDTO(rs.getInt("requestID"), rs.getString("statusReq"), rs.getDate("dateBook"), rs.getString("email"), rs.getString("serviceID"));
                    list.add(request);
                }
            }
        } finally {
            close();
        }
        return list;
    }
    public ArrayList<tblRequestDTO> searchRequestByDate( Date date , String email) throws SQLException, NamingException {
        tblRequestDTO request = null;
         ArrayList<tblRequestDTO> list = new ArrayList<>();
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "select requestID, dateBook, statusReq, email, serviceID "
                        + "from tblrequest where dateBook = ? and  email = ?";
                pstm = cn.prepareStatement(sql);
                
                pstm.setDate(1, date);
                pstm.setString(2, email);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    request = new tblRequestDTO(rs.getInt("requestID"), rs.getString("statusReq"), rs.getDate("dateBook"), rs.getString("email"), rs.getString("serviceID"));
                    list.add(request);
                }
            }
        } finally {
            close();
        }
        return list;
    }
}

//select requestID, dateBook, statusReqID, email, productID
//                    from Requests where requestID = 14
