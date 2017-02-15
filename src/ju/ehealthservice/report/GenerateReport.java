/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.report;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ju.ehealthservice.utils.Constants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import org.w3c.dom.Document;
/**
 *
 * @author Stifler
 */
public class GenerateReport extends HttpServlet {

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
        String fileName = request.getParameter("fileName");
        String ID = fileName.substring(0, fileName.indexOf('_'));
        PreProcessReport ppr = new PreProcessReport(fileName);
        Document doc = ppr.getXMLData();
        try {
            /* TODO output your page here. You may use following sample code. */
            ServletOutputStream os = response.getOutputStream();
            InputStream isRT = getServletContext().getResourceAsStream(Constants.REPORT_TEMPLATE);
            JasperReport jasperReport = JasperCompileManager.compileReport(isRT);
            JRXmlDataSource xmlds = new JRXmlDataSource(doc,Constants.XML_RECORD_PATH);
            Map params = new HashMap();
            String name = fileName.substring(0, fileName.length() - 4) + ".jpg";
            String imgName = Constants.IMAGE_REPOSITORY_PATH + ID + ".jpg";
            params.put("realPath", Constants.GRAPH_REPOSITORY_PATH + ID);
            params.put("ecg", "\\ECG\\" + name);
            params.put("respRate", "\\RespRate\\" + name);
            File imgFile = new File(imgName);
            if(!imgFile.exists()) {
                params.put("image", getServletContext().getRealPath(Constants.DEFAULT_IMAGE_PATH));
            } else {
                params.put("image", imgName);
            }
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,params, xmlds);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename='" + 
                    fileName.substring(0, fileName.length() - 4) + ".pdf'");
            
            JasperExportManager.exportReportToPdfStream(jasperPrint, os);
        } catch (JRException ex) {
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

}
