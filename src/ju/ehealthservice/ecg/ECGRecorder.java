/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.ecg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Deque;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import ju.ehealthservice.patient.Patient;

/**
 *
 * @author SMCC
 */
public class ECGRecorder {

    Deque<EcgPoint> list;
    Patient thePatient;
    
    static int xPixel=0;

    Timer t;
    
    static boolean b = false;
    public ECGRecorder(Deque<EcgPoint> ecgGraph,Patient patient){
        list = ecgGraph;
        thePatient = patient;
    }
    public void setPatient(Patient patient){
        thePatient = patient;
    }
    public void setEcgGraph(Deque<EcgPoint> ecgGraph){
        list = ecgGraph;
    }
    public Deque<EcgPoint> getEcgGraph(){
        return list;
    }
    public void startEcgRecording(){
        xPixel = 0;
        b=false;
    Thread th = new Thread(new Runnable(){
        
        @Override
        public void run(){

                t = new Timer(8, new ActionListener() {
                    
                    @Override
                    public void actionPerformed(ActionEvent e) {


                        Double ecgVolt=new Double(0.00);
                            if(!thePatient.ecg.equals("na")){
                                ecgVolt = Double.parseDouble(thePatient.ecg);
                            }
                           long tt = System.currentTimeMillis();
                           EcgPoint p = new EcgPoint(xPixel,ecgVolt, tt);
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
    public void stopEcgRecording(){
        t.stop();
    }
   
}
