/**
 * Class: CMSC203
 * Instructor: Professor Grinberg
 * Description: Represents a single rental property with a name, city,
 * monthly rent, and owner. Provides default, parameterized, and copy
 * constructors, getters and setters for all fields, and a formatted
 * toString method.
 * Due: 10/26/2026
 * Platform/compiler: Java 26, Eclipse IDE
 * I pledge that I have completed the programming
 * assignment independently. I have not copied the code
 * from a student or any source. I have not given my code
 * to any student.
 *   Print your Name here: Jacen Cheskin
 */
public class Property {
	private String propertyName;
	private String city;
	private double rentAmount;
	private String owner;
	
	//contructors
	
	/**
	 * no args constructor
	 * creates a Property object with default values, either empty strings or 0.0 for rentAmount
	 */
	public Property() {
		this.propertyName = "";
		this.city = "";
		this.rentAmount = 0.0;
		this.owner = "";
	}
	
	/**
	 * parameterized constructor
	 * @param propertyName
	 * @param city
	 * @param rentAmount
	 * @param owner
	 * stores the provided values in the corresponding fields of the Property object
	 */
	public Property(String propertyName, String city, double rentAmount, String owner) {
		this.propertyName = propertyName;
		this.city = city;
		this.rentAmount = rentAmount;
		this.owner = owner;
	}
	
	/**
	 * copy constructor
	 * @param other
	 * 
	 * create a new Property object that is a copy of the provided Property object, copying all field values
	 */
	public Property(Property other) {
		this.propertyName = other.propertyName;
		this.city = other.city;
		this.rentAmount = other.rentAmount;
		this.owner = other.owner;
	}
	
	//getters and setters
	
	//returns the property name
	public String getPropertyName() {
		return propertyName;
	}
	
	//sets the property name
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	//returns the city
	public String getCity() {
		return city;
	}
	
	//sets the city
	public void setCity(String city) {
		this.city = city;
	}
	
	//returns the rent amount
	public double getRentAmount() {
		return rentAmount;
	}
	
	//sets the rent amount
	public void setRentAmount(double rentAmount) {
		this.rentAmount = rentAmount;
	}
	
	//returns the owner
	public String getOwner() {
		return owner;
	}
	
	//sets the owner
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	//
	//toString method
	/**
	 * returns a formatted string  of the Property object, including property name, city, rent amount, and owner
	 */
	@Override
	public String toString() {
		return String.format("%s, %s, Rent: $%.2f, Owner: %s", propertyName, city, rentAmount, owner);
	}
	
}
