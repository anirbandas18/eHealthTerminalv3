package ju.ehealthservice.connections;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import ju.ehealthservice.patient.Patient;

/**
 * Created by SMCC on 3/5/14.
 */
public class UDPReader {
    private Patient thePatient;
    private boolean listening = true;
    private int i=0;
    private DatagramPacket packet;
    private byte[] buf;
    private DatagramSocket socket = null;
    private Timer t;
    private static final int INTERVAL = 500;
    
    public void setPatient(Patient p){
        thePatient = p;

    }
    
   private static boolean available(int port) {
    System.out.println("--------------Testing port " + port);
    Socket s = null;
    try {
        s = new Socket("localhost", port);

        // If the code makes it this far without an exception it means
        // something is using the port and has responded.
        System.out.println("--------------Port " + port + " is not available");
        return false;
    } catch (IOException e) {
        System.out.println("--------------Port " + port + " is available");
        return true;
    } finally {
        if( s != null){
            try {
                s.close();
            } catch (IOException e) {
                throw new RuntimeException("You should handle this error." , e);
            }
        }
    }
}
    
    public void initialiseUDP(){
        available(12345);
        try {
            socket = new DatagramSocket(12345);
            socket.setBroadcast(true);
            socket.setReuseAddress(true);
            System.out.println("Listen on " + socket.getLocalAddress() + " from " + socket.getInetAddress() + " port " + socket.getBroadcast());
        } catch (SocketException ex) {
            Logger.getLogger(UDPReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void stopUDP(){
        listening = false;
        t.stop();
    }
    
    public void terminateUDP() {
        socket.close();
    }
    
    private static String parseString(String input, int index){
        String[] splits = input.split(" ");
        return(splits[index]);
    }
    private static String[] separateNewlinetoArray(String input){
        String[] splits = input.split("\n");
        return(splits);
    }

    
    public void startUDP() {
        Thread th = new Thread(new Runnable() {

            @Override
            public void run() {
                t = new Timer(INTERVAL, new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
            
            buf = new byte[4096];
            packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String inputLine = new String(packet.getData());
                buf = new byte[4096];
                packet = new DatagramPacket(buf, buf.length);
                System.out.println("Data recieved! " + new Date().toString());
                String[] sensorStrings  = separateNewlinetoArray(inputLine);

                for(String sensorString : sensorStrings){

                    if(sensorString == null){sensorString="anything_but_blank";}
                    String value = "", ecgtime = "";
                    String key = "";
                    if(sensorString.contains(" ")){
                        key = parseString(sensorString, 0);

                        switch (key){
                            case "BPSYS" :
                                value = parseString(sensorString, 1);
                                if(Integer.parseInt(value) != 0){
                                    thePatient.bpsys = value;
                                }
                                thePatient.notifyPatientObservers(thePatient.parameterChange);
                                break;
                            case "BPDIA" :
                                value = parseString(sensorString, 1);
                                if(Integer.parseInt(value) != 0){
                                    thePatient.bpdia = value;
                                }
                                thePatient.notifyPatientObservers(thePatient.parameterChange);
                                break;
                            case "AIRFW" :
                                value = parseString(sensorString, 1);
                                thePatient.airfw = value;
                                thePatient.notifyPatientObservers(thePatient.parameterChange);
                                break;
                            case "BPM" :
                                value = parseString(sensorString, 1);
                               thePatient.bpm = value;
                               thePatient.notifyPatientObservers(thePatient.parameterChange);
                                break;
                            case "O2" :
                                value = parseString(sensorString, 1);
                                thePatient.o2 = value;
                                thePatient.notifyPatientObservers(thePatient.parameterChange);
                                break;
                            case "TEMP" :
                                value = parseString(sensorString, 1);
                                thePatient.temp = value;
                                thePatient.notifyPatientObservers(thePatient.parameterChange);
                                break;
                            case "POS" :
                                value = sensorString.substring(sensorString.indexOf(" "));
                                thePatient.pos = value;
                                thePatient.notifyPatientObservers(thePatient.parameterChange);
                                break;
                            case "COND" :
                                value = sensorString.substring(sensorString.indexOf(" "));
                                thePatient.cond = value;
                                thePatient.notifyPatientObservers(thePatient.parameterChange);
                                break;
                            case "RES" :
                                value = sensorString.substring(sensorString.indexOf(" "));
                                thePatient.res = value;
                                thePatient.notifyPatientObservers(thePatient.parameterChange);
                                break;
                            case "ECG" :
                                value = parseString(sensorString, 1);
                                thePatient.ecg = value;
                                thePatient.notifyPatientObservers(thePatient.parameterChange);

                                break;
                            default:
                                value = sensorString;
                                thePatient.notifyPatientObservers(thePatient.parameterChange);
                                break;
                        }
                    }
                    else{
                        
                    }
                }
        } catch (IOException z) {
            z.printStackTrace();
        }
                    }
                    
                });
                t.start();
            }
            
        });
        th.start();
    }
}