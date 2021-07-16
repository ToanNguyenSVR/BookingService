/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toan.tblCategory;

import java.io.Serializable;
import java.sql.Connection;
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
public class tblCategoryDAO implements Serializable{
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

    public ArrayList<tblCategoryDTO> getAllListCategories() throws SQLException , NamingException {
        ArrayList<tblCategoryDTO> listCategories = new ArrayList<>();
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "select categoryID, categoryName from tblcategory";
                pstm = cn.prepareStatement(sql);
                rs = pstm.executeQuery();
                
                while (rs.next()) {
                    listCategories.add(new tblCategoryDTO(rs.getString("categoryID"), rs.getString("categoryName")));              
                }
            }
        }  finally {
            close();
        }
        return listCategories;
    }
}
