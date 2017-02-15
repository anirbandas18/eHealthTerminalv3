/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ju.ehealthservice.images.GetGraphImages;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Stifler
 */
public class ImageHandler {
    
    public static String formatString(byte[] bin) {
        StringBuffer data = new StringBuffer(Base64.encodeBase64String(bin));
        data.insert(0, Constants.IMAGE_HEADER);
        return data.toString();
    }
    
    public static byte[] getBinData(String fileName) {
        try {
            File f = new File(fileName);
            byte[] bin = FileUtils.readFileToByteArray(f);
            return bin;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GetGraphImages.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GetGraphImages.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
