package pl.edu.agh.jtp.zad10;

import java.util.LinkedList;
import java.util.List;

/**
 * Interface used for MVC Model class.
 * @author zbyszek
 *
 */
public abstract class IModel {
	
	private List<IView> views = new LinkedList<IView>();
	
	private List<IController> controllers = new LinkedList<IController>();
	
	/**
	 * Notifies the rest of the system that parameters have changed
	 */
	public void notifyMVC() {
		for (IView view : views) {
			view.updateValues();
		}
	}

	/**
	 * Adds a view to views list
	 * 
	 * @param view
	 *            IView which is going to be using this particle
	 */
	public void addView(IView view) {
		if (!views.contains(view))
			(this.views).add(view);
	}

	/**
	 * Removes the view from the list
	 * 
	 * @param view
	 *            IView which is no longer going to using this particle
	 */
	public void removeView(IView view) {
		(this.views).remove(view);
	}

	/**
	 * Adds a controller to controller list
	 */
	public void addController(IController controller) {
		(this.controllers).add(controller);
	}

	/**
	 * Removes the given controller from the list
	 */
	public void removeController(IController controller) {
		(this.controllers).remove(controller);
	}

}
