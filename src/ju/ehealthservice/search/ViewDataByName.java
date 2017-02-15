/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.search;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static ju.ehealthservice.system.EHealthTerminal.metadata;
import ju.ehealthservice.beans.HealthService;

/**
 *
 * @author Stifler
 */
public class ViewDataByName extends HttpServlet {

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
        String from = "";
        String to = "";
        int time = 0;
        String Name = request.getParameter("patient_name");
        String field = request.getParameter("field");
        ArrayList<HealthService> ob = metadata.getData(Name);
        int l = ob.size();
        if(l > 0) {
            if(field.compareTo("from_to") == 0) {
                from = request.getParameter("from");
                to = request.getParameter("to");
                request.setAttribute("from", from);//
                request.setAttribute("to", to);//
            } else if(field.compareTo("show_all") == 0) {
                //do nothing
            } else {
                time = Integer.parseInt(request.getParameter("time_int"));
                request.setAttribute("time", time);//
            }
            request.setAttribute("field", field);//
            if(l == 1) {
                HealthService hs = ob.get(0);
                Search results = new Search(hs.getID());
                if(field.compareTo("from_to") == 0) {
                    request.setAttribute("list", results.getFileNames(from, to));
                } else if(field.compareTo("show_all") == 0) {
                    request.setAttribute("list", results.getFileNames());
                } else {
                    request.setAttribute("list", results.getFileNames(time, field));
                }
                request.setAttribute("patient", hs);//
                getServletContext().getRequestDispatcher("/Readings.jsp").forward 
                    (request, response); 
            }
        }
        request.setAttribute("patient_list", ob);//
        request.setAttribute("name", Name);//
        getServletContext().getRequestDispatcher("/Results.jsp").forward(request, response); 
    
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
