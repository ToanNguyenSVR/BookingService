/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toan.servlet;

import toan.tblRequest.tblRequestDTO;
import toan.tblRequest.tblRequestsDAO;
import toan.tblService.tblServiceDTO;
import toan.tblService.tblServiceDAO;
import toan.tblUser.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author toann
 */
@WebServlet(name = "BookingServlet", urlPatterns = {"/BookingServlet"})
public class BookingServlet extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        String url = "";
        boolean error = false;
        try {
            response.setContentType("text/html;charset=UTF-8");
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("USER");
            String emailUserLoginGG = (String) session.getAttribute("loginGG");
            ServletContext context = request.getServletContext();
            Map<String, String> listMapping = (Map<String, String>) context.getAttribute("PROPERTY");
            url = listMapping.get(SUCCESS);
            if (user == null && emailUserLoginGG == null) {
                url = FAIL;
            } else {
                String ServiceID = request.getParameter("ServiceID");
                String date = request.getParameter("date");
                System.out.println("date :" + date);
                if (date.equals("")) {
                    request.setAttribute("bookingFail", "Booking FAIL " + "Please Chose your date you want to book Service");
                } else {
                    tblRequestsDAO requestDAO = new tblRequestsDAO();
                    tblServiceDAO serviceDAO = new tblServiceDAO();
                    HttpSession sesion = request.getSession();
                    String emailLoginGG = (String) sesion.getAttribute("loginGG");
                    tblRequestDTO requestBooking = null;
                    tblServiceDTO service = serviceDAO.getDetailResource(ServiceID);
                    
                    // Chan quantity
                    if (service.getQuanlity() >= 1) {
                        if (user != null) {
                            requestBooking = new tblRequestDTO(0, "NEW", Date.valueOf(date), user.getEmail(), ServiceID);
                        }
                        if (emailLoginGG != null) {
                            requestBooking = new tblRequestDTO(0, "NEW", Date.valueOf(date), emailLoginGG, ServiceID);
                        }
                        boolean isBooking = requestDAO.bookingResource(requestBooking);
                        if (isBooking) {
                            request.setAttribute("bookingSuccess", "The  " + service.getServiceName() + " Service is Booked Please Wait the Admin Accpect to use it ");

                        } else {
                            request.setAttribute("bookingFail", "Booking FAIL " + ServiceID);
                        }
                    } else {
                        request.setAttribute("errBooking", "Please booking another resource " + ServiceID);
                        request.setAttribute("idProductOutOfNumber", service.getServiceID());
                    }
                    request.setAttribute("ServiceID", ServiceID);
                }
            }
        } catch (NamingException e) {
            log("BookingServlet_NamingException" + e.getMessage());
        } catch (SQLException e) {
            log("BookingServlet_SQLException" + e.getMessage());
        }catch (Exception e) {
            log("BookingServlet_Exception" + e.getMessage());
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
