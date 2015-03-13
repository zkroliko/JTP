package pl.agh.jtp;

import java.util.Map;
import java.util.EnumMap;

/** 	@author Zbigniew Krolikowski 
  	@version 1.0
*/

/** 	Great mage of sorts.
	Worth to mark that the stats had been extended by beardLength.
	@see Hero
 */
public class Mage extends Hero implements java.io.Serializable {

	public Mage (String name, int level, int strength, int intelligence, int agility, int beardLength) {
		super(name, level, strength, intelligence, agility);
		setStat(Statistic.beardLength, beardLength);	
	}
/**
	@see Hero @see Statistic
*/
	public void growBeard (int value) {
		setStat(Statistic.beardLength, getStat(Statistic.beardLength) + value);
	}
/**
	@see Hero @see Statistic
*/
	public void trimBeard (int value) {
		setStat(Statistic.beardLength, getStat(Statistic.beardLength) - value);
	}
/**	Prints stats from parent + extra 
	@see Hero
*/
	@Override
	public String toString () {
		return (super.toString() + ", Proud beard owner of length: " + this.getStat(Statistic.beardLength));
	}
	public String getType () {
		return "Mage";	
	}
}
