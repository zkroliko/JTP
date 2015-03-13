package pl.agh.jtp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import java.io.File;

/** 	@author Zbigniew Krolikowski 
  	@version 1.0
*/

/** 	Function responsible for assembling a team.
 */

public class Tavern {
/**	Path to the dirctory we will be watching*/
	private String directoryPath;
	public static File directory;
/** 	Entry level for a hero */
	private int minimalLevel;

/**	Constuctor, with a directory name specified
	@param directory The directory you want Tavern to observe.
*/
	public Tavern (String directoryPath, int minimalLevel) {
		this.directoryPath = directoryPath;
		this.minimalLevel = minimalLevel;
		initialize();	

	}
/**	Tavern consturctor, that asks for the directory */
	public Tavern () {	
			Scanner scan = new Scanner(System.in);
			System.out.println("Provide the path to the observed directory: ");
			directoryPath = scan.nextLine();	
			initialize();
			System.out.println("Provide the minimal level for heroes to join quests: ");
			minimalLevel = scan.nextInt();
	}

/**	Opens a directory */
	private void initialize () {
			this.directory = new File(directoryPath);
	}

/**	Load all .ser files from the directory */
	public void load() {
    		for (final File fileEntry : directory.listFiles()) {
        		if (fileEntry.isFile() && (fileEntry != null))
				deserializeHero(fileEntry);
			// the hero is now loaded into the map
		}
	}

/**	Deserializes a hero and checks the level */ 
	public void deserializeHero (File file) {	
		try {
			FileInputStream fileIn = new FileInputStream(file.getAbsolutePath());
         		ObjectInputStream in = new ObjectInputStream(fileIn);
			// If hero doesn't match a specfic entry condtition he will be thrown out
			Hero hero = (Hero)in.readObject();
			if ((hero != null) && (hero.getStat(Statistic.level) < minimalLevel))
					file.delete();;
			// Closing input
        		in.close();
         		fileIn.close();
      		} catch (IOException i) {
			System.out.println("File not found or something wrong with the input.");
          		return;
      		} catch (ClassNotFoundException c) {
         		System.out.println("Hero class not found");
         		return;
      		} 
	}
}
