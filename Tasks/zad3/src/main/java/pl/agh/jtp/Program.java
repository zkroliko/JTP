package pl.agh.jtp;

/** 	@author Zbigniew Krolikowski 
  	@version 1.0
*/

/** 	Main function. It's only purpose is to direct control
	@see ContactLoader
 */
public class Program {
	public static void main(String args[]) {
		CsvLoader loader = new CsvLoader();
		loader.load();
		loader.save();
		loader.printResults();
	}
}
