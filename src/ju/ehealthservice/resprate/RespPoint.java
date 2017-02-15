/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.resprate;

/**
 *
 * @author SMCC
 */
public class RespPoint {
   public double value;
   public  int XPix;
    public     long time;
    public RespPoint( int xPix,double v, long actTime){
        value = v;XPix = xPix; time = actTime;
    }
    public double getValue(){
        return value;
    }
    public int getXPix(){
        return XPix;
    }
}
