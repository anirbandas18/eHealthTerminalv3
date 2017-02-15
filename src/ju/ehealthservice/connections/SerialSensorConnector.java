/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.connections;


import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener; 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import ju.ehealthservice.ecg.EcgPoint;
import ju.ehealthservice.patient.Patient;


/**
 *
 * @author root
 */

public class SerialSensorConnector implements SerialPortEventListener {
    SerialPort serialPort;
    Patient thePatient;
    EcgPoint aEcgPoint;
    
    String bpRecS = "REC#";
    
    boolean monitoring = false;
    
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
    private BufferedReader input;
    /** The output stream to the port */
    private OutputStream output;
    /** Milliseconds to block while waiting for port open */
    private static final int TIME_OUT = 2000;
    /** Default bits per second for COM port. */
    private static final int DATA_RATE = 115200;


        public void initialize() {
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        //First, Find an instance of serial port as set in PORT_NAMES.
        while (portEnum.hasMoreElements()) {
                CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
                for (String portName : PORT_NAMES) {
                        if (currPortId.getName().equals(portName)) {
                                portId = currPortId;
                                break;
                        }
                }
        }
        
        if (portId == null) {
                //lblConsole.setText("Could not find COM part.");
                System.out.println("Could not find COM port.");
                
                return;
        }

        try {
                // open serial port, and use class name for the appName.
                serialPort = (SerialPort) portId.open(this.getClass().getName(),
                                TIME_OUT);

                // set port parameters
                serialPort.setSerialPortParams(DATA_RATE,
                                SerialPort.DATABITS_8,
                                SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);

                // open the streams
                input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
                output = serialPort.getOutputStream();

                // add event listeners
                serialPort.addEventListener(this);
                serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
                System.err.println(e.toString());
        }
        
    }
    
    public void reinitialize(String com) {
        serialPort.removeEventListener();
        serialPort.close();
        
        
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        //First, Find an instance of serial port as set in PORT_NAMES.
        while (portEnum.hasMoreElements()) {
                CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
                //for (String portName : PORT_NAMES) {
                        if (currPortId.getName().equals(com)) {
                                portId = currPortId;
                                break;
                        }
                //}
        }
        
        if (portId == null) {
                //lblConsole.setText("Could not find COM part.");
            if(!com.equals("AUTO")){
                System.out.println("Could not find "+ com);
            }
                return;
        }

        try {
                // open serial port, and use class name for the appName.
                serialPort = (SerialPort) portId.open(com,
                                TIME_OUT);

                // set port parameters
                serialPort.setSerialPortParams(DATA_RATE,
                                SerialPort.DATABITS_8,
                                SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);

                // open the streams
                input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
                output = serialPort.getOutputStream();

                // add event listeners
                serialPort.addEventListener(this);
                serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
                System.err.println(e.toString());
        }
        
    }
        
    /**
     * This should be called when you stop using the port.
     * This will prevent port locking on platforms like Linux.
     */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}
    public void startMonitoring(Patient patient){
        thePatient = patient;
        monitoring = true;
        
        
    } 
    public void stopMonitoring(){
        monitoring = false;
    } 
    public void requestBP(){
        try {
            output.write(bpRecS.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(SerialSensorConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     

    @Override
    public  synchronized void serialEvent(SerialPortEvent oEvent) {
    		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
                            while(monitoring){
                                //System.out.println("monitoring");
                                //if (serialPort != null) {initialize();}
                                while(!input.ready());
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
                                else{
                                    //lblConsole.setText(value);
                                }
                                
                                }
                            
                            //close();
			} catch (Exception e) {
				//System.err.println(e.toString());
			}
		}
    
    }
    private static String parseString(String input, int index){
        String[] splits = input.split(" ");
        return(splits[index]);
    }
}