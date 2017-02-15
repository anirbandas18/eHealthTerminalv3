/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.simulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContext;
import ju.ehealthservice.utils.Constants;

public class UDPGenerator {
    private DatagramSocket socket;
    private BufferedReader br;
    private InetAddress IPAddress;
    private ScheduledExecutorService service;
    private static final String HOST = "localhost";
    private static final int PORT = 12345;
    private static final long INITIAL_DELAY = 0;
    private static final long DELAY = 2;
    
    public UDPGenerator(ServletContext sc) {
        try {
            InputStream is = sc.getResourceAsStream(Constants.DATA_FILE_1);
            br = new BufferedReader(new InputStreamReader(is));
            socket = new DatagramSocket();
            IPAddress = InetAddress.getByName(HOST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void generateData() {
        try {
            byte[] sendData = new byte[65500];
            String sentence = "";
            boolean status = true;
            for (int i = 0; i < 11; i++) {
		String data = br.readLine();
		if (data != null) {
                    sentence = sentence + data + "\n";
		} else {
                    status = false;
                    break;
		}
            }
            if (status) {
                sentence = sentence.substring(0, sentence.length() - 1);
            } else {
                sentence = "";
            }
            sendData = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData,
            sendData.length, IPAddress, PORT);
            socket.send(sendPacket);
            //System.out.println("Data sent " + new Date().toString());
	} catch (NumberFormatException | IOException e) {
            e.printStackTrace();
	}
    }
    
    
   
    
    public void startGeneration() {
    	service = Executors.newSingleThreadScheduledExecutor();
	    service.scheduleWithFixedDelay(new Runnable()
	      {
	        @Override
	        public void run()
	        {
	        	generateData();
	        }
	      }, INITIAL_DELAY, DELAY, TimeUnit.MILLISECONDS);
	    
	  }
    
    
    public boolean stopGeneration() {
        try {
        	service.shutdown();
            socket.close();
            br.close();
            return true;
	} catch (IOException ex) {
		ex.printStackTrace();
                return false;
	}
    }
    
}
    