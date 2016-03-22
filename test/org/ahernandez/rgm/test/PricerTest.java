package org.ahernandez.rgm.test;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import org.ahernandez.rgm.Action;
import org.ahernandez.rgm.Pricer;
import org.junit.Test;

public class PricerTest {
	
	@Test
	public void testPricer10000() throws IOException {

        String InputfileName = ".\\resources\\pricer.in";
        String OutputfileName = ".\\output\\pricer10000.out";
        String line = null;
		
        FileReader fileReader = new FileReader(InputfileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        OutputStream lOut = new FileOutputStream(OutputfileName);

        Pricer lPricer = new Pricer(10000, lOut);
        while((line = bufferedReader.readLine()) != null) {
            Action lAction = new Action(line);
            lPricer.executeAction(lAction);
        }   
        lPricer.end();
        bufferedReader.close();  
	}
	
	@Test
	public void testPricer200() throws IOException {

        String InputfileName = ".\\resources\\pricer.in";
        String OutputfileName = ".\\output\\pricer200.out";
        String line = null;
		
        FileReader fileReader = new FileReader(InputfileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        OutputStream lOut = new FileOutputStream(OutputfileName);

        Pricer lPricer = new Pricer(200, lOut);
        while((line = bufferedReader.readLine()) != null) {
            Action lAction = new Action(line);
            lPricer.executeAction(lAction);
        }   
        lPricer.end();
        bufferedReader.close();  
	}
	
	@Test
	public void testPricer1() throws IOException {

        String InputfileName = ".\\resources\\pricer.in";
        String OutputfileName = ".\\output\\pricer1.out";
        String line = null;
		
        FileReader fileReader = new FileReader(InputfileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        OutputStream lOut = new FileOutputStream(OutputfileName);


        Pricer lPricer = new Pricer(1, lOut);
        while((line = bufferedReader.readLine()) != null) {
            Action lAction = new Action(line);
            lPricer.executeAction(lAction);
        }   
        lPricer.end();
        bufferedReader.close();  
	}

}
