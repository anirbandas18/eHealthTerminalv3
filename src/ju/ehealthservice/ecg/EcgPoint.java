/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.ecg;


/**
 *
 * @author SMCC
 */
public class EcgPoint {
    public double volt;
    public int eightmsXPix;
    public long time;
    public EcgPoint( int xPix,double v, long actTime){
        volt = v;eightmsXPix = xPix; time = actTime;
    }
}