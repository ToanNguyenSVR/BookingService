/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toan.listener;

import com.sun.activation.registries.LogSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.ws.spi.http.HttpContext;
import jdk.nashorn.internal.runtime.regexp.joni.Config;

/**
 *
 * @author toann
 */
public class ContextListener implements ServletContextListener {

    private final String PROPERTY_FILE = "/WEB-INF/Property.txt";
    private final String USER_ACTION = "/WEB-INF/UserAction.txt";
    private final String ADMIN_ACTION = "/WEB-INF/AdminAction.txt";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ServletContext context = sce.getServletContext();
            String path = context.getRealPath("/");
            Map<String, String> property = readProperty(path + PROPERTY_FILE);
         
            if (property != null) {
                context.setAttribute("PROPERTY", property);
            }
            List<String> actionUser = readUserAction(path + USER_ACTION);
            if (actionUser != null) {
                context.setAttribute("ACTIONUSER", actionUser);
            }
            List<String> actionAdmin = readUserAction(path + ADMIN_ACTION);
            if (actionAdmin != null) {
                context.setAttribute("ACTIONADMIN", actionAdmin);
            }
          
        }catch(IOException e){
            LogSupport.log("contextInitialized" +e.getMessage());
        }
    }

    public Map<String, String> readProperty(String fileName) throws IOException , FileNotFoundException{
        FileReader f = null;
        BufferedReader br = null;
        Map<String, String> property = null;
        try {
            f = new FileReader(fileName);
            br = new BufferedReader(f);

            while (br.ready()) {
                String[] value = br.readLine().split("=");
                if (value.length == 2) {
                    if (property == null) {
                        property = new HashMap<>();
                    }
                    property.put(value[0], value[1]);
                }
            }
        }finally{
            if(br!=null){
                br.close();
            }
            if(f!=null){
                f.close();
            }
        } 
        return property;
    }

    public List<String> readUserAction(String fileName) throws FileNotFoundException , IOException{
        FileReader f = null;
        BufferedReader br = null;
        List<String> action = null;
        try {
            f = new FileReader(fileName);
            br = new BufferedReader(f);

            while (br.ready()) {
                String value = br.readLine();
                if (value != null) {
                    if (action == null) {
                        action = new ArrayList<>();
                    }
                    action.add(value);
                   
                }

            }
        }finally{
            if(br!=null){
                br.close();
            }
            if(f!=null){
                f.close();
            }
        } 
        
        return action;
    }
     public List<String> readAdminAction(String fileName) throws FileNotFoundException , IOException{
        FileReader f = null;
        BufferedReader br = null;
        List<String> action = null;
        try {
            f = new FileReader(fileName);
            br = new BufferedReader(f);

            while (br.ready()) {
                String value = br.readLine();
                if (value != null) {
                    if (action == null) {
                        action = new ArrayList<>();
                    }
                    action.add(value);
                   
                }

            }
        }finally{
            if(br!=null){
                br.close();
            }
            if(f!=null){
                f.close();
            }
        } 
        
        return action;
    }

    

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
       

    }

}
