/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.report;

import ju.ehealthservice.beans.Report;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import ju.ehealthservice.converter.XMLToHashMap;
import ju.ehealthservice.beans.HealthService;
import ju.ehealthservice.remarks.Remarks;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import static ju.ehealthservice.system.EHealthTerminal.metadata;
import ju.ehealthservice.utils.Constants;

/**
 *
 * @author Stifler
 */
public class PreProcessReport {
    
    private String fileName, ID;
    
    public PreProcessReport(String fileName) {
        this.fileName = fileName;
        this.ID = fileName.substring(0, fileName.indexOf('_'));
    }
    
    private Report hashMapToReport(HashMap<String, String> values) {
        Report r = new Report();
        r.setID(values.get("PatientID"));
        r.setSys(values.get("Systole"));
        r.setDia(values.get("Diastole"));            
        r.setMem(values.get("mem"));          
        r.setBreathingRate(values.get("Breathing Rate"));   
        r.setPosition(values.get("BodyPosition"));         
        r.setECG(values.get("ElectroCardioGram"));      
        r.setO2(values.get("Oxymeter")); 
        r.setBPM(values.get("Pulse"));  
        r.setTemperature(values.get("Body Temperature"));   
        r.setCond(values.get("Galvinic Skin Response cond"));      
        r.setRes(values.get("Galvinic Skin Response res"));            
        r.setReadingDate(values.get("Time"));    
        r.setName(values.get("name"));            
        r.setAge(values.get("age"));  
        r.setMobile(values.get("mobile"));
        r.setImage(values.get("image"));
        r.setRemarks(values.get("remarks"));
        return r;
    }
    
    private Document reportToDocument(Report r) {
        Document doc = null;
        System.out.println(r);
        HashMap<String, String> keyVal = parseReportData(r.toString());
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();
            Element Patient = doc.createElement("Patient");
            doc.appendChild(Patient);
            for(Map.Entry<String, String> m : keyVal.entrySet()) {
                Element Id=doc.createElement(m.getKey());
                Id.appendChild(doc.createTextNode(m.getValue()));
                Patient.appendChild(Id);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return doc;
    }
    
    private HashMap<String, String> parseReportData(String report) {
        HashMap<String, String> data = new HashMap();
        StringTokenizer pair = new StringTokenizer(report, "&");
        while(pair.hasMoreTokens()) {
            StringTokenizer keyVal = new StringTokenizer(pair.nextToken(), "=");
            data.put(keyVal.nextToken(), keyVal.nextToken());
        }
        return data;
    }
    
    public Document getXMLData() {
        HashMap<String, String> data = XMLToHashMap.convert(ID, fileName);
        HealthService ob = metadata.getData(Integer.parseInt(ID));
        data.put("name", ob.getName());
        data.put("age", Integer.toString(ob.getAge()));
        data.put("remarks", Remarks.abstain(ID, fileName.substring(0, fileName.length() - 4) + ".txt"));
        data.put("mobile", ob.getMobile());
        if(ob.isImage()) {
            data.put("image", ID);
        } else {
            data.put("image", Constants.DEFAULT_IMAGE_PATH);
        }
        Report r = hashMapToReport(data);
        Document doc = reportToDocument(r);
        return doc;
    }
    
}
