/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.xml;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ju.ehealthservice.converter.XMLToHashMap;

/**
 *
 * @author Stifler
 */
public class LoadFromXML extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            String fileName = request.getParameter("fileName");
        String ID = request.getParameter("id");
        HashMap<String, String> h = XMLToHashMap.convert(ID, fileName);
        out.println("<table>");
        out.println("<tr>");
        out.println("<th>Field</th>");
        out.println("<th>Value</th>");
        out.println("</tr>");
        for(Map.Entry<String, String> m : h.entrySet()) {
            if(!m.getKey().equals("PatientID")) {
                out.println("<tr>");
                out.println("<td>" + m.getKey() + "</td>");
                out.println("<td>" + m.getValue() + "</td>");
                out.println("</tr>");
            }
        }
        out.println("<tr>");
        out.println("<td style='text-align : center' colspan='2'>"
                + "<form name='generateReport' action='GenerateReport' method='post'  target='_blank'>"
                + "<input type='hidden' name='fileName' value='" + fileName + "'>"
                + "<input type='submit' value='Print'>");
        out.println("&nbsp;&nbsp;<input type='button' id='" + fileName.substring(0, fileName.length() - 4) 
                + "' value='View Graph' onclick='viewGraph(this)'>"
                + "&nbsp;&nbsp;<input type='button' id='" + fileName.substring(0, fileName.length() - 4) + ".txt' "
                + "onclick='xyz(this)' value='Remarks'></td>");
        out.println("</form>");
        out.println("</tr>");
        out.println("</table>");
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
