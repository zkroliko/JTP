package pl.edu.agh.jtp.zad6;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * For making images, and exporting them
 * @author Zbigniew Królikowski
 *
 */
public final class ImageMaker {
	private int width = 800;
	private int height = 600;
	private RenderedImage image;
	
	public void make(String fileName, Map<String, FragmentChecker> threads, List<String> names) {		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		export(fileName, image);
	}
	
	private void export (String fileName, RenderedImage image) {
		try {
		    File outputfile = new File(fileName + ".png");
		    ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {
		    
		}
	}
}
