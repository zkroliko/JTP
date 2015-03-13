package pl.edu.agh.jtp.zad7.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

import pl.edu.agh.jtp.zad7.Task;

/**
 * Client class for loading a group of images from
 * a directory and sending them to the server with
 * the specified size parameters.
 * @author Zbigniew Krolikowski
 *
 */
public final class Client {
	private File obsDirectory;
	
	private File serverCommDirectory;
	
	// For console input
	Scanner scan;
	
	/**
	 * Constructor 
	 * @param obsDirectory The directory from which the files are 
	 * going to be sent to the server
	 */
	public Client (File obsDirectory, File commDirectory) {
		scan = new Scanner(System.in);
		this.obsDirectory = obsDirectory;
		this.serverCommDirectory = commDirectory;	
		// Now checking the directories
		try {
			validateDirectories();
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Client will terminate");
			return;
		}
		workLoop();
	}
	
	/**
	 * Constructor without parameters
	 */
	public Client () {
		scan = new Scanner(System.in);
		System.out.println("Specify the image directory");
		this.obsDirectory = new File(scan.nextLine());
		System.out.println("Specify the directory used for communcation with the server");
		this.serverCommDirectory = new File(scan.nextLine());	
		// Now checking the directories
		try {
			validateDirectories();
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Client will terminate");
			return;
		}
		workLoop();
		scan.close();
	}
	
	/**
	 * Checks whether the obs and comm directories exist
	 */	
	private void validateDirectories () throws IOException{
		if (!obsDirectory.isDirectory()) {
			throw new IOException("Image directory is not valid");			
		}
		if (!serverCommDirectory.isDirectory()) {
			throw new IOException("Communication directory is not valid");
		}
	}
	
	/**
	 * Controls the execution
	 */
	private void workLoop () {
		while (true) {
			grabAction(input());			
		}
	}
	/**
	 * Main function of client, copying images, making a Task
	 * serialized object to communicate with the server.
	 */
	private void grabAction(int[] sizes) {
		// Name of the folders will be current dates and hours
		String time = (Long.toString(System.currentTimeMillis()));
		copyFiles(obsDirectory, serverCommDirectory, new File(time));
		Task task = new Task(new File(serverCommDirectory + File.separator + time), sizes[0], sizes[1]);
		serializeTask(task, serverCommDirectory);
	}
	
	/**
	 * Method for getting the desired image sizes from user
	 * 
	 * @return Int array containing height and width
	 */
	private int[] input() {
		System.out.println("When ready input: <height> <width>");
		String[] input;
		int[] sizes = new int[2];
		// Now asking for the width and height
		boolean goodInput = false;
		while (!goodInput) {
			goodInput = true;
			input = (scan.nextLine()).split(" ");
			// Checking whether there are only two arguments
			if (input.length != 2) {
				System.out.println("Wrong arguments.");
				System.out.println("Provide: <height> <width>");
				goodInput = false;
				continue;
			}
			try {
				sizes[0] = Integer.parseInt(input[0]);
				sizes[1] = Integer.parseInt(input[1]);
			} catch (NumberFormatException n) {
				goodInput = false;
			}
		}
		// Now let's say we got 2 integers in the sizes[]
		return sizes;
	}

	/**
	 * Serializes a task for the server
	 * 
	 * @return
	 */
	private void serializeTask(Task task, File targetDirectory) {
		try {
			FileOutputStream fileOut = new FileOutputStream(new File(
					targetDirectory + File.separator
							+ (task.getImageDirectory()).getName() + ".ser"));
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(task);
			out.close();
			fileOut.close();
		} catch (IOException f) {
			System.out.println("Task serialization failed");
		}
	}
	
	/**
	 * Copies files from observed directory into a new directory
	 * @param sourceDirectory
	 * @param targetDirectory
	 */
	private void copyFiles (File sourceDirectory, File targetDirectory, File folder) {
		File destFile;
		for (File entry : sourceDirectory.listFiles()) {		
			try {
			destFile = new File(targetDirectory + File.separator + folder + File.separator + entry.getName());
			FileUtils.copyFile(entry,destFile);
			} catch (IOException e) {
				System.out.println("Copying file failed");
			}
		}
	} 

}
