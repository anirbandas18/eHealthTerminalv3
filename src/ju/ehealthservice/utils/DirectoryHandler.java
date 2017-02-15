/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.utils;

import java.io.File;

/**
 *
 * @author Stifler
 */
public class DirectoryHandler {
    
    private String dirName;
    
    public DirectoryHandler(String dirName) {
        this.dirName = dirName;
    }
    
    private void createXMLDir() {
        File d = new File("EHealthService\\XML");
        if(!d.exists()) {
            d.mkdir();
        }
    }
    
    private void createRepository() {
        File d = new File("EHealthService");
        if(!d.exists()) {
            d.mkdir();
        }
    }
    
    private boolean createUserXMLDir() {
        createRepository();
        createXMLDir();
        File dir = new File(Constants.XML_REPOSITORY_PATH + dirName);
        if(!dir.exists()) {
            boolean r = dir.mkdir();
            return r;
        } else {
            return true;
        }
    }
    
    private boolean createUserRemarksDir() {
        createRepository();
        createRemarksDir();
        File dir = new File(Constants.REMARKS_REPOSITORY_PATH + dirName);
        if(!dir.exists()) {
            boolean r = dir.mkdir();
            return r;
        } else {
            return true;
        }
    }
    
    private void createGraphDir() {
        File d = new File("EHealthService\\Graph");
        if(!d.exists()) {
            d.mkdir();
        }
    }
    
    private void createRemarksDir() {
        File d = new File("EHealthService\\Remarks");
        if(!d.exists()) {
            d.mkdir();
        }
    }
    
    public boolean createUserDir() {
        boolean x = createUserXMLDir();
        boolean y = createUserGraphDir();
        boolean z = createUserImageDir();
        boolean w = createUserRemarksDir();
        System.out.println(w + " " + x + " " + y + " " + z);
        return (w & x & y & z);
    }
    
    private boolean createUserGraphDir() {
        createRepository();
        createGraphDir();
        File dir = new File(Constants.GRAPH_REPOSITORY_PATH + dirName);
        if(!dir.exists()) {
            boolean r = dir.mkdir();
            createECGDir();
            createRespRateDir();
            return r;
        } else {
            return true;
        }
    }
    
    private void createECGDir() {
        File d = new File(Constants.GRAPH_REPOSITORY_PATH + dirName + "\\ECG");
        if(!d.exists()) {
            d.mkdir();
        }
    }
    
    private void createRespRateDir() {
        File d = new File(Constants.GRAPH_REPOSITORY_PATH + dirName + "\\RespRate");
        if(!d.exists()) {
            d.mkdir();
        }
    }
    
    private boolean createUserImageDir() {
        File dir = new File("EHealthService\\Image");
        if(!dir.exists()) {
            boolean r = dir.mkdir();
            return r;
        } else {
            return true;
        }
    }
}
