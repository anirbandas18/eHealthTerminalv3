/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.search;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static ju.ehealthservice.system.EHealthTerminal.metadata;
import ju.ehealthservice.beans.HealthService;
import ju.ehealthservice.utils.Constants;

/**
 *
 * @author Stifler
 */
public class ViewDataByID extends HttpServlet {

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
        int ID = Integer.parseInt(request.getParameter("patient_id"));
        String field = request.getParameter("field");
        Search results = new Search(ID);
        if(field.compareTo("from_to") == 0) {
            String from = request.getParameter("from");
            String to = request.getParameter("to");
            request.setAttribute("from", from);
            request.setAttribute("to", to);
            request.setAttribute("list", results.getFileNames(from, to));
        } else if(field.compareTo("show_all") == 0) {
            request.setAttribute("list", results.getFileNames());
        } else {
            int time = Integer.parseInt(request.getParameter("time_int"));
            request.setAttribute("time", time);
            request.setAttribute("list", results.getFileNames(time, field));
        }
        request.setAttribute("field", field);
        HealthService ob = new HealthService();
        if(metadata.searchData(ID)) {
            ob = metadata.getData(ID);
        }
        request.setAttribute("patient", ob);
        if(ob.isImage()) {
             request.setAttribute("image", Constants.IMAGE_REPOSITORY_PATH + ID + ".jpg");
        } else {
             request.setAttribute("image", Constants.DEFAULT_IMAGE_PATH);
        }
        getServletContext().getRequestDispatcher("/Readings.jsp").forward 
           (request, response); 
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
