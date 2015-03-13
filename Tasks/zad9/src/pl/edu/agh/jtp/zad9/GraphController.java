package pl.edu.agh.jtp.zad9;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public final class GraphController implements Controller {

	/** View from MVC */
	@SuppressWarnings("unused")
	private AppView view;
	
	/** Model from MVC */
	private Particle particle;
	
	/** Data */ 
	private ArrayList<Double> energies;
	private ArrayList<Double> velocities;
	
	/** File to save CSV */
	private File exportFile;
	
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
	public void changeParticle(Particle particle) {
		this.particle = particle;
	}
	
	/**
	 * Changes the objects view to a new one
	 */	
	public void changeView (AppView view) {
		this.view = view;
	}
	
	/**
	 * Generates data from given start and end energy and fills the data structure
	 */
	public void generateDataFromVelocity (double start, double end) {
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
		// Restoring starting energy
		particle.changeVelocity(fixVelocity);
	}
	
	/**
	 * Generates data from given start and end velocity and fills the data structure
	 */
	public void generateDataFromKEnergy (double start, double end) {
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
	 * Exports data into CSV format file
	 */
	public void export () { 
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
			System.out.println("Saving to CSV file failed");
		}
		
	}
	
	/**
	 * Informs the controller that file has changed
	 * @param file File to save the CSV data
	 */
	public void changeFile(File file) {
		this.exportFile = file;
	}	
}
