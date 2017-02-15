/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.utils;

import java.lang.reflect.Field;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stifler
 */
public class ClassScope {
   
    public static String[] getLoadedLibraries(final ClassLoader loader[]) {
        String[] x = null;
        try {
            Field LIBRARIES = ClassLoader.class.getDeclaredField("loadedLibraryNames");
            LIBRARIES.setAccessible(true);
            final Vector<String> libraries = (Vector<String>) LIBRARIES.get(loader);
            x = libraries.toArray(new String[] {});
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ClassScope.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ClassScope.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(ClassScope.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ClassScope.class.getName()).log(Level.SEVERE, null, ex);
        }
        return x;
    }
}
