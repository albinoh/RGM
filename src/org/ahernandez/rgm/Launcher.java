package org.ahernandez.rgm;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Launcher
 * Main entry point. Takes target-size for the Pricer and starts reading the system in. 
 * 
 * @author ahernandez
 *
 */
public class Launcher {

	public static void main(String[] args) {
		
		if (args.length != 1) {
			System.out.println("usage: Pricer target-size");
			return;
		}
		
		BufferedOutputStream lOutput = new BufferedOutputStream(System.out);
		BufferedReader lInput = new BufferedReader(new InputStreamReader(System.in));
		int lTargetSize = Integer.parseInt(args[0]);
        String lLine = null;

        try {
            Pricer lPricer = new Pricer(lTargetSize, lOutput);
            while((lLine = lInput.readLine()) != null) {
            	try{
                    Action lAction = new Action(lLine);
                    lPricer.executeAction(lAction);
            	} catch(IllegalArgumentException ex) {
                    System.err.println(ex);                  
                }

            }   
            lPricer.end();
            lInput.close();         
        }
        catch(IOException ex) {
            System.err.println("Error reading input");                  
        }

	}

}
