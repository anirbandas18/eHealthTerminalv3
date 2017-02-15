/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.simulator;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.swing.Timer;
import ju.ehealthservice.connections.SerialSensorConnector;
import ju.ehealthservice.ecg.EcgPoint;
import ju.ehealthservice.patient.Patient;
import ju.ehealthservice.utils.Constants;


/**
 *
 * @author root
 */

public class AutoSerialSensor {
    
    Patient thePatient;
    EcgPoint aEcgPoint;
    
    String bpRecS = "REC#";
    Timer t;
    boolean monitoring = false;
    private static final int INTERVAL = 50;
    static int xPixel=0;
    static boolean ecgQueueFull = false;
    /** The port we're normally going to use. */
    private static final String PORT_NAMES[] = { 
		"/dev/tty.usbserial-A9007UX1", // Mac OS X
                "/dev/ttyACM0", // Linux My Machine 1
                "/dev/ttyACM1", // Linux My Machine 2
		"COM3", // Windows
                //"COM5", // Windows
                "/dev/ttyS86", // Linux
                "/dev/ttyUSB0", // Linux
    };
    
    /**
    * A BufferedReader which will be fed by a InputStreamReader 
    * converting the bytes into characters 
    * making the displayed results codepage independent
    */
    private BufferedWriter bw;
    private BufferedReader input;
    /** The output stream to the port */
    private OutputStream output;
    /** Milliseconds to block while waiting for port open */
    private static final int TIME_OUT = 2000;
    /** Default bits per second for COM port. */
    private static final int DATA_RATE = 115200;


        public void initialize(ServletContext sc) {
       

        try {
            InputStream fstream = sc.getResourceAsStream(Constants.DATA_FILE);
                input = new BufferedReader(new InputStreamReader(fstream));
                String path = sc.getRealPath("/");
                System.out.println(path);
                output = new FileOutputStream(path + "/bp.txt", true);
                
        } catch (Exception e) {
                System.err.println(e.toString());
        }
        
    }
    
    public void reinitialize(String com) {
       
        
    }
  
    public void startMonitoring(Patient patient) {
        System.out.print("Start in monitor");
        thePatient = patient;
        monitoring = true;
        
        
         Thread th = new Thread(new Runnable(){
        
        @Override
        public void run(){

                t = new Timer(INTERVAL, new ActionListener() {
                    
                    @Override
                    public void actionPerformed(ActionEvent e) {

			try {
                           
                                if(monitoring)
                                {
				String inputLine=input.readLine();
				//System.out.println(inputLine);
                                if(inputLine == null){inputLine="anything_but_blank";}
                                String value = "",key = "", ecgtime = "";
 
                                if(inputLine.contains(" ")){
                                    key = parseString(inputLine, 0);
                                
                                switch (key){
                                    case "BPSYS" :
                                        value = parseString(inputLine, 1);
                                        if(Integer.parseInt(value) != 0){
                                            thePatient.bpsys = value;
                                        }
                                        thePatient.notifyPatientObservers(thePatient.parameterChange);
                                        break;
                                    case "BPDIA" :
                                        value = parseString(inputLine, 1);
                                        if(Integer.parseInt(value) != 0){
                                            thePatient.bpdia = value;
                                        }
                                        thePatient.notifyPatientObservers(thePatient.parameterChange);
                                        break;                                      
                                    case "AIRFW" :
                                        value = parseString(inputLine, 1);
                                        thePatient.airfw = value;
                                        thePatient.notifyPatientObservers(thePatient.parameterChange);
                                        break;
                                    case "BPM" : 
                                        value = parseString(inputLine, 1);
                                        if(Integer.parseInt(value) != 0){
                                            thePatient.bpm = value;
                                        }
                                        thePatient.notifyPatientObservers(thePatient.parameterChange);
                                        break;
                                    case "O2" :
                                        value = parseString(inputLine, 1);
                                        if(Integer.parseInt(value) != 0){
                                            thePatient.o2 = value;
                                        }
                                        thePatient.notifyPatientObservers(thePatient.parameterChange);
                                        break;
                                    case "TEMP" :
                                        value = parseString(inputLine, 1);
                                        thePatient.temp = value;
                                        thePatient.notifyPatientObservers(thePatient.parameterChange);
                                        break;
                                    case "POS" :
                                        value = inputLine.substring(inputLine.indexOf(" "));
                                        thePatient.pos = value;
                                        thePatient.notifyPatientObservers(thePatient.parameterChange);
                                        break;
                                    case "COND" :
                                        value = inputLine.substring(inputLine.indexOf(" "));
                                        thePatient.cond = value;
                                        thePatient.notifyPatientObservers(thePatient.parameterChange);
                                        break;
                                    case "RES" :
                                        value = inputLine.substring(inputLine.indexOf(" "));
                                        thePatient.res = value;
                                        thePatient.notifyPatientObservers(thePatient.parameterChange);
                                        break;
                                    case "ECG" :
                                        value = parseString(inputLine, 1);
                                        //ecgtime = parseString( inputLine, 2);
                                        thePatient.ecg = value;
                                        thePatient.notifyPatientObservers(thePatient.parameterChange);
                                        break;  
                                    case "DEVM" :
                                        value = parseString(inputLine, 1);
                                        //ecgtime = parseString( inputLine, 2);
                                        thePatient.devMem = value;
                                        thePatient.notifyPatientObservers(thePatient.parameterChange);
                                        break;                                         
                                    default:
                                        value = inputLine;
                                        thePatient.notifyPatientObservers(thePatient.parameterChange);
                                        break;
                                }
                      
                            }
                        }
                        }
                            //close();
			catch (IOException | NumberFormatException e1) {
				System.err.println(e.toString());
			}
                   
                    }
                });
                t.start();

        }
    });
    th.start();
        
    }
    
    public boolean disposeConnection() {
         try {
            input.close();
            output.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(AutoSerialSensor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public void stopMonitoring(){
        monitoring = false;
       t.stop();
    } 
    public void requestBP(){
        try {
            output.write(bpRecS.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(SerialSensorConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     

  
    private static String parseString(String input, int index){
        String[] splits = input.split(" ");
        return(splits[index]);
    }
    
    public boolean isMonitoring() {
        return monitoring;
    }
}


