/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toan.servlet;

import toan.tblUser.UserDTO;
import toan.tblUser.UsersDAO;
import toan.utils.GooglePojo;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import toan.utils.GoogleUtils;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import toan.utils.MyConstants;

/**
 *
 * @author toann
 */
@WebServlet(name = "LoginGoogleServlet", urlPatterns = {"/LoginGoogleServlet"})
public class LoginGoogleServlet extends HttpServlet {

    private String SUCCESS = "LoadService";
    private String FAIL = "index";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
         PrintWriter out = response.getWriter();
        ServletContext context = request.getServletContext();
        Map<String, String> listMapping = (Map<String, String>) context.getAttribute("PROPERTY");
        String url = listMapping.get(SUCCESS);
        String code = request.getParameter("code");
        HttpSession session = request.getSession();
        if (code == null || code.isEmpty()) {
            url = listMapping.get(FAIL);
        } else {
            try {
                String accessToken = GoogleUtils.getToken(code);
                GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);
                request.setAttribute("id", googlePojo.getId());
                request.setAttribute("name", googlePojo.getName());
                request.setAttribute("email", googlePojo.getEmail());
                UsersDAO userDAO = new UsersDAO();
                if (googlePojo.getEmail() != null) {
                    boolean checkExistAccount = userDAO.checkDuplicateEmail(googlePojo.getEmail());
                    if (!checkExistAccount) {
                        boolean isAdd = userDAO.insertUser(new UserDTO("", "", "", "", googlePojo.getEmail(), MyConstants.STATUS_ACCOUNT_ACTIVE, MyConstants.ROLE_ACCOUNT_USER, Date.valueOf(java.time.LocalDate.now())));
                    }
                    session.setAttribute("loginGG", googlePojo.getEmail());
                    request.setAttribute("email", googlePojo.getEmail());
                }
            } catch (SQLException e) {
                log("LoginGoogleServlet_SQLException" + e.getMessage());
            } catch (NamingException e) {
                log("LoginGoogleServlet_NamingException" + e.getMessage());
            } finally {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
                out.close();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
