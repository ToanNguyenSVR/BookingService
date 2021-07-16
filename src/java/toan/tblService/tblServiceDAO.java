/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toan.tblService;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;
import toan.tblBokingByDate.tblBookingByDateDTO;
import toan.utils.DBHelper;

/**
 *
 * @author toann
 */
public class tblServiceDAO implements Serializable {

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

    public ArrayList<tblServiceDTO> getAllListReources() throws SQLException, NamingException {
        ArrayList<tblServiceDTO> listProducts = new ArrayList<>();
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "select serviceID, serviceName, color, categoryID, quanlity , createDate from tblService ";
                pstm = cn.prepareStatement(sql);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    listProducts.add(new tblServiceDTO(rs.getString("serviceID"), rs.getString("serviceName"), rs.getString("color"), rs.getString("categoryID"), rs.getInt("quanlity"), rs.getDate("createDate")));
                }
            }
        } finally {
            close();
        }
        return listProducts;
    }

    public ArrayList<tblServiceDTO> searchService(String serviceName, String categoryID) throws SQLException, NamingException {

        ArrayList<tblServiceDTO> listProducts = new ArrayList<>();
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String condition = "";
                if (categoryID != null && !categoryID.isEmpty()) {
                    condition = "and categoryID = ? ";
                }

                String sql = "select serviceID, serviceName, color, categoryID, quanlity , createDate from tblService Where serviceName Like ? " + condition;
                pstm = cn.prepareStatement(sql);
                pstm.setString(1, "%" + serviceName + "%");
                if (categoryID != null && !categoryID.isEmpty()) {
                    pstm.setString(2, categoryID);
                }

                rs = pstm.executeQuery();
                while (rs.next()) {
                    listProducts.add(new tblServiceDTO(rs.getString("serviceID"), rs.getString("serviceName"), rs.getString("color"), rs.getString("categoryID"), rs.getInt("quanlity"), rs.getDate("createDate")));
                }
            }
        } finally {
            close();
        }
        return listProducts;
    }

    public ArrayList<tblServiceDTO> searchByDayService(Date date) throws SQLException, NamingException {

        ArrayList<tblServiceDTO> listProducts = new ArrayList<>();
        ArrayList<tblBookingByDateDTO> listBooking = new ArrayList<>();
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {

                String sql = "select serviceID, quantityService , dateBooking\n"
                        + "from tblBookingByDate \n"
                        + "where dateBooking = ? ";
                pstm = cn.prepareStatement(sql);
                pstm.setDate(1, date);

                rs = pstm.executeQuery();
                while (rs.next()) {
                    listBooking.add(new tblBookingByDateDTO(rs.getString("serviceID"), null,rs.getInt("quantityService"), rs.getDate("dateBooking")));
                }
                // tức có nghĩa ngày đó ko có ai đặt lịch 
                if(listBooking.size() < 1){
                    listProducts = getAllListReources();
                }else{
                    listProducts = getAllListReources();
                    if(listProducts != null ){
                        for (tblServiceDTO listProduct : listProducts) {
                            for (tblBookingByDateDTO bookingByDateDTO : listBooking) {
                                if(bookingByDateDTO.getServiceID().equals(listProduct.getServiceID())){
                                    int quantity = listProduct.getQuanlity() - bookingByDateDTO.getQuantity();
                                    if(quantity < 0 ){
                                        quantity = 0;
                                    }
                                    listProduct.setQuanlity(quantity);
                                }
                            }
                        }
                    }
                }
                
            }
        } finally {
            close();
        }
        return listProducts;
    }

    public tblServiceDTO getDetailResource(String resourceID) throws NamingException, SQLException {
        tblServiceDTO resouce = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "select serviceID, serviceName, color, categoryID, quanlity, createDate from tblService where serviceID = ? ";
                pstm = cn.prepareStatement(sql);
                pstm.setString(1, resourceID);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    resouce = new tblServiceDTO(rs.getString("serviceID"), rs.getString("serviceName"), rs.getString("color"), rs.getString("categoryID"), rs.getInt("quanlity"), rs.getDate("createDate"));
                }
            }
        } finally {
            close();
        }
        return resouce;
    }

    public boolean updateQuanityService(String productID, int quanity) throws SQLException, NamingException {
        boolean flag = false;
        int result = 0;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "update tblService SET quanlity = ? "
                        + "where serviceID = ? ";
                pstm = cn.prepareStatement(sql);
                pstm.setInt(1, quanity);
                pstm.setString(2, productID);
                result = pstm.executeUpdate();
                if (result > 0) {
                    flag = true;
                }
            }
        } finally {
            close();
        }
        return flag;
    }
}
