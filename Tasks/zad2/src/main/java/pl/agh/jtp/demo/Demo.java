/**
 * TASK2, JTP2, Wednesday 9:35
 * @author "Zbigniew Kroliowski"
 */


package pl.agh.jtp.demo;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**Exception if given aircraft cannot be found on the airport */ 
class NotFoundException extends Exception {
	public NotFoundException () {super();}
}
/**Exception if wrong action has been given*/
class WrongActionException extends Exception {
	public WrongActionException () {super();}
}

/**Contains aircraft and locations, methods for their management*/
final class Airport {
	private String name;
	private List<Location> places;
	private List<Aircraft> stock;

	/* Constuctors */
	public Airport(String name, List<Location> places, List<Aircraft> stock) {
		this.name = name;
		this.places = places;
		this.stock = stock;
	}
	/* Methods */
	/**Add a new aircraft to the stock*/
	public final void addAircraft(Aircraft aircraft, Type desiredType) {

		for(Location loc : places) { //looking for a empty spot
			if (loc.getOccupant() == null && desiredType.equals(loc.getType())) {
				loc.setOccupant(aircraft);
				aircraft.setLocation(loc);
				break; //found a empty spot
			}
		}
		stock.add(aircraft);
		//tutaj jakis exception
	}	
	/**Changes given's aircraft action, argument is aircraft's number*/
	public final void setAction(int aircraftNumber, Action action) throws NotFoundException, WrongActionException {
		Aircraft wantedAircraft = null; //finding the aircraft with given number
		for (Aircraft aircraft : stock) {
			if ((aircraft.getNumber()) == aircraftNumber) {
				wantedAircraft = aircraft;				
			}
		}
		if (wantedAircraft == null){
			throw new NotFoundException();
		}
		wantedAircraft.setAction(action);

	}
	/**Chanes given's aircraft destination, input is integer*/
	public final void setDestination(int aircraftNumber, int locationNumber) throws NotFoundException {
		Aircraft wantedAircraft = null; //finding the aircraft with given number
		for (Aircraft aircraft : stock) {
			if ((aircraft.getNumber()) == aircraftNumber) {
				wantedAircraft = aircraft;				
			}
		}
		if (wantedAircraft == null){
			throw new NotFoundException();
		}

		Location wantedLocation = null;
		for (Location loc : places) { //finding a location with given number
			if ((loc.getNumber()) == locationNumber) {
				wantedLocation = loc;
			}
		}
		if (wantedLocation == null) {
			// throw new NotFoundExeption nf
		}
		wantedAircraft.setDestination(wantedLocation);
	}
	/** Executes all aircraft's currently set action */
	public final void execute () {
		for(Aircraft a : stock) {
			switch (a.getAction()) {
				case WAIT: 
					break;
				case MOVE:
					a.move(a.getDestination());
					break;
				case START:
					//not done yet
					a.start();	
					break;
			}
		}
	}
	/** Prints out all the planes and their actions, looks out for not set fields */
	public final void print(){
		System.out.println("LOC TYPE  LOC_NR  OCC_NR   OCC_MAN  OCC_ORD    OCC_DEST");
		for (Location loc : places) {
			try {
			System.out.print( loc.getType() + "\t" + loc.getNumber() + "\t");
			System.out.print((loc.getOccupant()).getNumber() + "\t" + (loc.getOccupant()).getManufacturer() + "\t" + (loc.getOccupant()).getAction() + "\t");
			if ( (loc.getOccupant()).getDestination() != null) {
				System.out.print( (loc.getOccupant()).getDestination());
			}
			else {
				System.out.print("not specified"); //if there is no destination then write this
			}
			System.out.println(); // next line
			}
			
			catch (NullPointerException n) {
					System.out.println("\t Not set \t");
			}
		}
	}
}
/**Enum containg the type of Locartion */
enum Type {
		PARKING, RUNWAY, HELIPAD	
}

class Location {
	private int number;
	private Type type;
	private Aircraft occupant;

	/* Constructors */
	public Location(int number, Type type) {
		this.type = type;
		this.occupant = null;
		this.number = number;
	}
	/* Methods */
	/**Get the location's nubmer */
	public int getNumber () {
		return this.number;
	}
	/** Gets the aircraft occupying this space*/ 
	public Aircraft getOccupant(){
		return this.occupant;
	}
	/** Sets a new aircraft to occupy this space*/
	public void setOccupant(Aircraft occupant){
		this.occupant = occupant;
	}
	/**Gets a type of location */
	public Type getType() {
		return this.type;
	}
	//Type is not going to be changed after construction
}
/**Enum containing action for the aicraft*/
enum Action {
	WAIT, MOVE, START 
}
/**That is what we will be having at the airport*/
abstract class Aircraft {
	private final int number; //number shouldn't change, it's from 1 to 999
	protected String manufacturer;
	protected Action action;
	protected Location location;
	protected Location destination;
	/*Constructors, only default fields*/
	public Aircraft() {
		Random generator = new Random();
		this.number = Math.abs(generator.nextInt())%1000; // serial number is quite random
		this.action = Action.WAIT;
	}
	/*Methods*/
	public Action getAction() {
		return this.action;
	}
	/**Set's aicrafts action, START is specific for subclasses*/
	public void setAction(Action action) throws WrongActionException {
		if(!action.equals(Action.START)) {
			this.action = action;
		}
	}
	/**Moves the aircraft to a given loaction */
	public void move(Location destination) {
		this.location.setOccupant(null); //zwalniamy aktualnie zajmowane miejsce
		this.location = destination;
		destination.setOccupant(this);
		this.destination = null; 
	}
	/**Aircraft stats and free's the spot */
	public void start () {
		this.location.setOccupant(null); //zwalniamy aktualnie zajmowane miejsce
	}
	/**Gets aircrafts Number*/
	public int getNumber() {
		return this.number;
	}
	/**Gets aircrafts Type*/
	public String getManufacturer() {
		return this.manufacturer;
	}
	/**Gets aircrafts location */ 
	public Location getDestination() {
		return this.destination;
	}
	/**Sets aircrafts Destination*/
	public void setDestination(Location destination) {
		this.destination = destination;
	}
	/**Gets aircrafts location*/
	public Location getLocation() { 
		return this.location;
	}
	/**Sets aircrafts location*/
	public void setLocation(Location location) {
		this.location = location;
		boolean good = false;
	}
}

