/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toan.servlet;

import toan.tblService.tblServiceDTO;
import toan.tblService.tblServiceDAO;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;


import javax.naming.NamingException;
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
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    private String SUCCESS = "UserPage";
    private String FAIL = "UserPage";

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
        String url = SUCCESS;
        try {
            // search category
            HttpSession session = request.getSession();
            
                String categorySelect = request.getParameter("category");
                String index = request.getParameter("index");
                String nameSearch = request.getParameter("nameSearch");
                
                if (nameSearch == null) {
                    nameSearch = "";
                }
                if (categorySelect == null) {
                    categorySelect = "";
                }
                if (categorySelect != null) {
                    if (categorySelect.equalsIgnoreCase("default")) {
                        categorySelect = "";
                    }
                }
                tblServiceDAO servicedao = new tblServiceDAO();
//                CategoriesDAO categoriesDAO = new CategoriesDAO();
                

                ArrayList<tblServiceDTO> list = servicedao.searchService(nameSearch, categorySelect);
                if (list != null) {

                    session.setAttribute("SERVICE", list);
                    System.out.println(list.toString());
                } else {
                    String s = "Dont have the service :" +nameSearch ;
                    request.setAttribute("MESSES", s);
                }
            
                if (categorySelect.isEmpty()) {
                    request.setAttribute("categorySelect", "default");
                } else {
                    request.setAttribute("categorySelect", categorySelect);
                }
                request.setAttribute("category", categorySelect);
                request.setAttribute("nameSearch", nameSearch);
//                request.setAttribute("listCategories", categoriesDAO.getAllListCategories());
            
        } catch (SQLException e) {
            log("SearchServlet_Err"+ e.getMessage());
        } catch (NamingException e) {
            log("SearchServlet_Err"+ e.getMessage());
        } finally {
            response.sendRedirect(url);
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
