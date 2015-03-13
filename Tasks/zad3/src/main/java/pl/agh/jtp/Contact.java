package pl.agh.jtp;

/** 	@author Zbigniew Krolikowski 
  	@version 1.0
*/

/**
	The basic datatype in the whole program, has couple of private fields and methods for CSV use.
*/ 
public class Contact {
	private	String firstName;
	private String lastName;
	private String phone;	
	private String email;
/*	Default contructor
@param firstName The first name. Nothing more to say
@param lastName Last name, that's all
@param phone Phone number
@param email Email adress at some domain	
*/
	public Contact(String firstName, String lastName, String phone, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
	}

	public String getNames (){
		return (firstName + " " + lastName); 	
	}
	public String toCSV (){
		return (firstName + "," + lastName + "," + phone + "," + email);	
	}
}

