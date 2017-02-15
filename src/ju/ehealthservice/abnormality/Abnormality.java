/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.abnormality;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Timer;
import ju.ehealthservice.system.EHealthTerminal;

/**
 *
 * @author Stifler
 */
public class Abnormality implements Observer {

    private Timer t;
    private ArrayList<String> result;
    private boolean isOn;
    
    public Abnormality() {
        EHealthTerminal.thePatient.addObserver(this);
        result = new ArrayList<>();
    }
    
    public void startMonitoring() {
        isOn = true;
        Thread th = new Thread(new Runnable() {

            @Override
            public void run() {
                t = new Timer(8, new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        EHealthTerminal.thePatient.crossThreshold(EHealthTerminal.thresholdValues, EHealthTerminal.thePatient);
                    }
                    
                });
                t.start();
            }
            
        });
        th.start();
        System.out.println("\nAbnormality started " + new Date().toString() + " " +isOn);
    }
    
    public void stopMonitoring() {
        isOn = false;
        t.stop();
        System.out.println("Abnormality stopped " + new Date().toString() + " " +isOn);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        result = (ArrayList<String>)arg;
    }
    
    public ArrayList<String> getNotificationData() {
        return result;
    }
    
    public boolean isMonitoring() {
        return isOn;
    }

}
