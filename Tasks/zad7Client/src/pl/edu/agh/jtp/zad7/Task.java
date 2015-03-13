package pl.edu.agh.jtp.zad7;

import java.io.File;

public class Task implements java.io.Serializable {

	/**
	 * Serial UID for this class
	 */
	private static final long serialVersionUID = 1L;
	private File imageDirectory;
	private int desiredHeight;
	private int desiredWidth;
	/**
	 * Constructor
	 * @param file Name of the directory containing images
	 * @param count How many threads should be used
	 */
	public Task(File file, int height, int width) {
		this.imageDirectory = file;
		this.desiredHeight = height;
		this.desiredWidth = width;
	}
	
	/**
	 * Getter for the image directory
	 * @return
	 */
	public File getImageDirectory () {
		return this.imageDirectory;
	}	
	
	/**
	 * Classic getter for height field
	 * @return Height
	 */
	public int getDesiredHeight () {
		return this.desiredHeight;
	}
	
	/**
	 * Classic getter for width field
	 * @return Width
	 */
	public int getDesiredWidth () {
		return this.desiredWidth;
	}
}
