/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.system;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import javax.servlet.ServletContext;
import ju.ehealthservice.utils.Constants;

/**
 *
 * @author Stifler
 */
public class Threshold {
    
    public static HashMap<String, String> loadValues(ServletContext sc) {
        HashMap<String, String> thresholdValues = new HashMap<>();
        Properties prop = new Properties();
	InputStream input = null;
	try {
            input = sc.getResourceAsStream(Constants.THRESHOLD_FILE);
            prop.load(input);
            @SuppressWarnings("rawtypes")
            Enumeration e = prop.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
		thresholdValues.put(key, prop.getProperty(key));
            }	    
        } catch (IOException ex) {
            ex.printStackTrace();
	} finally {
            if (input != null) {
                try {
                    input.close();
		} catch (IOException e) {
                    e.printStackTrace();
		}
	}
    }
        return thresholdValues;
    }
}
