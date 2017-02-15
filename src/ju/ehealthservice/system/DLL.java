/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.system;

import javax.servlet.ServletContext;
import ju.ehealthservice.utils.ClassScope;
import ju.ehealthservice.utils.Constants;

/**
 *
 * @author Stifler
 */
public class DLL {
    public static boolean loadLibrary(ServletContext sc) {
        int bitVersion = Integer.parseInt(System.getProperty("sun.arch.data.model"));
        if(isLoaded()) {
            return true;
        } else {
            try {
                if(bitVersion == 64) {
                    System.load(sc.getRealPath(Constants.DLL_FILE_x64));
                } else {
                    System.load(sc.getRealPath(Constants.DLL_FILE_x86));            
                }
                return true;
            } catch(UnsatisfiedLinkError e) {
                e.printStackTrace();
                return false;
            }
        }
    }
    
    private static boolean isLoaded() {
                ClassLoader appLoader = ClassLoader.getSystemClassLoader();
                ClassLoader currentLoader = DLL.class.getClassLoader();
 
                ClassLoader[] loaders = new ClassLoader[] { appLoader, currentLoader };
                final String[] libraries = ClassScope.getLoadedLibraries(loaders);
                for (String library : libraries) {
                        String[] path = library.split("\\\\");
                        String dllName  = path[path.length - 1];
                        if(dllName.equals(Constants.DLL_FILE_NAME)) {
                            return true;
                        }
                }
                return false;
        }
    
}
