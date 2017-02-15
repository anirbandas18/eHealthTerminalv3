/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.graphs;

import java.io.FileOutputStream;
import ju.ehealthservice.utils.Constants;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Stifler
 */
public class ECGGraphImage {
    
    public static boolean save(String fileName, String img) {
        try {
            StringBuffer data = new StringBuffer(img);
            data = new StringBuffer(data.substring(23));
            int c = 0;
            for(int i = 0 ; i < data.length() ; i++) {
                if(data.charAt(i) == ' ') {
                    c++;
                    data.setCharAt(i, '+');
                }
            }
            byte[] imageByteArray = Base64.decodeBase64(data.toString());
            String ID = fileName.substring(0, fileName.indexOf('_'));
            String path = Constants.GRAPH_REPOSITORY_PATH + ID + "\\ECG\\";
            fileName = fileName + ".jpg";
            FileOutputStream imageOutFile = new FileOutputStream(path + fileName);
            imageOutFile.write(imageByteArray);
            imageOutFile.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}