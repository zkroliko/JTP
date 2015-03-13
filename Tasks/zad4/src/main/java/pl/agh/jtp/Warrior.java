package pl.agh.jtp;

import java.util.Map;
import java.util.EnumMap;

/** 	@author Zbigniew Krolikowski 
  	@version 1.0
*/

/** 	Mighty warrior, thirsty for blood.
	@see Statistic
	@see Hero
 */

public class Warrior extends Hero implements java.io.Serializable {

	public Warrior (String name, int level, int strength, int intelligence, int agility, int maxKillStreak) {
		super(name, level, strength, intelligence, agility);
		setStat(Statistic.maxKillStreak, maxKillStreak);		
	}

	public void submitRecord (int newKillStreak) {
		if (newKillStreak > getStat(Statistic.maxKillStreak))
			setStat(Statistic.maxKillStreak, newKillStreak);
	}
/**	Prints stats from parent + extra 
	@see Hero
*/
	@Override
	public String toString () {
		return (super.toString() + ", Maximal KillStreak: " + this.getStat(Statistic.maxKillStreak));
	}
	public String getType () {
		return "Warrior";	
	}
}
