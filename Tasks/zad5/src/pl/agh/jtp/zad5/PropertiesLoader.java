package pl.agh.jtp.zad5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.google.common.base.Stopwatch;

/**
 * Class for loading Properties object from file.
 * 
 * @author Zbigniew Krolikowski
 * @see Demo
 */
public final class PropertiesLoader {

	/**
	 * Main method for loading Properties files. Also remebers the time requiver
	 * for loading preferenced, which might be usefull, if somebody is looking
	 * for tight spots.
	 * 
	 * @param name
	 *            Path to the file containing properties
	 * @return Properties file under the given filename
	 */
	public static Properties load(String name) {
		/** Just a logger */
		Logger logger = Logger.getLogger(Demo.class);
		logger.info("Started looking for file: " + name);
		/** Stopwatch for measuring time **/
		Stopwatch stopwatch = Stopwatch.createStarted();

		Properties properties = null;
		FileInputStream input = null;
		try {
			input = new FileInputStream(name);
			logger.info("Found the file: " + name);
			properties = new Properties();
			properties.load(input);
		} catch (FileNotFoundException f) {
			logger.error("No file found under name: " + name);
		} catch (IOException i) {
			logger.error("Problem with loading the .properties file");
		} finally {
			if (input != null)
				try {
					input.close();
				} catch (IOException i) {
					logger.error("Problem with closing the file: " + name);
				}
		}
		stopwatch.stop(); // stoping the stopwach and measuring time
		logger.info("Time elapsed for loading: " + name + " is "
				+ stopwatch.elapsed(TimeUnit.NANOSECONDS) + "ns");
		return properties;
	}
}
