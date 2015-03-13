package pl.edu.agh.jtp.zad6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public final class Program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Entry> list = null;
		try {
			list = ArrayLoader.load(new File("data"));
		} catch (FileNotFoundException f) {
			System.out.println("File not found");
			return;
		}
		ArrayChecker checker = new ArrayChecker(list);
		checker.check();
	}

}
