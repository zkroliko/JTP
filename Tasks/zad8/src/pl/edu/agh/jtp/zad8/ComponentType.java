package pl.edu.agh.jtp.zad8;

/**
 * Representing subatomic particles as electron, proton, neutron
 * @author Zbigniew Krolikowki
 * Mass is in units of Mev/c^2
 *
 */
public enum ComponentType {
	ELECTRON(0.510998928), 
	PROTON(938.272046),
	NEUTRON(939.5653782);
	
	double mass;

	ComponentType (double mass) {
		this.mass = mass;
	}
}
