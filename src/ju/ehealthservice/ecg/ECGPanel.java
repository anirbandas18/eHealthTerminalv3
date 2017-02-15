/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.ecg;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Deque;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ju.ehealthservice.system.EHealthTerminal;

/**
 *
 * @author Stifler
 */
public class ECGPanel extends HttpServlet {

    private Deque<EcgPoint> listOfEcgPoints;
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
            //listOfEcgPoints = (Deque<EcgPoint>)request.getAttribute("listOfEcgPoints");
            int first=0;
            String Coordinates="";
            listOfEcgPoints = EHealthTerminal.thePatient.EcgGraph;
            if(!listOfEcgPoints.isEmpty())
                first = listOfEcgPoints.getFirst().eightmsXPix;
            //Iterator<EcgPoint> ii = listOfEcgPoints.iterator();
            int CurXpix=0,PreXpix=0;
            EcgPoint previousPoint = null;
            for(EcgPoint currentPoint : listOfEcgPoints){
                //EcgPoint currentPoint = ii.next();
                if(currentPoint.eightmsXPix >= first)
                    CurXpix = currentPoint.eightmsXPix - first;
                else
                    CurXpix = (375 - first) + currentPoint.eightmsXPix;
                if(previousPoint != null){
                    if(previousPoint.eightmsXPix >= first)
                        PreXpix = previousPoint.eightmsXPix - first;
                    else
                        PreXpix = (375 - first) + previousPoint.eightmsXPix;
                    Coordinates+= "("+PreXpix+","+(200 - (int)(previousPoint.volt*40))+"):"
                            +"("+CurXpix+","+(200 - (int)(currentPoint.volt*40))+")_";
            }
            previousPoint = currentPoint;
        }
            if(Coordinates.length() != 0) {
                Coordinates = Integer.toString(listOfEcgPoints.size() * 4) + " " 
                + Coordinates.substring(0, Coordinates.length()-1);
            } else {
                Coordinates = "4 (100,100):(150,50)_(200,100):(250,50)";
            }
        
        out.print(Coordinates);
        //out.print("4 (100,100):(150,50)_(200,100):(250,50)");
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
