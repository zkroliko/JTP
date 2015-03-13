package pl.edu.agh.jtp.zad10;

import java.util.NoSuchElementException;


/**
 * Factory method for making generic particles
 * @author Zbigniew Krolikowski
 *
 */
public class ParticleFactory {
	public static Particle makeParticle(String name) {
		switch (name) {
		case "electron":
			return (new Particle(1,0,0));
		case "proton":
			return (new Particle(0,1,0));
		case "neutron":
			return (new Particle(0,0,1));			
		case "alpha":
			return (new Particle(0,2,2));
		case "lithium":
			return (new Particle(0,6,3));
		case "carbon":
			return (new Particle(0,6,6));
		case "iron":
			return (new Particle(0,26,30));
		case "plutonium":
			return (new Particle(0,94,150));

		default: throw new NoSuchElementException("No particle like this in the particle factory");
	}
	}

}
