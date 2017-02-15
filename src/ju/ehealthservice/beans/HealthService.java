/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.beans;

import java.util.Arrays;
import ju.ehealthservice.utils.Constants;

public class HealthService {
	
	private int ID, Age;
	private String Name, Mobile;
        private boolean Image;
	@Override
	public String toString() {
		return "ID=" + ID + "&Age=" + Age + "&Name=" + Name + "&Mobile="
				+ Mobile + "&Image=" + isImage();
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getID() {
		return ID;
	}
	public int getAge() {
		return Age;
	}
	public String getName() {
		return Name;
	}
	public String getMobile() {
		return Mobile;
	}
	public void setAge(int age) {
		Age = age;
	}
	public void setName(String name) {
		Name = name;
	}
	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public HealthService() {
		ID = Age = 0;
		Name = Mobile = "";
                Image = false;
	}
        
        public boolean isEmpty() {
            boolean a[] = new boolean[4];
            boolean s = false;
            Arrays.fill(a, true);
            if(this.Age == 0) {
                a[0] = false;
            }
            if(this.ID == 0) {
                a[1] = false;
            }
            if(this.Name.length() == 0) {
                a[2] = false;
            }
            if(this.Mobile.length() == 0) {
                a[3] = false;
            }
            for(boolean x : a) {
                if(x == false) {
                    s = true;
                    break;
                }
            }
            return s;
        }

    /**
     * @return the Image
     */
    public boolean isImage() {
        return Image;
    }

    /**
     * @param Image the Image to set
     */
    public void setImage(boolean Image) {
        this.Image = Image;
    }

}

