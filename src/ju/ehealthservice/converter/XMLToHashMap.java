/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.converter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import ju.ehealthservice.utils.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Stifler
 */
public class XMLToHashMap {
    public static HashMap<String, String> convert() {
        HashMap<String, String> values = new HashMap<>();
        values.put("sys","0");
        values.put("dia","0");
        values.put("mem","0");
        values.put("breathing_rate","0");
        values.put("position","idle");
        values.put("ecg","N/A");
        values.put("o2","0");
        values.put("bpm","0");
        values.put("temperature","0.0");
        values.put("cond","1.24561482");
        values.put("res","-1.24561482");
        return values;
    }
    
   static HashMap<String,String> quantVal;
    static ArrayList<String> quantityList;
    
    public static void buildMap(String values)
    {

        String [] val=values.split(",");
        for(int i=0;i<val.length;i++)
        quantVal.put(quantityList.get(i),val[i].trim()+" "+quantVal.get(quantityList.get(i)).trim());
    }
    
    public static HashMap<String,String> convert(String ID, String fileName)
    {
        try {
            String path = Constants.XML_REPOSITORY_PATH + ID + "\\" + fileName;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(path));
            quantityList=new ArrayList<>();
            quantVal=new HashMap<>();
            
            NodeList nodeList = document.getDocumentElement().getChildNodes();
            
            NodeList observation=nodeList.item(3).getChildNodes();
            Node procedure =observation.item(3);
            
            String Id=procedure.getAttributes().getNamedItem("ns0:href").getNodeValue();
            Id=Id.substring(Id.lastIndexOf(":")+1);
            String tagId=Id.substring(0,Id.indexOf("-"));
            String valId=Id.substring(Id.indexOf("-")+1,Id.length());
            quantVal.put(tagId, valId);
            
            NodeList resultList=procedure.getChildNodes().item(5).getChildNodes();//children list of result
            NodeList dataArrayList=resultList.item(1).getChildNodes();
            NodeList dataRecordList=dataArrayList.item(3).getChildNodes().item(1).getChildNodes();
            
            for (int j = 1; j < dataRecordList.getLength(); j+=2)
            {
                String quantity=dataRecordList.item(j).getAttributes().getNamedItem("name").getNodeValue();
                if(!quantity.equals("feature"))
                {
                    quantityList.add(quantity);
                    
                }
                else
                {
                    continue;
                }
                if(j>2)
                {
                    Node quant=dataRecordList.item(j).getChildNodes().item(1);
                    String uomCode;
                    uomCode = quant.getChildNodes().item(1).getAttributes().getNamedItem("code").getNodeValue();
                    uomCode=uomCode.replace('*', ' ').trim();
                    quantVal.put(quantity,uomCode) ;
                    
                }
                else
                {
                    
                    quantVal.put(quantity,"");
                }
                
            }
            String values=dataArrayList.item(7).getTextContent().replace('\n', ' ').trim();
            buildMap(values);
            
            
            
            
        } catch (ParserConfigurationException|SAXException|IOException ex) {
            Logger.getLogger(XMLToHashMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quantVal;
    }
 
}
