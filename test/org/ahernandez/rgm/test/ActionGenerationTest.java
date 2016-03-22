package org.ahernandez.rgm.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.ahernandez.rgm.Action;
import org.junit.Test;

public class ActionGenerationTest {
	
	@Test
	public void testLineInRegex() throws IOException {

        String fileName = ".\\resources\\pricer.in";

        String line = null;
        FileReader fileReader = new FileReader(fileName);
	    BufferedReader bufferedReader = new BufferedReader(fileReader);

        while((line = bufferedReader.readLine()) != null) {
            Action lAction = new Action(line);
        }   
        bufferedReader.close();
	}          
	
	@Test
	public void testLineInRegex2() throws IOException {

        String fileName = ".\\resources\\pricer2.in";

        String line = null;
        FileReader fileReader = new FileReader(fileName);
	    BufferedReader bufferedReader = new BufferedReader(fileReader);

        while((line = bufferedReader.readLine()) != null) {
        	try{
                Action lAction = new Action(line);
        	} catch(IllegalArgumentException ex) {
                System.err.println(ex);                  
            }
        }   
        bufferedReader.close();
	}      
}
