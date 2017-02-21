package net.dallascricket.scheduleLoader.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ServiceProperties {
	
	private static Properties serviceProps = null;
	
	public synchronized static String getServiceProperty(String key) throws Exception{
		if (serviceProps == null) {
			try {
				System.out.println("--------building service properties in getServiceProperty-----");
				serviceProps = buildServiceProperties();
			} catch (IOException | NamingException e) {
				System.err.print("error building service properties "+e);
				throw e;
			}
		}
	
		String augmentedKey = key;
		String propertyValue = serviceProps.getProperty(augmentedKey);		 
		
		return propertyValue;
	}

	private static Properties buildServiceProperties() throws IOException, NamingException {

		Properties props = new Properties();
		//String propsfile = "target/config.properties";
		String propsfile = getPropertiesFileLocation();
		InputStream propsStream  = new FileInputStream(propsfile);;
		props.load(propsStream);
		propsStream.close();
		System.out.println("loaded base properties from:" + propsfile);
		return props;

	}
	
	
	
	private static String getPropertiesFileLocation() throws NamingException {
		String folderName = null;
		try {
		   InitialContext context = new InitialContext();
		   folderName = (String) context.lookup("java:comp/env/config");
		   return folderName + "config.properties";
		} catch (NamingException ex) {
		   System.err.println("exception in jndi lookup: "+ex.getMessage());
		   throw ex;
		}
		
	}

	public static void main(String[]args) throws Exception
    {
        File curDir = new File(".");
        getAllFiles(curDir);
        System.out.println( getServiceProperty("tapeBallUserName"));
    }
    private static void getAllFiles(File curDir) {

        File[] filesList = curDir.listFiles();
        for(File f : filesList){
            if(f.isDirectory())
                System.out.println(f.getName());
            if(f.isFile()){
                System.out.println(f.getName());
            }
        }

    }

}
