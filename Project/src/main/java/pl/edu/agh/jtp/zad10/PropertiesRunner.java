package pl.edu.agh.jtp.zad10;

import pl.edu.agh.jtp.zad10.controller.AppController;
import pl.edu.agh.jtp.zad10.controller.GraphController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * Class that will run the app with a parameters from a 
 * properties file provided in constructor
 * @author Zbigniew Krolikowski
 *
 */
public final class PropertiesRunner {
	
	/**
	 * Controlers for the app
	 */
	AppController appControler;
	GraphController graphController;
	
	/**
	 * File for loading properties
	 */
	File file;
	
	/**
	 * Properties
	 */
	Properties properties;
	
	/**
	 * Main constructor
	 * @param appController
	 * @param graphController
	 * @param file
	 */	
	public PropertiesRunner (AppController appController, GraphController graphController, File file) {
		this.appControler = appController;
		this.graphController = graphController;
		this.file = file;
		properties = new Properties();
		loadProperties();
		work();
	}
	
	/**
	 *  For loading properties from a file
	 */
	private void loadProperties () {
		try {
			FileInputStream fileIn = new FileInputStream(file);
			properties.load(fileIn);
		} catch (IOException e) {
			// If no properties file found then this class will
			// do nothing, user will do his own will
		}
	}
	
	private void work() {
			// First let's check properties
			if (!properties.containsKey("particleName")) {
				System.out.println("Config: No particle name specified");
				return;
			}
			if (!properties.containsKey("decision")) {
				System.out.println("Config: No decision specified");
				return;
			}
			if (!properties.containsKey("varDecision")) {
				System.out.println("Config: No decision about variable specified");
				return;
			}
			// Does the particle exists in the program?
			try {
				appControler.particleChangeRequested(properties.getProperty("particleName"));
			} catch (NoSuchElementException e) {
				System.out.println("Config: Unknown particle! Do more research!");
				return;
			}
			String decision = properties.getProperty("decision");
			String varDecision = properties.getProperty("varDecision");
			
			// This is for the graph
			if (decision.equals("graph")) {
				// Now we will be loading doubles
				// Let's check whether they exist
				if (!properties.containsKey("start")) {
					System.out.println("Config: Start of the interval not specified!");
					return;
				}
				if (!properties.containsKey("end")) {
					System.out.println("Config: End of the interval not specified!");
					return;
				}
				// They do let's go.
				try {
					double start = Double.parseDouble(properties
							.getProperty("start"));
					double end = Double.parseDouble(properties
							.getProperty("end"));
					if (start > end || start < 0 || end < 0) {
						throw new NumberFormatException();
					}
					if (varDecision.equals("velocity")
							&& (start > 1 || end > 1)) {
						throw new NumberFormatException();
					}
					graphController.chartRequested(start, end, varDecision);
				} catch (NumberFormatException e) {
					System.out.println("Config: Incorrect double numbers!");
					return;
				}
			}
			// This is for normal operations
			// First let's check whether
			if (decision.equals("calc")) {
				// Now we will be loading a double value
				// Let's check whether it exists in properties
				if (!properties.containsKey("value")) {
					System.out.println("Config: No calculation value specified!");
					return;
				}
				try {
					double value = Double.parseDouble(properties.getProperty("value"));
					if (value < 0) {
						throw new NumberFormatException();
					}
					if (varDecision.equals("velocity")) {
						appControler.velocityChangeRequested(value);
					} else {
						appControler.energyChangeRequested(value);
					}
				} catch (NumberFormatException e) {
					System.out.println("Config: Incorrect double number!");
					return;
				}
			}
	}
}
