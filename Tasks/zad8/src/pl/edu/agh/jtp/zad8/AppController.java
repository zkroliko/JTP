package pl.edu.agh.jtp.zad8;

/**
 * Controller from MVC Pattern.
 * @author Zbigniew Krolikowski
 *
 */
public final class AppController {
	
	/** View from MVC */
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
	
	/** Informs the controller about the change of particle via buttons
	 *  Uses a String to send a name of a new particle
	 */
	public void buttonPressed(String name) {
		Particle candidateParticle = ParticleFactory.makeParticle(name);
		if (candidateParticle != null) {
			particle = candidateParticle;
			view.setParticle(particle);
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
	public void velocityChanged(int val) {
		
	}
	
	/** Informs the controller about the change of particle kinetic energy
	 *  Uses int to send the new energy value
	 */
	public void kinEChanged(int val) {
		
	}
	

}
