package pl.edu.agh.jtp.zad10.controller;

import pl.edu.agh.jtp.zad10.view.AppView;
import pl.edu.agh.jtp.zad10.model.IModel;

/**
 * Interface for controller from MVC pattern.
 * @author zbyszek
 *
 */
public interface IController {
	public void changeModel(IModel model);
	public void changeView(AppView view);
}
