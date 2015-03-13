package pl.edu.agh.jtp.zad7.server;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import pl.edu.agh.jtp.zad7.Task;

/**
 * Class being a server side of the application
 * @author Zbigniew Krolikowski
 */
public final class Server {
	
	/** Directories */
	private File obsDirectory;
	private File outputDirectory;
	
	/** Control flow */
	private final int maxNumThreads;	
	private int curNumThreads;
	
	/** For the images */
	Map<String,BufferedImage> rawImages = new HashMap<String, BufferedImage>();
	Map<String,BufferedImage> scaledImages = new HashMap<String, BufferedImage>();
	
	/** For the threads */
	List<Rescaler> threads = new ArrayList<Rescaler>();
	HashMap<Rescaler,Boolean> threadsPrinted = new HashMap<Rescaler,Boolean>();
	
	/**
	 * Constructor for server
	 * @param directory Directory which is going to be observed
	 * @param numThreads Number of threads program will use
	 */
	public Server (File directory, File outDirectory, int numThreads) {
		this.maxNumThreads = numThreads;
		this.curNumThreads = 0;
		
		this.obsDirectory = directory;	
		this.outputDirectory = outDirectory;
		//Now let us check whether the provided directory exists
		if (!obsDirectory.isDirectory()) {
			System.out.println("The directory doesn't exist");
			System.out.println("Client will terminate");
			return;
		}
		// Seems it does
		workLoop();
	}
	
	/**
	 * Constructor without parameters, ask the console for input
	 */
	public Server () {
		Scanner scan = new Scanner(System.in);
		this.curNumThreads = 0;
		
		System.out.println("Provide the maximum number of threads");		
		// Now for the maximum number of threads
		int inputNumber = 1; // Let's make it default to one thread
		boolean goodInput;
		goodInput = false;
		while(!goodInput) {
			goodInput = true;
			try {
				inputNumber = Integer.parseInt(scan.nextLine());
			} catch (NumberFormatException e) {
				goodInput = false;
				System.out.println("This isn't a nubmer, probably, not shure.");
				System.out.println("Provide the maximum number of threads");
			}
			if (inputNumber <= 0) {
				goodInput = false;
				System.out.println("Negative. Very funny...");
				System.out.println("Provide the maximum number of threads");
			}
		}		
		this.maxNumThreads = inputNumber;
		
		System.out.println("Provide the path to the observed directory");
		this.obsDirectory = new File(scan.nextLine());
		
		//Now let us check whether the provided directory exists
		if (!obsDirectory.isDirectory()) {
			System.out.println("The directory doesn't exist");
			System.out.println("Client will terminate");
			scan.close();
			return;
		}
		
		System.out.println("Provide the path to the output directory");
		this.outputDirectory = new File(scan.nextLine());
		
		//Now let us check whether the provided directory exists
		if (!outputDirectory.isDirectory()) {
			System.out.println("The directory doesn't exist");
			System.out.println("Server will terminate");
			scan.close();
			return;
		}
		
		// Seems it does
		scan.close();
		workLoop();
	}
	
	/**
	 * Main program execution loop
	 */
	private void workLoop() {
		while (true) {
			while (curNumThreads < maxNumThreads) {
				// Checking the directory for new tasks
				checkDir();
				// Updating data about threads
				upgradeThreadCount();
				upgradeThreadReports();
				// Will be waiting for some time until the next check
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					System.out.println("bump");
					continue;
				}
			}
		}
	}
	
	/**	
	 * Checks directory for tasks
	 */
	private void checkDir() {
		Task task;		
    	for (File fileEntry : obsDirectory.listFiles()) {    		
        	if (fileEntry.isFile() && (fileEntry != null) && curNumThreads < maxNumThreads) {
	       		try {
	        		task = loadTask(fileEntry);
	        		executeTask(task);	        		
	        	} catch (Exception f ) {
	       			System.out.println(f);
	       			continue;
	       		}
       		}
        // If curNumThreads >= maxNumThreads then the files will be omitted
		}
	}

	/**	Deserializes a task class file and loads images into the map 
	 * @throws IOException */ 
	private Task loadTask (File file) throws IOException {	
		try {
			FileInputStream fileIn = new FileInputStream(file.getAbsolutePath());
         	ObjectInputStream in = new ObjectInputStream(fileIn);
         	
			Task task = (Task)in.readObject();
			if (task != null) {
				file.delete();
			}
        	in.close();
         	fileIn.close();
			return task;			
      	} catch (IOException i) {
			throw new IOException("Class file not found or something wrong with the input.");
      	} catch (ClassNotFoundException c) {
         	throw new IOException("Task class file not found");
      	}		
	}		
	
	/**
	 * For executing a given task
	 */
	private void executeTask(Task task) {
		try {
			List<String> imageNames = loadImages(task.getImageDirectory());
			Rescaler rescaler = new Rescaler(rawImages, scaledImages,
					imageNames, task.getDesiredHeight(), task.getDesiredWidth());
			threads.add(rescaler);
			// Updating data about threads
			upgradeThreadCount();
			upgradeThreadReports();
		} catch (FileNotFoundException f) {
			System.out.println(f);
			return;
			// Tasks without image directory will be ignored
		}
	}

	/**
	 * For updating the thread count
	 */
	private void upgradeThreadCount() {
		int count = 0;
		for (Rescaler rescaler : threads) {
			if (!rescaler.isFinished()) {
				count++;
			}
		}
		this.curNumThreads = count;
	}
	
	/**
	 * For updating the thread count
	 */
	private void upgradeThreadReports() {
		int count = 0;
		// If a finished threads images and statistics hasn't been saved they will be
		for (Rescaler rescaler : threads) {
			if (!threadsPrinted.containsKey(rescaler)) {
				threadsPrinted.put(rescaler, false);
			}
			if (rescaler.isFinished() && !threadsPrinted.get(rescaler)) {
				threadsPrinted.put(rescaler, true);
				rescaler.outputStats(outputDirectory);
				rescaler.outputImages(outputDirectory);
			}
		}
		this.curNumThreads = count;
	}
	
	/**
	 * For loading all the images from a given directory
	 * Images will be loaded into a data structure and
	 * their names returned in a List collection
	 * @throws FileNotFoundException 
	 */
	private List<String> loadImages(File directory) throws FileNotFoundException {
		List<String> imageNames = new ArrayList<String>();
		
		for (File entry : directory.listFiles()) {			
			try {				
				if (entry.isFile()) {	
					//DEBUG
					//System.out.println(entry.getName());
					BufferedImage image = ImageIO.read(entry);					
					rawImages.put(entry.getName(), image);
					imageNames.add(entry.getName());
					entry.delete();		
				}							
			} catch (IOException i) {
				System.out.println("Problem loading:" + entry.getName());				
			} 
		}
		// Deleting the image directory
		directory.delete();

		// And returning the value
		return imageNames;
	}
}
