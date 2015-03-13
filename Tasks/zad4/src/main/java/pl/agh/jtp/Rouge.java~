package pl.agh.jtp;

import java.util.Map;
import java.util.EnumMap;

/** 	@author Zbigniew Krolikowski 
  	@version 1.0
*/

/** 	Extension of hero class, a rouge.
	The statistic had been extended by totalGoldStolen.
	@see Hero
 */
public class Rouge extends Hero implements java.io.Serializable {

	public Rouge (String name, int level, int strength, int intelligence, int agility, int gold) {
		super(name, level, strength, intelligence, agility);
		setStat(Statistic.totalGoldStolen, gold);		
	}
/**	Prints stats from parent + extra 
	@see Hero
*/
	@Override
	public String toString () {
		return (super.toString() + "Gold stolen in career: " + this.getStat(Statistic.totalGoldStolen));
	}
	public String getType () {
		return "Rouge";	
	}
}
