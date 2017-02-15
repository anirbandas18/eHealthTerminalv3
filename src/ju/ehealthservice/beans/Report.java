/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.beans;

import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import ju.ehealthservice.utils.Constants;

/**
 *
 * @author Stifler
 */
public class Report {
    
    private String Name;
    private String Age;
    private String ID;
    private String ReadingDate;
    private String Sys;
    private String Dia;
    private String BreathingRate;
    private String Mem;
    private String ECG;
    private String Cond;
    private String Res;
    private String Temperature;
    private String BPM;
    private String O2;
    private String Position;
    private String Mobile;
    private String Image;
    private String Remarks;

    
    @Override
	public String toString() {
		return "Name=" + Name + "&Age=" + Age + "&ID=" + ID + "&ReadingDate="
				+ ReadingDate + "&Sys=" + Sys + "&Dia=" + Dia
				+ "&BreathingRate=" + BreathingRate + "&Mem=" + Mem + "&ECG="
				+ ECG + "&Cond=" + Cond + "&Res=" + Res + "&Temperature="
				+ Temperature + "&BPM=" + BPM + "&O2=" + O2 + "&Position="
				+ Position + "&Mobile=" + Mobile + "&Image=" + Image + "&Remarks=" + Remarks;
	}

    /**
     * @param Name the Name to set
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * @param Age the Age to set
     */
    public void setAge(String Age) {
        this.Age = Age;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * @param ReadingDate the ReadingDate to set
     */
    public void setReadingDate(String ReadingDate) {
        
        try {
            Date x = Constants.sdf.parse(ReadingDate);
            this.ReadingDate = x.toString();
        } catch (ParseException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param Sys the Sys to set
     */
    public void setSys(String Sys) {
        this.Sys = Sys;
    }

    /**
     * @param Dia the Dia to set
     */
    public void setDia(String Dia) {
        this.Dia = Dia;
    }

    /**
     * @param BreathingRate the BreathingRate to set
     */
    public void setBreathingRate(String BreathingRate) {
        this.BreathingRate = BreathingRate;
    }

    /**
     * @param Mem the Mem to set
     */
    public void setMem(String Mem) {
        this.Mem = Mem;
    }

    /**
     * @param ECG the ECG to set
     */
    public void setECG(String ECG) {
        this.ECG = ECG;
    }

    /**
     * @param Cond the Cond to set
     */
    public void setCond(String Cond) {
        this.Cond = Cond;
    }

    /**
     * @param Res the Res to set
     */
    public void setRes(String Res) {
        this.Res = Res;
    }

    /**
     * @param Temperature the Temperature to set
     */
    public void setTemperature(String Temperature) {
        this.Temperature = Temperature;
    }

    /**
     * @param BPM the BPM to set
     */
    public void setBPM(String BPM) {
        this.BPM = BPM;
    }

    /**
     * @param O2 the O2 to set
     */
    public void setO2(String O2) {
        this.O2 = O2;
    }

    /**
     * @param Position the Position to set
     */
    public void setPosition(String Position) {
        this.Position = Position;
    }

    /**
     * @return the Mobile
     */
    public String getMobile() {
        return Mobile;
    }

    /**
     * @param Mobile the Mobile to set
     */
    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    /**
     * @param Image the Image to set
     */
    public void setImage(String Image) {
        this.Image = Image;
    }

    /**
     * @param Remarks the Remarks to set
     */
    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }

    /**
     * @param Position the Position to set
     */
    
}