package pl.edu.agh.jtp.zad6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

public final class ArrayChecker {

	List<Entry> numbers = new ArrayList<Entry>();
	List<String> names = new ArrayList<String>();
	Map<String, FragmentChecker> threads = new HashMap<String, FragmentChecker>();
	
	private Stopwatch stopwatch = Stopwatch.createUnstarted();

	Scanner scan = new Scanner(System.in);
	
	/**
	 * Constructor 
	 * @param list Containing entries
	 */
	public ArrayChecker(List<Entry> list) {
		this.numbers = list;
		
	}

	/**
	 * Main execution method, runs threads.
	 */
	public void check() {
		// Keeps on working until the whole array has been checked
		// Displays help
		displayHelp();
		stopwatch.start(); // we will be counting the time until the creation of threads
		while (!allChecked()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException i) {
				;
			}
			commandInput();
		}
		stopwatch.stop();
	}

	/**
	 * This method will ask the user for commands
	 */
	private void commandInput() {
		String line;
		String[] args;
		boolean goodInput = false;
		while (!goodInput) {
			goodInput = true;
			try {
				// Now getting the command
				line = scan.nextLine();
				args = line.split(" ");
				// Now switch running the correct actions
				switch (args[0]) {
				case "new":
					newCommand(args);
					break;
				case "stop":
					stopCommand(args);
					break;
				case "run":
					runCommand(args);
					break;
				case "print":
					printCommand();
					break;
				case "export":
					exportCommand();
					break;
				case "image":
					imageCommand();
					break;
				case "help":
					displayHelp();
					break;
				default:
					System.out.println("No such command!");
					break;
				}
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	/**
	 * Displays the commands the user can use
	 */
	private void displayHelp() {
		System.out.println("Avaiable commands: ");
		System.out.println("new <name> <startindex <endindex>");
		System.out.println("stop <name>");
		System.out.println("run <name>");
		System.out.println("print - prints threads");
		System.out.println("export - exports primes into a file");
		System.out.println("image - makes an informational image");
		System.out.println("help - displays help");		
		System.out.println("Index Space is from 0 to "
				+ (numbers.size() - 1));
	}

	/**
	 * Command for making new threads
	 * 
	 * @param args
	 * @throws IllegalArgumentException
	 */
	private void newCommand(String[] args) throws IllegalArgumentException {
		if (args.length < 4) {
			throw new IllegalArgumentException("Not enought arguments");
		}
		int startIndex;
		int endIndex;
		try {
			startIndex = Integer.parseInt(args[2]);
			endIndex = Integer.parseInt(args[3]);
		} catch (NumberFormatException n) {
			throw new IllegalArgumentException(
					"These aren't really natural numbers");
		}
		if (startIndex < 0 || startIndex > (numbers.size() - 1))
			throw new IllegalArgumentException("Start index is out of bounds");
		if (endIndex < 0 || endIndex > (numbers.size() - 1))
			throw new IllegalArgumentException("End index is out of bounds");
		if (endIndex < startIndex)
			throw new IllegalArgumentException("End index lower than start index");
		// Creating new thread, currently stopped
		threads.put(args[1], new FragmentChecker(numbers, startIndex, endIndex, stopwatch.elapsed(TimeUnit.MILLISECONDS)));
		// Adding it to the names list
		names.add(args[1]);
		// Now making it start and stopping
		threads.get(args[1]).start();
		threads.get(args[1]).stopWorking();
	}

	/**
	 * Command for stopping a thread
	 * 
	 * @param args
	 * @throws IllegalArgumentException
	 */
	private void stopCommand(String[] args) throws IllegalArgumentException {
		if (args.length < 2) {
			throw new IllegalArgumentException("No name specified");
		}
		try {
			(threads.get(args[1])).stopWorking();
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("No thread under that name");
		}
	}

	/**
	 * Command for resuming the work of a thread
	 * 
	 * @param args
	 * @throws IllegalArgumentException
	 */
	private void runCommand(String[] args) throws IllegalArgumentException {
		if (args.length < 2) {
			throw new IllegalArgumentException("No name specified");
		}
		try {
			(threads.get(args[1])).continueWorking();
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("No thread under that name");
		}
	}

	/**
	 * Used for printing out all the strings
	 * 
	 * @param args
	 */
	private void printCommand() {
		System.out.println("NAME\tSTART INDEX\tCURRENT INDEX\tEND INDEX");
		for (String s : names) {
			System.out.println(s + "\t" + (threads.get(s)).getStartIndex()
					+ "\t\t" + (threads.get(s)).getCurrIndex()
					+ "\t\t" + (threads.get(s)).getEndIndex());
		}
	}
	
	/**
	 * Used for exporting commands into a file
	 */
	private void exportCommand() {
		ArrayExporter.export(numbers);
	}
	
	/**
	 * Displays a image showing the program execution
	 */
	private void imageCommand() {
		ImageMaker maker = new ImageMaker();
		maker.make("bars", threads, names);
	}
	
	/**
	 * Returns information that all the numbers in the array have been checked
	 * 
	 * @return true if all checked, false otherwise
	 */
	private boolean allChecked() {
		// Loop working while the numbers are checked and we are still in the
		// array
		for (int i = 0; i < numbers.size(); i++) {
			if (numbers.get(i).getChecked() != true) {
				return false;
			}
		}
		// If we didn't got to the end
		return true;
	}
}
