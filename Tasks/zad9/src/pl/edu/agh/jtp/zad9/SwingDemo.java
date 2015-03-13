package pl.edu.agh.jtp.zad9;

public final class SwingDemo {

	/** This launches the demo
	 * @param args
	 */
	public static void main(String[] args) {		
		Particle particle = new Particle(0,0,0);
		
		AppView view = new AppView();
		AppController controller = new AppController(view, particle);
		@SuppressWarnings("unused")
		GraphController graphController = new GraphController(view, particle);
		
		particle.addController(controller);
		particle.addView(view);
	}

}
