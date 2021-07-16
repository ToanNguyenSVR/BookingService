/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toan.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;
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
import toan.tblUser.UsersDAO;
import toan.tblUser.UserDTO;
import toan.utils.MyConstants;

/**
 *
 * @author toann
 */
@WebServlet(name = "StarUpServlet", urlPatterns = {"/StarUpServlet"})
public class StarUpServlet extends HttpServlet {

    public final String USER_PAGE = "LoadService";
    public final String ADMIN_PAGE = "LoadRequest";
    public final String LOGIN_PAGE = "index";

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
        String url = listMapping.get(LOGIN_PAGE);
        try {
            Cookie cookies[] = request.getCookies();
            if (cookies != null) {
                String email = null;
                String password = null;
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("EMAIL")) {
                        email = cookie.getValue();
                    }
                    if (cookie.getName().equals("PASSWORD")) {
                        password = cookie.getValue();
                    }
                }
                if (email != null || password != null) {
                    UsersDAO dao = new UsersDAO();
                    UserDTO dto = dao.checkLogin(email, password);
                    if (dto != null) {

                        HttpSession session = request.getSession();
                        if (dto.getRoleID().trim().equals(MyConstants.ROLE_ACCOUNT_ADMIN)) {
                            session.setAttribute("USER_ADMIN", dto);
                            request.setAttribute("email", dto.getEmail());
                            url = listMapping.get(ADMIN_PAGE);
                        }
                        if (dto.getRoleID().trim().equals(MyConstants.ROLE_ACCOUNT_USER)) {
                            session.setAttribute("USER", dto);
                            request.setAttribute("email",email);
                            url = listMapping.get(USER_PAGE);
                        }
                    }
                }
            }

        } catch (NamingException e) {
            log("NamingException" + e.getMessage());
        } catch (SQLException e) {
            log("SQLException" + e.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
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
