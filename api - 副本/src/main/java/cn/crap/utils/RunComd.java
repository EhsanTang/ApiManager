package cn.crap.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RunComd {
	private static Log logger = LogFactory.getLog(RunComd.class);
	public static String run(String url, String des) throws Exception{
		Process process;   
        InputStream is =null;  
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        try {   
      
            Runtime runtime = Runtime.getRuntime();
			String command = "wkhtmltopdf " + url + " " + des;
			process = runtime.exec(command);
            is = process.getInputStream();   
       
            isr = new InputStreamReader(is,Charset.forName("utf-8"));   
  
            br = new BufferedReader(isr);   
            String line = null;   
            while ((line = br.readLine()) != null) {   
                result.append(line);
            }   
            logger.info(result.toString());
        } catch (Exception e) {   
            e.printStackTrace(); 
            result.append(e.getMessage());
            logger.error(e);
        }finally{
        	try {
        		 if(is!=null)
        			 is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}   
             try {
            	 if(isr!=null)
            		 isr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}   
             try {
            	if(br!=null)
            		br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}   
        }
		return result.toString();
	}
	public static void main(String args[]){

	}
}
