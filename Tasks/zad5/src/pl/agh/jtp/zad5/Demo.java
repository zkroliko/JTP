package pl.agh.jtp.zad5;

import org.apache.log4j.Logger;

import com.google.common.base.Joiner;
import com.google.common.math.BigIntegerMath;

import java.io.IOException;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Properties;

/**
 * Class for demonstration purposes.
 * Excessive use of console output.
 * @author Zbigniew Krolikowski
 */
public final class Demo {
	/** Just a logger */
	private final Logger logger = Logger.getLogger(Demo.class);
	/** Properties containing the name of the file for messages. */
	private Properties options;
	/** Messages which will be printed */
	private Properties messages;

	/**
	 * Constructor, requires a Demo.properties. and a language file.
	 * 
	 * @throws IOException
	 * @see PropertiesLoader
	 */
	public Demo() throws IOException {
		options = PropertiesLoader.load("Demo.properties");
		// Did we got it right
		if (options == null)
			throw new IOException();
		messages = PropertiesLoader.load(options.getProperty("languageFile"));
		// Now checking whether we got it right
		if (messages == null) {
			throw new IOException();
		}
	}

	/** Function for showing mutability examples */
	public void showMutability() {
		logger.info("Started showing mutability");
		showStringImMutablility();
		showBigIntegerImMutability();
		logger.info("Finished showing mutability");
	}

	/** Function for showing immutablility of class String */
	private void showStringImMutablility() {
		logger.info("Started showing String immutability");
		System.out.println("\n\t" + messages.getProperty("stringMessage") + "\n");
		String a = messages.getProperty("string1");
		String b = messages.getProperty("string2");
		System.out.println(messages.getProperty("string1Announce") + "\n" + a);
		System.out.println(messages.getProperty("string2Announce") + "\n" + b);
		System.out.println(messages.getProperty("stringsJoinMessage"));
		// Now to copying a and joining the strings
		String c = a;
		Joiner joiner = Joiner.on(" ").skipNulls(); // GUAVA
		a = joiner.join(a, b);
		// Printing results
		System.out.println(messages.getProperty("stringResult1Announce")
				+ " \n" + a);
		System.out.println(messages.getProperty("stringResult2Announce")
				+ " \n" + c);
		System.out.println(messages.getProperty("stringResultMessagePart1"));
		System.out.println(messages.getProperty("stringResultMessagePart2"));
		logger.info("Finished showing String immutability");
	}

	/** Function for showing immutablility of the class BigInteger */
	private void showBigIntegerImMutability() {
		logger.info("Started showing BigInteger immutability");
		System.out.println("\n\t" + messages.getProperty("BigIntegerMessage") + "\n");
		BigInteger a = new BigInteger(messages.getProperty("BigInteger1"));
		BigInteger b = new BigInteger(messages.getProperty("BigInteger2"));
		System.out.println(messages.getProperty("BigInteger1Announce") + " \n"
				+ a);
		System.out.println(messages.getProperty("BigInteger2Announce") + " \n"
				+ b);
		System.out.println(messages.getProperty("BigIntegersAddMessage"));
		// Now to copying a and joining the strings
		BigInteger c = a;
		a = BigIntegerMath.divide(a, b, RoundingMode.CEILING); // GUAVA
		// Printing results
		System.out.println(messages.getProperty("BigIntegerResult1Announce")
				+ " \n" + a);
		System.out.println(messages.getProperty("BigIntegerResult2Announce")
				+ " \n" + c);
		System.out
				.println(messages.getProperty("BigIntegerResultMessagePart1"));
		System.out
				.println(messages.getProperty("BigIntegerResultMessagePart2"));
		logger.info("Finished showing BigInteger immutability");
	}

}