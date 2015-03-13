package pl.edu.agh.jtp.zad10;

/**
 * Interface for controller from MVC pattern.
 * @author zbyszek
 *
 */
public interface IController {
	public void changeModel(IModel model);
	public void changeView(AppView view);
}
