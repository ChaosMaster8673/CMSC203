/**
 * Class: CMSC203
 * Instructor: Professor Grinberg
 * Description: Represents a property management company. Uses aggregation
 * to hold a fixed-size array of Property objects. Supports adding
 * properties (stored as copies), computing total rent, counting
 * properties, and a deep copy constructor.
 * Due: 10/26/2026
 * Platform/compiler: Java 26, Eclipse IDE
 * I pledge that I have completed the programming
 * assignment independently. I have not copied the code
 * from a student or any source. I have not given my code
 * to any student.
 *   Print your Name here: Jacen Cheskin
 */
public class ManagementCompany {
	
	public final static int MAX_PROPERTIES = 5; //caps array size
	private int propertyCount;
	private String name;
	private String taxId;
	private Property[] properties;
	
	/**
	 *  Constructor for ManagementCompany
	 * @param name
	 * @param taxId
	 * makes the properties array with a size of MAX_PROPERTIES, sets name and taxId to the provided values, and initializes propertyCount to 0
	 */
	public ManagementCompany(String name, String taxId) {
		this.name = name;
		this.taxId = taxId;
		this.properties = new Property[MAX_PROPERTIES];
		propertyCount = 0;
	}
	
	/**
	 * deep copy constructor
	 * @param other
	 * sets name, taxId, and propertyCount to the values of the provided ManagementCompany object, and creates a new properties array with copies of the Property objects
	 */
	public ManagementCompany(ManagementCompany other) {
		this.name = other.name;
		this.taxId = other.taxId;
		this.propertyCount = other.propertyCount;
		this.properties = new Property[MAX_PROPERTIES];
		for (int i = 0; i < other.propertyCount; i++) {
			this.properties[i] = new Property(other.properties[i]);
		}
	}
	
	
	/**
	 *  storing the object directly would let the Property object be modified outside the class so it must be copied.
	 * @param property
	 * @return
	 * returns -1 if the array is full, otherwise adds a copy of the provided Property object to the properties array and returns the index where it was added
	 * 
	 */
	public int addProperty(Property property) {
		if (propertyCount == MAX_PROPERTIES) {
			return -1; // array is full, signal the error
		} else {
			Property pCopy = new Property(property); // create a copy of the property object
			properties[propertyCount] = pCopy; // store the copy in the array
			propertyCount++;
			return propertyCount - 1; // return the index where the property was added
		}
	}
	
	/**
	 * computes the total rent of all properties in the properties array
	 * @return
	 */
	public double totalRent() {
		double sum = 0;
		for (int i = 0; i < propertyCount; i++) {
			sum += properties[i].getRentAmount();
		}
		return sum;
	}
	
	
	//getters
	
	// propertyCount getter. returns propertyCount
	public int getPropertyCount() {
		return propertyCount;
	}
	
	//get property at index
	public Property getProperty(int index) {
		return properties[index];
	}
	
	//return name
	public String getName() {
		return name;
	}
	
	//reutrn taxId
	public String getTaxId() {
		return taxId;
	}
	
	/**to string
	 * returns a string representation of the ManagementCompany object, including its name, total rent, tax ID, and a list of all properties with their details
	 */
	@Override
	public String toString() {
		String result = "Management Company: " + name + " (Tax ID: " + taxId + ")\n" 
	                  + "Properties:\n";
		for (int i = 0; i < propertyCount; i++) {
			result += "  " + i + ": " + properties[i].toString() + "\n";
		}
		result += "Total Rent: $" + String.format("%.2f", totalRent());
		
		return result;
	}
	
}
