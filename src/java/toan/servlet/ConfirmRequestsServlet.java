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

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;
import java.util.ArrayList;
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
import toan.tblBokingByDate.tblBookingByDateDAO;
import toan.tblBokingByDate.tblBookingByDateDTO;
import toan.tblNotification.tblNotifiactionDAO;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ConfirmRequestsServlet", urlPatterns = {"/ConfirmRequestsServlet"})
public class ConfirmRequestsServlet extends HttpServlet {

    public final String LOAD = "LoadRequest";

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
        String url = listMapping.get(LOAD);
        try {

            String requestID = request.getParameter("requestID");
            String email = request.getParameter("email");
            String status = request.getParameter("statusConfig");
            String serviceID = request.getParameter("serviceID");

            tblRequestsDAO requestDAO = new tblRequestsDAO();
            tblServiceDAO serviceDAO = new tblServiceDAO();
            tblBookingByDateDAO bookingDateDAO = new tblBookingByDateDAO();

            tblServiceDTO service = serviceDAO.getDetailResource(serviceID);
            tblRequestDTO requestDetail = requestDAO.getDetailRequest(Integer.parseInt(requestID));
            tblBookingByDateDTO bookingDate = bookingDateDAO.searchBookingDay(service.getServiceID(), requestDetail.getDateBook());
            if (bookingDate != null) {
                int quantityAvailable = service.getQuanlity() - bookingDate.getQuantity();

                if (quantityAvailable > 0) {
                    boolean isStatusActive = requestDAO.updateStatusRequest(Integer.parseInt(requestID), status);
                    boolean isUpdated = bookingDateDAO.updateQuantity(bookingDate.getQuantity() + 1, service.getServiceID());
                    if ((quantityAvailable - 1) == 0) {
                        ArrayList<tblRequestDTO> requestList = requestDAO.getRequestByDate(service.getServiceID(), requestDetail.getDateBook());
                        for (tblRequestDTO requestDto : requestList) {
                            boolean deleted = requestDAO.updateStatusRequest(requestDto.getRequestID(), "Cancel");
                            if (deleted) {
                                tblNotifiactionDAO notificationDao = new tblNotifiactionDAO();
                                int result = notificationDao.insertNotification(requestDto.getEmail(), "The Service" + service.getServiceName() + "is Cancel you can chosse order ");
                            }
                        }
                    }
                    if (isStatusActive && isUpdated) {
                        request.setAttribute("successConfirm", "Confirm successfully rquestID " + requestID);
                        tblNotifiactionDAO notificationDao = new tblNotifiactionDAO();
                        int result = notificationDao.insertNotification(email, "The Service" + service.getServiceName() + "is Accepted you can use it ");
                        if (result > 0) {
                            request.setAttribute("successConfirm", "Confirm successfully rquestID " + requestID);

                        }

                    }
                } else {
                    request.setAttribute("errorConfirm", "Sorry quanity resource " + requestID + " = " + service.getQuanlity());
                }
            } else {
               
                tblBookingByDateDTO bookingDateNew = new tblBookingByDateDTO(serviceID, null, 1, requestDetail.getDateBook());
                int resultBookingDate = bookingDateDAO.insertBookingDate(bookingDateNew);
                boolean isStatusActive = requestDAO.updateStatusRequest(Integer.parseInt(requestID), status);
                if ((service.getQuanlity() - 1) == 0) {
                    ArrayList<tblRequestDTO> requestList = requestDAO.getRequestByDate(service.getServiceID(), requestDetail.getDateBook());
                    for (tblRequestDTO requestDto : requestList) {
                        boolean deleted = requestDAO.updateStatusRequest(requestDto.getRequestID(), "Cancel");
                        if (deleted) {
                            tblNotifiactionDAO notificationDao = new tblNotifiactionDAO();
                            int result = notificationDao.insertNotification(requestDto.getEmail(), "The Service" + service.getServiceName() + "is Cancel you can chosse order ");
                        }
                    }
                }
                if (resultBookingDate > 0 && isStatusActive) {
                    request.setAttribute("successConfirm", "Confirm successfully rquestID " + requestID);
                    tblNotifiactionDAO notificationDao = new tblNotifiactionDAO();
                    int resultNotification = notificationDao.insertNotification(email, "The Service" + service.getServiceName() + "is Accepted you can use it ");
                    if (resultNotification > 0) {
                        request.setAttribute("successConfirm", "Confirm successfully rquestID " + requestID);

                    }

                }
            }

            // 
        } catch (NamingException e) {
            log("ConfirmRequestsServlet_NamingException" + e.getMessage());
        } catch (SQLException e) {
            log("ConfirmRequestsServlet_SQLException" + e.getMessage());
        } catch (CloneNotSupportedException e) {
            log("ConfirmRequestsServlet_SQLException" + e.getMessage());
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
