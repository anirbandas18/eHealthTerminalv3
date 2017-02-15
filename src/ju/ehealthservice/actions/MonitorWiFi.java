/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.actions;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import ju.ehealthservice.patient.Patient;
import ju.ehealthservice.system.EHealthTerminal;
import static ju.ehealthservice.system.EHealthTerminal.ecgRecorder;
import static ju.ehealthservice.system.EHealthTerminal.respRecorder;
import static ju.ehealthservice.system.EHealthTerminal.thePatient;
import static ju.ehealthservice.system.EHealthTerminal.udpSensor;
import static ju.ehealthservice.system.EHealthTerminal.xivelyFeeder;

/**
 *
 * @author Stifler
 */
public class MonitorWiFi extends HttpServlet {

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
        String monitoringWiFi = request.getParameter("monitoringWiFi");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if (monitoringWiFi.equalsIgnoreCase("Start Monitoring WiFi")) {
                udpSensor.setPatient(thePatient);
                udpSensor.startUDP();
                EHealthTerminal.abnormality.startMonitoring();
                xivelyFeeder.setPatient(thePatient);
                Patient.EcgGraph.clear();
                ecgRecorder.setEcgGraph(Patient.EcgGraph);
                ecgRecorder.startEcgRecording();
                Patient.RespGraph.clear();
                respRecorder.setRespGraph(Patient.RespGraph);
                respRecorder.startRespRecording();
                out.println("Monitoring via WiFi...");
            } else {
                //serialSensor.stopMonitoring();
                EHealthTerminal.abnormality.stopMonitoring();
                udpSensor.stopUDP();
                ecgRecorder.stopEcgRecording();
                respRecorder.stopRespRecording();
                out.println("Monitoring Stopped.");
            }
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
