
/** It is not really necessary but what is java with only one class? */
class Number {
	/* Two integer fields and one String containing decValue in given base */  
	int decValue;
	int curBase; //other presumably more suitable name: radix
	String representation;

	/** Here are some constructors, can't thay they all serve a purpose */
	/**Basic constructor, base set to decimal, and some representation is up
	 * Programmmer could define a new Number() and try to use it initialized, with the base of 0
	 * */ 
	Number () {
		curBase = 10;
		representation = Integer.toString(decValue,curBase);	
	}
	/**Constructor, default radix is decimal*/ 
	Number (int value) {
		decValue = value;
	       	curBase = 10;
		representation = Integer.toString(decValue,curBase);	
	}
	/**Construct from value and given base*/
	Number (int value, int base) {
		decValue = value;
	       	curBase = base;
		representation = Integer.toString(decValue,curBase);	
	}
	/** Let's say that is enought of these */
	
	/** Method for changing the base(or radix as Oracle like to call it)*/
	void changeValue (int value) {
		decValue = value;
		representation = Integer.toString(decValue,curBase);		
	}
	/** Quite obvious, of course we already tested for the correct base value, since alphabet doesn't go on forever */
	void changeBase(int base) {
		curBase = base;
		representation = Integer.toString(decValue,curBase);	
	}
	/** This is quite basic printing really*/
	void printRep() {
		System.out.println(this.representation);
	}
}

/**The Primary class */

public class BaseChanger {
	/** Some messages for command line usage */
	static final String msgBasic="Usage BaseChanger: [OPTION]... [RADIX], [DECIMAL NUMBER] \nOr: BaseChanger -m [RADIX] <[NUMBER]> (multiple) \n";
	static final String msgHelpSuggest="For help write 'BaseChanger -h'\n";
	static final String msgWarn="Are you mad?! The arguments must be wrong, at least one of them.\n";

	public static void main(String[] args) {
		/**Control flow concerning options 
		 * The basic disticion is by how many arguments do we have
		 * Depeding on what's given program will:
		 * -display message concering the wrong arguments
		 * -displa help
		 * -perform function for one base and one number
		 * -perform many baseChanges on many numbers and the base is given ony once in args[1]
		*/

		/**No arguments at all */
		if (args.length == 0) {
			System.out.print(msgBasic+msgHelpSuggest);
			return;
		}

		/**One argument and it's -h*/
		if ((args.length == 1) && (args[0].equals("-h"))) { 
			displayHelp();
			return;
		}

		/**Checking whether do we have multiple arguments 
		 * If there are multiple it's the default function, or multiple,
		 * or someone is poping the programs nose with bad arguments and will receive a message
		 * */
		if (args[0].equals("-m")) {
			if (checkArgs(args, 2, args.length)) {
				workMultipleForOneRadix(args, Integer.parseInt(args[1]));
				return;
			} else {
				displayWarning();
				displayHelp();
				return;
			}
		}
		/** Default flow of the program, for two arguments */
		if ((args.length == 2) && checkArgs(args, 0, args.length)) {
			workDefault(args);
			return;
		} else {
			displayWarning();
			displayHelp();
			return;
		}
	}


	/** Displaying some help*/
	public static void displayHelp() {
		System.out.print(msgBasic);
	}

	/** And warnings */
	public static void displayWarning() {
		System.out.print(msgWarn);
	}

	/** When one radix and many nubmers are given */
	public static void workMultipleForOneRadix(String[] args, int base) {
		Number numb = new Number();
		for (int i = 1; i < args.length; i++) {
			numb.changeValue(Integer.parseInt(args[i]));
			numb.changeBase(base);
			numb.printRep();
		}		
	}

	/** Simple one number and base */
	public static void workDefault(String[] args) {
		Number numb = new Number(Integer.parseInt(args[1]));
		numb.changeBase(Integer.parseInt(args[0]));
		numb.printRep();	
	}

	/** Checking whether the input is good */
	public static boolean checkArgs(String[] args, int start, int end) {
		for (int i = start; i < end; i++ ) {
			if (!isNumber(args[i])) {
				return false;
			}
		}
		return true;
	}

	/** Testing one string for being parseable to a integer */
	public static boolean isNumber(String str) {  
	     	try {  
			int d = Integer.parseInt(str);  
		}  
			catch( NumberFormatException nfe)  
		{  
			return false;  
		}  
			return true; 
	}
}
