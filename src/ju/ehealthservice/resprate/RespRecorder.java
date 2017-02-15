/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.resprate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Deque;
import javax.swing.Timer;
import ju.ehealthservice.patient.Patient;
import ju.ehealthservice.resprate.RespPoint;

/**
 *
 * @author SMCC
 */
public class RespRecorder {
    Deque<RespPoint> list;
    Patient thePatient;
    
    static int xPixel=0;

    Timer t;
    
    static boolean b = false;
    public RespRecorder(Patient patient){
        list = patient.RespGraph;
        thePatient = patient;
    }
    public void setPatient(Patient patient){
        thePatient = patient;
    }
    public void setRespGraph(Deque<RespPoint> respGraph){
        list = respGraph;
    }
    public void startRespRecording(){
        xPixel = 0;
        b= false;
    Thread th = new Thread(new Runnable(){
        @Override
        public void run(){

                t = new Timer(26, new ActionListener() {
                    
                    @Override
                    public void actionPerformed(ActionEvent e) {


                        Integer respValue=new Integer(0);
                            if(!thePatient.airfw.equals("-10")){
                                respValue = Integer.parseInt(thePatient.airfw);
                            }
                           long tt = System.currentTimeMillis();
                           RespPoint p = new RespPoint(xPixel,respValue, tt);
                           //System.out.println(ecgVolt + " a " + tt );
                           list.addLast(p);

                           if(xPixel >375){
                               b= true;
                               xPixel=0;
                           }
                           xPixel=xPixel+1;
                           if(b){
                               list.removeFirst();
                           }
                        
                    }
                });
                t.start();

        }
    });
    th.start();
    }
    public void stopRespRecording(){
        t.stop();
    }
    
}
