/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.search;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import ju.ehealthservice.utils.Constants;
import org.apache.commons.lang3.time.DateUtils;

/**
 *
 * @author SAHA
 */
public class Search {
    
    private int id;
    private File folder;
    private File[] listOfFiles;
    private List<String> files;
    
    public Search(int id) {
        this.id = id;
    }
    public boolean patientExists() {
        folder = new File(Constants.XML_REPOSITORY_PATH + id);
        if(!folder.exists())
        {
            System.out.print("Folder does not exist");
            return false;
        } else {
            listOfFiles = folder.listFiles();
            files = new LinkedList<>();
            return true;
        }
    }
    
    public static boolean patient(int id) {
        File folder = new File(Constants.XML_REPOSITORY_PATH + id);
        if(!folder.exists())
        {
            return false;
        } else {
            return true;
        }
    }
    
    public List<String> getFileNames()
    {
        if(patientExists()) {
            for (File listOfFile : listOfFiles)
            {
                if (listOfFile.isFile()) {
                    files.add(listOfFile.getName());
                }
            }
        }
        return trimFileExtension(files);
    }
    public List<String> getFileNames(String from,String to)
    {
        if(patientExists()) {
           try {
                Date fromDate=Constants.small.parse(from);
                Date toDate=Constants.small.parse(to);
                if(fromDate.equals(toDate)) {
                    toDate = DateUtils.addSeconds(toDate, 86399);
                }
                for (File listOfFile : listOfFiles) {
                    if (listOfFile.isFile()) {
                        if(!listOfFile.getName().equals("metadata.xml")) {
                            String originalName = listOfFile.getName();
                            String fileName = originalName.split("_", 2)[1];
                            System.out.println("File name is " + fileName);
                            Date date=Constants.sdf.parse(fileName);
                            if(fromDate.compareTo(date)*toDate.compareTo(date)<0) {
                                files.add(originalName);
                            }         
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return trimFileExtension(files);
    }
    
    public List<String> getFileNames(int time,String field)
    {
        if(patientExists()) {
            long duration=0;
            if(field.compareTo("minutes")==0) {
                duration=time*60*1000L;
            } else if(field.compareTo("hours")==0) {
                duration=time*60*60*1000L;
            } else if(field.compareTo("days")==0) {
                duration=time*24*60*60*1000L;
            } else if(field.compareTo("years")==0) {
                //year calculation
                duration=time*365*24*60*60*1000L;
            }
            Date now=new Date();
            try {
                for (File listOfFile : listOfFiles) {
                    if (listOfFile.isFile()) {
                        String fileName = listOfFile.getName().split("_", 2)[1];
                        Date date=Constants.sdf.parse(fileName);
                        if (now.getTime()-date.getTime()<duration) {
                            files.add(listOfFile.getName());
                        }
                    }
                }
             } catch (Exception e) {
                 e.printStackTrace();
             }
        }
        return trimFileExtension(files);
    }
    
    private List<String> trimFileExtension(List<String> x) {
        List<String> z = new LinkedList<>();
        for(String s: x) {
            if(!s.equals("metadata.xml")) {
                s = s.substring(0, s.length()-4);
                s = s.substring(s.indexOf('_') + 1, s.length());
                z.add(s);
            }
        }
        return z;
    }
    
}

