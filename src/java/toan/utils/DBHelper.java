/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toan.utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author toann
 */
public class DBHelper {

    public static Connection makeConnection() throws NamingException, SQLException {
        Context context = new InitialContext();
        Context tomcat = (Context) context.lookup("java:comp/env");
        DataSource dataSource = (DataSource)tomcat.lookup("DB");
        Connection con = dataSource.getConnection();
        return con;
        
    }

}
