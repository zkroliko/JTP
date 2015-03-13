package pl.edu.agh.jtp.zad8;

public final class SwingDemo {

	/** This launches the demo
	 * @param args
	 */
	public static void main(String[] args) {		
		Particle particle = new Particle(0,0,0);
		
		AppView view = new AppView();
		@SuppressWarnings("unused")
		AppController controler = new AppController(view, particle);
	}

}
