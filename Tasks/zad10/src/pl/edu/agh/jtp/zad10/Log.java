package pl.edu.agh.jtp.zad10;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Used for logging the events in the program
 * @author Zbigniew Krolikowski
 *
 */
public class Log extends IModel {

	/**
	 * String list of events
	 */
	private List<String> events = new ArrayList<String>();
	
	/**
	 * Constructor
	 */
	public Log() {
	}
	
	/**
	 * Adds an event to data structure
	 * @param line The line you wish to add to the logger
	 */
	public void addEvent(String line) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		events.add(dateFormat.format(date) + "\t" + line);
		//System.out.println(events.get(events.size() -1));
		notifyMVC();
	}
	
	/**
	 * Gets an String array of all the events
	 * @return
	 */
	public String[] getEvents() {
		String[] lines = new String[events.size()];
		for (int i = 0; i < events.size(); i++) {
			lines[i] = events.get(i);
		}
		return lines;
	}
}
