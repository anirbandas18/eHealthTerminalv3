/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.utils;

import java.text.SimpleDateFormat;

/**
 *
 * @author Stifler
 */
public class Constants {
    public static final String IMAGE_HEADER = "data:image/jpeg;base64,";
    private static final String DATE_FORMAT = "yyyy-MM-dd_HH-mm-ss";
    public static final String DATE_PICKER_FORMAT = "yyyy-MM-dd";
    public static final String TEMPLATE_NAME_ = "health.xsl";
     public static final String METADATA_INDEX_PATH = "EHealthService\\MetadataIndex.xml";
    public static final String XML_REPOSITORY_PATH = "EHealthService\\XML\\";
    public static final String REMARKS_REPOSITORY_PATH = "EHealthService\\Remarks\\";
    public static final String GRAPH_REPOSITORY_PATH = "EHealthService\\Graph\\";
    public static final String IMAGE_REPOSITORY_PATH = "EHealthService\\Image\\";
    public static final String DEFAULT_IMAGE_PATH = "Images/NoImageAvailable.jpg";
    public static final int INTERVAL = 500;
    public static final SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
    public static final SimpleDateFormat small = new SimpleDateFormat("yyyy-MM-dd");
    public static final String XML_RECORD_PATH = "/Patient";
    private static final String PROPERTY_KEY = "ju.ehealthservice.threshold.";
    public static final String DIA = PROPERTY_KEY + "dia";
    public static final String SYS = PROPERTY_KEY + "sys";
    public static final String O2 = PROPERTY_KEY + "o2";
    public static final String BPM = PROPERTY_KEY + "bpm";
    public static final String TEMPERATURE = PROPERTY_KEY + "temperature";
    public static final String COND = PROPERTY_KEY + "cond";
    public static final String RES = PROPERTY_KEY + "res";
    public static final String AIRF = PROPERTY_KEY + "airf";
    public static final String MEM = PROPERTY_KEY + "mem";
    public static final String THRESHOLD_FILE = "/WEB-INF/thresholdvalues.properties";
    public static final String DATA_FILE = "/datafile.txt";//to be removed
    public static final String DATA_FILE_1 = "/datafile1.txt";//to be removed
    public static final String REPORT_TEMPLATE = "/WEB-INF/Templates/Patient.jrxml";
    public static final String DLL_FILE_x64 = "/WEB-INF/dlls/x64/" + Constants.DLL_FILE_NAME;
    public static final String DLL_FILE_x86 = "/WEB-INF/dlls/x86/" + Constants.DLL_FILE_NAME;
    public static final String DLL_FILE_NAME = "rxtxSerial.dll";
    public static final String TWILIO_ACCOUNT_SID = "AC700654a726182c8d78e23282f927b1d3";
    public static final String TWILIO_AUTH_TOKEN = "74ae2e2b5656dc2d27aee20805c3dfe4";
}
