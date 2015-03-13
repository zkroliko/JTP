package pl.agh.jtp;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.TreeMap;

/** 	@author Zbigniew Krolikowski 
  	@version 1.0
*/

/** 	Function dedicated for loading the CSVFiles, constructor ask only for number of files and desired name, the rest is 
	specified during runtime.
*/
public class CsvLoader {
	private int contactCount = 0;
	private int duplicates = 0;
	private String desiredName;
	private int desiredNameCount = 0;
	private int fileCount;

	private Map<String,Contact> contacts = new TreeMap<String,Contact>();

/**	Only constructor, calls functions  {@link #askForTheFileCount()} {@link #askForTheName()} */
	public CsvLoader() {
		this.fileCount = askForTheFileCount();
		this.desiredName = askForTheName();			
	}

/**	Asks for the number of files user wants to load. 
	Used by {@link #CsvLoader} to set objects field.
 */
	private int askForTheFileCount () {
		System.out.println("Plase specify a number of adress books to be loaded:");
		int fileCount = 0;
		Scanner numberScanner = new Scanner(System.in);
		try {
			fileCount = numberScanner.nextInt();
		} catch (InputMismatchException e){
			System.out.println("You have specified a illegal number. Bye! Bye!");
			numberScanner.close();
			return 0;	// meaning no files to read	
		}
		numberScanner.close();
		return fileCount;	
	}
/**	Asks for the name of which occurence we will be counting.
	Used by {@link #CsvLoader} to set objects field.
 */
	private String askForTheName () {
		Scanner nameScanner = new Scanner(System.in);
		System.out.println("Specify the name of the person you want count throught the adress book:");
		String name = nameScanner.nextLine();
		nameScanner.close();
		return name;		
	}

	public void load () {
		if (fileCount == 0) // Checking whether we got files to read
			return;

		for (int i = fileCount; i > 0; i--) {
			loadFromOneFile();
		}
	}	
	private void loadFromOneFile () {
		ContactLoader loader;
		try {
			loader = new ContactLoader();
		} catch (FileNotFoundException fnfe) {
			System.out.println("Operation failed. There is no file under that name.");
			return;			
		}	
		Contact curContact = loader.nextContact();
		Contact feedbackContact;
		while (curContact != null) {	
			if (curContact.getNames().equals(desiredName)) //counting the occurances of the name we are looking for
				desiredNameCount++;	
			feedbackContact = contacts.put(curContact.getNames(),curContact);
			// if .put() receives a contact which already is in the mpa then he returns it, else it gives back null
			if (feedbackContact != null && curContact !=null) { //making shure they are not null pointers
				if (((feedbackContact.getNames()).equals(curContact.getNames()))) // counting duplicats
					duplicates++;
			}
			contactCount++;	// we count the instances of received files
			curContact = loader.nextContact(); //loads the next contact from file, return null if end of file	
		}
	}
/** Basicly runs a function to save the Contacts contained in the map to a file */
	public void save() {
		Writer.writeToFile(contacts, "output.txt");				
	}
/** Prints some stats that is loadead contacts, unique contacts, and occurances of desired name */
	public void printResults() {
		System.out.println("Loaded contacts: " + contactCount);
		System.out.println("Unique contacts: " + (contactCount - duplicates));
		System.out.println("Occurances of " + desiredName + " 's name : 	" + desiredNameCount);
	}
}		

