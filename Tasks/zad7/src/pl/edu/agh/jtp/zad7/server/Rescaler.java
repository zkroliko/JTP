package pl.edu.agh.jtp.zad7.server;

import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;

/**
 * Thread for executing the operation on images
 * @author Zbigniew Krolikowski
 *
 */
public final class Rescaler extends Thread {

	/** Data structures **/
	Map<String,BufferedImage> rawImages = new HashMap<String, BufferedImage>();
	Map<String,BufferedImage> scaledImages = new HashMap<String, BufferedImage>();
	private List<String> imageNames;
	
	/** Desired sizes of images */
	private int desiredHeight;
	private int desiredWidth;
	
	/** Control flags */
	private boolean finished;
	
	/** Execution time */
	private long executionTime;
	
	/**
	 * Constructor for executor
	 * @param images Map containing images
	 * @param imageDirectory Directory in which the images are contained
	 * @param imageNames Names of images which will be executed
	 */
	public Rescaler(Map<String, BufferedImage> rawImages,
			Map<String, BufferedImage> scaledImages,
			List<String> imageNames, int height, int width) {
		this.rawImages = rawImages;
		this.scaledImages = scaledImages;
		this.imageNames = imageNames;
		this.desiredHeight = height;
		this.desiredWidth = width;
		this.executionTime = 0;
		this.finished = false;
		this.start();
	}
	
	public void run() {
		// Remembering the start time
		long startTime = System.currentTimeMillis();
		// Executing
		execute();
		// Saving execution time
		executionTime = System.currentTimeMillis() - startTime;
		this.finished = true;
	}
	
	/** 
	 * Method for iterating thru the given names
	 * and scaling images
	 */
	private void execute() {	
		// So we won't get a second execution
		if (finished) {
			return;
		}
		// Will be iterating thru the images
		BufferedImage image;
		for (String imageName : imageNames) {
			image = rawImages.get(imageName);
			//DEBUG
			//System.out.println(imageName);
			scaledImages.put(imageName, rescaleImage(image));
		}				
	}
	
	/** 
	 * Rescales the image to desired height and width
	 * @param image
	 */
	private BufferedImage rescaleImage(BufferedImage image) {
		BufferedImage scaledImage = null;
        try {
                scaledImage = Scalr.resize(image, Mode.AUTOMATIC, this.desiredHeight, this.desiredWidth);
        } catch (IllegalArgumentException e1) {
                System.out.println("Scaling has not been succefull.");
                return null;
        } catch (ImagingOpException e1) {
                System.out.println("Scaling has not been succefull.");
                return null;
        }
        return scaledImage;
	}
	
	/**
	 * "Getter" for boolean isFinished
	 * 
	 * @return Whether the thread has finished execution
	 */
	public boolean isFinished() {
		return this.finished;
	}

	/**
	 * Getter for executionTime field
	 * 
	 * @return Time which has passed
	 */
	public long getExecutionTime() {
		return this.executionTime;
	}

	/**
	 * Getter for the images names
	 * 
	 * @return The list containing image names
	 */
	public List<String> getNames() {
		return this.imageNames;
	}
	
	/**
	 * Outputs the images into a given directory + images + hashCode directory
	 */
	public void outputImages(File directory) {
		// File destination = directory;
		File destination = new File(directory + File.separator + "images"
				+ File.separator + this.hashCode());
		destination.mkdirs();
		BufferedImage image;
		File outputFile;
		
		// We are iterating and saving to file
		for (String imageName : this.imageNames) {
			image = scaledImages.get(imageName);			
			outputFile = new File(destination + File.separator + imageName);
			try {
				ImageIO.write(image, "jpg", outputFile);
			} catch (IOException e) {
				System.out.println(imageName + " saving failed.");
			}
		}
	}
	
	/**
	 * Outputs the statistics into a file in the given directory
	 */
	public void outputStats(File directory) {
		File destination = new File(directory + File.separator + this.hashCode());
		try {
			PrintWriter saver = new PrintWriter(destination);
			saver.println("Time in nanoseconds for the task completion:" + (this.executionTime));
			saver.println("Number of images scaled:" + (this.imageNames).size());
			saver.close();
		} catch (IOException e) {
			System.out.println("One of the threads was unable to save it's stats to a file");
		}

	}
	
}
