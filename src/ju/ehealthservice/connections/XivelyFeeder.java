/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.connections;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import ju.ehealthservice.patient.Patient;


/**
 *
 * @author SMCC
 */
public class XivelyFeeder{
    Timer t;
    Patient thePatient;
    int interval;
    String xivelyValue = "";
    //List<String> xivelyChannelNames = new ArrayList<>();
    Authenticator authenticator;
    String data;
    public void setPatient(Patient patient){
        thePatient = patient;
    }
    public void startPosting(Patient patient, int inter){
        thePatient = patient;
        interval = inter;
        System.setProperty("http.proxySet", "true");
        System.setProperty("http.proxyHost", "192.168.250.21");
        System.setProperty("http.proxyPort", "3128");
        System.setProperty("http.nonProxyHosts", "localhost|127.0.0.1");
        
            authenticator = new Authenticator() {

                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return (new PasswordAuthentication("121010",
                            "121010".toCharArray()));
                }
            };
            Authenticator.setDefault(authenticator);
            
        Thread th = new Thread(new Runnable(){
        @Override
        public void run(){
        t = new Timer(interval*1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    URL url = new URL("https://api.xively.com/v2/feeds/10248266");
                        HttpURLConnection httpCon = (HttpURLConnection)url.openConnection();
                        httpCon.setDoOutput(true);
                        httpCon.setRequestMethod("PUT");
                        httpCon.setRequestProperty("Content-Type","application/json");
                        httpCon.setRequestProperty("X-ApiKey","El8x2wFYtILVwQ6S1s4U5imv6Y3hfvZE45oKP89MNqdAscka");


                            String j =  "{\n" +
                                        "  \"version\":\"1.0.0\",\n" +
                                        "   \"datastreams\" : [ {\n" +
                                        "    \"id\" : \"0_Name\",\n" +
                                        "      \"current_value\" : \""+thePatient.name+"\"\n" +
                                        "    },\n" +
                                        "    { \n" +
                                        "    \"id\" : \"1_Age\",\n" +
                                        "    \"current_value\" : \""+thePatient.age+"\"      \n" +
                                        "    },\n" +
                                        "	{\n" +
                                        "	\"id\" : \"2_Body_Temperature\",\n" +
                                        "      \"current_value\" : \""+thePatient.temp+"\"\n" +
                                        "    },\n" +
                                        "    { \n" +
                                        "    \"id\" : \"3_Pulse_Rate\",\n" +
                                        "    \"current_value\" : \""+thePatient.bpm+"\"      \n" +
                                        "    },\n" +
                                        "	{ \"id\" : \"4_Blood_O2\",\n" +
                                        "      \"current_value\" : \""+thePatient.o2+"\"\n" +
                                        "    },\n" +
                                        "    { \n" +
                                        "    \"id\" : \"5_BP_SYS\",\n" +
                                        "    \"current_value\" : \""+thePatient.bpsys+"\"      \n" +
                                        "    },\n" +
                                        "    { \n" +
                                        "     \"id\" : \"6_BP_DIA\",\n" +
                                        "    \"current_value\" : \""+thePatient.bpdia+"\"\n" +
                                        "    }\n" +
                                        "  ]\n" +
                                        "}";
                
                        httpCon.setRequestProperty("Content-Length",""+j.length());
                        DataOutputStream out = new DataOutputStream(httpCon.getOutputStream());
                        out.writeBytes(j);
                        out.close();
                        
                        BufferedReader br = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
        
                        String input;
                        switch(httpCon.getResponseCode()){
                            case 200 :
                                //System.out.println("Xively Feed Post Success!!!");
                                break;
                            case 400 :
                                System.out.println("Json format error");
                                break;
                            default:
                                System.out.println("Error "+ httpCon.getResponseCode());
                                break;
                        }

                        while ((input = br.readLine()) != null){
                            data = input;
                            System.out.println(input);
                         }
                    
                
                }
                catch(IOException ioe ){System.out.println(ioe);}
                        
                        
                        
            }
        });
        t.start();
        
        
       }
    });
    th.start();
    }
    public void stopPosting(){
        t.stop();
    }
}
