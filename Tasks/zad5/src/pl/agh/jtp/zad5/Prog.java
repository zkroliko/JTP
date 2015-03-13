package pl.agh.jtp.zad5;

import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * @author Zbigniew Krolikowski
 */

public final class Prog {

	static final Logger logger = Logger.getLogger(Prog.class);

	/**
	 * @param args
	 *            Arguments from the command line
	 */
	public static void main(String[] args) {
		logger.info("The program has started");
		Demo demo;
		try {
			demo = new Demo();
		} catch (IOException i) {
			System.out.println("\n Execution failed, see logger for specifics");
			logger.error("Problem with execution, see previous messages");
			return;
		}
		demo.showMutability();
		logger.info("The program has ended without errors");
	}


}
