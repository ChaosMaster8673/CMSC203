/**
 * Procedure class represents a medical procedure with its details.
 * It includes attributes such as name, date, practitioner, and charges.
 * The class provides constructors, getters, setters, and additional methods
 * to manipulate and retrieve information about the procedure.
 *
 * Course: CMSC203 CRN 21305
 *  * Professor Grinberg
 *  * Due Date: 09/28/2026
 *  * Platform/Compiler: Windows 11, javac
 *  *
 *  * Integrity Pledge: I pledge that I have completed the programming
 *  * assignment independently. I have not copied the code from a student
 *  * or any source.
 *  *
 *  * @author Jacen Cheskin
 *  * @version 1.0
 */

public class Procedure {

    //initialize variables
    private String name;
    private String date;
    private String practitioner;
    private double charges;

    //constructors
    //no argument constructor
    public Procedure() {
        this.name = "";
        this.date = "";
        this.practitioner = "";
        this.charges = 0.0;
    }

    // constructor with name and date
    public Procedure(String name, String date) {
        this.name = name;
        this.date = date;
        this.practitioner = "";
        this.charges = 0.0;
    }

    // constructor with all variables
    public Procedure(String name, String date, String practitioner, double charges) {
        this.name = name;
        this.date = date;
        this.practitioner = practitioner;
        this.charges = charges;
    }

    //getters and setters
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(String practitioner) {
        this.practitioner = practitioner;
    }

    public double getCharges() {
        return charges;
    }

    public void setCharges(double charges) {
        this.charges = charges;
    }

    //toString method
    public String toString() {
        return "Procedure Name: " + name + "\nDate: " + date + "\nPractitioner: " + practitioner + "\nCharges: $" + charges;
    }

    //additional methods

    //expensive procedure method
    public boolean isExpensiveProcedure() {
        return charges >= 1000.00;
    }

    //apply discount method
    public void applyDiscount(double percentage) {
        if (percentage < 0 || percentage > 100) {
            //illegal percentage
            System.out.println("Invalid percentage");
            return;
        }
        charges = charges - (charges * (percentage / 100));
    }

    //get charge category method
    public String getChargeCategory() {
        if (charges <= 250.00) {
            return "Low";
        } else if (charges > 250.00 && charges <= 1000.00) {
            return "Medium";
        } else {
            return "High";
        }
    }

    //is performed by practitioner method name

    public boolean isPerformedBy(String practitionerName) {
        return practitioner.equalsIgnoreCase(practitionerName);
    }

    //get formatted charges method and separate by commas every 3 digits
    public String getFormattedCharge() {
        return String.format("$%,.2f", charges);
    }

}
