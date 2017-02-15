/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.resprate;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Deque;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ju.ehealthservice.resprate.RespPoint;
import ju.ehealthservice.system.EHealthTerminal;

/**
 *
 * @author Stifler
 */
public class RespRatePanel extends HttpServlet {

    static Deque<RespPoint> listOfRespPoints;
    
    public static void drawRespWith(Deque<RespPoint> l){
        listOfRespPoints = l;
    }
    
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
            /* TODO output your page here. You may use following sample code. */
            String Coordinates="";
            listOfRespPoints = EHealthTerminal.thePatient.RespGraph;
            if(listOfRespPoints.size() > 1){
                int first=0;
                if(!listOfRespPoints.isEmpty())
                    first = listOfRespPoints.getFirst().getXPix();
                //Iterator<RespPoint> ii = listOfRespPoints.iterator();
                int CurXpix=0,PreXpix=0;
                RespPoint previousPoint = null;
               for(RespPoint currentPoint : listOfRespPoints){
                    if(currentPoint.getXPix() >= first)
                        CurXpix = currentPoint.getXPix() - first;
                    else
                        CurXpix = (375 - first) + currentPoint.getXPix();
                    if(previousPoint != null){
                        if(previousPoint.getXPix() >= first)
                            PreXpix = previousPoint.getXPix() - first;
                        else
                            PreXpix = (375 - first) + previousPoint.getXPix();
                        Coordinates+= "("+PreXpix+","+(200 - (int)(previousPoint.getValue()))+"):"
                            +"("+CurXpix+","+(200 - (int)(currentPoint.getValue()))+")_";
                }
                previousPoint = currentPoint;
            }
        }
            if(Coordinates.length() != 0) {
                Coordinates = Integer.toString(listOfRespPoints.size() * 4) + " " 
                + Coordinates.substring(0, Coordinates.length()-1);
            } else {
                Coordinates = "4 (100,100):(150,50)_(200,100):(250,50)";
            }
        out.print(Coordinates);
        //out.print("");
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
