/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.xml;

import ju.ehealthservice.beans.HealthService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ju.ehealthservice.converter.HashMapToXML;
import ju.ehealthservice.graphs.ECGGraphImage;
import ju.ehealthservice.graphs.RespRateGraphImage;
import ju.ehealthservice.utils.Constants;
import ju.ehealthservice.utils.DirectoryHandler;
import static ju.ehealthservice.system.EHealthTerminal.metadata;

/**
 *
 * @author Stifler
 */
public class SaveAsXML extends HttpServlet {

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
            HashMap<String, String> values = buildHashMap(request);
            HealthService ob = new HealthService();
            ob.setID(Integer.parseInt(values.get("ID")));
            ob.setName(request.getParameter("name"));
            ob.setAge(Integer.parseInt(request.getParameter("age")));
            ob.setMobile(request.getParameter("mobile"));
            DirectoryHandler dirH = new DirectoryHandler(String.valueOf(ob.getID()));
            boolean r = dirH.createUserDir();
            if(r) {
                boolean x = metadata.searchData(ob.getID());
                boolean q = true;
                boolean z = true;
                if(!x) {
                    q = metadata.appendData(ob);
                } else {
                    HealthService temp = metadata.getData(ob.getID());
                    if(!temp.getName().equalsIgnoreCase(ob.getName())) {
                        q = false;
                        z = false;
                    } else {
                        if(temp.getAge() < ob.getAge()) {
                            q = metadata.updateAge(ob.getAge());
                        }
                        if(!temp.getMobile().equals(ob.getMobile())) {
                            q = metadata.updateMobile(ob.getMobile());
                        }
                    }
                }
                if(q) {
                    String fileName = HashMapToXML.convert(getServletContext(), String.valueOf(ob.getID()), values);
                    if(!fileName.equals("")){
                        String ecg = request.getParameter("ecgGraph");
                        String resp = request.getParameter("respRateGraph");
                        boolean r1 = ECGGraphImage.save(fileName, ecg);
                        boolean r2 = RespRateGraphImage.save(fileName, resp);
                        if(r1 & r2) { 
                            out.print("Data successfully saved as " + fileName + ".xml to XML directory in server."
                                    + " Graph instances also saved to server.");
                        } else {
                            out.print("Data successfully saved as " + fileName + ".xml to XML directory in server. "
                                    + "Failed to save graph instances.");
                        }
                    } else {
                        out.print("Failed to save data as XML file on server");
                    }
                } else {
                    if(z) {
                         out.print("Failed to create metadata file for the Patient ID : " + ob.getID());
                    } else {
                        out.print("Patient with ID : " + ob.getID() + " already exists in the system. "
                            + "Please provide a new ID for this Patient with name : " + ob.getName());
                    }
                }
                
            } else {
                out.print("Failed to create a personal repository for the Patient ID : " + ob.getID());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
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

     private HashMap<String, String> buildHashMap(HttpServletRequest request) {                     
        HashMap<String, String> values = new HashMap<>(); 
        values.put("ID",request.getParameter("id")) ;
        values.put("sys",request.getParameter("sys"));            
        values.put("dia",request.getParameter("dia"));            
        values.put("mem",request.getParameter("mem"));          
        values.put("breathing_rate",request.getParameter("breathing_rate"));   
        values.put("position",request.getParameter("position"));         
        values.put("ecg",request.getParameter("ecg"));      
        values.put("o2",request.getParameter("o2")); 
        values.put("pulse_rate",request.getParameter("bpm"));  
        values.put("temperature",request.getParameter("temperature"));   
        values.put("GSRcond",request.getParameter("cond"));      
        values.put("GSRres",request.getParameter("res"));            
        values.put("time", Constants.sdf.format(new Date()));    
        return values;     
     }
}
