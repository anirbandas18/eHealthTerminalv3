/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.remarks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import ju.ehealthservice.utils.Constants;

/**
 *
 * @author Stifler
 */
public class Remarks {
    public static boolean commit(String directory, String fileName, String remarks) {
        try {
            File remarksFile = new File(Constants.REMARKS_REPOSITORY_PATH + directory + "\\"+ fileName);
            BufferedWriter bw = new BufferedWriter(new FileWriter(remarksFile));
            bw.write(remarks);
            bw.close();
            return true;
        } catch(IOException e) {
            System.out.println(e);
            return false;
        }
    }
    public static String abstain(String directory, String fileName) {
        String remarks = "";
        try {
            File remarksFile = new File(Constants.REMARKS_REPOSITORY_PATH + directory + "\\"+ fileName);
            BufferedReader br = new BufferedReader(new FileReader(remarksFile));
            remarks = br.readLine();
            System.out.println(remarks);
            br.close();
        } catch(IOException e) {
            System.out.println(e);
        }
        return remarks;
    }
}
