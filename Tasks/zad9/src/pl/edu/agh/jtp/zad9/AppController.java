package pl.edu.agh.jtp.zad9;

/**
 * Controller from MVC Pattern.
 * @author Zbigniew Krolikowski
 *
 */
public final class AppController implements Controller {
	
	/** View from MVC */
	@SuppressWarnings("unused")
	private AppView view;
	
	/** Model from MVC */
	private Particle particle;
	
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
	public void changeParticle(Particle particle) {
		this.particle = particle;
	}
	
	/**
	 * Changes the objects view to a new one
	 */	
	public void changeView (AppView view) {
		this.view = view;
	}
	
	
	/** Informs the controller about the change of particle via buttons
	 *  Uses a String to send a name of a new particle
	 */
	public void changeParticle(String name) {
		Particle tempParticle = ParticleFactory.makeParticle(name);
		if (tempParticle != null) {
			particle.changeComponents(tempParticle.getComponents());
		}
	}
	
	/** Informs the controller about the change of particle values
	 *  Uses int to send a configuration of a new particle
	 */
	public void particleValueChanged(int val, ComponentType type) {
		// This is for feature build up of the app
	}
	
	/** Informs the controller about the change of particle velocity
	 *  Uses int to send the velocity value
	 */
	public void velocityChanged(double val) {
		
	}
	
	/** Informs the controller about the change of particle kinetic energy
	 *  Uses int to send the new energy value
	 */
	public void kinEChanged(double val) {
		
	}
	

}
