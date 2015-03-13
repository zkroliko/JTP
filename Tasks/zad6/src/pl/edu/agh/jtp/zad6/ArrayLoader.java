package pl.edu.agh.jtp.zad6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class ArrayLoader {

	/**
	 * @param File File from which the number are going to be loaded
	 * @throws FileNotFoundException 
	 */
	public static List<Entry> load(File file) throws FileNotFoundException {
		
		List<Entry> list = new ArrayList<Entry>();
		Scanner scan = null;
		scan = new Scanner(file);		
		while (scan.hasNextBigInteger()) {
			list.add(new Entry (scan.nextBigInteger()));	
		}
		// Closing scanner
		if (scan != null)
			scan.close();
		
		return list;
	}

}
