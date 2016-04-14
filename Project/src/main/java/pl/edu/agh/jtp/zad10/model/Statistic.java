package pl.edu.agh.jtp.zad10.model;

import pl.edu.agh.jtp.zad10.model.IModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains statistics of usage of certain elements of the GUI
 * @author Zbigniew Krolikowski
 *
 */
public final class Statistic extends IModel {

	/**
	 * Data structure for holding statistics
	 */	
	private Map<String,Integer> stats = new HashMap<String,Integer>();
	
	/**
	 * Data structure for holding name of buttons
	 */
	private List<String> names = new ArrayList<String>();
	
	/**
	 * Increments a value of a given key
	 * if it's not accounted for yet it will be added
	 * @param name
	 */
	public void increment(String name) {
		if (stats.containsKey(name)) {
			int count = stats.get(name);
			stats.put(name, ++count);
			//System.out.println(count);
		} else {
			stats.put(name, 1);
			names.add(name);
			//System.out.println(1);
		}
		notifyMVC();
	}
	
	/**
	 * Returns a string array which holds a name and int
	 * @return
	 */
	public String[] getStats () {
		String[] lines = new String[names.size()];
		for (int i = 0; i< names.size(); i++) {
			lines[i] = names.get(i) + "\t\t" + stats.get(names.get(i));
		}
		return lines;
	}
}
