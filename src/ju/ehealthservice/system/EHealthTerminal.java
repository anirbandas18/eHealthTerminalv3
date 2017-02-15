/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.system;

import ju.ehealthservice.connections.XivelyFeeder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ju.ehealthservice.patient.Patient;
import ju.ehealthservice.connections.UDPReader;
import ju.ehealthservice.ecg.ECGRecorder;
import ju.ehealthservice.abnormality.Abnormality;
import ju.ehealthservice.connections.SerialSensorConnector;
import ju.ehealthservice.simulator.UDPGenerator;
import ju.ehealthservice.resprate.RespRecorder;
import ju.ehealthservice.simulator.AutoSerialSensor;
import ju.ehealthservice.utils.Constants;
import ju.ehealthservice.xml.MetadataIndex;

/**
 *
 * @author Stifler
 */
public class EHealthTerminal extends HttpServlet {
    
    
    
    public static Patient thePatient;  //the patient object
    public static AutoSerialSensor serialSensor; //serialConnection to the arduino sensors
    public static UDPReader udpSensor; //WiFI Connection to the arduino sensors
    public static XivelyFeeder xivelyFeeder;
    public static ECGRecorder ecgRecorder;
    public static RespRecorder respRecorder;
    public static HashMap<String, String> thresholdValues;
    public static Abnormality abnormality;
    public static MetadataIndex metadata;
    
    public static UDPGenerator udpGen;
    
    //public static AutoSerialSensor serialSensor;
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
        boolean status = DLL.loadLibrary(getServletContext());
        System.out.println("DLL : " + status); 
        if(status) {
            thePatient = new Patient();
            serialSensor = new AutoSerialSensor();
           // serialSensor = new SerialSensorConnector();
            udpSensor = new UDPReader();
            respRecorder = new RespRecorder( thePatient);
            ecgRecorder = new ECGRecorder(thePatient.EcgGraph, thePatient);
            xivelyFeeder = new XivelyFeeder();
            serialSensor.initialize(getServletContext());
            //serialSensor.initialize();
            udpSensor.initialiseUDP();        
            abnormality = new Abnormality();        
            thresholdValues = Threshold.loadValues(getServletContext());        
            metadata = new MetadataIndex(Constants.METADATA_INDEX_PATH);
            metadata.createFile();
            System.out.println("Started");
            udpGen = new UDPGenerator(getServletContext());
            udpGen.startGeneration();        
            getServletContext().getRequestDispatcher("/GUIMonitorPanel.jsp").forward 
                    (request, response); 
        } else {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<h1>Failed to load dll! System can't start</h1>");
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
