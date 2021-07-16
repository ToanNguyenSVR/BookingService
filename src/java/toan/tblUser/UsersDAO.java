/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toan.tblUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import toan.utils.DBHelper;
import javax.naming.NamingException;

/**
 *
 * @author toann
 */
public class UsersDAO {

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

    public UserDTO checkLogin(String userID, String password) throws SQLException, NamingException {
        UserDTO user = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "SELECT userName, phone, password, address, userStatus , userRole , createDate, email "
                        + " From tblUser "
                        + "Where email = ? And password = ? ";
                pstm = cn.prepareStatement(sql);
                pstm.setString(1, userID);
                pstm.setString(2, password);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    user = new UserDTO(rs.getString("userName"), rs.getString("phone"), rs.getString("password"), rs.getString("address"),
                            rs.getString("email"), rs.getString("userStatus"), rs.getString("userRole"), rs.getDate("createDate"));
                }
            }
        } finally {
            close();
        }
        return user;
    }

    public boolean insertUser(UserDTO newUser) throws SQLException, NamingException {
        boolean flag = false;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "INSERT into tblUser(email, password , userName , userRole ,userStatus ,  phone, address, createDate)"
                        + "VALUES(?,?,?,?,?,?,?,?)";
                pstm = cn.prepareStatement(sql);
                pstm.setString(1, newUser.getEmail());
                pstm.setString(2, newUser.getPassword());
                pstm.setString(3, newUser.getName());
                pstm.setString(4, newUser.getRoleID());
                pstm.setString(5, newUser.getStatusID());
                pstm.setString(6, newUser.getPhone());
                pstm.setString(7, newUser.getAddress());
                pstm.setDate(8, newUser.getCreateDate());
                flag = pstm.executeUpdate() > 0;
            }
        } finally {
            close();
        }
        return flag;
    }

    public boolean updateUser(String email) throws SQLException, NamingException {
        boolean flag = false;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "update tblUser set userStatus = 'Active'"
                        + "where email = ?";
                pstm = cn.prepareStatement(sql);
                pstm.setString(1, email);
                flag = pstm.executeUpdate() > 0;
            }
        } finally {
            close();
        }
        return flag;
    }

    public boolean checkDuplicateEmail(String email) throws SQLException, NamingException {
        boolean flag = false;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "select email From [tblUser] "
                        + "where email = ?";
                pstm = cn.prepareStatement(sql);
                pstm.setString(1, email);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    flag = true;
                }
            }
        } finally {
            close();
        }
        return flag;
    }
}
