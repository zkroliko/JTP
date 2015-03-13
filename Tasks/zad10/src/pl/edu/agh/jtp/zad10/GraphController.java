package pl.edu.agh.jtp.zad10;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public final class GraphController implements IController {

	/** IView from MVC */
	private AppView view;
	
	/** IModel from MVC */
	private Particle particle;
	private Log log;
	
	/** Data */ 
	private ArrayList<Double> energies;
	private ArrayList<Double> velocities;
	
	/** File to save CSV */
	private File exportFile;
	
	/** Chart to show in the view */
	private JFreeChart chart;
	
	public GraphController (AppView view, Particle particle) {
		this.view = view;
		this.particle = particle;
		view.setGraphControler(this);
		view.setParticle(this.particle);
		view.show();
	}
	
	/**
	 * Changes the objects particle to a provided one
	 */
	public void changeModel(IModel model) {
		this.particle = (Particle)model;
		particle.addController(this);
	}
	
	/**
	 * Changes the objects view to a new one
	 */	
	public void changeView (AppView view) {
		this.view = view;
	}
	
	/**
	 * Changes the log object
	 */
	public void setLog (Log log) {
		this.log = log;
	}
	
	/**
	 * Generates data from given start and end energy and fills the data structure
	 */
	public void generateDataFromVelocity (double start, double end) {
		log.addEvent("Generating data from " + start + " to " + end);
		// Fist we need to save the particle velocity so it can be restored
		double fixVelocity = particle.getVelocity();
		energies = new ArrayList<Double>();
		velocities = new ArrayList<Double>();
		double step = Math.abs((end - start) / 100);
		while (start < end && start < 1.0) {
			particle.changeVelocity(start);	
			velocities.add(start);				
			energies.add(particle.getKinEnergy());
			start += step;
		}	
		log.addEvent("Generating data successfull");
		// Restoring starting energy
		particle.changeVelocity(fixVelocity);
	}
	
	/**
	 * Generates data from given start and end velocity and fills the data structure
	 */
	public void generateDataFromKEnergy (double start, double end) {
		log.addEvent("Generating data from " + start + " to " + end);
		// Fist we need to save the particle energy so it can be restored
		double fixEnergy = particle.getKinEnergy();
		energies = new ArrayList<Double>();
		velocities = new ArrayList<Double>();
		double step = Math.abs((end - start) / 1000);
		while (start < end) {				
			particle.changeKinEnergy(start);
			energies.add(start);
			velocities.add(particle.getVelocity());
			start += step;
		}
		log.addEvent("Generating data successfull");
		// Restoring starting energy
		particle.changeKinEnergy(fixEnergy);
	}
	
	/** 
	 * Returns the array containing energies 
	 */
	public Double[] getEnergies() {
		Double list[] = new Double[energies.size()];
		for (int i = 0; i < energies.size(); i++) {
			list[i] = energies.get(i);
		}
		return list;
	}
	
	/** 
	 * Returns the array containing velocities 
	 */
	public Double[] getVelocities() {
		Double list[] = new Double[velocities.size()];
		for (int i = 0; i < velocities.size(); i++) {
			list[i] = velocities.get(i);
		}
		return list;
	}
	
	/**
	 * Makes a chart to show in the view
	 */
	private void makeChart(String firstVariable) {
		String firstName;
		String secondName;
		Double[] first;
		Double[] second;		
		// Logic for choosing the axis right
		if (firstVariable.equals("velocity")) {
			firstName = "Energy";
			secondName = "Velocity";
			first  = getEnergies();
			second = getVelocities();
		} else {		
			firstName = "Velocity";
			secondName= "Energy";
			first  = getVelocities();
			second = getEnergies();
		}		
		XYSeries series = new XYSeries(firstName + " / " + secondName);
		
		for (int i = 0; i < Math.max(first.length, second.length); i++) {
			series.add(first[i], second[i]);
		}
		XYDataset xyDataset = new XYSeriesCollection(series);
		JFreeChart chart = ChartFactory.createXYLineChart(firstName + " / "
				+ secondName + " chart", firstName, secondName, xyDataset,
				PlotOrientation.HORIZONTAL, true, true, true);
		// Now setting the chart
		this.chart = chart;
		log.addEvent("Chart of " + firstVariable +" has been drawn.");
	}
	
	/**
	 * Responds to need for a chart to be shown in the view
	 */	
	public void chartRequested(double start, double end, String firstVariable) {
		log.addEvent("Requested a " + firstVariable + " chart from "+ start + " to " + end);
		if (firstVariable.equals("velocity")) {
			generateDataFromVelocity(start, end);
		} else {
		generateDataFromKEnergy(start, end);
		}
		makeChart(firstVariable);
		view.showChart(chart);
	}
	
	/**
	 * Exports data into CSV format file
	 */
	public void export () { 
		log.addEvent("Requested CSV data to be exported into:" + this.exportFile.getAbsolutePath());
		// First checking whether we got anything in the lists
		if (velocities == null || energies == null) {
			return;
		}
		try {
			PrintWriter writer = new PrintWriter(exportFile);
			writer.append("energy,velocity\n");
			for (int i = 0; i < Math.max(energies.size(), velocities.size()); i++) {
				writer.append(Double.toString(energies.get(i)));
				writer.append(",");
				writer.append(Double.toString(velocities.get(i)));
				writer.append("\n");
			}
			writer.close();
		} catch (IOException e ) {
			log.addEvent("Saving to CSV file failed");
			log.addEvent("Export to: " + this.exportFile.getAbsolutePath() + " failed");
		}
		log.addEvent("Export to: " + this.exportFile.getAbsolutePath() + " successfull.");
	}
	
	/**
	 * Informs the controller that file has changed
	 * @param file File to save the CSV data
	 */
	public void changeFile(File file) {
		this.exportFile = file;
	}	
}
