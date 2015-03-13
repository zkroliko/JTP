package pl.agh.jtp;

import org.apache.log4j.Logger;

/** 	@author Zbigniew Krolikowski 
  	@version 1.0
	Logger has not been interagrated into the project
*/

/** 	Main function. It's only purpose is to direct control

 */
public class Program {	
	private static final Logger logger1 = Logger.getLogger(Program.class.getName());
	public static void main(String args[]) {

		logger1.info("Hello, program!");

		//Creating tavern
		Tavern tavern = new Tavern();

		// Main loop, executing indefinetly
		while(true) {
			tavern.load();			
			try {
	    			Thread.sleep(1000);
			} catch(InterruptedException ex) {
	    			Thread.currentThread().interrupt();
			}			
		}
	}
}

