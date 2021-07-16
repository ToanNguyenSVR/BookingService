/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toan.servlet;

import toan.utils.VerifyUtils;
import toan.tblUser.UserDTO;
import toan.tblUser.UsersDAO;
import toan.utils.MyConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author toann
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private String URL_ADMIN = "LoadRequest";
    private String FAIL = "index";
    private String URL_USER = "LoadService";

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
         PrintWriter out = response.getWriter();
        ServletContext context = request.getServletContext();
        Map<String, String> listMapping = (Map<String, String>) context.getAttribute("PROPERTY");
        String url = FAIL;
        try {
            response.setContentType("text/html;charset=UTF-8");
            String email = request.getParameter("email").trim();
            String password = request.getParameter("password").trim();
            UsersDAO userDAO = new UsersDAO();
            UserDTO user = userDAO.checkLogin(email, password);
            HttpSession session = request.getSession();
            boolean valid = false;
            boolean checkCapCha = false;
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");

            request.setAttribute("valueEmail", email);
            request.setAttribute("valuePW", password);
            if (email.isEmpty()) {
                valid = true;
                request.setAttribute("email", "email is empty");
            }
            if (password.isEmpty()) {
                valid = true;
                request.setAttribute("password", "password is empty");
            }
            // Verify CAPTCHA.
            checkCapCha = VerifyUtils.verify(gRecaptchaResponse);
            if (!checkCapCha) {
                valid = true;
                request.setAttribute("errorCapCha", "Please choosen capCha");
            }
            if (!valid) {
                if (user != null) {
                    if (user.getStatusID().equals(MyConstants.STATUS_ACCOUNT_NEW)) {
                        request.setAttribute("unConfirmedEmail", "Please confirm email before login");
                    } else {
                        Cookie userCookie = new Cookie("EMAIL", user.getEmail());
                        userCookie.setMaxAge(60 * 60 * 5);
                        Cookie passwordCookie = new Cookie("PASSWORD", user.getPassword());
                        passwordCookie.setMaxAge(60 * 60 * 5);
                        response.addCookie(userCookie);
                        response.addCookie(passwordCookie);
                        if (user.getRoleID().trim().equals(MyConstants.ROLE_ACCOUNT_USER)) {
                            url = URL_USER;
                            session.setAttribute("USER", user);
                            

                        } else {
                            url = URL_ADMIN;
                            session.setAttribute("USER_ADMIN", user);
                        }
                    }
                } else {
                    request.setAttribute("error", "user or password wrong");
                }
            }
        } catch (SQLException ex) {
            log("LoginServlet_SQLException" + ex.getMessage());
        } catch (NamingException ex) {
            log("LoginServlet_SQLException" + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(listMapping.get(url));
            rd.forward(request, response);
            out.close();
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
