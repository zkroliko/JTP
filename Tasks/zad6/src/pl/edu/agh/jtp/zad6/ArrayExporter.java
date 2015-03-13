package pl.edu.agh.jtp.zad6;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
/**
 * Class used for saving primes into a file
 * @author Zbigniew Królikowski
 *
 */
public final class ArrayExporter {
	/**
	 * Saves numbers which are confirmed to be prime into a file
	 * @param list 
	 */
	public static void export(List<Entry> list) {
		FileWriter writer;
		BufferedWriter out;
		try {
			writer = new FileWriter("primes.txt");
			out = new BufferedWriter(writer);
			for (Entry b : list) {
				if (b.getChecked() && b.getPrime()) {
					out.write((b.getValue()).toString() + "\n");
				}
			}
			out.close();
		} catch (IOException e) {
			System.out.println("Unable to save to file");
		}
	}
}