class Plane extends Aircraft {
	/* Constuctor */
	public Plane() {
		super();			
		this.manufacturer = "Airbus"; //why not?
	}
	/* Methods */
	@Override
	/**Same as parent just has to be RUNWAY*/
	public void setAction(Action action) throws WrongActionException {
		super.setAction(action);
		if ((action.equals(Action.START)) && !((this.location).equals(Type.RUNWAY))) { 
			throw new WrongActionException();
		}
	}
}

class Helicopter extends Aircraft {
	/* Constuctor */
	public Helicopter() {
		super();
		this.manufacturer = "Bell"; // because
	}
	/* Methods */
	@Override
	/**Same as parent just has to be HELIPAD*/
	public void setAction(Action action) throws WrongActionException {
		super.setAction(action);
		if ((action.equals(Action.START)) && !((this.location).equals(Type.HELIPAD))) { 
			throw new WrongActionException();
		}
	}
}
/**Factory method design pattern for creating aicraft*/
class AircraftFactory { //factory making aircrafts
	final public static Aircraft createAircraft(String name){
		Aircraft aircraft = null;
		if (name.equals ("Airbus")) {
		return new Plane(); 
		}
		if (name.equals("Bell")) {
		return new Helicopter();
		}
		if (name.equals("random")) {	
			Random generator = new Random();
			boolean choice = generator.nextBoolean();
			if (choice) {
				return new Plane(); 
			} else {
				return new Helicopter();
			}
		}
		throw new IllegalArgumentException("No such aircraft");
	}
}

public class Demo {
	public static void main(String[] args) {
		System.out.println("Welcome to the airport's ground control.");

		Airport balice = initAirport("Balice");
		programLoop(balice);		
	}
	/**Sets up a basic airport for demonstation needs*/
	static Airport initAirport(String name) {

		List<Location> locations = new ArrayList<Location>(); //setting up the location		
		for (int n = 1; n < 13; n++) {
			locations.add(new Location(n,Type.PARKING));			
		}
		for (int n = 13; n < 15; n++) {
			locations.add(new Location(n,Type.RUNWAY));
		}
		for (int n = 15; n < 17; n++) {
			locations.add(new Location(n,Type.HELIPAD));
		}
		List<Aircraft> stock = new ArrayList<Aircraft>(); //setting up the aircraft's
		Airport airport = new Airport(name, locations, stock);
		for (int i = 0; i<10; i++) {
			airport.addAircraft((AircraftFactory.createAircraft("random")),Type.PARKING);
		}
			
		return airport;
	}	

	/**Program's main loop, trap kills it*/
	static void programLoop(Airport airport) {
		while (true) { //waiting for a kill signal
			airport.print();	
			inputAndIssue(airport);		
			airport.print();	
			inputAndIssue(airport);		
			airport.execute();
		}
	}

	/**Parses input and issues a given command to the airport given as a arguemnt */
	static void inputAndIssue(Airport airport) {
		int aircraftNumber = askNumber("Provide a valid number of the aircraft");
		Action action =	askAction();
		try {
			airport.setAction(aircraftNumber, action); //changing action of a given aircraft
		} catch (NotFoundException nf) {
			System.out.println(" \t You must have typen in wrong number \t");
		} catch (WrongActionException wa) {
			System.out.println(" \t You cannot perform this action from this point \t");
		}
		/*Part specified for move action*/
		if (action.equals(Action.MOVE)) {
			int locationNumber = askNumber("Provide a valid number of location you want the aircraft to be moved");
			try {
				airport.setDestination(aircraftNumber, locationNumber);
			} catch (NotFoundException nf ) {
				System.out.println(" \t You must have typen in wrong number A\t");
			}
		}		
	}

	/**Gets a number from 1 to 999*/
	static int askNumber(String memo) {
		Scanner scanner = new Scanner(System.in);
		int number = 0; //initializing just for the compiler
		boolean good = false;
		while (!good) {
			System.out.println(memo);
			good = true;
			String line = scanner.nextLine();
			try {
				number = Integer.parseInt(line);
			}
			catch (NumberFormatException nfe) {
				good=false;			
			}
		}
		return number;
	}

	/**Gets an action WAIT, MOVE, START*/ 
	static Action askAction() {
		Scanner scanner = new Scanner(System.in);
		Action action = Action.WAIT; //just for the compiler
		boolean good = false;
		while (!good) {
			System.out.println("Provide the action you want to set to the aircraft:");
			good = false;
			String line = scanner.nextLine();
			if (line.equals("wait")) {
				action = Action.WAIT;
				good=true;
			}				
			if (line.equals("move")) {
				action = Action.MOVE;
				good=true;
			}				
			if (line.equals("start")) {
				action = Action.START;
				good=true;
			}
					
		}
		return action;
	}
}
