package pl.agh.jtp;

import java.util.Map;
import java.util.TreeMap;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

/** 	@author Zbigniew Krolikowski 
  	@version 1.0
*/

/** 	Writes given map under a file with given path and name
	@see save()
 */
public abstract class Writer {
	/** 	Sole metod 
		@param contacts The map containing names and contacts 
		@param filePath Path and name of the file you wan to write your data to.
	*/
	public static void writeToFile(Map<String,Contact> contacts, String filePath) {
		try {
			PrintWriter out = new PrintWriter(filePath);		
			for (String name : contacts.keySet()) {
				out.println(contacts.get(name).toCSV());
			}
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("File to write to has not been found");		
		}

	}
}
