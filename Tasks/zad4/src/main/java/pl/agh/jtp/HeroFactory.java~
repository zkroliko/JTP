package pl.agh.jtp;

/** 	@author Zbigniew Krolikowski 
  	@version 1.0
*/

/** 	Factory design pattern for making heroes
	@see Hero
 */
public class HeroFactory {
	public static Hero makeHero (String heroClass, String name, int level) {
		switch (heroClass) {
			case "warrior": return new Warrior (name, level % 60, 6 + level * 3, 4 + level * 2, 4 + level * 2, level);
			case "mage" : return new Mage (name, level % 60, 4 + level * 2, 6 + level * 3, 4 + level * 2, level / 2);
			case "rouge": return new Rouge (name, level % 60, 4 + level * 2, 4 + level * 2, 6 + level * 3, level * 10);
			default: return new Warrior ("noob", 1, 4, 4, 4, 0);	 		
		}	
	}
}
