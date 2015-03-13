package pl.agh.jtp;

import java.util.Map;
import java.util.EnumMap;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

/** 	@author Zbigniew Krolikowski 
  	@version 1.0
*/

/** 	Class representing heroes in the program. 
	Any of the statistics are to in range from 0 to 200;

 */
public abstract class Hero implements java.io.Serializable {
	protected String name;
	protected Map<Statistic,Integer> stats = new EnumMap<Statistic,Integer>(Statistic.class);

/** 	Default constructor, extenden in subclasses */ 
	public Hero (String name, int level, int strength, int intelligence, int agility) {
		this.name = name;
		setStat(Statistic.level, level);
		setStat(Statistic.strength, strength);
		setStat(Statistic.intelligence, intelligence);
		setStat(Statistic.agility, agility);
	}
/** 	Getting the of the hero */
	public String getName() {
		return this.name;
	}
/** 	Mantaing and getting stats.
	Any stat should be in range from 0 to 200.
*/ 
	public int getStat (Statistic stat) {
		return stats.get(stat);
	}
	public void setStat (Statistic stat, int value) {
		value %= 200; //
		if (value >= 0) {
			stats.put(stat, value);
		} else {
			value = 0;
			stats.put(stat, value);
		}
	}
/**	Saves all data about the hero to a String 
	
*/ 
	@Override
	public String toString () {
		return ("Name: " + getName()
			+ ", " + getType()
			+ ", Level: " + Integer.toString(getStat(Statistic.level))
			+ ", STR: " + Integer.toString(getStat(Statistic.strength))
			+ ", INT: " + Integer.toString(getStat(Statistic.intelligence))
			+ ", AGI :" + Integer.toString(getStat(Statistic.agility))		
			);
	}
/** Abstact method for returning the type of the hero */
	public abstract String getType ();

/** Used for deserializng the hero*/
	public void serialize (String path) {
		try {
			FileOutputStream fileOut = 
			new FileOutputStream(path + getName() + "_" + getType() + "_" + getStat(Statistic.level) + ".ser");
       	  		ObjectOutputStream out = new ObjectOutputStream(fileOut);
         		out.writeObject(this);
         		out.close();
         		fileOut.close();
         		System.out.println("Serialized data is saved in " +
					path + getName() + "_" + getType() + "_" + getStat(Statistic.level) + ".ser");	
		} catch(IOException i)	{
          		i.printStackTrace();
      		}
	}
}
