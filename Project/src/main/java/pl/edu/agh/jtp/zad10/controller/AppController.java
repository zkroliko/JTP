package pl.edu.agh.jtp.zad10.controller;

import pl.edu.agh.jtp.zad10.view.AboutView;
import pl.edu.agh.jtp.zad10.model.Log;
import pl.edu.agh.jtp.zad10.model.Particle;
import pl.edu.agh.jtp.zad10.model.ParticleFactory;
import pl.edu.agh.jtp.zad10.model.IModel;
import pl.edu.agh.jtp.zad10.view.AppView;
import pl.edu.agh.jtp.zad10.view.LogView;
import pl.edu.agh.jtp.zad10.view.StatsView;

/**
 * IController from MVC Pattern.
 * @author Zbigniew Krolikowski
 *
 */
public final class AppController implements IController {
	
	/** IViews from MVC */
	private AppView view;
	private AboutView aboutView;
	private StatsView statsView;
	private LogView logView;
	
	/** IModel from MVC */
	private Particle particle;
	private Log log;
	
	public AppController (AppView view, Particle particle) {
		this.view = view;
		this.particle = particle;
		view.setAppControler(this);
		view.setParticle(this.particle);
		view.show();
	}
	
	/**
	 * Changes the objects particle to a provided one
	 */
	public void changeModel(IModel model) {
		this.particle = (Particle)particle;
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
	
	/** Informs the controller about the change of particle via buttons
	 *  Uses a String to send a name of a new particle
	 */
	public void particleChangeRequested(String name) {
		log.addEvent("Requsition for change of particle to: " + name);
		Particle tempParticle = ParticleFactory.makeParticle(name);
		if (tempParticle != null) {
			particle.changeComponents(tempParticle.getComponents());
		}
	}
	
	/**
	 * Informs the controller that opening of the About window 
	 * has been requested
	 */
	public void aboutWindowrequested() {
		log.addEvent("User wanted an About window to be open");
		if (this.aboutView == null) {
			this.aboutView = new AboutView();
			this.aboutView.makeVisible();
		} else {
			this.aboutView.makeVisible();
		}
	}
	
	/**
	 * Informs the controller that opening of the Statistic window 
	 * has been requested
	 */
	public void statisticWindowRequested() {
		log.addEvent("User wanted Statistic window to be open");		
		if (this.statsView == null) {
			this.statsView = new StatsView(view.getStatistics());
			this.statsView.makeVisible();
		} else {
			this.statsView.makeVisible();
		}
	}
	
	/**
	 * Informs the controller that opening of the Log window 
	 * has been requested
	 */
	public void logWindowRequested() {
		log.addEvent("User wanted Logger window to be open");
		if (this.logView == null) {
			this.logView = new LogView(this.log);
			this.logView.makeVisible();
		} else {
			this.logView.makeVisible();
		}
	}
	
	/** Informs the controller about the change of particle velocity
	 *  Uses int to send the velocity value
	 */
	public void velocityChangeRequested(double value) {
		log.addEvent("User wanted to change particle's velocity to: " + value);
		particle.changeVelocity(value);
	}
	
	/** Informs the controller about the change of particle kinetic energy
	 *  Uses int to send the new energy value
	 */
	public void energyChangeRequested(double value) {
		log.addEvent("User wanted to change particle's energy to: " + value);
		particle.changeKinEnergy(value);	
	}

	/**
	 * Informs the controller that termination of the process has been requested
	 */
	public void exitRequested() {
		log.addEvent("Exit requested");
		System.exit(0);
	}
}
