/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Set;
import javax.servlet.ServletContext;
import ju.ehealthservice.utils.Constants;

/**
 *
 * @author Stifler
 */
public class HashMapToXML {
    
     
     public static String convert(ServletContext sc, String ID, HashMap<String,String> hm)
    {
        try {
			String templateFileName = "/WEB-INF/Templates/"  + Constants.TEMPLATE_NAME_;
                        InputStream f = sc.getResourceAsStream(templateFileName);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(f));
			
			String outputFileName = ID + "_" + hm.get("time");
			String path = Constants.XML_REPOSITORY_PATH + ID + "\\" + outputFileName + ".xml";
                        File fout = new File(path);
			FileOutputStream fos = new FileOutputStream(fout);	
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			
			String line;
                        boolean startWrite=false;
                        boolean Replacement=false;
                        Set<String> keys = hm.keySet();
			while ((line = bufferedReader.readLine()) != null) {
                            line=line.replace("@ID", ID);
                            if(line.compareTo("<xsl:template match=\"/\">")==0)
                            {
                                startWrite = true;
                                continue;
                            }
                            else if(line.compareTo("</xsl:template>")==0)
                            {
                                break;
                            }
                            if(!startWrite)
                            {
                                continue;
                            }
                            if(line.contains("<swe:value>"))
                                Replacement=true;
                            if(Replacement==true)
                            {
                                for(String key: keys)
                                    line=line.replace("@"+key, hm.get(key));
                            }
			bw.write(line);
			bw.newLine();
                        }
			f.close();
			bw.close();
                        return outputFileName;
			
		} catch (IOException e) {
			e.printStackTrace();
                        return "";
 	
}
    }
}
