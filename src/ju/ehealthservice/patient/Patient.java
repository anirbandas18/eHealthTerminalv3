/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.patient;



import ju.ehealthservice.resprate.RespPoint;
import ju.ehealthservice.ecg.EcgPoint;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.ConcurrentLinkedDeque;
import ju.ehealthservice.utils.Constants;
/**
 *
 * @author SMCC
 */
public class Patient extends Observable {
    
    public boolean parameterChange = false;
    public boolean ecgChange = false;
    public String name = ""; 
    public String age = "";
    public static int id = 0;
    public static String bpsys="0", bpdia = "0", airfw = "0", bpm="0",o2 ="0";
    public static String temp = "0.0";
    public static String pos="IDLE", cond = "0", res = "0",ecg="na";
    public static String devMem = "0";
    public static Deque<EcgPoint> EcgGraph = new ConcurrentLinkedDeque<>();

    public static Deque<RespPoint> RespGraph = new ConcurrentLinkedDeque<>();
    
    public void notifyPatientObservers(boolean ch){
        parameterChange = !ch;
        setChanged();
        notifyObservers();
    }
    public void notifyEcgPlotter(Boolean ecgC){
        ecgChange = !ecgC;
        setChanged();
        notifyObservers();
    }
    public String stringifyEcgGraph(){
        Gson gson = new Gson();
        return gson.toJson(EcgGraph);
    }
    public String stringifyRespGraph(){
        Gson gson = new Gson();
        return gson.toJson(RespGraph);
    }
    
   public HashMap<String, String> getInstantData() {
        HashMap<String, String> values = new HashMap<>(); 
 values.put("sys",bpsys);            
 values.put("dia",bpdia);            
 values.put("mem",devMem);          
 values.put("breathing_rate",airfw);   
 values.put("position",pos);         
 values.put("ecg",ecg);      

 values.put("o2",o2); 
 values.put("bpm",bpm);  
 values.put("temperature",temp);   
 values.put("cond",cond);      
 values.put("res",res);            
   return values;  
    }
   
   public void crossThreshold(HashMap<String,String> threshold, Patient thePatient)
    {
        ArrayList<String> result = new ArrayList<>();
        double val,thres;
        val=Double.parseDouble(thePatient.bpsys);
        thres=Double.parseDouble(threshold.get(Constants.SYS));
        if(val>thres) {
            result.add("Systole : " + thres + " &#8594; <b>" + val + "</b>");
        }
        
        val=Double.parseDouble(thePatient.bpdia);
        thres=Double.parseDouble(threshold.get(Constants.DIA));
        if(val>thres) {
            result.add("Diastole : " + thres + " &#8594; <b>" + val + "</b>");
        }
        
        val=Double.parseDouble(thePatient.airfw);
        thres=Double.parseDouble(threshold.get(Constants.AIRF));
        if(val>thres) {
            result.add("Breathing rate : " + thres + " &#8594; <b>" + val + "</b>");
        }
        
        val=Double.parseDouble(thePatient.bpm);
        thres=Double.parseDouble(threshold.get(Constants.BPM));
        if(val<thres) {
            result.add("Pulse : " + thres + " &#8594; <b>" + val + "</b>");
        }
        
        val=Double.parseDouble(thePatient.cond);
        thres=Double.parseDouble(threshold.get(Constants.COND));
        if(val>thres) {
            result.add("Skin conductance : " + thres + " &#8594; <b>" + val + "</b>");
        }
        
        val=Double.parseDouble(thePatient.res);
        thres=Double.parseDouble(threshold.get(Constants.RES));
        if(val>thres) {
            result.add("Skin Resistance : " + thres + " &#8594; <b>" + val + "</b>");
        }
        
        val=Double.parseDouble(thePatient.devMem);
        thres=Double.parseDouble(threshold.get(Constants.MEM));
        if(val>thres) {
            result.add("Mem : " + thres + " &#8594; <b>" + val + "</b>");
        }
        
        val=Double.parseDouble(thePatient.temp);
        thres=Double.parseDouble(threshold.get(Constants.TEMPERATURE));
        if(val>thres) {
            result.add("Temperature : " + thres + " &#8594; <b>" + val + "</b>");
        }
        val=Double.parseDouble(thePatient.o2);
        thres=Double.parseDouble(threshold.get(Constants.O2));
        if(val<thres) {
            result.add("Oxymeter : " + thres + " &#8594; <b>" + val + "</b>");
        }
        setChanged();
        notifyObservers(result);
    }    
    
}    
    
